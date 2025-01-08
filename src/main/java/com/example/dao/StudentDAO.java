package com.example.dao;

import com.example.model.Student;

import java.io.InputStream;
import java.util.List;

public interface StudentDAO {
    void addStudent(Student student);
    void deleteStudent(int id);
    void updateStudent(Student student);
    Student getStudentById(int id);
    List<Student> getAllStudents();
    void deleteAllStudents();
    void importStudentsFromExcel(InputStream inputStream);
}
