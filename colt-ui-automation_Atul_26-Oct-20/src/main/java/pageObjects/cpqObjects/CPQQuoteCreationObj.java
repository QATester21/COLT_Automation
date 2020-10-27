package pageObjects.cpqObjects;

import java.io.File;

import baseClasses.ReadExcelFile;

public class CPQQuoteCreationObj
{
	static ReadExcelFile read = new ReadExcelFile();
	static File path = new File(".\\src\\test\\resources\\TestData.xls");
	static String testDataFile = path.toString();
	static String sheetname="CPQData";
	static String quoteId = read.getDataFromCell(testDataFile,sheetname,11,0).trim();
	
	
	public static class TransactionPage
	{
		public static final String TransactionIcn="@xpath=//*[@title='Transaction']"; 
		public static final String QuoteID="@xpath=//a[text()='"+quoteId+"']"; 

	}
	public static class AddProductionPage
	{
		public static final String AddProductBtn="@name=add_product"; 
		public static final String IpAccessDrpDwn="@id=dataVoice-iPProduct"; 
		public static final String ColtIpAccessOption="@xpath=//*[@id='dataVoice-iPProduct']//a[contains(text(),'Colt IP Access')]";
		public static final String StartBtn="@xpath=//a[text()='Start']";
		
	}
	
	public static class PrimaryAddressPage
	{
		public static final String PrimaryAddressHeader="@xpath=//*[contains(text(),'PRIMARY ADDRESS DETAILS')]";
		public static final String SiteAddresslbl="@id=siteAddressAEnd_label";
		public static final String SiteAddTxFld="@id=siteAddressAEnd"; 
		public static final String SearchBtn="@xpath=//*[@title='Search for an Address']"; 
		public static final String NextBtn="@id=next_model"; 
		
	}
	public static class PrimaryConnectionPage
	{
		public static final String ServiceBandwidthLbl="@id=serviceBandwidth_label";
		public static final String ServiceBandwidthDrpDwn="@name=serviceBandwidth";
		public static final String SericeBandwitdthVal="@xpath=//*[@id='serviceBandwidth']//option[contains(@value,'10 Gbps')]";	
	}
	
	public static class IPFeaturesPage
	{
		public static final String RouterTypeLbl="@id=selectRouterTypeAEnd_label";
		public static final String RouterTypeDrpDwn="@id=selectRouterTypeAEnd";
		public static final String RouterTypeVal="@xpath=//*[@id='selectRouterTypeAEnd']//*[contains(@value,'Customer Provided Router')]";	
	}
	
	public static class AdditionalProductDataPage
	{
		public static final String PrimaryHdrLbl="@xpath=//*[text()='Primary']";	
		public static final String SiteAccessTechnologyLbl="@id=siteAccessTechnology_label";
		public static final String SiteAccessTechnologyDrpDwn="@id=siteAccessTechnology";
		public static final String EthernetVal="@xpath=//select[@id='siteAccessTechnology']//option[@value='Ethernet']";	
		public static final String SiteCabinetIDLbl="@id=siteCabinetID_label";
		public static final String SiteCabinetIDFld="@id=siteCabinetID";
		public static final String RouterTechnologyLbl="@id=routerTechnology_label";
		public static final String RouterTechnologyDrpDwn="@id=routerTechnology";
		public static final String ManagedVirRouterVal="@xpath=//select[@id='routerTechnology']//option[contains(@value,'Managed Virtual Router')]";
		public static final String ExistingCapcityLeadTimeLbl="@id=existingCapacityLeadTimeAEnd_label";
		public static final String ExistingCapcityLeadTimeDrpDwn="@id=existingCapacityLeadTimeAEnd";
		public static final String CapacityLeadTimeNoValue="@xpath=//select[@id='existingCapacityLeadTimeAEnd']//option[contains(@value,'No')]";
		public static final String FirstNameTxtFld="@id=firstName";
		public static final String LastNameTxtFld="@id=lastName";
		public static final String ContactNumberTxtFld="@id=contactNumber";
		public static final String MobileNumberTxtFld="@id=mobileNumber";
		public static final String EmailTxtFld="@id=email";
	}
	public static class PricesAndPromotionPage
	{
		public static final String PromotionsOffer="@xpath=//div[@class='cfg-im-image']//img";
		public static final String SaveToQuoteBtn="@id=add_to_transaction";
	}
	
	public static class LegalAndTechnicalContactDetails
	{		
		public static final String LegalContactDetailslink="@xpath=//*[@id='customerInformation_t-label']";
		public static final String LegalContactDetailsBtn="@xpath=//*[@id='legalGetContacts_d']/input";
		public static final String ManageContactsNextBtn="@id=contactList_next";
		public static final String SelectLegalContact="@xpath=//*[@id='searchResults']/tr[3]/td[1]/i";
		public static final String UseOCHContact="@xpath=//a[@onclick='actionUseOCH()']";
		public static final String TechanicalDetailsLink="@id=technicalContactsDetails_t-label";
		public static final String TechanicalDetailsBtn="@xpath=//*[@id='technicalGetContacts_d']/input";
		public static final String SaveBtn="@xpath=//*[@id='ui-id-3']";	
	}
	
	public static class SubmissionForCommercialApproval
	{	
		public static final String CommercialApprovalLink="@xpath=//*[@title='Commercial Approval']";
		public static final String SubmitToApprovalBtn="@xpath=//*[@name='submit_to_approval']//*[text()='Submit to Approval']";
	}
	
	public static class SubmissionForTechnicalApproval
	{	
		public static final String TechnicalApprovalLink="@xpath=//*[@title='Technical Approval']";
		public static final String ReadyForTechnicalApprovalRd="@xpath=//*[@id='fastlaneOption_t0']/parent::span";
		public static final String SubmitForTechnicalApprovalBtn="@xpath=//*[contains(text(),'Submit For Technical Approval')]";		
	}
	public static class Logout
	{	
		public static final String LogOutLink="@xpath=//*[contains(@title,'Log out')]";
	}
}
