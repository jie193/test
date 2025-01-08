<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>点名系统</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Pointing System</h1>
        <form action="point" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="uploadFile">
            <label for="file">更新学生信息，上传.xlsx文件:</label>
            <input type="file" id="file" name="file" accept=".xlsx">
            <button type="submit">更新</button>
        </form>
        <br>
        <a href="${pageContext.request.contextPath}/point?action=randomCall" class="btn">Random Call</a>
        <a href="${pageContext.request.contextPath}/point?action=statistics" class="btn">View Statistics</a>
    </div>
</body>
</html>