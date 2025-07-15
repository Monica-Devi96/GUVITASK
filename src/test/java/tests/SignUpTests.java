package tests;

import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.SignUpPage;
import utils.ConfigReader;
import utils.ExcelUtils;

import java.time.Duration;

public class SignUpTests extends BaseTest {

    @DataProvider(name = "signUpData")
    public Object[][] signUpData() {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/SignUpData.xlsx";
        return ExcelUtils.getSheetData(filePath, "SignUpData");
    }

    @Test(dataProvider = "signUpData")
    public void testSignUpScenarios(
            String firstName, String lastName, String email,
            String password, String expectedResult) {

        String baseUrl = ConfigReader.get("base.url");
        driver.get(baseUrl);

        ExtentTest test = extent.createTest("SignUp Test - " + email);

        try {
            // Navigate to sign up page after loading base url
            SignUpPage signUpPage = new SignUpPage(driver);
            signUpPage.navigateToSignUp();

            signUpPage.enterFirstName(firstName);
            signUpPage.enterLastName(lastName);
            signUpPage.enterEmail(email);
            signUpPage.enterPassword(password);
            signUpPage.clickSignUp();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            if (expectedResult.trim().equalsIgnoreCase("success")) {
                WebElement addContactBtn = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("add-contact"))
                );

                Assert.assertTrue(addContactBtn.isDisplayed(),
                        "Expected successful signup and navigation to page with add-contact button, but button was not visible.");
                test.pass("Signup successful for: " + email);
            } else {
                WebElement errorMsg = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("error"))
                );
                String actualError = errorMsg.getText().toLowerCase();
                Assert.assertTrue(actualError.contains(expectedResult.toLowerCase()),
                        "Expected error message to contain: " + expectedResult + ", but got: " + actualError);
                test.pass("Proper error message displayed for invalid signup: " + actualError);
            }

        } catch (AssertionError | Exception e) {
            test.fail(e);
            throw e;
        }
    }
}
