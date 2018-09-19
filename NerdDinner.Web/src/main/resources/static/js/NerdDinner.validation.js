(function (ND) {
    ND.submitForm = function (button) {
        var $form = $(button).closest('form');
        var $summaryContainer = $form.find(".summaryContainer");
        $summaryContainer.empty();
        $summaryContainer.css({
            display: "none"
        });
        var validator = $form.validate();
        if (!$form.valid()) {
            validator.showErrors();
            return;
        }
        var $inputs = $form.find('input, textarea, select');
        var jsonData = ND.createJsonObjectFromInputs($inputs);
        var successFunc = function (result) {
            if (result.isValid) {
                ND.performRedirect(result.successUrl, $form.data('success-url'));
            } else {
                ND.mapServerErrorsToInputs(result, validator, $inputs, $summaryContainer);
            }
        };
        ND.performJsonPost($form[0].action, jsonData, successFunc);
    };

    ND.performRedirect = function (serverSuccessUrl, formSuccessUrl) {
        if (serverSuccessUrl != null && serverSuccessUrl != "") {
            window.location.href = serverSuccessUrl;
        }
        else if (formSuccessUrl != null && formSuccessUrl != "") {
            window.location.href = formSuccessUrl;
        } else {
            throw 'No valid redirect url found';
        }
    };

    ND.mapServerErrorsToInputs = function (result, validator, $inputs, $summaryContainer) {
        var matchedToInputs = _.filter(result.validationErrors, function (ve) {
            var matchingInput = _.find($inputs, function (input) {
                return input.name == ve.modelPath;
            });
            return matchingInput != null;
        });
        var keys = _.map(matchedToInputs, function (ve) {
            return ve.modelPath;
        });
        var values = _.map(matchedToInputs, function (ve) {
            return ve.errorMessage;
        });
        var errors = _.object(keys, values);
        validator.showErrors(errors);

        var notMatchedToInputs = _.filter(result.validationErrors, function (ve) {
            return !_.contains(matchedToInputs, ve);
        });
        if (notMatchedToInputs.length > 0) {
            $summaryContainer.css({
                display: "block"
            });
            _.each(notMatchedToInputs, function (ve) {
                var $label = $('<div></div>')
                    .text(ve.errorMessage)
                    .attr('id', ve.modelPath);
                $summaryContainer.append($label)
            });
        }
    };

    ND.performJsonPost = function (url, jsonData, successFunc) {
        $.post({
            url: url,
            data: JSON.stringify(jsonData),
            contentType: 'application/json',
            dataType: 'json',
            success: successFunc,
            error: function (result) {
                debugger;
            }
        })

    };

    ND.createJsonObjectFromInputs = function ($inputs) {
        var jsonData = {};
        _.each($inputs, function (input) {
            var currentObject = jsonData;
            var paths = input.name.split('.');
            while (paths.length != 0) {
                var nextPath = paths.splice(0, 1);
                var shouldAssignValue = paths.length == 0;
                var arrayPath = /(\w*)\[(\d*)\]/.exec(nextPath);
                var mapPath = /(\w*)\[(.*)\]/.exec(nextPath);

                if (arrayPath) {
                    var propertyName = arrayPath[1];
                    var arrayIndex = parseInt(arrayPath[2]);
                    var arrayObject = currentObject[propertyName] === undefined ?
                        currentObject[propertyName] = [] :
                        currentObject[propertyName];
                    if (shouldAssignValue) {
                        arrayObject[arrayIndex] = input.value;
                    } else {
                        var objectAtArrayIndex = arrayObject[arrayIndex] === undefined ?
                            arrayObject[arrayIndex] = {} :
                            arrayObject[arrayIndex];
                        currentObject = objectAtArrayIndex;
                    }
                }
                else if (mapPath) {
                    var propertyName = mapPath[1];
                    var key = mapPath[2];
                    var mapObject = currentObject[propertyName] === undefined ?
                        currentObject[propertyName] = {} :
                        currentObject[propertyName];
                    if (shouldAssignValue) {
                        mapObject[key] = input.value;
                    } else {
                        var objectAtArrayIndex = mapObject[key] === undefined ?
                            mapObject[key] = {} :
                            mapObject[key];
                        currentObject = objectAtArrayIndex;
                    }
                }
                else if (shouldAssignValue) {
                    currentObject[nextPath] = input.value;
                }
                else {
                    currentObject =
                        currentObject[nextPath] == undefined ?
                            currentObject[nextPath] = {} :
                            currentObject[nextPath];
                }
            }
        });
        return jsonData;
    };

    ND.initializeForm = function (options) {
        var rules = options.rules || {};
        if (options.addHandlers) {
            _.each(options.addHandlers, function (opts) {
                opts.$button.on('click', function () {
                    ND.createElement(opts);
                });
            })
        }
        if (options.removeHandlers) {
            _.each(options.removeHandlers, function (opts) {
                $(options.form).on('click', opts.deleteButtonSelector, function () {
                    var $toRemove = $(this).closest(opts.closestSelector);
                    $toRemove.remove();
                });
            });
        }
        return $(options.form).validate(rules);
    };

    ND.createElement = function (opts) {
        debugger;
        var template = opts.$template[0].content.cloneNode(true);
        var key = ND.generateUuid();
        var elementsInTemplate = $(template).find("*");
        elementsInTemplate.each(function (index, domElement) {
            var $e = $(domElement);
            var existingName = $e.attr('name');
            if (existingName == null || existingName.indexOf("{key}") == -1) {
                return;
            }
            var newName = existingName.replace('{key}', key);
            $e.attr('name', newName);
        });
        opts.$container.append(template);
        elementsInTemplate.each(function (index, domElement) {
            var $e = $(domElement);
            var validationName = $e.data('validation-name');
            if (validationName) {
                var rulesForNewElement = $.extend({}, opts.templateRules.rules[validationName], true);
                rulesForNewElement.messages = $.extend({}, opts.templateRules.messages[validationName], true);
                $e.rules("add", rulesForNewElement);
            }
        });

    };


    ND.generateUuid = function () {
        var chars = '0123456789abcdef'.split('');
        var uuid = [], rnd = Math.random, r;
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4'; // version 4
        var x = 36;
        for (var i = 0; i < x; i++) {
            if (!uuid[i]) {
                r = 0 | rnd() * 16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r & 0xf];
            }
        }
        return uuid.join('');
    };

    ND.initializeValidation = function (errorClass) {
        jQuery.validator.setDefaults({
            errorClass: errorClass,
            highlight: function (element, errorClass) {
                //Override default behavior of setting errorClass to input element
            }
        });
        $.validator.addMethod(
            "regex",
            function (value, element, regexp) {
                var re = new RegExp(regexp);
                return this.optional(element) || re.test(value);
            },
            "Please check your input."
        );
    }
})(window.NerdDinner = window.NerdDinner || {});