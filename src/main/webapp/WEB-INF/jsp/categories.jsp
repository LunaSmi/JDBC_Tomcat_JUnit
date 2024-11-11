
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"  %>
<%@taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>categories</title>
</head>
<body>
    <c:if test="${not empty requestScope.list}">
        <div>
            <p> size: ${requestScope.list.size()}</p>
            <table>
                <tr>
                    <td colspan="3">name</td>
                </tr>
                <c:forEach var = "category" items="${requestScope.list}">
                    <tr>
                        <td>
                            ${category.name}
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


