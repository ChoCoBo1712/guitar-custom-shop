function localeChange(localeSelect, locale) {
    localeSelect.val(locale);
    localeSelect.change( function () {
        $.cookie('locale', localeSelect.val(), {expires: 7});
        location.reload();
    })
}