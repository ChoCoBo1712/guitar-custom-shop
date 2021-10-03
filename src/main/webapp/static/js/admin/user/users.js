$(document).ready( function () {
    let bodyTag = $('body');
    let idData = bodyTag.data('id');
    let emailData = bodyTag.data('email');
    let loginData = bodyTag.data('login');
    let roleData = bodyTag.data('role');
    let statusData = bodyTag.data('status');
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

    let table = $('#users_table').DataTable( {
        language: {
            jsonUrl
        },
        dom: '<"toolbar">tipr',
            processing: true,
            serverSide: true,
            ordering: false,
            ajax: {
            url: '/controller?command=get_users',
                data: function (data) {
                data.filterCriteria = $('#searchCriteria').val();
                data.requestType = 'DATATABLE';
            }
        },
        columns: [
            { data: 'entityId'},
            { data: 'email'},
            { data: 'login'},
            { data: 'role'},
            { data: 'status'},
            {
                data: null,
                render: function (row) {
                    return '<a href="/controller?command=go_to_edit_user_page&id=' + row.entityId + '">'
                        + editData + '</a>'
                        + '<br>'
                        + '<a href="/controller?command=delete_user&id=' + row.entityId + '">'
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
                <div class="input-group mb-3">
                <button id="createButton" type="button" class="btn btn-secondary">
                    ${createData}
                </button>
                <select id="searchCriteria" class="form-select">
                    <option value="ID">${idData}</option>
                    <option value="EMAIL">${emailData}</option>
                    <option value="LOGIN">${loginData}</option>
                    <option value="ROLE">${roleData}</option>
                    <option value="STATUS">${statusData}</option>
                </select>
                <input id="searchInput" maxlength="50" type="text" class="form-control w-50"
                 placeholder=${searchData}>
                 <select id="searchSelect"></select>
                </div>
            `);

        let searchInput = $('#searchInput');
        let searchCriteria = $('#searchCriteria');
        let searchSelect = $('#searchSelect');

        searchSelect.hide();

        $('#createButton').click(function () {
            window.location.href = "/controller?command=go_to_create_user_page";
        });

        searchInput.keyup(function () {
            table.search(searchInput.val().trim()).draw();
        });

        searchCriteria.change(function () {
            if (searchCriteria.val() === 'ROLE') {
                searchInput.hide();
                searchSelect.show();
                searchSelect.html("")
                    .append($("<option></option>").attr("value", "").text("None"))
                    .append($("<option></option>").attr("value", "ADMIN").text("ADMIN"))
                    .append($("<option></option>").attr("value", "CLIENT").text("CLIENT"))
                    .append($("<option></option>").attr("value", "MASTER").text("MASTER"))
                searchSelect.change();
            } else if (searchCriteria.val() === 'STATUS') {
                searchInput.hide();
                searchSelect.show();
                searchSelect.html("")
                    .append($("<option></option>").attr("value", "").text("None"))
                    .append($("<option></option>").attr("value", "NOT_CONFIRMED").text("NOT_CONFIRMED"))
                    .append($("<option></option>").attr("value", "CONFIRMED").text("CONFIRMED"))
                searchSelect.change();
            } else {
                searchInput.val("");
                searchInput.show();
                searchSelect.hide();
                table.search(searchInput.val()).draw();
            }
        });

        searchSelect.change(function () {
            table.search(searchSelect.val()).draw();
        });
    }
});