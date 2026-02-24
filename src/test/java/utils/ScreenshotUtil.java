
package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    private final WebDriver driver;

    public ScreenshotUtil(WebDriver driver) {
        this.driver = driver;
    }

    public String captureScreenshot(String testName, String status) {
        String screenshotPath = "";
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + status + "_" + timestamp + ".png";

            Path destPath = Paths.get(System.getProperty("user.dir"), "screenshots", fileName);
            File destFile = destPath.toFile();

            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }

            TakesScreenshot ts = (TakesScreenshot) driver;
            File srcFile = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, destFile);

            screenshotPath = destFile.getAbsolutePath();
            System.out.println("Screenshot saved successfully: " + screenshotPath);
        } catch (WebDriverException e) {
            System.err.println("WebDriver failed to take screenshot: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException while saving screenshot: " + e.getMessage());
        }
        return screenshotPath;
    }
}

