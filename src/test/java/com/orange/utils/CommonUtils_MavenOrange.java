package com.orange.utils;

import org.openqa.selenium.By;

import com.orange.base.BaseClass_MavenOrange;

public class CommonUtils_MavenOrange extends BaseClass_MavenOrange{
	
	public static boolean validLogin() throws Exception {
		
		driver.findElement(By.xpath(getLocatorFromMap("Login", "UserName_Editbox"))).sendKeys(getTestDataFromMap("Login", "UserName_Editbox"));
		driver.findElement(By.xpath(getLocatorFromMap("Login", "Password_Editbox"))).sendKeys(getTestDataFromMap("Login", "Password_Editbox"));
		driver.findElement(By.xpath(getLocatorFromMap("Login", "Login_Button"))).click();
		
		boolean welcomeText = driver.findElement(By.xpath(getLocatorFromMap("Home", "Welcome_text"))).isDisplayed();
		
		return welcomeText;
	}

	public static boolean inValidLogin() throws Exception {
		
		driver.findElement(By.xpath(getLocatorFromMap("Login", "UserName_Editbox"))).sendKeys(getTestDataFromMap("Login", "InvalidUserName_Editbox"));
		driver.findElement(By.xpath(getLocatorFromMap("Login", "Password_Editbox"))).sendKeys(getTestDataFromMap("Login", "InvalidPassword_Editbox"));
		driver.findElement(By.xpath(getLocatorFromMap("Login", "Login_Button"))).click();
		
		boolean errorMsg = driver.findElement(By.xpath(getLocatorFromMap("Login", "Error_Msg"))).isDisplayed();
		
		return errorMsg;
	}

	public static boolean validLoginWithParameters(String userName, String password) throws Exception{
		boolean welcomeText = true;
		
		driver.findElement(By.xpath(getLocatorFromMap("Login", "UserName_Editbox"))).sendKeys(userName);
		driver.findElement(By.xpath(getLocatorFromMap("Login", "Password_Editbox"))).sendKeys(password);
		driver.findElement(By.xpath(getLocatorFromMap("Login", "Login_Button"))).click();
		
		welcomeText = driver.findElement(By.xpath(getLocatorFromMap("Home", "Welcome_text"))).isDisplayed();
		
		return welcomeText;
		
	}

	public static boolean inValidLoginWithData(String userName, String password) throws Exception{
		
		driver.findElement(By.xpath(getLocatorFromMap("Login", "UserName_Editbox"))).sendKeys(userName);
		driver.findElement(By.xpath(getLocatorFromMap("Login", "Password_Editbox"))).sendKeys(password);
		driver.findElement(By.xpath(getLocatorFromMap("Login", "Login_Button"))).click();
		
		boolean errorMsg = driver.findElement(By.xpath(getLocatorFromMap("Login", "Error_Msg"))).isDisplayed();
		return errorMsg;
	}

}
