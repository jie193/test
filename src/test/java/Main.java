import com.example.model.Student;
import com.example.dao.StudentDAO;
import com.example.dao.StudentDAOImpl;
import com.example.service.StudentService;
import com.example.service.StudentServiceImpl;
import com.example.util.DatabaseConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try (Connection connection = DatabaseConnection.getConnection()) {
            StudentDAO studentDAO = new StudentDAOImpl(connection);
            StudentService studentService = new StudentServiceImpl(studentDAO);

            // 导入学生信息
            FileInputStream inputStream = new FileInputStream("students.xlsx");
            studentService.importStudentsFromExcel(inputStream);

            // 获取所有学生信息


            // 点名
            Student student = studentService.callStudent_undern();
            studentService.updateStudentAnswerStatus(student,true);
            studentService.updateStudent(student);
            // 更新学生回答状态
            studentService.printAllStudents();
            System.out.println(studentService.getAllStudents().size());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
       }
        catch (FileNotFoundException e) {throw new RuntimeException(e);}
    }
}
