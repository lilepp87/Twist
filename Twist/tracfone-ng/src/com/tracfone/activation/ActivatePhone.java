package com.tracfone.activation;

// JUnit Assert framework can be used for verification

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import com.tracfone.ESNUtil;
import com.tracfone.PhoneTwistDaoImpl;
import com.tracfone.SimTwistDaoImpl;

public class ActivatePhone {
	
	private WebDriver browser;
	@Autowired
	private PhoneTwistDaoImpl phoneUtil;
	@Autowired
	private SimTwistDaoImpl simUtil;
	@Autowired
	private ESNUtil esnUtil;

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public ActivatePhone(WebDriver browser) {
		this.browser = browser;
	}
	
//	public ActivatePhone() {
//		
//	}
	
	public void setPhoneUtil(PhoneTwistDaoImpl phoneUtil) {
		this.phoneUtil = phoneUtil;
	}

	public void setSimUtil(SimTwistDaoImpl simUtil) {
		this.simUtil = simUtil;
	}

	public void setEsnUtil(ESNUtil esnUtil) {
		this.esnUtil = esnUtil;
	}

	public void chooseToActivateBrandedPhone() throws Exception {
//		String str = phoneUtil.getNewEsnByPartNumber("TFLGL48VCPAS");
//		System.out.println(str);
		By linkText = By.linkText("ACTIVATE");
		WebElement el = browser.findElement(linkText);
		el.click();
		el.click();

		List<WebElement> els = browser.findElements(By.xpath("(//button[@type='submit'])"));
		int i =0 ;
//		for (WebElement el : els) {
//			try {
//				el.click();
//				System.out.println(i + " passed");
//			} catch(Exception e) {
////				e.printStackTrace();
//				System.out.println(i + " failed");
//			}
//			i++;
//		}
		els.get(1).click();
//		WebDriverWait wait = new WebDriverWait(browser, 20);
//		wait.until(ExpectedConditions.elementToBeClickable(By.id("terms")));
		List<WebElement> findElements = browser.findElements(By.id("tfvalesn-esn"));
		System.out.println(findElements.size());
//		browser.findElement(By.id("tfvalesn-esn")).sendKeys("12345678901");
//		findElements.get(0).sendKeys("12345678901");
//		Twist
		String esn = phoneUtil.getNewEsnByPartNumber("TFLGL48VCPAS");
		findElements.get(1).sendKeys(esn);
//		browser.findElement(By.id("terms")).click();
		browser.findElement(By.xpath("//div[2]/div/label")).click();
		browser.findElement(By.xpath("(//button[@type='submit'])")).click();
	}

}
