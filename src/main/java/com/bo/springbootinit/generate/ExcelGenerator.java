package com.bo.springbootinit.generate;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExcelGenerator {

    public static void main(String[] args) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Example Sheet");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        String[] columns = {"ID", "Name", "Age"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // 添加一些示例数据
        Object[][] data = {
                {1, "John Doe", 30},
                {2, "Jane Smith", 25},
                {3, "Mike Johnson", 15},
                {4, "Mike", 16},
                {5, "Johnson", 19},
                {6, "Mikhnson", 30},
                {7, "Mson", 45},
                {8, "Mbon", 50}
        };

        int rowNum = 1;
        for (Object[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            for (int colNum = 0; colNum < rowData.length; colNum++) {
                Cell cell = row.createCell(colNum);
                if (rowData[colNum] instanceof String) {
                    cell.setCellValue((String) rowData[colNum]);
                } else if (rowData[colNum] instanceof Integer) {
                    cell.setCellValue((Integer) rowData[colNum]);
                }
            }
        }

        // 指定生成文件的位置
        String filePath = System.getProperty("user.home") + "/Desktop/example.xlsx"; // 将文件保存到桌面
        Path directoryPath = Paths.get(System.getProperty("user.home"), "Desktop");

        // 在写入文件之前创建目录（如果目录不存在）
        try {
            if (Files.notExists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // 将工作簿写入文件
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            System.out.println(filePath + " 文件已生成！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭工作簿
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
