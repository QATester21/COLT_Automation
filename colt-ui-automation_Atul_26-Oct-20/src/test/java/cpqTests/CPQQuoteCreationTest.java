package cpqTests;

import org.testng.annotations.Test;

import baseClasses.Report;
import baseClasses.SeleniumUtils;
import testHarness.cpqFunctions.CPQQuoteCreation;


@Test(singleThreaded=true)
public class CPQQuoteCreationTest extends SeleniumUtils
{
	CPQQuoteCreation AddProduct = new CPQQuoteCreation();
	
	@Test(priority = 1, groups={"testAddProductToQuote"})
	public void testAddProductToQuote()
	{
		Report.createTestCaseReportHeader();	
		
		try
		{
			AddProduct.AddProductToQuote();

		}catch(Exception e)
		{
			Report.LogInfo("Exception", "Exception in testAddProductToQuote"+e.getMessage(), "FAIL");
		} 
		Report.createTestCaseReportFooter();
		Report.SummaryReportlog("Add Product to Quote");
		
	}	
	
	@Test(priority = 2, groups={"testAddProductDetailsToQuote"}, dependsOnGroups = { "testAddProductToQuote" })
	public void testAddProductDetailsToQuote()
	{
		Report.createTestCaseReportHeader();	
		
		try
		{
			AddProduct.AddProductDetailsToQuote();

		}catch(Exception e)
		{
			Report.LogInfo("Exception", "Exception in testAddProductDetailsToQuote"+e.getMessage(), "FAIL");
		} 
		Report.createTestCaseReportFooter();
		Report.SummaryReportlog("Add Product Details to Quote");
		
	}	
	
	@Test(priority = 3, groups={"testEnterLegalAndTechnicalContactDetails"}, dependsOnGroups = { "testAddProductDetailsToQuote" })
	public void testEnterLegalAndTechnicalContactDetails()
	{
		Report.createTestCaseReportHeader();	
		
		try
		{
			
			AddProduct.EnterLegalAndTechnicalContactDetails();

		}catch(Exception e)
		{
			Report.LogInfo("Exception", "Exception in testEnterLegalAndTechnicalContactDetails "+e.getMessage(), "FAIL");
		} 
		Report.createTestCaseReportFooter();
		Report.SummaryReportlog("Enter Legal & Technical Contact Details");
		
	}	
	
	
	@Test(priority = 4, groups={"testSubmissionForCommercialApproval"}, dependsOnGroups = { "testEnterLegalAndTechnicalContactDetails" })
	public void testSubmissionForCommercialApproval()
	{
		Report.createTestCaseReportHeader();	
		
		try
		{
			
			AddProduct.SubmissionForCommercialApproval();

		}catch(Exception e)
		{
			Report.LogInfo("Exception", "Exception in testSubmissionForCommercialApproval "+e.getMessage(), "FAIL");
		} 
		Report.createTestCaseReportFooter();
		Report.SummaryReportlog("Submission for Commercial Approval");
		
	}	
	
	@Test(priority = 5, groups={"testSubmissionForTechnicalApproval"}, dependsOnGroups = { "testSubmissionForCommercialApproval" })
	public void testSubmissionForTechnicalApproval()
	{
		Report.createTestCaseReportHeader();	
		
		try
		{
			AddProduct.SubmissionForTechnicalApproval();

		}catch(Exception e)
		{
			Report.LogInfo("Exception", "Exception in testSubmissionForTechnicalApproval "+e.getMessage(), "FAIL");
		} 
		Report.createTestCaseReportFooter();
		Report.SummaryReportlog("Submission for Technical Approval");
		
	}	
	
}
