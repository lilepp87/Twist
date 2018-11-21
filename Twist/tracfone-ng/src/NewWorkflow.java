// JUnit Assert framework can be used for verification

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;

public class NewWorkflow {

	private WebDriver browser;

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public NewWorkflow(WebDriver browser) {
		this.browser = browser;
	}

	public void test() throws Exception {
//		browser.navigate().to("https://sitcing.tracfone.com");
		browser.get("https://sitcing.tracfone.com");
		browser.findElement(By.linkText("ACTIVATE")).click();
		browser.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
//		WebDriverWait wait = new WebDriverWait(browser, 20);
//		wait.until(ExpectedConditions.elementToBeClickable(By.id("terms")));
		List<WebElement> findElements = browser.findElements(By.id("tfvalesn-esn"));
		System.out.println(findElements.size());
//		browser.findElement(By.id("tfvalesn-esn")).sendKeys("12345678901");
//		findElements.get(0).sendKeys("12345678901");
//		Twist
		findElements.get(1).sendKeys("12345678901");
//		browser.findElement(By.id("terms")).click();
		browser.findElement(By.xpath("//div[2]/div/label")).click();
		browser.findElement(By.xpath("(//button[@type='submit'])")).click();
	}

}