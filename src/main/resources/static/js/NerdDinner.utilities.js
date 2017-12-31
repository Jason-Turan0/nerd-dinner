(function (ND) {
    ND.setLocale = function (localeId) {
        $.cookie('nerd-locale', localeId);
        location.reload();
    }
})(window.NerdDinner = window.NerdDinner || {});