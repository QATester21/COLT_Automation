package cpqTests;

import org.testng.annotations.Test;

import baseClasses.Report;
import baseClasses.SeleniumUtils;
import testHarness.cpqFunctions.CPQQuoteApproval;
import testHarness.cpqFunctions.CPQQuoteCreation;

@Test(singleThreaded=true)
public class CPQQuoteApprovalTest extends SeleniumUtils
{
	CPQQuoteApproval Approval = new CPQQuoteApproval();
	
	@Test(priority = 6, groups={"testQuoteSEApproval"}, dependsOnGroups = { "testSubmissionForTechnicalApproval" })
	public void testQuoteSEApproval()
	{
		Report.createTestCaseReportHeader();	
		
		try
		{
			Approval.QuoteSEApproval();

		}catch(Exception e)
		{
			Report.LogInfo("Exception", "Exception in testQuoteSEApproval "+e.getMessage(), "FAIL");
		} 
		Report.createTestCaseReportFooter();
		Report.SummaryReportlog("Quote SE Approval flow");
		
	}	
	

	
	
}
