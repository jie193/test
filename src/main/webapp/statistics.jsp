<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.Student" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<html>
<head>
    <title>学生信息</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Student Statistics</h1>
        <table border="1" class="stats-table">
            <tr>
                <th>学号</th>
                <th>姓名</th>
                <th>班级</th>
                <th>被叫次数</th>
                <th>回答问题正确次数</th>
                <th>正达率</th>
                <th>操作</th>
            </tr>
            <%
                List<Student> students = (List<Student>) request.getAttribute("students");
                if (students != null) {
                    for (Student student : students) {
            %>
                        <tr>
                            <td><%= student.getStudentId() %></td>
                            <td><%= student.getName() %></td>
                            <td><%= student.getClassName() %></td>
                            <td><%= student.getCalledCount() %></td>
                            <td><%= student.getCorrectCount() %></td>
                            <td><%= String.format("%.2f%%", student.getCorrectRate() * 100) %></td>
                            <td>
                                <form action="point" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="deleteStudent">
                                    <input type="hidden" name="studentId" value="<%= student.getId() %>">
                                    <button type="submit" class="btn-delete">Delete</button>
                                </form>
                            </td>
                        </tr>
            <%
                    }
                }
            %>
        </table>
        <form action="point" method="post" style="margin-top: 20px;">
            <input type="hidden" name="action" value="deleteAllStudents">
            <button type="submit" class="btn-danger">Delete All Students</button>
        </form>
        <div class="student-count">
                    学生人数： <%= students != null ? students.size() : 0 %>
                </div>
        <a href="<%= request.getContextPath() %>/index.jsp" class="btn">Back to Home</a>

    </div>
</body>
</html>