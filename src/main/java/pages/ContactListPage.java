package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ContactListPage {
	private WebDriver driver;
	private WebDriverWait wait;

	public ContactListPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	private By table = By.cssSelector("table.contactTable");
	private By rows = By.cssSelector("table.contactTable tbody tr");

	public void waitForPage() {
		new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.visibilityOfElementLocated(table));
	}

	public boolean isContactPresent(String name) {
	    return driver.findElements(
	            By.xpath("//table[contains(@class,'contactTable')]//tr[td[normalize-space()='" + name + "']]")).size() > 0;
	}


	public void clickContactName(String fullName) {
		waitForPage();

		String xpath = "//table[contains(@class,'contactTable')]//tr/td[text()='" + fullName + "']";

		WebElement nameCell = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		try {
			nameCell.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", nameCell);
		}
	}

	public AddContactPage clickAddContact() {
		By addContactButton = By.id("add-contact");
		wait.until(ExpectedConditions.elementToBeClickable(addContactButton)).click();
		return new AddContactPage(driver);
	}

	public boolean isContactListDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-contact")));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getEmailForContact(String fullName) {
		waitForPage();
		String xpath = "//table[contains(@class,'contactTable')]//tr[td[text()='" + fullName + "']]/td[2]";
		WebElement emailCell = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		return emailCell.getText().trim();
	}

	public String getPhoneForContact(String fullName) {
		waitForPage();
		String xpath = "//table[contains(@class,'contactTable')]//tr[td[text()='" + fullName + "']]/td[3]";
		WebElement phoneCell = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		return phoneCell.getText().trim();
	}

	public void clickLogout() {
		wait.until(ExpectedConditions.elementToBeClickable(By.id("logout"))).click();
	}

	public void waitUntilContactIsDeleted(String name) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    wait.until(driver -> driver.findElements(
	        By.xpath("//table[contains(@class,'contactTable')]//tr[td[normalize-space()='" + name + "']]"))
	        .isEmpty());
	}




}
