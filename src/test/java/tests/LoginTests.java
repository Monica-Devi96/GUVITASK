package tests;

import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ExcelUtils;

public class LoginTests extends BaseTest {
	
	
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        String filePath = getClass().getClassLoader()
                .getResource("testdata/LoginData.xlsx")
                .getPath();
        return ExcelUtils.getSheetData(filePath, "LoginData");
    }
    
    @Test(dataProvider = "loginData")
    public void testLoginScenarios(String email, String password, String expectedResult) {
    	String baseUrl = ConfigReader.get("base.url");
        driver.get(baseUrl);

        ExtentTest test = extent.createTest("Login Test - " + email);

        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(email, password);

            if (expectedResult.equalsIgnoreCase("success")) {
                boolean isOnContactListPage = loginPage.waitForSuccessfulLogin();
                Assert.assertTrue(isOnContactListPage, "User did not navigate to Contact List page.");
                test.pass("Login successful for " + email);
            } else {
                String errorMsg = loginPage.waitForErrorMessage();
                Assert.assertTrue(errorMsg.contains(expectedResult),
                        "Expected error: " + expectedResult + " but got: " + errorMsg);
                test.pass("Proper error message displayed for invalid login: " + errorMsg);
            }

        } catch (AssertionError | Exception e) {
            test.fail(e);
            throw e;
        }
    }

    @Test
    public void verifyPasswordIsMasked() {
        ExtentTest test = extent.createTest("Verify Password Masking");

        try {
            LoginPage loginPage = new LoginPage(driver);
            Assert.assertTrue(loginPage.isPasswordMasked(), "Password is not masked.");
            test.pass("Password field is properly masked.");
        } catch (AssertionError | Exception e) {
            test.fail(e);
            throw e;
        }
    }
    protected void login() {
        driver.get("https://thinking-tester-contact-list.herokuapp.com/");
        driver.findElement(By.id("email")).sendKeys("chitturaju96@gmail.com");
        driver.findElement(By.id("password")).sendKeys("chitturaju");
        driver.findElement(By.id("submit")).click();

    }

}
