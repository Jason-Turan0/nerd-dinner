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
        var $inputs = $form.find('input, textarea');

        var jsonData = {};
        _.each($inputs, function (input) {
            var currentObject = jsonData;
            var paths = input.name.split('.');
            while (paths.length != 0) {
                var nextPath = paths.splice(0, 1);
                var shouldAssignValue = paths.length == 0;
                var arrayPath = /(\w*)\[(\d*)\]/.exec(nextPath);
                if (arrayPath) {
                    var propertyName = arrayPath[1];
                    var arrayIndex = parseInt(arrayPath[2]);
                    var arrayObject = currentObject[propertyName] == undefined ?
                        currentObject[propertyName] = [] :
                        currentObject[propertyName];
                    if (shouldAssignValue) {
                        arrayObject[arrayIndex] = input.value;
                    } else {
                        var objectAtArrayIndex = arrayObject[arrayIndex] == undefined ?
                            arrayObject[arrayIndex] = {} :
                            arrayObject[arrayIndex];
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

        $.post({
            url: $form[0].action,
            data: JSON.stringify(jsonData),
            contentType: 'application/json',
            dataType: 'json',
            success: function (result) {
                if (result.isValid) {
                    if (result.successUrl != null && result.successUrl != "") {
                        window.location.href = result.successUrl;
                    }
                    else {
                        var successUrl = $form.data('success-url');
                        if (successUrl != null && successUrl != "") {
                            window.location.href = successUrl;
                        }
                    }
                } else {
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

                }
            },
            error: function (result) {
                debugger;
            }
        })
    };

    ND.initilizeForm = function (form, rules) {
        return $(form).validate(rules);
    };

    ND.initializeValidation = function (errorClass) {
        jQuery.validator.setDefaults({
            errorClass: errorClass,
            highlight: function (element, errorClass) {
                //Override default behavior of setting errorClass to input element
            }
        });
    }
})(window.NerdDinner = window.NerdDinner || {});