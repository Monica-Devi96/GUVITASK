package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ContactListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ContactListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By tableRows = By.cssSelector("table.contactTable tr.contactTableBodyRow");

    public void waitForPage() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));
    }

    public boolean waitForContact(String fullName) {
        return wait.until(driver -> {
            List<WebElement> rows = driver.findElements(tableRows);
            return rows.stream().anyMatch(row -> row.getText().contains(fullName));
        });
    }

    public String getEmailForContact(String fullName) {
        return getCellValue(fullName, 2);
    }

    public String getPhoneForContact(String fullName) {
        return getCellValue(fullName, 3);
    }

    private String getCellValue(String fullName, int cellIndex) {
        List<WebElement> rows = driver.findElements(tableRows);
        for (WebElement row : rows) {
            if (row.getText().contains(fullName)) {
                return row.findElements(By.tagName("td")).get(cellIndex).getText();
            }
        }
        return null;
    }

    public void clickContactNameToEdit(String fullName) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));

        List<WebElement> rows = driver.findElements(tableRows);
        for (WebElement row : rows) {
            String rowText = row.getText().trim();
            if (rowText.contains(fullName)) {
                WebElement nameCell = row.findElement(By.tagName("td"));
                wait.until(ExpectedConditions.elementToBeClickable(nameCell)).click();
                return;
            }
        }
        throw new RuntimeException("Contact not found to edit: " + fullName);
    }

    }
