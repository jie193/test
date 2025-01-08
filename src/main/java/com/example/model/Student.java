package com.example.model;

public class Student {
    private int id;
    private String studentId;
    private String name;
    private String className;
    private int calledCount;
    private int correctCount;
    private double correctRate;

    public Student() {
    }
    public Student(int id, String studentId, String name, String className, int calledCount, int correctCount, double correctRate) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.className = className;
        this.calledCount = calledCount;
        this.correctCount = correctCount;
        this.correctRate = correctRate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() { return name;}
    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public int getCalledCount() {
        return calledCount;
    }
    public void setCalledCount(int calledCount) {
        this.calledCount = calledCount;
    }

    public int getCorrectCount() {
        return correctCount;
    }
    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public double getCorrectRate() {
        return correctRate;
    }
    public void setCorrectRate(double correctRate) {
        this.correctRate = correctRate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", calledCount=" + calledCount +
                ", correctCount=" + correctCount +
                ", correctRate=" + correctRate +
                '}';
    }
}
