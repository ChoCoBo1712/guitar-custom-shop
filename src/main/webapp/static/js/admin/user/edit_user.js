$(document).ready( function () {
    let bodyTag = $('body');

    let footer = $('footer');
    let locale = footer.data('locale');
    localeChange($('#localeSelect'), locale);

    $('#role_select').val(bodyTag.data('role'));
    $('#status_select').val(bodyTag.data('status'));
});