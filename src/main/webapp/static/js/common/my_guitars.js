$(document).ready( function () {
    let bodyTag = $('body');
    let guitars = bodyTag.data('guitars-string');
    let boltOnData = bodyTag.data('bolt-on');
    let setNeckData = bodyTag.data('set-neck');
    let neckThroughData = bodyTag.data('neck-through');
    let orderedData = bodyTag.data('ordered');
    let inProgressData = bodyTag.data('in-progress');
    let completedData = bodyTag.data('completed');

    let footer = $('footer');
    let locale = footer.data('locale');
    localeChange($('#localeSelect'), locale);

    let guitarSelect = $('#guitarSelect');
    let guitarId;
    let nameInput = $('#nameInput');
    let bodyInput = $('#bodyInput');
    let neckInput = $('#neckInput');
    let pickupInput = $('#pickupInput');
    let neckJointInput = $('#neckJointInput');
    let colorInput = $('#colorInput');
    let orderStatusInput = $('#orderStatusInput');
    let image = $('#image');

    guitarSelect.select2({
        language: locale.substring(0, 2),
        placeholder: guitars,
        theme: 'bootstrap',
        width: '100%',
        maximumInputLength: 50,
        ajax: {
            delay: 250,
            url: '/controller?command=get_guitars',
            data: function (params) {
                return {
                    term: params.term || '',
                    page: params.page || 1,
                    pageSize: 10,
                    requestType: 'SELECT'
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

    guitarSelect.change( function () {
        guitarId = guitarSelect.val();
        fetchGuitar(guitarId, function (guitar) {
            nameInput.val(guitar.name);

            let bodyName;
            fetchBody(guitar.bodyId, function (body) {
                let woodName;
                fetchWood(body.woodId, function (wood) {
                    woodName = wood.name + ' ';
                }, false);
                bodyName = woodName + body.name;
            }, false);
            bodyInput.val(bodyName);

            let neckName;
            fetchNeck(guitar.neckId, function (neck) {
                let woodName;
                fetchWood(neck.woodId, function (wood) {
                    woodName = wood.name + ' ';
                }, false);
                neckName = woodName + neck.name;
            }, false);
            neckInput.val(neckName);

            let pickupName;
            fetchPickup(guitar.pickupId, function (pickup) {
                pickupName = pickup.name;
            }, false);
            pickupInput.val(pickupName);

            if (guitar.neckJoint === 'BOLT_ON') {
                neckJointInput.val(boltOnData);
            } else if (guitar.neckJoint === 'SET_NECK') {
                neckJointInput.val(setNeckData);
            } else {
                neckJointInput.val(neckThroughData);
            }

            if (guitar.orderStatus === 'ORDERED') {
                orderStatusInput.val(orderedData);
            } else if (guitar.orderStatus === 'IN_PROGRESS') {
                orderStatusInput.val(inProgressData);
            } else {
                orderStatusInput.val(completedData);
            }
            colorInput.val(guitar.color);
            image.attr('src', guitar.picturePath);
        });
    });
});