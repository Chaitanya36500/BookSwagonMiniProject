package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.ReentrantLock;

public class ExcelReportUtil {
    private static final String REPORT_PATH = "BookSwagon_Report.xlsx";
    private static final String TEST_DATA_PATH = "C:\\Users\\2461961\\IdeaProjects\\BookSwagonMiniProject\\test-data\\testdata.xlsx"; // Update path if different

    // This lock stops the "File in Use" error by queuing the threads
    private static final ReentrantLock lock = new ReentrantLock();

    /**
     * SAFE WRITE: Logs test results to Excel
     */
    public static void logToExcel(String browser, String tc, String desc, String status, String details) {
        lock.lock(); // Request entry
        try {
            File file = new File(REPORT_PATH);
            Workbook wb;
            Sheet sheet;

            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    wb = new XSSFWorkbook(fis);
                }
                sheet = wb.getSheet("Results");
                if (sheet == null) sheet = wb.createSheet("Results");
            } else {
                wb = new XSSFWorkbook();
                sheet = wb.createSheet("Results");
                Row header = sheet.createRow(0);
                String[] cols = {"Time", "Browser", "TC_ID", "Description", "Status", "Details"};
                for (int i = 0; i < cols.length; i++) header.createCell(i).setCellValue(cols[i]);
            }

            int lastRow = sheet.getLastRowNum();
            Row row = sheet.createRow(lastRow + 1);
            row.createCell(0).setCellValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            row.createCell(1).setCellValue(browser.toUpperCase());
            row.createCell(2).setCellValue(tc);
            row.createCell(3).setCellValue(desc);
            row.createCell(4).setCellValue(status);
            row.createCell(5).setCellValue(details);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                wb.write(fos);
            }
            wb.close();

        } catch (IOException e) {
            System.err.println("Excel Error: " + e.getMessage());
        } finally {
            lock.unlock(); // Release for the next browser
        }
    }


}
