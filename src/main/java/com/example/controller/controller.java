package com.example.controller;


import com.example.dao.*;
import com.example.model.Student;
import com.example.service.StudentServiceImpl;
import com.example.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/point")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class controller extends HttpServlet {
    private StudentServiceImpl studentService;
    private int consecutiveIncorrectCount = 0;
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Connection connection = DatabaseConnection.getConnection();
            StudentDAO studentDAO = new StudentDAOImpl(connection);
            studentService = new StudentServiceImpl(studentDAO);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("randomCall".equals(action)) {
            randomCall(request, response);
        } else if ("statistics".equals(action)) {
            showStatistics(request, response);
        }
        else {
            response.sendRedirect( "/index.html");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");


        if ("uploadFile".equals(action)) {
            uploadFile(request, response);
        } else if ("recordAnswer".equals(action)) {
            recordAnswer(request, response);
        } else if ("deleteStudent".equals(action)) {
            deleteStudent(request, response);
        } else if ("deleteAllStudents".equals(action)) {
            deleteAllStudents(request,response);
        }   else if ("increaseCorrectCount".equals(action)) {
            increaseCorrectCount(request, response);
        } else if ("decreaseCorrectCount".equals(action)) {
            decreaseCorrectCount(request, response);
        }else {
            response.sendRedirect( "/index.html");
        }
    }

    private void randomCall(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> students = studentService.getAllStudents();
        Collections.shuffle(students);
        Random rand = new Random();
        Student selectedStudent = null;

        while (consecutiveIncorrectCount < 3 && !students.isEmpty()) {
            selectedStudent = students.remove(rand.nextInt(students.size()));

            consecutiveIncorrectCount++;
        }

        if (selectedStudent != null) {
            request.setAttribute("selectedStudent", selectedStudent);
        } else {
            selectedStudent = callFromMoreCorrect();

            request.setAttribute("selectedStudent", selectedStudent);
        }

        request.getRequestDispatcher("/jsp/callResult.jsp").forward(request, response);
    }

    private Student callFromMoreCorrect() {
        return studentService.callStudent_upn();
    }

    private void recordAnswer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        boolean isCorrect = Boolean.parseBoolean(request.getParameter("isCorrect"));

        Student student = studentService.getStudentById(studentId);
        student.setCalledCount(student.getCalledCount() + 1);
        if (student != null) {
            if (isCorrect) {
                consecutiveIncorrectCount=0;
                student.setCorrectCount(student.getCorrectCount() + 1);
            }
            else {
                if(consecutiveIncorrectCount<3) {
                    consecutiveIncorrectCount++;
                }
            }
            studentService.updateStudent(student);
        }
        response.sendRedirect(request.getContextPath() + "/point?action=randomCall");
    }

    private void showStatistics(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> students = studentService.getAllStudents();
        students.sort(Comparator.comparingDouble(Student::getCorrectRate).reversed());
        request.setAttribute("students", students);
        request.getRequestDispatcher("/jsp/statistics.jsp").forward(request, response);
    }
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        studentService.deleteStudent(studentId);
        response.sendRedirect(request.getContextPath() + "/point?action=statistics");
    }
    private void deleteAllStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        studentService.deleteAllStudents();
        response.sendRedirect(request.getContextPath() + "/point?action=statistics");
    }
    private void uploadFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        if (filePart == null || filePart.getSize() == 0) {
            return;
        }
        InputStream fileContent = filePart.getInputStream();
        studentService.importStudentsFromExcel(fileContent);
        response.sendRedirect(request.getContextPath() + "/point?action=statistics");
    }
    private void increaseCorrectCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentIdParam = request.getParameter("studentId");
        if (studentIdParam != null && !studentIdParam.isEmpty()) {
            int studentId = Integer.parseInt(studentIdParam);
            studentService.increaseCorrectCount(studentId);
        }
        response.sendRedirect("point?action=statistics");
    }

    private void decreaseCorrectCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentIdParam = request.getParameter("studentId");
        if (studentIdParam != null && !studentIdParam.isEmpty()) {
            int studentId = Integer.parseInt(studentIdParam);
            studentService.decreaseCorrectCount(studentId);
        }
        response.sendRedirect("point?action=statistics");
    }
}
