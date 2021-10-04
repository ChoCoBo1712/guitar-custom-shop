$(document).ready( function () {
    let bodyTag = $('body');
    let bodyData = bodyTag.data('body');
    let neckData = bodyTag.data('neck');
    let pickupData = bodyTag.data('pickup');
    let userData = bodyTag.data('user');

    let footer = $('footer');
    let locale = footer.data('locale');
    localeChange($('#localeSelect'), locale);

    let bodyId = bodyTag.data('body-id');
    let neckId = bodyTag.data('neck-id');
    let pickupId = bodyTag.data('pickup-id');
    let userId = bodyTag.data('user-id');
    let bodySelect = $('#bodySelect');
    let neckSelect = $('#neckSelect');
    let pickupSelect = $('#pickupSelect');
    let userSelect = $('#userSelect');

    $('#neckJointSelect').val(bodyTag.data('neck-joint'))
    $('#orderStatusSelect').val(bodyTag.data('order-status'))

    bodySelect.select2({
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

    neckSelect.select2({
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

    pickupSelect.select2({
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

    userSelect.select2({
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

    if (!isNaN(Number.parseInt(bodyId))) {
        fetchBody(bodyId, function (entity) {
            let option = new Option(entity.name, entity.entityId);
            bodySelect.append(option).trigger('change');
        });
    }

    if (!isNaN(Number.parseInt(neckId))) {
        fetchNeck(neckId, function (entity) {
            let option = new Option(entity.name, entity.entityId);
            neckSelect.append(option).trigger('change');
        });
    }

    if (!isNaN(Number.parseInt(pickupId))) {
        fetchPickup(pickupId, function (entity) {
            let option = new Option(entity.name, entity.entityId);
            pickupSelect.append(option).trigger('change');
        });
    }

    if (!isNaN(Number.parseInt(userId))) {
        fetchUser(userId, function (entity) {
            let option = new Option(entity.login, entity.entityId);
            userSelect.append(option).trigger('change');
        });
    }

    let form = $('#edit_guitar_form');
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