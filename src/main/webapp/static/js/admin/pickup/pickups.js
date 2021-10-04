$(document).ready( function () {
    let bodyTag = $('body');
    let idData = bodyTag.data('id');
    let nameData = bodyTag.data('name');
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

    let table = $('#pickups_table').DataTable( {
        language: {
            jsonUrl
        },
        dom: '<"toolbar">tipr',
            processing: true,
            serverSide: true,
            ordering: false,
            ajax: {
            url: '/controller?command=get_pickups',
                data: function (data) {
                data.filterCriteria = $('#searchCriteria').val();
                data.requestType = 'DATATABLE';
            }
        },
        columns: [
            { data: 'entityId'},
            { data: 'name'},
            {
                data: null,
                render: function (row) {
                    return '<a href="/controller?command=go_to_edit_pickup_page&id=' + row.entityId + '" type="button" class="btn btn-outline-primary me-1">'
                        + editData + '</a>'
                        + '<a href="/controller?command=delete_pickup&id=' + row.entityId + '" type="button" class="btn btn-outline-primary me-1">'
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
            window.location.href = "/controller?command=go_to_create_pickup_page";
        });

        searchInput.keyup(function () {
            table.search(searchInput.val().trim()).draw();
        });

        searchCriteria.change(function () {
            searchInput.val("");
            searchInput.show();
            searchSelect.hide();
            table.search(searchInput.val()).draw();
        });

        searchSelect.change(function () {
            table.search(searchSelect.val()).draw();
        });
    }
});