<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"  %>
<%@taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>actors</title>
</head>
<body>
    <c:if test="${not empty requestScope.list}">
        <div>
            <p> size: ${requestScope.list.size()}</p>
            <table>
                <tr>
                    <td colspan="3">name</td>
                </tr>
                <c:forEach var = "actor" items="${requestScope.list}">
                    <tr>
                        <td>
                            ${actor.fullName}
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
