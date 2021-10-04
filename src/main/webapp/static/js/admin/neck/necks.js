$(document).ready( function () {
    let bodyTag = $('body');
    let idData = bodyTag.data('id');
    let nameData = bodyTag.data('name');
    let woodData = bodyTag.data('wood');
    let fretboardWoodData = bodyTag.data('fretboard-wood');
    let searchData = bodyTag.data('search');
    let editData = bodyTag.data('edit');
    let deleteData = bodyTag.data('delete');
    let createData = bodyTag.data('create');

    let footer = $('footer');
    let locale = footer.data('locale');
    localeChange($('#localeSelect'), locale);

    let jsonUrl;
    if (locale === 'en_US') {
        jsonUrl = 'https://cdn.datatables.net/plug-ins/1.11.1/i18n/en-gb.json'
    } else if (locale === 'ru_RU') {
        jsonUrl = 'https://cdn.datatables.net/plug-ins/1.11.1/i18n/ru.json'
    }

    let table = $('#necks_table').DataTable( {
        language: {
            jsonUrl
        },
        dom: '<"toolbar">tipr',
            processing: true,
            serverSide: true,
            ordering: false,
            ajax: {
            url: '/controller?command=get_necks',
                data: function (data) {
                data.filterCriteria = $('#searchCriteria').val();
                data.requestType = 'DATATABLE';
            }
        },
        drawCallback: function () { onDataLoaded(table); },
        columns: [
            { data: 'entityId'},
            { data: 'name'},
            {
                data: null,
                render: function (row) {
                    return '<a href="/controller?command=go_to_edit_wood_page&id=' + row.woodId + '" class="text-decoration-none"></a>'
                }
            },
            {
                data: null,
                render: function (row) {
                    return '<a href="/controller?command=go_to_edit_wood_page&id=' + row.woodId + '" class="text-decoration-none"></a>'
                }
            },
            {
                data: null,
                render: function (row) {
                    return '<a href="/controller?command=go_to_edit_neck_page&id=' + row.entityId + '" type="button" class="btn btn-outline-primary me-1">'
                        + editData + '</a>'
                        + '<a href="/controller?command=delete_neck&id=' + row.entityId + '" type="button" class="btn btn-outline-primary me-1">'
                        + deleteData + '</a>'
                }
            },
        ],
        initComplete: function () {
            onDataTableInitComplete(table);
        }
    });

    function onDataTableInitComplete(table) {
        $("div.toolbar").html(`
            <div class="input-group input-group-sm mb-2">
            <button id="createButton" type="button" class="btn btn-secondary">
                ${createData}
            </button>
            <select id="searchCriteria" class="form-select input-sm">
                <option value="ID">${idData}</option>
                <option value="NAME">${nameData}</option>
                <option value="WOOD_ID">${woodData}</option>
                <option value="FRETBOARD_WOOD_ID">${fretboardWoodData}</option>
            </select>
            <input id="searchInput" maxlength="50" type="text" class="form-control w-50"
             placeholder=${searchData}>
             <select id="searchSelect" class="form-select w-50"></select>
            </div>
        `);

        let searchInput = $('#searchInput');
        let searchCriteria = $('#searchCriteria');
        let searchSelect = $('#searchSelect');

        searchSelect.hide();

        $('#createButton').click(function () {
            window.location.href = "/controller?command=go_to_create_neck_page";
        });

        searchInput.keyup(function () {
            table.search(searchInput.val().trim()).draw();
        });

        searchCriteria.change(function () {
            searchInput.val('');
            searchSelect.html('');
            if (searchCriteria.val() === 'WOOD_ID') {
                searchInput.hide();
                searchSelect.show();
                searchSelect.select2({
                    language: locale.substring(0, 2),
                    placeholder: woodData,
                    theme: 'bootstrap',
                    width: '71%',
                    maximumInputLength: 30,
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
                table.search(searchInput.val()).draw();
            } else if (searchCriteria.val() === 'FRETBOARD_WOOD_ID') {
                searchInput.hide();
                searchSelect.show();
                searchSelect.select2({
                    language: locale.substring(0, 2),
                    placeholder: fretboardWoodData,
                    theme: 'bootstrap',
                    width: '71%',
                    maximumInputLength: 30,
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
                table.search(searchInput.val()).draw();
            } else {
                table.search(searchInput.val()).draw();
                searchInput.show();
                searchSelect.hide();
                searchSelect.select2('destroy');
            }
        });

        searchSelect.change(function () {
            table.search(searchSelect.val()).draw();
        });

        searchSelect.on('select2:select', function () {
            let searchValue = $(this).val();
            table.search(searchValue).draw();
        });
    }

    function onDataLoaded(table) {
        table.rows().data().each(function (value, index) {
            fetchWood(value.woodId, function (entity) {
                let woodName = entity.name;
                let cell = table.cell(index, 2).node();
                $(cell).find('a').text(woodName);
            });
        });

        table.rows().data().each(function (value, index) {
            fetchWood(value.fretboardWoodId, function (entity) {
                let woodName = entity.name;
                let cell = table.cell(index, 3).node();
                $(cell).find('a').text(woodName);
            });
        });
    }
} );