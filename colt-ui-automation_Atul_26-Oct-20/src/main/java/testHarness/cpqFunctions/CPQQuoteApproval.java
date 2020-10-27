package testHarness.cpqFunctions;

import baseClasses.Configuration;
import baseClasses.GlobalVariables;
import baseClasses.ReadExcelFile;
import baseClasses.SeleniumUtils;
import pageObjects.cpqObjects.CPQLoginObj;
import pageObjects.cpqObjects.CPQQuoteApprovalsObj;
import pageObjects.cpqObjects.CPQQuoteCreationObj;
import testHarness.commonFunctions.ReusableFunctions;
import java.io.File;

public class CPQQuoteApproval extends SeleniumUtils 
{
	GlobalVariables g = new GlobalVariables();
	CPQLoginPage Login = new CPQLoginPage();
	ReusableFunctions Reusable = new ReusableFunctions();
	public void QuoteSEApproval() throws Exception
	{
		//Login with CPQ SE 
		openurl(Configuration.CPQ_URL);
		Login.CPQLogin(Configuration.SEUser_Username, Configuration.SEUser_Password);
		waitForAjax();
		
		waitForElementToAppear(CPQLoginObj.Login.OracleQuotelink, 15);
		verifyExists(CPQLoginObj.Login.OracleQuotelink,"Oracle Quote to order -Manager link");
		
		//CPQ SE Approval flow
		click(CPQLoginObj.Login.OracleQuotelink,"Oracle Quote to order -Manager link");
		waitForElementToAppear(CPQQuoteApprovalsObj.QuoteApprovals.SEQuoteID, 15);
		verifyExists(CPQQuoteApprovalsObj.QuoteApprovals.SEQuoteID,"Quote ID");
		click(CPQQuoteApprovalsObj.QuoteApprovals.SEQuoteID,"Quote ID");
		Reusable.WaitforC4Cloader();
		Reusable.WaitforCPQloader();
		waitForAjax();
		Reusable.Waittilljquesryupdated();
		waitForElementToAppear(CPQQuoteCreationObj.SubmissionForTechnicalApproval.TechnicalApprovalLink, 15);
		verifyExists(CPQQuoteCreationObj.SubmissionForTechnicalApproval.TechnicalApprovalLink,"Technical Approval Link");
		click(CPQQuoteCreationObj.SubmissionForTechnicalApproval.TechnicalApprovalLink,"Technical Approval Link");
		waitForAjax();
		waitForElementToAppear(CPQQuoteApprovalsObj.QuoteApprovals.SubmittoCSTApprovalBtn, 10);
		verifyExists(CPQQuoteApprovalsObj.QuoteApprovals.SubmittoCSTApprovalBtn,"Submit to CST Approval button");
		click(CPQQuoteApprovalsObj.QuoteApprovals.SubmittoCSTApprovalBtn,"Submit to CST Approval button");
		Reusable.WaitforCPQloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.Logout.LogOutLink, 20);
		verifyExists(CPQQuoteCreationObj.Logout.LogOutLink,"Log out button");
		click(CPQQuoteCreationObj.Logout.LogOutLink,"Log out button");
		waitForAjax();
		
	}
}
