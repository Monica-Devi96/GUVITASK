package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    private By submit = By.id("submit");
    private By cancelButton = By.id("cancel");
    private By errorMessage = By.id("error");
    private By editContactButton = By.id("edit-contact");
    private By returnButton = By.id("return");
    
    
    private By currentEmail = By.id("email");
    private By currentPhone = By.id("phone");

    public void waitForPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(editContactButton));
    }

    public void clickEditContact() {
        wait.until(ExpectedConditions.elementToBeClickable(editContactButton)).click();
        waitForEditableFields();
        try {
            Thread.sleep(2000); // wait for editable fields to really be ready
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void waitForEditableFields() {
        
        wait.until(driver -> {
            WebElement lastName = driver.findElement(lastNameField);
            return lastName.isDisplayed() && lastName.isEnabled();
        });
    }

    public void updateEmail(String email) throws InterruptedException {
    	Thread.sleep(2000);
        WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(emailField));
        emailInput.clear();
        emailInput.sendKeys(email);
    }
    
    public void clickReturn() {
    	 WebElement returntoPage = wait.until(ExpectedConditions.elementToBeClickable(returnButton));
    	 returntoPage.click();
    }

    public void updatePhone(String phone) throws InterruptedException {
    	Thread.sleep(2000);
        WebElement phoneInput = wait.until(ExpectedConditions.elementToBeClickable(phoneField));
        phoneInput.clear();
        phoneInput.sendKeys(phone);
    }

    public void clearLastName() throws InterruptedException {
    	Thread.sleep(2000);
        WebElement lastNameInput = wait.until(ExpectedConditions.elementToBeClickable(lastNameField));
        lastNameInput.click();
        lastNameInput.clear();
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submit)).click();
    }

    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
    }

    public String waitForErrorMessage() throws InterruptedException {
    	Thread.sleep(3000); 
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return driver.findElement(errorMessage).getText();
    }

    public String getCurrentEmail() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(currentEmail))
                   .getText().trim();
    }

    public String getCurrentPhone() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(currentPhone))
                   .getText().trim();
    }
}
