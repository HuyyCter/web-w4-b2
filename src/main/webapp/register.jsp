<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>Register</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
    </head>

    <body>

        <h1>Join our email list</h1>
    	<p>To join our email list, enter your name and email address below.</p>
    	<form action="download" method="post">
        	<input type="hidden" name="action" value="registerUser">

        	<label>Email:</label>
        	<input type="email" name="email" value="${user.email}" required><br>

        	<label>First Name:</label>
        	<input type="text" name="firstName" value="${user.firstName}" required><br>

        	<label>Last Name:</label>
        	<input type="text" name="lastName" value="${user.lastName}" required><br>

        	<label>&nbsp;</label>
        	<input type="submit" value="Register" id="submit">
    </form>

    </body>

    </html>