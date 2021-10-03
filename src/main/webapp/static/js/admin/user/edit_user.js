$(document).ready( function () {
    let bodyTag = $('body');
    let roleData = bodyTag.data('role');
    let statusData = bodyTag.data('status');

    let footer = $('footer');
    let locale = footer.data('locale');
    localeChange($('#localeSelect'), locale);

    $('#role_select').val(roleData);
    $('#status_select').val(statusData);
});