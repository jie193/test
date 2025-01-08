package com.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GenerateStudentData {
    public static void main(String[] args) {
        String path = "Students.xlsx";
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(path)) {

            Sheet sheet = workbook.createSheet("Students");

            // 添加标题行
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("学号");
            headerRow.createCell(1).setCellValue("姓名");
            headerRow.createCell(2).setCellValue("班级");

            // 生成40条学生数据
            Set<String> studentIds = new HashSet<>();
            for (int i = 1; i <= 40; i++) {
                Row row = sheet.createRow(i);
                Random random = new Random();
                int min = 100000;
                int max = 999999;
                String studentId;
                do {
                    int randomNumber = random.nextInt(max - min + 1) + min;
                    studentId = String.valueOf(randomNumber);
                } while (studentIds.contains(studentId));
                studentIds.add(studentId);
                row.createCell(0).setCellValue(studentId);

                // 生成随机姓名
                String name = generateRandomName();
                row.createCell(1).setCellValue(name);

                // 生成随机班级
                String className = generateRandomClassName();
                row.createCell(2).setCellValue(className);
            }

            // 写入文件
            workbook.write(fileOut);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateRandomName() {
        String[] names = {"张三", "李四", "王五", "赵六", "孙七", "周八", "吴九", "郑十", "钱十一", "冯十二"};
        Random random = new Random();
        return names[random.nextInt(names.length)];
    }

    private static String generateRandomClassName() {
        String[] classes = {"A班", "B班", "C班", "D班", "E班"};
        Random random = new Random();
        return classes[random.nextInt(classes.length)];
    }
}
