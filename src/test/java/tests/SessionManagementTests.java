package tests;

import base.BaseTest;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.AddContactPage;
import pages.ContactListPage;
import pages.LoginPage;
import utils.ConfigReader;

public class SessionManagementTests extends BaseTest {

	String baseUrl = ConfigReader.get("base.url");

	@Test(priority = 1)
	public void testLogoutRedirectsToLogin() {
		driver.get(baseUrl);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("chitturaju96@gmail.com", "chitturaju");
		ContactListPage contactListPage = new ContactListPage(driver);
		contactListPage.clickLogout();
		Assert.assertTrue(loginPage.isLoginPageDisplayed(), "User should be redirected to Login page after logout");
	}

	@Test(priority = 2)
	public void testLoginStateOnRefresh() {
		driver.get(baseUrl);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("chitturaju96@gmail.com", "chitturaju");
		driver.findElement(By.id("submit")).click();
		ContactListPage contactListPage = new ContactListPage(driver);
		driver.navigate().refresh();
		Assert.assertTrue(contactListPage.isContactListDisplayed(),
				"User should remain logged in after refreshing the page");
	}

	@Test(priority = 3)
	public void testLoginRequiredForContactList() {
		driver.get(baseUrl + "/contact-list");
		LoginPage loginPage = new LoginPage(driver);
		Assert.assertTrue(loginPage.isLoginPageDisplayed(),
				"User should be redirected to Login page if not logged in and accessing contact list");
	}

	@Test(priority = 4)
	public void testBackButtonAfterLogout() {
		driver.get(baseUrl);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("chitturaju96@gmail.com", "chitturaju");
		driver.findElement(By.id("submit"));
		ContactListPage contactListPage = new ContactListPage(driver);
		contactListPage.clickLogout();
		driver.navigate().back();
		Assert.assertTrue(loginPage.isLoginPageDisplayed(),
				"After logout, browser back should not return to contact list");
	}
	@Test(priority = 5)
	public void testContactFormAlignment() {
		driver.get(baseUrl);
	    LoginPage loginPage = new LoginPage(driver);
	    loginPage.login("chitturaju96@gmail.com", "chitturaju");
	    ContactListPage contactListPage = new ContactListPage(driver);
	    AddContactPage addContactPage = contactListPage.clickAddContact();

	    
	    Assert.assertTrue(addContactPage.isFirstNameFieldDisplayed(), "First Name field is missing");
	    Assert.assertTrue(addContactPage.isLastNameFieldDisplayed(), "Last Name field is missing");
	    Assert.assertTrue(addContactPage.isEmailFieldDisplayed(), "Email field is missing");
	    Assert.assertTrue(addContactPage.isPhoneFieldDisplayed(), "Phone field is missing");
	    Assert.assertTrue(addContactPage.isSubmitButtonDisplayed(), "Save button is missing");
	    Assert.assertTrue(addContactPage.isCancelButtonDisplayed(), "Cancel button is missing");

	   
	    takeScreenshot("AddContactFormAlignment.png");
	}

}
