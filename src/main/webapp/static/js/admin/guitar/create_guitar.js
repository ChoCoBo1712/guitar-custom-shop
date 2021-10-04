$(document).ready( function () {
    let bodyTag = $('body');
    let bodyData = bodyTag.data('body');
    let neckData = bodyTag.data('neck');
    let pickupData = bodyTag.data('pickup');
    let userData = bodyTag.data('user');

    let footer = $('footer');
    let locale = footer.data('locale');
    localeChange($('#localeSelect'), locale);

    $('#bodySelect').select2({
        language: locale.substring(0, 2),
        placeholder: bodyData,
        theme: 'bootstrap',
        width: '100%',
        maximumInputLength: 50,
        ajax: {
            delay: 250,
            url: '/controller?command=get_bodies',
            data: function (params) {
                return {
                    term: params.term || '',
                    page: params.page || 1,
                    pageSize: 10,
                    requestType: 'SELECT',
                    filterCriteria: 'NAME'
                }
            },
            processResults: function (data, params) {
                data = JSON.parse(data);
                let mappedData = $.map(data.results, function (item) {
                    item.id = item.entityId;
                    item.text = item.name;
                    return item;
                });
                params.page = params.page || 1;

                return {
                    results: mappedData,
                    pagination: {
                        more: data.paginationMore
                    }
                }
            }
        }
    });

    $('#neckSelect').select2({
        language: locale.substring(0, 2),
        placeholder: neckData,
        theme: 'bootstrap',
        width: '100%',
        maximumInputLength: 50,
        ajax: {
            delay: 250,
            url: '/controller?command=get_necks',
            data: function (params) {
                return {
                    term: params.term || '',
                    page: params.page || 1,
                    pageSize: 10,
                    requestType: 'SELECT',
                    filterCriteria: 'NAME'
                }
            },
            processResults: function (data, params) {
                data = JSON.parse(data);
                let mappedData = $.map(data.results, function (item) {
                    item.id = item.entityId;
                    item.text = item.name;
                    return item;
                });
                params.page = params.page || 1;

                return {
                    results: mappedData,
                    pagination: {
                        more: data.paginationMore
                    }
                }
            }
        }
    });

    $('#pickupSelect').select2({
        language: locale.substring(0, 2),
        placeholder: pickupData,
        theme: 'bootstrap',
        width: '100%',
        maximumInputLength: 50,
        ajax: {
            delay: 250,
            url: '/controller?command=get_pickups',
            data: function (params) {
                return {
                    term: params.term || '',
                    page: params.page || 1,
                    pageSize: 10,
                    requestType: 'SELECT',
                    filterCriteria: 'NAME'
                }
            },
            processResults: function (data, params) {
                data = JSON.parse(data);
                let mappedData = $.map(data.results, function (item) {
                    item.id = item.entityId;
                    item.text = item.name;
                    return item;
                });
                params.page = params.page || 1;

                return {
                    results: mappedData,
                    pagination: {
                        more: data.paginationMore
                    }
                }
            }
        }
    });

    $('#userSelect').select2({
        language: locale.substring(0, 2),
        placeholder: userData,
        theme: 'bootstrap',
        width: '100%',
        maximumInputLength: 50,
        ajax: {
            delay: 250,
            url: '/controller?command=get_users',
            data: function (params) {
                return {
                    term: params.term || '',
                    page: params.page || 1,
                    pageSize: 10,
                    requestType: 'SELECT',
                    filterCriteria: 'LOGIN'
                }
            },
            processResults: function (data, params) {
                data = JSON.parse(data);
                let mappedData = $.map(data.results, function (item) {
                    item.id = item.entityId;
                    item.text = item.login;
                    return item;
                });
                params.page = params.page || 1;

                return {
                    results: mappedData,
                    pagination: {
                        more: data.paginationMore
                    }
                }
            }
        }
    });

    let form = $('#create_guitar_form');
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