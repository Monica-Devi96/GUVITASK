package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DeleteContactPage {
	private WebDriver driver;
	private WebDriverWait wait;

	public DeleteContactPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// If the delete button ID ever changes, you can use a fallback XPath
	private final By deleteBtn = By.id("delete");

	public void waitForPage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(deleteBtn));
	}

	public void clickDelete() {
		WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(deleteBtn));
		btn.click();
	}

	// Optional: generic click by button name (if page has other buttons)
	public void clickButtonByText(String buttonText) {
		String xpath = "//button[normalize-space()='" + buttonText + "']";
		WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		btn.click();
	}
}
