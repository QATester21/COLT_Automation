package pageObjects.cpqObjects;

import java.io.File;

import baseClasses.GlobalVariables;
import baseClasses.ReadExcelFile;
import baseClasses.ReadingAndWritingTextFile;

public class CPQQuoteApprovalsObj {
	
	static ReadingAndWritingTextFile readText=new ReadingAndWritingTextFile();
	static String quoteId = readText.getQuoteId().trim();
	
	public static class QuoteApprovals
	 { 
	///	public static final String SEQuoteID="@xpath=//a[contains(text(),'"+quoteId+"')]"; //Qoute ID		
		
		//SE Approvals
		public static final String SEQuoteID="@xpath=//a[contains(text(),'"+quoteId+"')]"; //Qoute ID		
	//	public static final String TechnicalApprovalLink="@xpath=//span[@class='oj-tabbar-item-label'][text()='Technical Approval']";
		public static final String SubmittoCSTApprovalBtn="@xpath=//button//*[contains(text(),'Submit to CST Approval')]";
	
		//CST Approvals
		public static final String CSTQuoteID="@xpath=//a[contains(text(),'"+quoteId+"')]"; //Qoute ID		
		public static final String ApproveBtn="@xpath=//span[@class='oj-button-text'][text()='Approve']";
		public static final String ApproveBtnElem="//span[@class='oj-button-text'][text()='Approve']";
		
		public static final String Logoutbtn="@xpath=//img[@title='Log out']";
		
		public static final String lineItemGridTable="//table[@aria-label='Line Item Grid']";
		public static final String billingInfoPopup="@xpath=//div[@class='panel-col oj-flex-item oj-form popup_on_viewbcn oj-sm-labels-inline']";
		public static final String selectProductChbx="@id=sELECT_A-1";
		public static final String applyBtn="@name=apply";
		public static final String closeBtn="@xpath=//div[@class='panel-col oj-flex-item oj-form popup_on_viewbcn oj-sm-labels-inline']/descendant::button[@name='close']";
		
	 }

}
