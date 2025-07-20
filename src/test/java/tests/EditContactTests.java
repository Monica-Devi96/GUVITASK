package tests;

import base.BaseTest;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import pages.ContactListPage;
import pages.EditContactPage;
import utils.ExcelUtils;

import java.io.IOException;

public class EditContactTests extends BaseTest {

	@DataProvider(name = "editData")
	public Object[][] editData() throws IOException {
		return ExcelUtils.getSheetData("src/test/resources/testdata/EditContactData.xlsx", "EditContactData");
	}

	@Test(dataProvider = "editData")
	public void testEditContact(String firstName, String lastName, String newEmail, String newPhone,
			String expectedResult, String action) throws InterruptedException {

		createTest("Edit Contact: " + action);

		loginValid();

		String fullName = firstName.trim() + " " + lastName.trim();

		ContactListPage listPage = new ContactListPage(driver);
		listPage.waitForPage();

		Assert.assertTrue(listPage.isContactPresent(fullName), "Contact not found in list: " + fullName);
        ExtentTest test = extent.createTest("Edit Contact Test - " + firstName + " " + lastName);


		listPage.clickContactName(fullName);

		EditContactPage editPage = new EditContactPage(driver);
		editPage.waitForPage();

		// Read original values
		String originalEmail = editPage.getCurrentEmail();
		String originalPhone = editPage.getCurrentPhone();

		editPage.clickEditContact();

		if (action.equalsIgnoreCase("validate")) {
		    editPage.clearLastName();
		    editPage.clickSubmit();
		    String errorMsg = editPage.waitForErrorMessage();
		    Assert.assertTrue(
		        errorMsg.toLowerCase().contains(expectedResult.trim().toLowerCase()),
		        "Expected error: " + expectedResult + " but got: " + errorMsg
		    );
		    test.pass("Validation error displayed as expected: " + errorMsg);
		    
		}

		else if (action.equalsIgnoreCase("cancel")) {
			editPage.updateEmail(newEmail);
			editPage.updatePhone(newPhone);
			editPage.clickCancel();
			editPage.clickReturn();

			listPage.waitForPage();
			listPage.clickContactName(fullName);
			editPage.waitForPage();

			String emailAfterCancel = editPage.getCurrentEmail();
			String phoneAfterCancel = editPage.getCurrentPhone();

			Assert.assertEquals(emailAfterCancel, originalEmail, "Email changed after cancel.");
			Assert.assertEquals(phoneAfterCancel, originalPhone, "Phone changed after cancel.");
			getTest().pass("Cancel worked; original data remains unchanged.");

		} else if (action.equalsIgnoreCase("save")) {
			editPage.updateEmail(newEmail);
			editPage.updatePhone(newPhone);
			editPage.clickSubmit();
			editPage.clickReturn();
			
			listPage.waitForPage();
			listPage.clickContactName(fullName);
			editPage.waitForPage();
			
			String emailAfterSave = editPage.getCurrentEmail();
			String phoneAfterSave = editPage.getCurrentPhone();

			Assert.assertEquals(emailAfterSave, newEmail, "Email not updated correctly.");
			Assert.assertEquals(phoneAfterSave, newPhone, "Phone not updated correctly.");
			getTest().pass("Contact updated and saved successfully.");

		} else {
			Assert.fail("Unknown action: " + action);
		}
	}
	 protected void loginValid() {
	        driver.get("https://thinking-tester-contact-list.herokuapp.com/");
	        driver.findElement(By.id("email")).sendKeys("chitturaju96@gmail.com");
	        driver.findElement(By.id("password")).sendKeys("chitturaju");
	        driver.findElement(By.id("submit")).click();

	    }
	
}
