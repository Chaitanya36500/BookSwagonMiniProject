
package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BrowserFactory {

    public static WebDriver startBrowser(String browserName, String url) {
        WebDriver driver = null;
        try {
            if (browserName == null || browserName.isBlank() || browserName.equalsIgnoreCase("chrome")) {
                driver = new ChromeDriver(); // Selenium Manager auto-resolves
            } else if (browserName.equalsIgnoreCase("edge")) {
                driver = new EdgeDriver();
            } else {
                // Fallback to Chrome if unknown browser
                driver = new ChromeDriver();
            }

            if (driver != null) {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                driver.manage().window().maximize();
                driver.get(url);

                // Register in ThreadLocal so listeners can access it
                DriverManager.setDriver(driver);
            }
        } catch (Exception e) {
            System.out.println("Error initializing browser: " + e.getMessage());
        }
        return driver;
    }
}
