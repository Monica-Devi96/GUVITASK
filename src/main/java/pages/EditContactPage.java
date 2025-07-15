package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditContactPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public EditContactPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By emailField = By.id("email");
    private By phoneField = By.id("phone");
    private By lastNameField = By.id("lastName");
    private By saveButton = By.xpath("//button[text()='Save']");
    private By cancelButton = By.xpath("//button[text()='Cancel']");
    private By errorMessage = By.cssSelector(".error-message");

    public void waitForPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(saveButton));
    }

    public void updateEmail(String email) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void updatePhone(String phone) {
        driver.findElement(phoneField).clear();
        driver.findElement(phoneField).sendKeys(phone);
    }

    public void clearLastName() {
        driver.findElement(lastNameField).clear();
    }

    public void clickSave() {
        driver.findElement(saveButton).click();
    }

    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    public String waitForErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }
}
