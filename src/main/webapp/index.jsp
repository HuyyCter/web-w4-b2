<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
    <html>

    <head>
        <title>CD List</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
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