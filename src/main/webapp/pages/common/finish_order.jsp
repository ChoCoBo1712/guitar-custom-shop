<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customshop-tags" prefix="cst" %>

<html>
<head>
  <title><cst:localeTag key="finish_order.title" /></title>
  <jsp:include page="shared/head.html" />
</head>
<body>
  <jsp:include page="shared/header.jsp" />

  <form id="finish_order_form" action="${pageContext.request.contextPath}/controller?command=finish_order"
        method="post" enctype="multipart/form-data">
    <input type="text" name="id" value="${requestScope.id}" hidden>
    <input type="file" name="picturePath" id="file_input" accept="image/png, image/jpeg" required>
    <br>
    <input type="submit">
  </form>

  <script>
    $(document).ready( function () {
      let form = $('#finish_order_form');
      form.submit( function () {
        let fileInput = $('#file_input');
        let files = fileInput.prop('files');
        for (let i = 0; i < files.length; i++) {
          let type = files[i].type;
          if (type !== "image/jpeg" && type !== "image/png") {
            alert('Invalid file type! Choose .png or .jpeg file.')
            return false;
          }
        }
        return true;
      });
    });
  </script>

  <jsp:include page="shared/footer.jsp" />
</body>
</html>