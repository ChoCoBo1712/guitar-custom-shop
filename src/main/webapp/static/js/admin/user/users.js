$(document).ready( function () {
    let bodyTag = $('body');
    let idData = bodyTag.data('id');
    let emailData = bodyTag.data('email');
    let loginData = bodyTag.data('login');
    let roleData = bodyTag.data('role');
    let adminData = bodyTag.data('admin');
    let makerData = bodyTag.data('maker');
    let clientData = bodyTag.data('client');
    let statusData = bodyTag.data('status');
    let confirmedData = bodyTag.data('confirmed');
    let notConfirmedData = bodyTag.data('not-confirmed');
    let searchData = bodyTag.data('search');
    let editData = bodyTag.data('edit');
    let deleteData = bodyTag.data('delete');
    let createData = bodyTag.data('create');
    let anyData = bodyTag.data('any');

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
            {
                data: 'role',
                render: function (data) {
                    if (data === "ADMIN") {
                        return "<p>" + adminData + "</p>";
                    }
                    else if (data === "MAKER") {
                        return "<p>" + makerData + "</p>";
                    } else {
                        return "<p>" + clientData + "</p>";
                    }
                }
            },
            {
                data: 'status',
                render: function (data) {
                    if (data === "NOT_CONFIRMED") {
                        return "<p>" + notConfirmedData + "</p>";
                    } else {
                        return "<p>" + confirmedData + "</p>";
                    }
                }
            },
            {
                data: null,
                render: function (row) {
                    return '<a href="/controller?command=go_to_edit_user_page&id=' + row.entityId + '" type="button" class="btn btn-outline-primary me-1">'
                        + editData + '</a>'
                        + '<a href="/controller?command=delete_user&id=' + row.entityId + '" type="button" class="btn btn-outline-primary me-1">'
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
                    <option value="EMAIL">${emailData}</option>
                    <option value="LOGIN">${loginData}</option>
                    <option value="ROLE">${roleData}</option>
                    <option value="STATUS">${statusData}</option>
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
                    .append($("<option></option>").attr("value", "").text(anyData))
                    .append($("<option></option>").attr("value", "ADMIN").text(adminData))
                    .append($("<option></option>").attr("value", "MAKER").text(makerData))
                    .append($("<option></option>").attr("value", "CLIENT").text(clientData))
                searchSelect.change();
            } else if (searchCriteria.val() === 'STATUS') {
                searchInput.hide();
                searchSelect.show();
                searchSelect.html("")
                    .append($("<option></option>").attr("value", "").text(anyData))
                    .append($("<option></option>").attr("value", "NOT_CONFIRMED").text(notConfirmedData))
                    .append($("<option></option>").attr("value", "CONFIRMED").text(confirmedData))
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