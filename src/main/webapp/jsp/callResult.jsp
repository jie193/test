<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.Student" %>

<!DOCTYPE html>
<html lang="en">
<html>
<head>
    <title>点名结果</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>
    <div class="container">
        <h1>点名结果</h1>
        <%
            Student selectedStudent = (Student) request.getAttribute("selectedStudent");
            if (selectedStudent != null) {
        %>
            <div class="student-info">
                <p><strong>姓名：</strong> <%= selectedStudent.getName() %></p>
                <p><strong>班级：</strong> <%= selectedStudent.getClassName() %></p>
                <p><strong>学号：</strong> <%= selectedStudent.getStudentId() %></p>
            </div>
            <form action="point" method="post">
                <input type="hidden" name="action" value="recordAnswer">
                <input type="hidden" name="studentId" value="<%= selectedStudent.getId() %>">
                <label for="isCorrect">对？ 错？</label>
                <select id="isCorrect" name="isCorrect">
                    <option value="true">对</option>
                    <option value="false">错</option>
                </select>
                <button type="submit" class="btn2">确定</button>
            </form>
        <%
            } else {
        %>
            <p>没有学生被选中</p>
        <%
            }
        %>

        <a href="<%= request.getContextPath() %>/index.html" class="btn1">Back to Home</a>
    </div>
</body>
</html>