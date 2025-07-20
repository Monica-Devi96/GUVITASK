package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));  
    }

    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By loginButton = By.id("submit");
    private By errorMessage = By.id("error");
    private By contactListUniqueElement = By.id("add-contact"); 

    public void enterEmail(String email) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        
    }

    public String waitForErrorMessage() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return error.getText();
    }

    public boolean isPasswordMasked() {
        String type = driver.findElement(passwordField).getAttribute("type");
        return type.equals("password");
    }

    public boolean waitForSuccessfulLogin() {
        try {
            WebElement contactButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(contactListUniqueElement)
            );
            return contactButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginPageDisplayed() {
        return driver.getCurrentUrl().contains("https://thinking-tester-contact-list.herokuapp.com/");
    }
}
