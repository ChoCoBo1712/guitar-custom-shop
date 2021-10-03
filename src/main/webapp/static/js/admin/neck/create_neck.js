$(document).ready( function () {
    let bodyTag = $('body');
    let woodData = bodyTag.data('wood');
    let fretboardWoodData = bodyTag.data('fretboard-wood');

    let footer = $('footer');
    let locale = footer.data('locale');
    localeChange($('#localeSelect'), locale);

    $('#woodSelect').select2({
        language: locale.substring(0, 2),
        placeholder: woodData,
        // theme: 'bootstrap',
        width: '10%',
        maximumInputLength: 50,
        ajax: {
            delay: 250,
            url: '/controller?command=get_woods',
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

    $('#fretboardWoodSelect').select2({
        language: locale.substring(0, 2),
        placeholder: fretboardWoodData,
        // theme: 'bootstrap',
        width: '10%',
        maximumInputLength: 50,
        ajax: {
            delay: 250,
            url: '/controller?command=get_woods',
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