$(document).ready( function () {
    let footer = $('footer');
    let locale = footer.data('locale');
    localeChange($('#localeSelect'), locale);

    let form = $('#finish_order_form');
    form.submit( function () {
        let fileInput = $('#file_input');
        let files = fileInput.prop('files');
        for (let i = 0; i < files.length; i++) {
            let type = files[i].type;
            if (type !== "image/jpeg" && type !== "image/png") {
                alert('Invalid file type! Choose .png or .jpeg file.')
                return false;
            }
        }
        return true;
    });
});