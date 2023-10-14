<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Java Technologies</title>
</head>
<body>
<h1><%= "Java Technologies - L1" %>
</h1>
<br/>

<h2>Compulsory</h2>
<form method="GET" action="compulsory">
    Number: <input type="text" name="number"/>
    <br>
    <input type="submit" value="Get digits" />
</form>

<h2>Homework</h2>
<form method="GET" action="homework">
    Number of vertices: <input type="text" name="numVertices"/>
    <br>
    <input type="submit" value="Get adjacency matrix of a randomly generated tree" />
</form>
</body>
</html>