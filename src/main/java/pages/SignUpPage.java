package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage {
    WebDriver driver;

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    private By linkSignUp = By.id("signup");
    private By inputFirstName = By.id("firstName");
    private By inputLastName = By.id("lastName");
    private By inputEmail = By.id("email");
    private By inputPassword = By.id("password");
    private By btnSignUp = By.id("submit");

    public void navigateToSignUp() {
        driver.findElement(linkSignUp).click();
    }

    public void enterFirstName(String firstName) {
        driver.findElement(inputFirstName).clear();
        driver.findElement(inputFirstName).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(inputLastName).clear();
        driver.findElement(inputLastName).sendKeys(lastName);
    }

    public void enterEmail(String email) {
        driver.findElement(inputEmail).clear();
        driver.findElement(inputEmail).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(inputPassword).clear();
        driver.findElement(inputPassword).sendKeys(password);
    }

    public void clickSignUp() {
        driver.findElement(btnSignUp).click();
    }
    

}
