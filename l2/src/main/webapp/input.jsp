<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Input</title>
</head>
<body>
    <h1>Upload a Graph in DIMACS Format</h1>
    <form action="analyzeGraph" method="post" enctype="multipart/form-data">
        <label for="graphFile">Select a DIMACS Graph File:</label>
        <input type="file" name="graphFile" id="graphFile" required>
        <br>
        <input type="submit" value="Upload and Analyze">
    </form>

    <h1>Generate a Graph</h1>
    <form action="generatedGraphAnalysis" method="post">
        <label for="order">Number of vertices:</label>
        <input type="text" name="order" id="order" placeholder="number of vertices" value="${empty param.order ? applicationScope.defaultOrder : param.order}">
        <br>
        <label for="size">Number of edges:</label>
        <input type="text" name="size" id="size" placeholder="number of edges" value="${empty param.size ? applicationScope.defaultSize : param.size}">
        <br>
        <input type="submit" value="Generate And Analyze">
    </form>
</body>
</html>
