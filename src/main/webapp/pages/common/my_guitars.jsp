<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
  <title><cst:localeTag key="index.title" /></title>
  <jsp:include page="shared/head.html" />

  <!-- jQuery Select2 -->
  <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
  <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
  <c:if test="${sessionScope.locale == 'en_US'}">
    <script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/i18n/en.js"></script>
  </c:if>
  <c:if test="${sessionScope.locale == 'ru_RU'}">
    <script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/i18n/ru.js"></script>
  </c:if>

  <script src="/static/js/util/fetch.js"></script>
</head>
<body>
  <jsp:include page="shared/header.jsp" />

  <select id="guitarSelect"></select>

  <div id="guitar">
    <input id="name" readonly/>
    <br>
    <input id="body" readonly/>
    <br>
    <input id="neck" readonly/>
    <br>
    <input id="pickup" readonly/>
    <br>
    <input id="neckJoint" readonly/>
    <br>
    <input id="color" readonly/>
    <br>
    <input id="orderStatus" readonly/>
    <br>
  </div>

  <script>
    $(document).ready( function () {

      let guitarSelect = $('#guitarSelect');
      let guitarId;
      let name = $('#name');
      let body = $('#body');
      let neck = $('#neck');
      let pickup = $('#pickup');
      let neckJoint = $('#neckJoint');
      let color = $('#color');
      let orderStatus = $('#orderStatus');

      guitarSelect.select2({
        language: '${sessionScope.locale}'.substring(0, 2),
        placeholder: '<cst:localeTag key="admin.guitars" />',
        // theme: 'bootstrap',
        width: '10%',
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
          name.val(guitar.name);

          let bodyName;
          fetchBody(guitar.bodyId, function (body) {
            let woodName;
            fetchWood(body.woodId, function (wood) {
              woodName = wood.name + ' ';
            }, false);
            bodyName = woodName + body.name;
          }, false);
          body.val(bodyName);

          let neckName;
          fetchNeck(guitar.neckId, function (neck) {
            let woodName;
            fetchWood(neck.woodId, function (wood) {
              woodName = wood.name + ' ';
            }, false);
            neckName = woodName + neck.name;
          }, false);
          neck.val(neckName);

          let pickupName;
          fetchPickup(guitar.pickupId, function (pickup) {
            pickupName = pickup.name;
          }, false);
          pickup.val(pickupName);

          neckJoint.val(guitar.neckJoint);
          color.val(guitar.color);
          orderStatus.val(guitar.orderStatus);
        });
      });

    });
  </script>

  <jsp:include page="shared/footer.jsp" />
</body>
</html>