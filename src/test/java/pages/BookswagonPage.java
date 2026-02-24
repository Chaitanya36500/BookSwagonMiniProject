package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BookswagonPage {
    WebDriver driver;
    WebDriverWait wait;

    // --- Locators (Prioritizing ID/Name) ---
    By searchBox = By.id("inputbar");
    By searchBtn = By.id("btnTopSearch");
    By sortDropdown = By.id("ddlSort");

    // XPath required here as specific IDs don't exist for result lists
    By bookTitles = By.xpath("//div[@class='title']/a");
    By bookPrices = By.xpath("//div[@class='sell']");

    // Constructor
    public BookswagonPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Action: Enter text and search
     */
    public void searchForBook(String bookName) {
        try {
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
            input.clear();
            input.sendKeys(bookName);
            driver.findElement(searchBtn).click();
            System.out.println("PASS: Search initiated for " + bookName);
        } catch (Exception e) {
            System.out.println("FAIL: Search element not found. " + e.getMessage());
        }
    }

    /**
     * Action: Sort by Price Low to High
     */
    public void sortResults() {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(sortDropdown));
            Select select = new Select(dropdown);
            select.selectByVisibleText("Price - Low to High");

            // Wait for URL or Element update to reflect sorting
            Thread.sleep(2000); // Short static wait for page refresh stability
            System.out.println("PASS: Sorted by Price Low to High");
        } catch (Exception e) {
            System.out.println("FAIL: Sorting failed. " + e.getMessage());
        }
    }

    /**
     * Validation: Check if results > 10 and print Top 5
     */
    public boolean validateAndPrintBooks() {
        wait.until(ExpectedConditions.presenceOfElementLocated(bookTitles));
        List<WebElement> titles = driver.findElements(bookTitles);
        List<WebElement> prices = driver.findElements(bookPrices);

        int count = titles.size();
        System.out.println("Total Results Found: " + count);

        // Print Top 5
        System.out.println("--- Top 5 Books ---");
        for (int i = 0; i < 5 && i < count; i++) {
            System.out.println("Book: " + titles.get(i).getText() + " | Price: " + prices.get(i).getText());
        }

        return count > 10;
    }
}
