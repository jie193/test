package com.example.dao;

import com.example.model.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    private Connection connection;

    public StudentDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addStudent(Student student) {
        String sql = "INSERT INTO students (student_id, name, class_name, called_count, correct_count, correct_rate) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getStudentId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getClassName());
            statement.setInt(4, student.getCalledCount());
            statement.setInt(5, student.getCorrectCount());
            statement.setDouble(6, student.getCorrectRate());
            statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateStudent(Student student) {
        String sql = "UPDATE students SET called_count = ?, correct_count = ?, correct_rate = ? WHERE id = ?";
        double correctRate;

        if (student.getCalledCount() == 0) {
            correctRate = 0.0;
        } else {
            correctRate = (double) student.getCorrectCount() / student.getCalledCount();
        }
        student.setCorrectRate(correctRate);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, student.getCalledCount());
            statement.setInt(2, student.getCorrectCount());
            statement.setDouble(3, student.getCorrectRate());
            statement.setInt(4, student.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("student_id"),
                        resultSet.getString("name"),
                        resultSet.getString("class_name"),
                        resultSet.getInt("called_count"),
                        resultSet.getInt("correct_count"),
                        resultSet.getDouble("correct_rate")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                students.add(new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("student_id"),
                        resultSet.getString("name"),
                        resultSet.getString("class_name"),
                        resultSet.getInt("called_count"),
                        resultSet.getInt("correct_count"),
                        resultSet.getDouble("correct_rate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    @Override
    public void deleteAllStudents() {
        String sql = "DELETE FROM students";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void importStudentsFromExcel(InputStream inputStream) {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {continue;}
                Student student = new Student();
                student.setStudentId(row.getCell(0).getStringCellValue());
                student.setName(row.getCell(1).getStringCellValue());
                student.setClassName(row.getCell(2).getStringCellValue());
                addStudent(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
