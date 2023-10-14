<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Graph Analysis Result</title>
</head>
<body>
    <h1>Graph Analysis Result</h1>

    <h2>Analysis Results:</h2>
    <p>Order (Number of Vertices): ${analysisResult.order}</p>
    <p>Size (Number of Edges): ${analysisResult.size}</p>
<%--    <p>Number of Connected Components: ${analysisResult.numComponents}</p>--%>
    <p>Minimum Degree: ${analysisResult.minDegree}</p>
    <p>Maximum Degree: ${analysisResult.maxDegree}</p>
    <p>Average Degree: ${analysisResult.avgDegree}</p>
    <p>Diameter: ${analysisResult.diameter}</p>
    <p>Radius: ${analysisResult.radius}</p>
</body>
</html>