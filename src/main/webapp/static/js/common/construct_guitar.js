$(document).ready( function () {
    let bodyTag = $('body');
    let body = bodyTag.data('body');
    let neck = bodyTag.data('neck');
    let pickup = bodyTag.data('pickup');

    let footer = $('footer');
    let locale = footer.data('locale');
    localeChange($('#localeSelect'), locale);

    $('#bodySelect').select2({
        language: locale.substring(0, 2),
        placeholder: body,
        // theme: 'bootstrap',
        width: '10%',
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
                    filterCriteria: 'NAME_AND_WOOD'
                }
            },
            processResults: function (data, params) {
                data = JSON.parse(data);
                let mappedData = $.map(data.results, function (item) {
                    item.id = item.entityId;
                    let woodName;
                    fetchWood(item.woodId, function (entity) {
                        woodName = entity.name + ' ';
                    }, false);
                    item.text = woodName + item.name;
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
        placeholder: neck,
        // theme: 'bootstrap',
        width: '10%',
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
                    filterCriteria: 'NAME_AND_WOOD'
                }
            },
            processResults: function (data, params) {
                data = JSON.parse(data);
                let mappedData = $.map(data.results, function (item) {
                    item.id = item.entityId;
                    let woodName;
                    let fretboardWoodName;
                    fetchWood(item.woodId, function (entity) {
                        woodName = entity.name + ' ';
                    }, false);
                    fetchWood(item.fretboardWoodId, function (entity) {
                        fretboardWoodName = ' ' + entity.name;
                    }, false);
                    item.text = woodName + item.name + fretboardWoodName + ' fretboard';
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
        placeholder: pickup,
        // theme: 'bootstrap',
        width: '10%',
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
});