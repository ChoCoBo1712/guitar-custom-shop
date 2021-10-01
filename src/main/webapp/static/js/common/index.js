$(document).ready( function () {
    let footer = $('footer');
    let locale = footer.data('locale');
    localeChange($('#localeSelect'), locale);
});