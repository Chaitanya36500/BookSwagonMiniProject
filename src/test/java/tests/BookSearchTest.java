
package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;
import pages.BookswagonPage;
import utils.BrowserFactory;
import utils.DriverManager;
import utils.ExcelUtils;

@Listeners(utils.ScreenshotListener.class)
public class BookSearchTest {

    private WebDriver driver;
    private BookswagonPage page;

    // Parameter from testng.xml for Multi-browser support
    @Parameters("browser")
    @BeforeClass
    public void setup(@Optional("chrome") String browser) {
        driver = BrowserFactory.startBrowser(browser, "http://www.bookswagon.com");
        page = new BookswagonPage(driver);
    }

    @Test
    public void performBookSearch() {
        // 1. Data Driven: Get keyword from Excel
        String keyword = ExcelUtils.getCellData("Sheet1", 2, 0);
        System.out.println(keyword);
        if (keyword == null || keyword.trim().isEmpty()) {
            keyword = "Selenium Webdriver"; // Default fallback
        }

        // 2. Search
        page.searchForBook(keyword);

        // 3. Validation: Results > 10
        boolean isValid = page.validateAndPrintBooks();
        if (isValid) {
            System.out.println("VALIDATION PASS: Result count is greater than 10.");
        } else {
            System.out.println("VALIDATION FAIL: Result count is less than 10.");
        }
        Assert.assertTrue(isValid, "Count should be > 10");

        // 4. Sort (as per your flow)
        page.sortResults();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            DriverManager.unload(); // Clear ThreadLocal
            System.out.println("Browser Closed Successfully.");
        }
    }
}
