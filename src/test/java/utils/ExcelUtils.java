
package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelUtils {

    /**
     * Reads data from Excel using relative path, safe for any cell type.
     */
    public static String getCellData(String sheetName, int rowNum, int colNum) {
        String filePath = System.getProperty("user.dir") + "/test-data/testdata.xlsx";

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) return "";

            Row row = sheet.getRow(rowNum);
            if (row == null) return "";

            Cell cell = row.getCell(colNum);
            if (cell == null) return "";

            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell); // handles String/Numeric/Date, etc.

        } catch (Exception e) {
            System.out.println("Data Read Error: " + e.getMessage());
            return "";
        }
    }
}

