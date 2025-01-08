package com.example.service;

import com.example.model.Student;

import java.io.InputStream;
import java.util.List;

public interface StudentService {
    void addStudent(Student student);
    void updateStudent(Student student);
    Student getStudentById(int id);
    List<Student> getAllStudents();
    void importStudentsFromExcel(InputStream inputStream);
    Student callStudent_undern();
    Student callStudent_upn();
    void updateStudentAnswerStatus(Student student, boolean isCorrect);
    void printAllStudents();
    void deleteAllStudents();
    void deleteStudent(int studentId);

    void increaseCorrectCount(int studentId);

    void decreaseCorrectCount(int studentId);
}
