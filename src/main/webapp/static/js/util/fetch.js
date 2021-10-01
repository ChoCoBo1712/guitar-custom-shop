function fetchGuitar(id, callback, async = true) {
    $.ajax({
        method: 'GET',
        url: '/controller?command=get_guitars',
        data: {
            id: id,
            requestType: 'FETCH'
        },
        async: async,
        success: function (response) {
            let data = JSON.parse(response);

            if (data) {
                callback(data.entity);
            }
        }
    });
}

function fetchBody(id, callback, async = true) {
    $.ajax({
        method: 'GET',
        url: '/controller?command=get_bodies',
        data: {
            id: id,
            requestType: 'FETCH'
        },
        async: async,
        success: function (response) {
            let data = JSON.parse(response);

            if (data) {
                callback(data.entity);
            }
        }
    });
}

function fetchNeck(id, callback, async = true) {
    $.ajax({
        method: 'GET',
        url: '/controller?command=get_necks',
        data: {
            id: id,
            requestType: 'FETCH'
        },
        async: async,
        success: function (response) {
            let data = JSON.parse(response);

            if (data) {
                callback(data.entity);
            }
        }
    });
}

function fetchPickup(id, callback, async = true) {
    $.ajax({
        method: 'GET',
        url: '/controller?command=get_pickups',
        data: {
            id: id,
            requestType: 'FETCH'
        },
        async: async,
        success: function (response) {
            let data = JSON.parse(response);

            if (data) {
                callback(data.entity);
            }
        }
    });
}

function fetchWood(id, callback, async = true) {
    $.ajax({
        method: 'GET',
        url: '/controller?command=get_woods',
        data: {
            id: id,
            requestType: 'FETCH'
        },
        async: async,
        success: function (response) {
            let data = JSON.parse(response);

            if (data) {
                callback(data.entity);
            }
        }
    });
}

function fetchUser(id, callback, async = true) {
    $.ajax({
        method: 'GET',
        url: '/controller?command=get_users',
        data: {
            id: id,
            requestType: 'FETCH'
        },
        async: async,
        success: function (response) {
            let data = JSON.parse(response);

            if (data) {
                callback(data.entity);
            }
        }
    });
}