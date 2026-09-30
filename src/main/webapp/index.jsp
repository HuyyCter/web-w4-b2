<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:if test="${empty albumList}">
    <jsp:forward page="/albums" />
</c:if>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CD List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <h1>List of albums</h1>
    <div class="album-list">
        <c:forEach var="album" items="${albumList}">
            <a href="download?action=checkUser&amp;productId=${album.productId}">
                ${album.title}
            </a><br>
        </c:forEach>
    </div>

</body>
</html>