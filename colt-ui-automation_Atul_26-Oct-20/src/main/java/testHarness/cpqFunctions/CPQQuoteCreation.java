package testHarness.cpqFunctions;

import baseClasses.Configuration;
import baseClasses.GlobalVariables;
import baseClasses.ReadExcelFile;
import baseClasses.SeleniumUtils;
import pageObjects.cpqObjects.CPQLoginObj;
import pageObjects.cpqObjects.CPQQuoteCreationObj;
import testHarness.commonFunctions.ReusableFunctions;
import java.io.File;

public class CPQQuoteCreation extends SeleniumUtils
{
	GlobalVariables g = new GlobalVariables();
	CPQLoginPage Login = new CPQLoginPage();
	ReusableFunctions Reusable = new ReusableFunctions();

	static ReadExcelFile read = new ReadExcelFile();
	static File path = new File(".\\src\\test\\resources\\TestData.xls");
	static String testDataFile = path.toString();
	String sheetname="CPQData";
	
	String CountryCode = "+91";

	public void AddProductToQuote() throws Exception
	{
		webDriver.get(Configuration.CPQ_URL);
		Login.CPQLogin(Configuration.SalesUser1_Username, Configuration.SalesUser1_Password);
		click(CPQLoginObj.Login.adminSettingLogo, "admin setting logo");
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.TransactionPage.TransactionIcn, 5);
		verifyExists(CPQQuoteCreationObj.TransactionPage.TransactionIcn,"Transaction logo");
		click(CPQQuoteCreationObj.TransactionPage.TransactionIcn, "Transaction logo");
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.TransactionPage.QuoteID, 10);
		verifyExists(CPQQuoteCreationObj.TransactionPage.QuoteID,"Created Quote ID");
		click(CPQQuoteCreationObj.TransactionPage.QuoteID, "Created Quote ID");
		Reusable.WaitforC4Cloader();
		Reusable.WaitforCPQloader();
		waitForAjax();
		Reusable.Waittilljquesryupdated();
		Reusable.WaitforC4Cloader();
		Reusable.WaitforCPQloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.AddProductionPage.AddProductBtn, 20);
		verifyExists(CPQQuoteCreationObj.AddProductionPage.AddProductBtn,"Add product button");
		click(CPQQuoteCreationObj.AddProductionPage.AddProductBtn, "Add product button");
		waitForAjax();
		Reusable.Waittilljquesryupdated();
		waitForElementToAppear(CPQQuoteCreationObj.AddProductionPage.IpAccessDrpDwn, 10);
		verifyExists(CPQQuoteCreationObj.AddProductionPage.IpAccessDrpDwn,"IP Access Dropdown");
		click(CPQQuoteCreationObj.AddProductionPage.IpAccessDrpDwn, "IP Access Dropdown");
		verifyExists(CPQQuoteCreationObj.AddProductionPage.ColtIpAccessOption,"Colt IP Access Option");
		click(CPQQuoteCreationObj.AddProductionPage.ColtIpAccessOption, "Colt IP Access Option");
		waitForAjax();
		verifyExists(CPQQuoteCreationObj.AddProductionPage.StartBtn,"Start button");
		click(CPQQuoteCreationObj.AddProductionPage.StartBtn, "Start button");
		waitForAjax();
	}
	
	public void AddProductDetailsToQuote() throws Exception
	{
        //**************** Primary Connection > Primary Address*******************************************//
		waitForAjax();
		Reusable.Waittilljquesryupdated();
		verifyExists(CPQQuoteCreationObj.PrimaryAddressPage.PrimaryAddressHeader,"Primary Address header");
		verifyExists(CPQQuoteCreationObj.PrimaryAddressPage.SiteAddresslbl,"Site address lable");
		verifyExists(CPQQuoteCreationObj.PrimaryAddressPage.SiteAddTxFld,"Site address text field");
		sendKeys(CPQQuoteCreationObj.PrimaryAddressPage.SiteAddTxFld,read.getDataFromCell(testDataFile,sheetname,1,0),"Site Address");
		verifyExists(CPQQuoteCreationObj.PrimaryAddressPage.SearchBtn, "Search Button");
		click(CPQQuoteCreationObj.PrimaryAddressPage.SearchBtn, "Search Button");
		Reusable.WaitforC4Cloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, 15);
		verifyExists(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn,"Next button");
		click(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next button");
		
		//*******************Primary Connection > Primary Connection *****************************//
		Reusable.WaitforCPQloader();
		waitForAjax();
		Reusable.WaitforCPQloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.PrimaryConnectionPage.ServiceBandwidthLbl, 10);
		verifyExists(CPQQuoteCreationObj.PrimaryConnectionPage.ServiceBandwidthLbl,"Service Bandwidth lable");
		verifyExists(CPQQuoteCreationObj.PrimaryConnectionPage.ServiceBandwidthDrpDwn,"Service Bandwidth DropDown");
		click(CPQQuoteCreationObj.PrimaryConnectionPage.ServiceBandwidthDrpDwn, "Servie Bandwidth DropDown");
		verifyExists(CPQQuoteCreationObj.PrimaryConnectionPage.SericeBandwitdthVal,"Service Bandwidth 10 Gbps value");
		click(CPQQuoteCreationObj.PrimaryConnectionPage.SericeBandwitdthVal, "Service Bandwidth 10 Gbps value");
		Reusable.WaitforCPQloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, 15);
		verifyExists(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn,"Next button");
		click(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next button");
		
		//*******************Primary Connection > IP Features *****************************//
		Reusable.WaitforCPQloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.IPFeaturesPage.RouterTypeLbl, 15);
		verifyExists(CPQQuoteCreationObj.IPFeaturesPage.RouterTypeLbl, "Router type field label");		
		verifyExists(CPQQuoteCreationObj.IPFeaturesPage.RouterTypeDrpDwn, "Router type DropDown");
		click(CPQQuoteCreationObj.IPFeaturesPage.RouterTypeDrpDwn, "Router type DropDown");
		verifyExists(CPQQuoteCreationObj.IPFeaturesPage.RouterTypeVal, "Customer Provided Router Value");
		click(CPQQuoteCreationObj.IPFeaturesPage.RouterTypeVal, "Customer Provided Router Value");
		Reusable.WaitforCPQloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, 15);
		verifyExists(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn,"Next button");
		click(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next button");
		
		//*******************Primary Connection > Site Addons*******************//
		Reusable.WaitforCPQloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, 15);
		verifyExists(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn,"Next button");
		click(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next button");
		
		//*******************L3 Resilience*******************//
		Reusable.WaitforCPQloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, 15);
		verifyExists(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next Button");
		click(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next Button");
	
		
		//******************* Diversity *******************//
		Reusable.WaitforCPQloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, 15);
		verifyExists(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next Button");
		click(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next Button");
		
		//******************* Service Addons *******************//
		Reusable.WaitforCPQloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, 15);
		verifyExists(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next Button");
		click(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next Button");
		
		//******************* Bespoke Features *******************//
		Reusable.WaitforCPQloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, 15);
		verifyExists(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next Button");
		click(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next Button");
		
		
		//********************** Additional Product Data > Primary Site ****************************//
		Reusable.WaitforCPQloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.AdditionalProductDataPage.PrimaryHdrLbl, 15);
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.PrimaryHdrLbl, "Primay Header Label");
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.SiteAccessTechnologyLbl, "Site Access Technology Label");
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.SiteAccessTechnologyDrpDwn, "Site Access Technology DropDown");
		click(CPQQuoteCreationObj.AdditionalProductDataPage.SiteAccessTechnologyDrpDwn, "Site Access Technology DropDown");
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.EthernetVal, "Site Access Technology DropDown Value");
		click(CPQQuoteCreationObj.AdditionalProductDataPage.EthernetVal, "Site Access Technology DropDown value");
		Reusable.WaitforCPQloader();
		waitForAjax();
		
		waitForElementToAppear(CPQQuoteCreationObj.AdditionalProductDataPage.SiteCabinetIDLbl, 15);
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.SiteCabinetIDLbl, "Site Cabinet ID field Label");
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.SiteCabinetIDFld, "Site Cabinet ID Text Field");
		sendKeys(CPQQuoteCreationObj.AdditionalProductDataPage.SiteCabinetIDFld,read.getDataFromCell(testDataFile,sheetname,4,0),"Site Cabinet ID");
		waitForElementToAppear(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, 15);
		verifyExists(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next Button");
		click(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next Button");
		
		//********************** Additional Product Data > Service Information ****************************//
				
		Reusable.WaitforCPQloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.AdditionalProductDataPage.RouterTechnologyLbl, 15);
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.RouterTechnologyLbl, "Router Technology field Label");
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.RouterTechnologyDrpDwn, "Router Technology field dropdown");
		click(CPQQuoteCreationObj.AdditionalProductDataPage.RouterTechnologyDrpDwn, "Router Technology field dropdown");
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.ManagedVirRouterVal, "Managed Virtual Router value");
		click(CPQQuoteCreationObj.AdditionalProductDataPage.ManagedVirRouterVal, "Managed Virtual Router value");
		Reusable.WaitforCPQloader();
		waitForAjax();
		
		waitForElementToAppear(CPQQuoteCreationObj.AdditionalProductDataPage.ExistingCapcityLeadTimeLbl, 15);
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.ExistingCapcityLeadTimeDrpDwn, "Existing Capacity Lead Time Dropdown");
		click(CPQQuoteCreationObj.AdditionalProductDataPage.ExistingCapcityLeadTimeDrpDwn, "Existing Capacity Lead Time Dropdown");
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.CapacityLeadTimeNoValue, "Capacity Lead Time No Value");
		click(CPQQuoteCreationObj.AdditionalProductDataPage.CapacityLeadTimeNoValue, "Capacity Lead Time No Value");
		Reusable.WaitforCPQloader();
		waitForAjax();
		
		waitForElementToAppear(CPQQuoteCreationObj.AdditionalProductDataPage.FirstNameTxtFld, 10);
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.FirstNameTxtFld, "First Name Text Field");
		sendKeys(CPQQuoteCreationObj.AdditionalProductDataPage.FirstNameTxtFld,read.getDataFromCell(testDataFile,sheetname,8,0),"First Name");
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.LastNameTxtFld, "Last Name Text Field");
		sendKeys(CPQQuoteCreationObj.AdditionalProductDataPage.LastNameTxtFld,read.getDataFromCell(testDataFile,sheetname,8,1),"Last Name");
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.ContactNumberTxtFld, "Contact Number Text Field");
		String ContactNumber = CountryCode + read.getDataFromCell(testDataFile,sheetname,8,2);
		sendKeys(CPQQuoteCreationObj.AdditionalProductDataPage.ContactNumberTxtFld,ContactNumber,"Contact Number");
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.MobileNumberTxtFld, "Mobile Number Text Field");
		String MobileNumber = CountryCode + read.getDataFromCell(testDataFile,sheetname,8,3);
		sendKeys(CPQQuoteCreationObj.AdditionalProductDataPage.MobileNumberTxtFld,MobileNumber,"Mobile Number");
		
		verifyExists(CPQQuoteCreationObj.AdditionalProductDataPage.EmailTxtFld, "Email Id Field");
		sendKeys(CPQQuoteCreationObj.AdditionalProductDataPage.EmailTxtFld, "test@gmail.com","Email Id Field");
		waitForElementToAppear(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, 15);
		verifyExists(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next Button");
		click(CPQQuoteCreationObj.PrimaryAddressPage.NextBtn, "Next Button");
		
		//******************** Prices and promotions ******************************//
		Reusable.WaitforCPQloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.PricesAndPromotionPage.PromotionsOffer, 20);
		verifyExists(CPQQuoteCreationObj.PricesAndPromotionPage.PromotionsOffer, "Promotions Offer Image");
		click(CPQQuoteCreationObj.PricesAndPromotionPage.PromotionsOffer, "Promotions Offer Image");
		Reusable.WaitforCPQloader();
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.PricesAndPromotionPage.SaveToQuoteBtn, 10);
		verifyExists(CPQQuoteCreationObj.PricesAndPromotionPage.SaveToQuoteBtn, "Save to Quote Button");
		click(CPQQuoteCreationObj.PricesAndPromotionPage.SaveToQuoteBtn, "Save to Quote Button");	
	}
	
	public void EnterLegalAndTechnicalContactDetails() throws Exception
	{
		
	/*	webDriver.get(Configuration.CPQ_URL);
		Login.CPQLogin(Configuration.SalesUser1_Username, Configuration.SalesUser1_Password);
		click(CPQLoginObj.Login.adminSettingLogo, "admin setting logo");
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.TransactionPage.TransactionIcn, 5);
		verifyExists(CPQQuoteCreationObj.TransactionPage.TransactionIcn,"Transaction logo");
		click(CPQQuoteCreationObj.TransactionPage.TransactionIcn, "Transaction logo");
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.TransactionPage.QuoteID, 10);
		verifyExists(CPQQuoteCreationObj.TransactionPage.QuoteID,"Created Quote ID");
		click(CPQQuoteCreationObj.TransactionPage.QuoteID, "Created Quote ID");
		Reusable.WaitforC4Cloader();
		Reusable.WaitforCPQloader();
		waitForAjax();
		Reusable.Waittilljquesryupdated();
		
		*/
		
		
		
		//*************************************** Get Legal Contact Details *******************************//
		Reusable.WaitforC4Cloader();
		Reusable.WaitforCPQloader();
		waitForAjax();
		Reusable.Waittilljquesryupdated();
		Reusable.waitForpageloadmask();
		
		waitForElementToAppear(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.LegalContactDetailslink, 20);
		verifyExists(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.LegalContactDetailslink, "Legal Contact details link");
		click(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.LegalContactDetailslink, "Legal Contact details link");
		
		waitForElementToAppear(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.LegalContactDetailsBtn, 20);
		verifyExists(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.LegalContactDetailsBtn, "Legal Contact details button");
		click(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.LegalContactDetailsBtn, "Legal Contact details button");
		
		
		String currentHandle =webDriver.getWindowHandle();
		switchWindow(Configuration.ContactDetails_URL,"Manage contacts");
		
		waitForElementToAppear(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.ManageContactsNextBtn, 15);
		verifyExists(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.ManageContactsNextBtn, "Manage Contacts Next button");
		click(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.ManageContactsNextBtn, "Manage Contacts Next button");
		
		waitForElementToAppear(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.SelectLegalContact, 15);
		verifyExists(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.SelectLegalContact, "Select Legal Contact");
		click(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.SelectLegalContact, "Select Legal Contact");
	
		
	/*	waitForElementToAppear(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.UseOCHContact, 15);
		verifyExists(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.UseOCHContact, "Use OCH Contact button");
		click(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.UseOCHContact, "Use OCH Contact button");
	*/
		waitForAjax();
		webDriver.switchTo().window(currentHandle);
		
		//**************************** Get Techanical Contact Details****************************//
	
		waitForElementToAppear(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.TechanicalDetailsLink, 10);
		verifyExists(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.TechanicalDetailsLink, "Technical Contact Details link");
		click(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.TechanicalDetailsLink, "Technical Contact Details link");
		
		verifyExists(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.TechanicalDetailsBtn, "Get Technical Contact Details button");
		click(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.TechanicalDetailsBtn, "Get Technical Contact Details button");
		
        switchWindow(Configuration.ContactDetails_URL,"Manage contacts");
		
		waitForElementToAppear(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.ManageContactsNextBtn, 15);
		verifyExists(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.ManageContactsNextBtn, "Manage Contacts Next button");
		click(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.ManageContactsNextBtn, "Manage Contacts Next button");
		
		waitForElementToAppear(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.SelectLegalContact, 15);
		verifyExists(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.SelectLegalContact, "Select Legal Contact");
		click(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.SelectLegalContact, "Select Legal Contact");
	
	/*	waitForElementToAppear(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.UseOCHContact, 15);
		verifyExists(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.UseOCHContact, "Use OCH Contact button");
		click(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.UseOCHContact, "Use OCH Contact button");
	*/
		waitForAjax();
		webDriver.switchTo().window(currentHandle);
		
		waitForElementToAppear(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.SaveBtn, 15);
		verifyExists(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.SaveBtn, "Save Button");
		click(CPQQuoteCreationObj.LegalAndTechnicalContactDetails.SaveBtn, "Save Button");
		waitForAjax();
		
	}
	public void SubmissionForCommercialApproval() throws Exception
	{
		
		
	/*	webDriver.get(Configuration.CPQ_URL);
		Login.CPQLogin(Configuration.SalesUser1_Username, Configuration.SalesUser1_Password);
		click(CPQLoginObj.Login.adminSettingLogo, "admin setting logo");
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.TransactionPage.TransactionIcn, 5);
		verifyExists(CPQQuoteCreationObj.TransactionPage.TransactionIcn,"Transaction logo");
		click(CPQQuoteCreationObj.TransactionPage.TransactionIcn, "Transaction logo");
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.TransactionPage.QuoteID, 10);
		verifyExists(CPQQuoteCreationObj.TransactionPage.QuoteID,"Created Quote ID");
		click(CPQQuoteCreationObj.TransactionPage.QuoteID, "Created Quote ID");
		Reusable.WaitforC4Cloader();
		Reusable.WaitforCPQloader();
		waitForAjax();
		Reusable.Waittilljquesryupdated();
		
		
		*/
	
		///////////////////////////////////////////////
	    Reusable.WaitforC4Cloader();
	    Reusable.WaitforCPQloader();
	    waitForElementToAppear(CPQQuoteCreationObj.SubmissionForCommercialApproval.CommercialApprovalLink, 15);
	    scrollUp();
	    verifyExists(CPQQuoteCreationObj.SubmissionForCommercialApproval.CommercialApprovalLink, "Commercial Approval link");
		click(CPQQuoteCreationObj.SubmissionForCommercialApproval.CommercialApprovalLink, "Commercial Approval link");
		verifyExists(CPQQuoteCreationObj.SubmissionForCommercialApproval.SubmitToApprovalBtn, "Submit To Approval Button");
		click(CPQQuoteCreationObj.SubmissionForCommercialApproval.SubmitToApprovalBtn, "Submit To Approval Button");
	
	}
	
	public void SubmissionForTechnicalApproval() throws Exception
	{
	/*	webDriver.get(Configuration.CPQ_URL);
		Login.CPQLogin(Configuration.SalesUser1_Username, Configuration.SalesUser1_Password);
		click(CPQLoginObj.Login.adminSettingLogo, "admin setting logo");
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.TransactionPage.TransactionIcn, 5);
		verifyExists(CPQQuoteCreationObj.TransactionPage.TransactionIcn,"Transaction logo");
		click(CPQQuoteCreationObj.TransactionPage.TransactionIcn, "Transaction logo");
		waitForAjax();
		waitForElementToAppear(CPQQuoteCreationObj.TransactionPage.QuoteID, 10);
		verifyExists(CPQQuoteCreationObj.TransactionPage.QuoteID,"Created Quote ID");
		click(CPQQuoteCreationObj.TransactionPage.QuoteID, "Created Quote ID");
		Reusable.WaitforC4Cloader();
		Reusable.WaitforCPQloader();
		waitForAjax();
		Reusable.Waittilljquesryupdated();
		
	*/
		///////////////////////////////////////////////
	    Reusable.WaitforCPQloader();
	    waitForAjax();
	    Reusable.Waittilljquesryupdated();
	    waitForElementToAppear(CPQQuoteCreationObj.SubmissionForTechnicalApproval.TechnicalApprovalLink, 15);
	    scrollUp();
	    verifyExists(CPQQuoteCreationObj.SubmissionForTechnicalApproval.TechnicalApprovalLink, "Technical Approval link");
		click(CPQQuoteCreationObj.SubmissionForTechnicalApproval.TechnicalApprovalLink, "Technical Approval link");
		verifyExists(CPQQuoteCreationObj.SubmissionForTechnicalApproval.ReadyForTechnicalApprovalRd, "Ready for Technical Approval radio button");
		click(CPQQuoteCreationObj.SubmissionForTechnicalApproval.ReadyForTechnicalApprovalRd, "Ready for Technical Approval radio button");
		verifyExists(CPQQuoteCreationObj.SubmissionForTechnicalApproval.SubmitForTechnicalApprovalBtn, "Submit for Technical approval button");
		click(CPQQuoteCreationObj.SubmissionForTechnicalApproval.SubmitForTechnicalApprovalBtn, "Submit for Technical approval button");
		Reusable.WaitforCPQloader();
		waitForAjax();
		Reusable.Waittilljquesryupdated();
		waitForElementToAppear(CPQQuoteCreationObj.Logout.LogOutLink, 20);
		verifyExists(CPQQuoteCreationObj.Logout.LogOutLink,"Log out button");
		click(CPQQuoteCreationObj.Logout.LogOutLink,"Log out button");
		waitForAjax();
	}
	
}
