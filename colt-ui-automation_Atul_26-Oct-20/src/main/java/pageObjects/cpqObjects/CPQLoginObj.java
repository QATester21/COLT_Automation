package pageObjects.cpqObjects;

import java.io.File;

import baseClasses.ReadExcelFile;

public class CPQLoginObj {
	
	
	public static class Login
	{
		public static final String userNameTxbx="@id=userId"; 
		public static final String passwordTxbx="@name=Ecom_Password"; 
		public static final String loginBtn="@xpath=//button[contains(text(),'Login')]";
		public static final String adminSettingLogo="@xpath=//*[@title='Admin']//parent::a";
		public static final String OracleQuotelink ="@xpath=//a[@class='commerce-sidebar-item'][1]"; 

	}
	
}
