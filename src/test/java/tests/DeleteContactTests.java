package tests;

import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import pages.DeleteContactPage;
import pages.ContactListPage;
import utils.ConfigReader;
import utils.ExcelUtils;

import java.time.Duration;
import java.util.List;

public class DeleteContactTests extends BaseTest {

    @BeforeMethod
    public void setUp() {
        driver.get(ConfigReader.get("base.url"));
        loginValid();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name = "deleteData")
    public Object[][] deleteData() {
        String path = System.getProperty("user.dir") + "/src/test/resources/testdata/DeleteContactData.xlsx";
        return ExcelUtils.getSheetData(path, "DeleteContactData");
    }

    @Test(dataProvider = "deleteData")
    public void testDeleteContact(String contactName, String scenarioType) {
        ExtentTest test = extent.createTest("Delete Contact Test - " + contactName + " - " + scenarioType);

        try {
            ContactListPage contactListPage = new ContactListPage(driver);
            contactListPage.waitForPage();

            switch (scenarioType.trim().toLowerCase()) {

                case "check_alert": {
                    Assert.assertTrue(contactListPage.isContactPresent(contactName),
                            "Contact should exist before deletion");

                    contactListPage.clickContactName(contactName);

                    DeleteContactPage detailsPage = new DeleteContactPage(driver);
                    detailsPage.waitForPage();
                    detailsPage.clickDelete();

                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                    Assert.assertTrue(alert.getText().toLowerCase().contains("delete"),
                            "Alert text is missing");
                    alert.dismiss(); // dismiss alert
                    test.pass("Alert appeared and dismissed: " + contactName);
                    break;
                }

                case "delete_contact": {
                    contactListPage.waitForPage();

                    Assert.assertTrue(contactListPage.isContactPresent(contactName),
                            "Contact should exist before deletion");

                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
                    while (contactListPage.isContactPresent(contactName)) {
                 
                        List<WebElement> contactRows = driver.findElements(
                                By.xpath("//table[contains(@class,'contactTable')]//tr[td[normalize-space()='" + contactName + "']]"));

                        if (contactRows.isEmpty()) {
                            break; 
                        }

                        
                        contactRows.get(0).click();

                        DeleteContactPage detailsPage = new DeleteContactPage(driver);
                        detailsPage.waitForPage();
                        detailsPage.clickDelete();

                        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                        alert.accept();

                        contactListPage.waitForPage();
                    }

                    
                    Assert.assertFalse(contactListPage.isContactPresent(contactName),
                            "Contact still present even after attempting to delete all.");
                    test.pass("Deleted all occurrences of contact: " + contactName);
                    break;
                }


                case "verify_deleted": {
                    contactListPage.waitForPage();
                    if (!contactListPage.isContactPresent(contactName)) {
                        test.pass("Verified contact is not present: " + contactName);
                    } else {
                        Assert.fail("Contact is still present in table: " + contactName);
                    }
                    break;
                }

                default:
                    Assert.fail("Unsupported scenario type: " + scenarioType);
            }

        } catch (AssertionError | Exception e) {
            test.fail(e);
            throw e;
        }
    }

    protected void loginValid() {
        driver.get("https://thinking-tester-contact-list.herokuapp.com/");
        driver.findElement(By.id("email")).sendKeys("chitturaju96@gmail.com");
        driver.findElement(By.id("password")).sendKeys("chitturaju");
        driver.findElement(By.id("submit")).click();
    }
}
