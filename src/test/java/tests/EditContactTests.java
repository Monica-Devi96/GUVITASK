package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.ContactListPage;
import pages.EditContactPage;
import utils.ExcelUtils;

import java.io.IOException;

public class EditContactTests extends BaseTest {

    @DataProvider(name = "editData")
    public Object[][] editData() throws IOException {
        return ExcelUtils.getSheetData(
                "src/test/resources/testdata/EditContactData.xlsx",
                "EditContactData"
        );
    }

    @Test(dataProvider = "editData")
    public void testEditContact(String firstName, String lastName,
                                String newEmail, String newPhone,
                                String expectedResult, String action) {

        // create Extent report test for this scenario
        createTest("Edit Contact: " + action);

        loginValidUser();

        String fullName = firstName.trim() + " " + lastName.trim();

        ContactListPage listPage = new ContactListPage(driver);
        listPage.waitForPage();

        Assert.assertTrue(listPage.waitForContact(fullName),
                "Contact not found in list: " + fullName);
        getTest().info("Found contact: " + fullName);

        String originalEmail = "";
        String originalPhone = "";

        if (action.equalsIgnoreCase("cancel")) {
            originalEmail = listPage.getEmailForContact(fullName);
            originalPhone = listPage.getPhoneForContact(fullName);
        }

        listPage.clickContactNameToEdit(fullName);

        EditContactPage editPage = new EditContactPage(driver);
        editPage.waitForPage();

        if (action.equalsIgnoreCase("validate")) {
            editPage.clearLastName();
            editPage.clickSave();
            String errorMsg = editPage.waitForErrorMessage();

            Assert.assertTrue(errorMsg.contains(expectedResult),
                    "Expected validation error not shown. Actual: " + errorMsg);
            getTest().pass("Validation error displayed as expected: " + errorMsg);

        } else if (action.equalsIgnoreCase("cancel")) {
            editPage.updateEmail(newEmail);
            editPage.updatePhone(newPhone);
            editPage.clickCancel();

            Assert.assertTrue(listPage.waitForContact(fullName),
                    "Contact not present after cancel.");

            String emailAfterCancel = listPage.getEmailForContact(fullName);
            String phoneAfterCancel = listPage.getPhoneForContact(fullName);

            Assert.assertEquals(emailAfterCancel, originalEmail, "Email changed after cancel.");
            Assert.assertEquals(phoneAfterCancel, originalPhone, "Phone changed after cancel.");
            getTest().pass("Cancel worked; original data remains unchanged.");

        } else if (action.equalsIgnoreCase("save")) {
            editPage.updateEmail(newEmail);
            editPage.updatePhone(newPhone);
            editPage.clickSave();

            Assert.assertTrue(listPage.waitForContact(fullName),
                    "Contact not present after save.");

            String emailAfterSave = listPage.getEmailForContact(fullName);
            String phoneAfterSave = listPage.getPhoneForContact(fullName);

            Assert.assertEquals(emailAfterSave, newEmail, "Email not updated correctly.");
            Assert.assertEquals(phoneAfterSave, newPhone, "Phone not updated correctly.");
            getTest().pass("Contact updated and saved successfully.");

        } else {
            Assert.fail("Unknown action: " + action);
        }
    }

    private void loginValidUser() {
        driver.get("https://thinking-tester-contact-list.herokuapp.com");
        driver.findElement(org.openqa.selenium.By.id("email")).sendKeys("chitturaju96@gmail.com");
        driver.findElement(org.openqa.selenium.By.id("password")).sendKeys("chitturaju");
        driver.findElement(org.openqa.selenium.By.id("submit")).click();
    }
}
