package com.irctc.qa.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtility {
    private Workbook workbook;
    private Sheet sheet;
    private int rowCount = 0;
    private int columnCount = 0;

    public ExcelUtility(String filePath) {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("DataSheet");
    }

    public void appendDataToExcel(String data, boolean bold) {
        Row row = sheet.createRow(rowCount);
        Cell cell = row.createCell(columnCount);
        cell.setCellValue(data);

        if (bold) {
            // Create a bold font
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);

            // Apply the bold font to the cell style
            CellStyle boldStyle = workbook.createCellStyle();
            boldStyle.setFont(boldFont);
            cell.setCellStyle(boldStyle);
        }

        rowCount++;
    }

    public void moveToNextColumn() {
        rowCount = 0; // Reset row count for the new column
        columnCount++;
    }

    public void moveToNextRow() {
        rowCount++;
    }

    public void saveExcelFile(String filePath) {
        try {
            File file = new File(filePath);
            // Check if the file already exists, and if so, delete it
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("Existing file deleted.");
                } else {
                    System.out.println("Failed to delete existing file.");
                }
            }
            // Create a new file and write the data
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
                System.out.println("File saved.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
