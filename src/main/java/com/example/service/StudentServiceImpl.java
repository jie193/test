package com.example.service;

import com.example.dao.StudentDAO;
import com.example.model.Student;

import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    private StudentDAO studentDAO;

    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public void addStudent(Student student) { studentDAO.addStudent(student);
    }

    @Override
    public void updateStudent(Student student) {
        studentDAO.updateStudent(student);
    }

    @Override
    public Student getStudentById(int id) {
        return studentDAO.getStudentById(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @Override
    public void importStudentsFromExcel(InputStream inputStream) {
        studentDAO.importStudentsFromExcel(inputStream);
    }

    @Override
    public Student callStudent_undern() {
        List<Student> students = getAllStudents();
        Random random = new Random();
        int index = random.nextInt(students.size());
        Student student = students.get(index);
        return student;
    }
    @Override
    public Student callStudent_upn() {
        List<Student> students = getAllStudents();
        if(students.size()==0) return null;
        Collections.sort(students, Comparator.comparing(Student::getCorrectRate).reversed());
        List<Student> morestudents = students.stream()
                .filter(student -> student.getCorrectCount() >= (int) (0.7*students.get(0).getCorrectCount())).collect(Collectors.toList());
        Random random = new Random();
        int index = random.nextInt(morestudents.size());
        Student student = morestudents.get(index);
        return student;
    }

    @Override
    public void updateStudentAnswerStatus(Student student, boolean isCorrect) {
        student.setCalledCount(student.getCalledCount() + 1);
        if (isCorrect) {
            student.setCorrectCount(student.getCorrectCount() + 1);
        }
        updateStudent(student);
    }
    @Override
    public void printAllStudents() {
        List<Student> students = getAllStudents();
        Collections.sort(students, Comparator.comparing(Student::getCorrectRate).reversed());
        for(Student student : students){
            System.out.println(student.toString());
        }
    }
    @Override
    public void deleteStudent(int studentId) {
        studentDAO.deleteStudent(studentId);
    }

    @Override
    public void increaseCorrectCount(int studentId)
    {
        Student student = getStudentById(studentId);
        student.setCorrectCount(student.getCorrectCount()+1);
        studentDAO.updateStudent(student);
    }

    @Override
    public void decreaseCorrectCount(int studentId)
    {
        Student student = getStudentById(studentId);
        student.setCorrectCount(student.getCorrectCount()-1);
        studentDAO.updateStudent(student);
    }

    @Override
    public void deleteAllStudents() {
        studentDAO.deleteAllStudents();
    }

}
