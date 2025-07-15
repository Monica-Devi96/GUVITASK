package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactFormPage {
    private WebDriver driver;

    public ContactFormPage(WebDriver driver) {
        this.driver = driver;
    }

    private By firstNameField = By.id("firstName");
    private By lastNameField = By.id("lastName");
    private By emailField = By.id("email");
    private By phoneField = By.id("phone");
    private By saveButton = By.id("submit");
    private By cancelButton = By.xpath("//button[text()='Cancel']");
    private By errorDiv = By.id("error");

    public void waitForPage() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
    }

    public void setFirstName(String value) {
        driver.findElement(firstNameField).clear();
        driver.findElement(firstNameField).sendKeys(value);
    }

    public void setLastName(String value) {
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(value);
    }

    public void setEmail(String value) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(value);
    }

    public void setPhone(String value) {
        driver.findElement(phoneField).clear();
        driver.findElement(phoneField).sendKeys(value);
    }

    public void clickSave() {
        driver.findElement(saveButton).click();
    }

    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    public String waitForErrorAndGetMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(driver -> {
            String text = driver.findElement(errorDiv).getText();
            return !text.isEmpty() ? text : null;
        });
    }

    public String getErrorMessage() {
        return driver.findElement(errorDiv).getText();
    }
}
