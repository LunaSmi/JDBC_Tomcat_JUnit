<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"  %>
<%@taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>movies</title>
</head>
<body>
  <c:if test="${not empty requestScope.list}">
    <div>
      <p> size: ${requestScope.list.size()}</p>
      <table>
        <tr>
          <td>name</td>
          <td>decription</td>
        </tr>
        <c:forEach var = "movie" items="${requestScope.list}">
          <tr>
            <td>
                ${movie.title}
            </td>
            <td>
                ${movie.description}
            </td>
            <td>
              <a href="">edit</a>
            </td>
            <td>
              <a href="">delete</a>
            </td>
          </tr>
        </c:forEach>
      </table>
    </div>
  </c:if>

</body>
</html>
