package testHarness.commonFunctions;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import baseClasses.SeleniumUtils;
import pageObjects.cpqObjects.CPQQuoteApprovalsObj;


public class ReusableFunctions extends SeleniumUtils
{
	public void WaitforC4Cloader() throws IOException, InterruptedException {
		 
		int i = 1;
		Thread.sleep(1000);   
		
          try {
        	  while (webDriver.findElement(By.xpath("//*[contains(@title,'Please wait')]")).isDisplayed()) {   
        		  if (i > 60) { break; }
                      Thread.sleep(1000);            
                      i = i+1;
                } 
          } catch(Exception e) {
        	  Waittilljquesryupdated();
          
          }
	}
	
	public void WaitforCPQloader() throws IOException, InterruptedException {
		int i = 1;
		Thread.sleep(1000);   
		
          try {
        	  while (webDriver.findElement(By.xpath("//*[@id='loading-mask']")).isDisplayed()) {   
        		  if (i > 60) { break; }
                      Thread.sleep(1000);
                      System.out.println("Waiting CPQ Page to load");
                      i = i+1;
                } 
          } catch(Exception e) {
        	  Waittilljquesryupdated();
          }
	}
	
	public void Waittilljquesryupdated() throws InterruptedException, SocketTimeoutException {
		
		boolean Status = false;
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		for (int i=1; i<10; i++) {
			if (js == null) {
				Thread.sleep(250);
				js = (JavascriptExecutor) webDriver;
				continue;
			} else {
				try {
					while(!(js.executeScript("return document.readyState").equals("complete")))
					{
						Thread.sleep(500);
					}
					Status = true;
					if (Status = true) { Thread.sleep(250); break; }
				} catch (Exception e) {
					continue;
				}
			}
		}	
	}
	
	public void waitForpageloadmask() throws InterruptedException
	{    
		boolean Status = false;
		Thread.sleep(1500);
		for (int j=0; j < 10; j++) {
			try {
				try {
					  for(int i=0;i<=20;i++) {
						  while(webDriver.findElement(By.xpath("//div[@id='lockCreateScreen' and not(@style='display: none;')]")).isDisplayed()) {
								Thread.sleep(1000);
								Status = true;
	//							System.out.println("Load Mask Displayed");
						  }
					  }
				} catch(Exception e) {
					Thread.sleep(1000);
					Waittilljquesryupdated();
	//				System.out.println("Load Mask Not Displayed");
	//				System.out.println(e.getMessage().toString());
				}
			} catch (SocketTimeoutException e1) {
				continue;
			}
			if (Status = true) { break; }
		} 
				
		Thread.sleep(300);
	}
	
	public String WebTableCellAction(String refColumn, String rowRef, String actColumn, String ActionType, String ActionValue) throws IOException, InterruptedException 
	{
			
		int row_number = 0; String tXpath = null, Row_Val;
		
		if (fluentWaitForElementToBeVisible(webDriver.findElement(By.xpath(CPQQuoteApprovalsObj.QuoteApprovals.lineItemGridTable)), 35)) {
			scrollIntoView(webDriver.findElement(By.xpath(CPQQuoteApprovalsObj.QuoteApprovals.lineItemGridTable)));
			String sXpath = CPQQuoteApprovalsObj.QuoteApprovals.lineItemGridTable.toString();
			tXpath = sXpath.substring(sXpath.indexOf("xpath:")+"xpath:".length(), sXpath.indexOf("]]")+1).trim();
		} else {
			
			return "False";
		}
		
//		getting the row and column number
		List<WebElement> rows = webDriver.findElement(By.xpath(CPQQuoteApprovalsObj.QuoteApprovals.lineItemGridTable)).findElements(By.tagName("tr"));
		List<WebElement> columns = rows.get(0).findElements(By.tagName("th"));
		int tot_row = rows.size();
		int tot_col = columns.size();
//		System.out.println("Total Column size is "+tot_col);
		int iCol, iRow, rColumn_number = 0, aColumn_number = 0;
		
		//Reading the column headers of table and set the column number with use of reference
		for(iCol = 1; iCol <= tot_col-1; iCol++){
			String Col_Val = columns.get(iCol).getAttribute("title").trim();
			if (Col_Val.equals(refColumn)){ 
				rColumn_number = iCol+1; 
//				System.out.println("ref Column number is "+rColumn_number); 
				break; 
			}
		}
		
//		Returns the function of reference column number
		if (iCol >= tot_col) {
			return "False";
		}		
		
		if (!ActionType.equals("Click")) {
			//Reading the actual column number ff table and set the column number with use of reference
			for(iCol = 1; iCol <= tot_col-1; iCol++){
				String Col_Val = columns.get(iCol).getAttribute("title").trim();
				if (Col_Val.contains(actColumn)){ 
					aColumn_number = iCol+1; 
//					System.out.println("act Column number is "+aColumn_number); 
					break; 
				}
			}
		
//			Returns the function of column names are not matched
			if (iCol >= tot_col) {
				return "False";
			}
		}
		
		//Taking the row value
		for(iRow =1; iRow < tot_row-1; iRow++){
			WebElement rowValue = webDriver.findElement(By.xpath(tXpath+"/tbody/tr["+iRow+"]/td["+rColumn_number+"]//child::span[contains(@data-bind,'text')]"));
			Row_Val = rowValue.getAttribute("title").trim();
			if (Row_Val.equalsIgnoreCase(rowRef)){ 
				row_number = iRow; 
//				System.out.println("Row number is "+row_number);
				break;
			}
		}
		
//		Returns the function if rows names are not matched
		if (iRow >= tot_row-1) {
			return "False";
		}
		
		waitForpageloadmask();
		
		WebElement Cell; String sOut = null;
		switch (ActionType) {
			case "Edit":
				Cell = webDriver.findElement(By.xpath(tXpath+"/tbody/tr["+row_number+"]/td["+aColumn_number+"]"));
				scrollIntoView(Cell);
				click(tXpath+"/tbody/tr["+row_number+"]/td["+aColumn_number+"]");
				//WebElement edit_Box = webDriver.findElement(By.xpath(tXpath+"/tbody/tr["+row_number+"]/td["+aColumn_number+"]//input"));
//				WebElement edit_Box = driver.findElement(By.xpath(tXpath+"/tbody/tr["+row_number+"]/td["+aColumn_number+"]"));
				sendKeys(tXpath+"/tbody/tr["+row_number+"]/td["+aColumn_number+"]//input", ActionValue);
				sOut = "True";
				break;
			case "Select":
				Cell = webDriver.findElement(By.xpath(tXpath+"/tbody/tr["+row_number+"]/td["+aColumn_number+"]"));
				scrollIntoView(Cell);
				Cell.click();
				WebElement dropdown = webDriver.findElement(By.xpath("//descendant::ul[@role='listbox']"));
				List<WebElement> options = dropdown.findElements(By.tagName("li"));
				for (WebElement option : options)
				{
				    if (option.getText().equals(ActionValue))
				    {
				        option.click(); // click the desired option
				        break;
				    }
				}
				sOut = "True";
				break;
			case "Store":
				Cell = webDriver.findElement(By.xpath(tXpath+"/tbody/tr["+row_number+"]/td["+aColumn_number+"]//child::span"));
				scrollIntoView(Cell);
				sOut = Cell.getAttribute("title");
				break;
				
			case "Click":
				Cell = webDriver.findElement(By.xpath(tXpath+"/tbody/tr["+row_number+"]/td["+rColumn_number+"]"));
				scrollIntoView(Cell);
				click(tXpath+"/tbody/tr["+row_number+"]/td["+rColumn_number+"]");
				sOut = "True";
				break;
		}
		
		return sOut;
	
	}
	
	public void UploadFile(String locator, String filepath) throws InterruptedException, IOException 
	{
	
		webDriver.findElement(By.xpath(locator)).sendKeys(filepath);
		
	}
	
}
	

