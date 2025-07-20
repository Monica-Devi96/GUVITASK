package tests;

import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AddContactPage;
import pages.ContactListPage;
import utils.ConfigReader;
import utils.ExcelUtils;

import java.time.Duration;

public class AddContactTests extends BaseTest {

    @DataProvider(name = "addContactData")
    public Object[][] getAddContactData() {
        String filePath = getClass().getClassLoader()
                .getResource("testdata/AddContactData.xlsx")
                .getPath();
        return ExcelUtils.getSheetData(filePath, "AddContactData");
    }

    @Test(dataProvider = "addContactData")
    public void testAddContactScenarios(String firstName, String lastName, String email, String phone, String expectedResult) {
        String baseUrl = ConfigReader.get("base.url");
        driver.get(baseUrl);

        ExtentTest test = extent.createTest("Add Contact Test - " + firstName + " " + lastName);

        try {
            logintoAdd();

            ContactListPage listPage = new ContactListPage(driver);
            listPage.clickAddContact();

            AddContactPage addPage = new AddContactPage(driver);
            addPage.enterFirstName(firstName);
            addPage.enterLastName(lastName);
            addPage.enterEmail(email);
            addPage.enterPhone(phone);
            addPage.clickSubmit();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            if (expectedResult.equalsIgnoreCase("success")) {
                // Wait until URL contains /contactList
                boolean isOnContactListPage = wait.until(ExpectedConditions.urlContains("/contactList"));
                Assert.assertTrue(isOnContactListPage, "Did not navigate to contact list page after adding contact.");

                test.pass("Successfully added contact: " + firstName + " " + lastName);
            } else {
                // Wait until error message is visible and verify
                String actualError = addPage.waitForErrorMessage();
                Assert.assertTrue(actualError.contains(expectedResult),
                        "Expected error: " + expectedResult + " but got: " + actualError);

                test.pass("Validation error displayed as expected: " + actualError);
            }

        } catch (AssertionError | Exception e) {
            test.fail(e);
            throw e;
        }
    }

    private void logintoAdd() {
        driver.get("https://thinking-tester-contact-list.herokuapp.com/");
        driver.findElement(By.id("email")).sendKeys("chitturaju96@gmail.com");
        driver.findElement(By.id("password")).sendKeys("chitturaju");
        driver.findElement(By.id("submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-contact")));
    }
}

