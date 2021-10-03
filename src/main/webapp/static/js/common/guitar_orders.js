$(document).ready( function () {
    let bodyTag = $('body');
    let takeOrder = bodyTag.data('take-order');
    let finishOrder = bodyTag.data('finish-order');
    let name = bodyTag.data('name');
    let body = bodyTag.data('body');
    let neck = bodyTag.data('neck');
    let pickup = bodyTag.data('pickup');
    let neckJoint = bodyTag.data('neck-joint');
    let color = bodyTag.data('color');
    let user = bodyTag.data('user');
    let orderStatus = bodyTag.data('order-status');
    let search = bodyTag.data('search');

    let footer = $('footer');
    let locale = footer.data('locale');
    localeChange($('#localeSelect'), locale);

    let jsonUrl;
    if (locale === 'en_US') {
        jsonUrl = 'https://cdn.datatables.net/plug-ins/1.11.1/i18n/en-gb.json'
    } else if (locale === 'ru_RU') {
        jsonUrl = 'https://cdn.datatables.net/plug-ins/1.11.1/i18n/ru.json'
    }

    let table = $('#orders_table').DataTable( {
        language: {
            url: jsonUrl
        },
        dom: '<"toolbar">tipr',
            processing: true,
            serverSide: true,
            ordering: false,
            ajax: {
            url: '/controller?command=get_guitars',
                data: function (data) {
                data.filterCriteria = $('#searchCriteria').val();
                data.requestType = 'DATATABLE';
                data.activeOrder = true;
            }
        },
        drawCallback: function () { onDataLoaded(table); },
        columns: [
            { data: 'name'},
            {
                data: null,
                render: function (row) {
                    return '<p></p>'
                }
            },
            {
                data: null,
                render: function (row) {
                    return '<p></p>'
                }
            },
            {
                data: null,
                render: function (row) {
                    return '<p></p>'
                }
            },
            { data: 'neckJoint'},
            { data: 'color'},
            {
                data: null,
                render: function (row) {
                    return '<p></p>'
                }
            },
            { data: 'orderStatus'},
            {
                data: null,
                render: function (row) {
                    if (row.orderStatus === "ORDERED") {
                        return '<a href="/controller?command=take_order&id=' + row.entityId + '">'
                            + takeOrder + '</a>'
                    } else {
                        return '<a href="/controller?command=go_to_finish_order_page&id=' + row.entityId + '">'
                            + finishOrder + '</a>'
                    }
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
            <select id="searchCriteria" class="form-select">
                <option value="NAME">${name}</option>
                <option value="BODY_ID">${body}</option>
                <option value="NECK_ID">${neck}</option>
                <option value="PICKUP_ID">${pickup}</option>
                <option value="NECK_JOINT">${neckJoint}</option>
                <option value="COLOR">${color}</option>
                <option value="USER_ID">${user}</option>
                <option value="ORDER_STATUS">${orderStatus}</option>
            </select>
            <input id="searchInput" maxlength="50" type="text" class="form-control w-50"
             placeholder=${search}>
             <select id="searchSelect"></select>
        </div>
    `);

        let searchInput = $('#searchInput');
        let searchCriteria = $('#searchCriteria');
        let searchSelect = $('#searchSelect');

        searchSelect.hide();

        searchInput.keyup(function () {
            table.search(searchInput.val().trim()).draw();
        });

        searchCriteria.change(function () {
            searchInput.val('');
            searchSelect.html('');
            if (searchCriteria.val() === 'BODY_ID') {
                searchInput.hide();
                searchSelect.show();
                searchSelect.select2({
                    language: locale.substring(0, 2),
                    placeholder: body,
                    // theme: 'bootstrap',
                    width: '10%',
                    maximumInputLength: 30,
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
                table.search(searchInput.val()).draw();
            } else if (searchCriteria.val() === 'NECK_ID') {
                searchInput.hide();
                searchSelect.show();
                searchSelect.select2({
                    language: locale.substring(0, 2),
                    placeholder: neck,
                    // theme: 'bootstrap',
                    width: '10%',
                    maximumInputLength: 30,
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
                table.search(searchInput.val()).draw();
            } else if (searchCriteria.val() === 'PICKUP_ID') {
                searchInput.hide();
                searchSelect.show();
                searchSelect.select2({
                    language: locale.substring(0, 2),
                    placeholder: pickup,
                    // theme: 'bootstrap',
                    width: '10%',
                    maximumInputLength: 30,
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
                table.search(searchInput.val()).draw();
            } else if (searchCriteria.val() === 'USER_ID') {
                searchInput.hide();
                searchSelect.show();
                searchSelect.select2({
                    language: locale.substring(0, 2),
                    placeholder: user,
                    // theme: 'bootstrap',
                    width: '10%',
                    maximumInputLength: 30,
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
                table.search(searchInput.val()).draw();
            } else if (searchCriteria.val() === 'NECK_JOINT') {
                table.search(searchInput.val()).draw();
                searchInput.hide();
                searchSelect.show();
                searchSelect.html('')
                    .append($("<option></option>").attr("value", "").text("None"))
                    .append($("<option></option>").attr("value", "BOLT_ON").text("BOLT_ON"))
                    .append($("<option></option>").attr("value", "SET_NECK").text("SET_NECK"))
                    .append($("<option></option>").attr("value", "NECK_THROUGH").text("NECK_THROUGH"))
                searchSelect.select2('destroy');
            } else if (searchCriteria.val() === 'ORDER_STATUS') {
                table.search(searchInput.val()).draw();
                searchInput.hide();
                searchSelect.show();
                searchSelect.html('')
                    .append($("<option></option>").attr("value", "").text("None"))
                    .append($("<option></option>").attr("value", "ORDERED").text("ORDERED"))
                    .append($("<option></option>").attr("value", "IN_PROGRESS").text("IN_PROGRESS"))
                searchSelect.select2('destroy');
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
            fetchBody(value.bodyId, function (entity) {
                let bodyName = entity.name;
                let woodName;
                fetchWood(entity.woodId, function (entity) {
                    woodName = entity.name + ' ';
                }, false);
                let cell = table.cell(index, 1).node();
                $(cell).find('p').text(woodName + bodyName);
            });
        });

        table.rows().data().each(function (value, index) {
            fetchNeck(value.neckId, function (entity) {
                let neckName = entity.name;
                let woodName;
                let fretboardWoodName;
                fetchWood(entity.woodId, function (entity) {
                    woodName = entity.name + ' ';
                }, false);
                fetchWood(entity.fretboardWoodId, function (entity) {
                    fretboardWoodName = ' ' + entity.name;
                }, false);
                let cell = table.cell(index, 2).node();
                $(cell).find('p').text(woodName + neckName + fretboardWoodName + ' fretboard');
            });
        });

        table.rows().data().each(function (value, index) {
            fetchPickup(value.pickupId, function (entity) {
                let pickupName = entity.name;
                let cell = table.cell(index, 3).node();
                $(cell).find('p').text(pickupName);
            });
        });

        table.rows().data().each(function (value, index) {
            fetchUser(value.userId, function (entity) {
                let userName = entity.login;
                let cell = table.cell(index, 6).node();
                $(cell).find('p').text(userName);
            });
        });
    }
} );