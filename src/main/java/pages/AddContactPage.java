package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddContactPage {
    WebDriver driver;
    WebDriverWait wait;

    public AddContactPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By firstNameInput = By.id("firstName");
    private By lastNameInput = By.id("lastName");
    private By emailInput = By.id("email");
    private By phoneInput = By.id("phone");
    private By submitBtn = By.id("submit");
    private By cancelBtn = By.id("cancel");  // ✅ Added missing cancel button locator
    private By errorMsg = By.id("error");
    private By toastMessage = By.className("toast-message");

    public void enterFirstName(String firstName) {
        driver.findElement(firstNameInput).clear();
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameInput).clear();
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void enterEmail(String email) {
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterPhone(String phone) {
        driver.findElement(phoneInput).clear();
        driver.findElement(phoneInput).sendKeys(phone);
    }

    public void clickSubmit() {
        driver.findElement(submitBtn).click();
    }

    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelBtn)).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMsg).getText();
    }

    public String waitForErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg));
        return driver.findElement(errorMsg).getText();
    }

    public void waitForPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
    }

    public boolean waitForNavigationToContactList() {
        return wait.until(driver -> driver.getCurrentUrl().contains("/contactList"));
    }

    public boolean isFirstNameFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).isDisplayed();
    }

    public boolean isLastNameFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput)).isDisplayed();
    }

    public boolean isEmailFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).isDisplayed();
    }

    public boolean isPhoneFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInput)).isDisplayed();
    }

    public boolean isSubmitButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(submitBtn)).isDisplayed();
    }

    public boolean isCancelButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cancelBtn)).isDisplayed();
    }

 
    
}
