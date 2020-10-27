package testHarness.cpqFunctions;

import baseClasses.SeleniumUtils;
import pageObjects.cpqObjects.CPQLoginObj;

public class CPQLoginPage extends SeleniumUtils 
{

	public void CPQLogin(String userName, String password) throws InterruptedException
	{
		waitForElementToAppear(CPQLoginObj.Login.userNameTxbx, 10);
		verifyExists(CPQLoginObj.Login.userNameTxbx,"Username Textbox field");
		sendKeys(CPQLoginObj.Login.userNameTxbx, userName,"Username textbox field");
		verifyExists(CPQLoginObj.Login.passwordTxbx,"Password textbx field");
		sendKeys(CPQLoginObj.Login.passwordTxbx, password,"password textbox field");
		verifyExists(CPQLoginObj.Login.loginBtn,"Login button");
		click(CPQLoginObj.Login.loginBtn,"Login button");
		waitForAjax();
		waitForElementToAppear(CPQLoginObj.Login.adminSettingLogo, 10);
		verifyExists(CPQLoginObj.Login.adminSettingLogo,"admin setting logo");
	}
	
}
