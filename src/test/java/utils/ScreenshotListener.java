
package utils;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {
        capture(result, "Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        capture(result, "Failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        capture(result, "Skipped");
    }

    private void capture(ITestResult result, String status) {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver != null) {
                ScreenshotUtil screenshotUtil = new ScreenshotUtil(driver);
                String methodName = result.getName(); // e.g., performBookSearch
                screenshotUtil.captureScreenshot(methodName, status);
                System.out.println("Screenshot captured by Listener for: " + methodName);
            } else {
                System.err.println("WebDriver was null. Could not take screenshot for: " + result.getName());
            }
        } catch (Exception e) {
            System.err.println("Listener failed to capture: " + e.getMessage());
        }
    }
}
