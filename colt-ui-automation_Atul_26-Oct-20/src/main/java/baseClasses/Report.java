package baseClasses;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.Reporter;


public class Report extends SeleniumUtils
{
	private static Utilities suiteConfig = new Utilities();
	
	static int Pass = 0;
	static int Fail = 0;
	static long Executionstart = 0;
	static long Executionend =0;
	static String duration ="";
	static int overallPass = 0;
	static int overallFail = 0;
	static long start = 0;
	static long end =0;
	public static long intOverallDuration;
	private static int counter=1;
	static Utilities ut = new Utilities();
	static GlobalVariables g = new GlobalVariables();
	
	
	public static void createSummaryReportHeader(String Summaryfile)
	{
		String topHeader="#ffffff";
		String fontColor = "BLACK";
		String TableHeader = "#ffffff";
		
		FileOutputStream out;
        PrintStream p; 

        String tmpBrowser;
        tmpBrowser = g.getBrowser();
        String environment = suiteConfig.getValue("Environment", "Glistr");
        
        if((tmpBrowser.toUpperCase()).indexOf("IE")!= -1)
        {
        	tmpBrowser = "(Internet Explorer)";
        }
        else if((tmpBrowser.toUpperCase()).indexOf("FIREFOX")!= -1)
        {
        	tmpBrowser = "(Firefox)";
        }
        else if((tmpBrowser.toUpperCase()).indexOf("CHROME")!= -1)
        {
        	tmpBrowser = "(Google Chrome)";
        }
        else if((tmpBrowser.toUpperCase()).indexOf("SAFARI")!= -1)
        {
        	tmpBrowser = "(Safari)";
        }
        else if((tmpBrowser.toUpperCase()).indexOf("EDGE")!= -1)
        {
        	tmpBrowser = "(EDGE)";
        }
       
		g.setSummaryReportFile(Summaryfile);

        try
        {
            out = new FileOutputStream(Summaryfile);
            p = new PrintStream( out );
            
            String header="<html><head><title>CTAF Automation Execution Results</title></head><Body>";
            header+= "<style type=text/css>"+
            		 "body {"+
            		 "height:100%;"+
            		 "background: linear-gradient(rgba(0,165,155,1.0),rgba(0,165,155,1.0)), url(https://rfsidm.colt.net/nidp/colt/imagesV1/colt_logo_RGB_teal.svg) repeat;"+
            		 "}"+
            		 "</style>";
            header +="<p align = center><table border=2 id=table1 width=65% height=31>" +
					"<tr>" +
						"<td COLSPAN = 4 bgcolor = "+topHeader+">";
            header += "<div class=grid_3>"+
            		"<img src="+g.getRelativePath()+"//src//main//resources//colt_logo.png>"+
            		"</div>";
			header+= "<p align=center><font color="+fontColor+" size=5>"+"CTAF "+suiteConfig.getValue("Project", "Test")+" Automation Execution Results on "+environment +"<B></font></p>";
			header +="</td>" +
					"</tr>"+
					"<tr>"+
						"<td COLSPAN = 4 bgcolor = #ffffff>"+
							"<p align=justify><b><font color="+fontColor+" size=2 face= Verdana>DATE:"+ suiteConfig.getCurrentDatenTime("dd MMMMM yyyy 'at' HH:mm:ss")+
						"</td>" +
					"</tr>";
			header+="<tr bgcolor="+TableHeader+">" +
						"<td align =center><b>Test Case Name</b></td>"+
						"<td align =center><b>Description</b>	</td>"+
						"<td align =center><b>Status</b></td>"+
						"<td align =center><b>Execution Time</b></td>"+
					"</tr>";
			
			p.println (header);

            p.close();	
        }
        catch (Exception e)
        {
        	System.err.println ("Error in creating summary report header");
        	System.out.println(e.getMessage());
        	
        }
	
	}
	
	public static void SummaryReportlog(String testname,String testDescription)
	{
		 BufferedWriter bw = null;
		 String step = null;
		 String status=null;
		 String statuscolor=null;
		 
		 //Getting the SummaryReportFile from GlobalVariables Class
		 GlobalVariables g = new GlobalVariables();
		 String SummaryReportFile = g.getSummaryReportFile();
		 
		 if (Fail>0)
	     {
	     	overallFail++;
	     	status = "FAIL";
	     	statuscolor = "<font color = red><B>";
	     	Fail=0;
	     	
	     }
	     else if (Pass > 0)
	     {
	     	overallPass++;
	     	status = "PASS";
	     	statuscolor = "<font color = green><B>";
	     	Pass=0;
	     	
	     }
	     else
	     {
	     	status = "INFO";
	     	statuscolor = "<font color = black><B>";
	     }
		 
	      try 
	      {
	    	  //Opening the SummaryReport File in append mode to add the steps of test summary
	    	  	bw = new BufferedWriter(new FileWriter(SummaryReportFile, true));
	    	  	step = "<tr bgcolor=#ffffff>" +
	    	  				"<td><a href='"+testname+".html" + "'" + "target=" + "about_blank" + ">"+testname+"</a></td>" +
	    	  				"<td>"+testDescription+"</td>" +
	    	  				"<td>"+statuscolor+status+"</td>" +
	    	  				"<td>"+duration+"</td>" +
	    	  			"</tr>";
	    	  	
	    	  	bw.write(step);
	    	  	bw.newLine();
	      }
	      catch (Exception e)
	      {
	    	System.out.println("Error While Adding a Log infor in Summary Report File");
	    	System.out.println(e.getMessage());
	      }
	      finally
	      {
	    	  try
	    	  {
	    		  bw.close();
	    	  }
	    	  catch (Exception e)
	    	  {
	    		  System.out.println("Error While Closing the file object of Summary Report file");
	  	    	System.out.println(e.getMessage());
	    	  }
	      }
	}
	
	public static void SummaryReportlog(String testDescription)
	{
		 BufferedWriter bw = null;
		 String step = null;
		 String status=null;
		 String statuscolor=null;
		 
		 //Getting the SummaryReportFile from GlobalVariables Class
		 GlobalVariables g = new GlobalVariables();
		 String SummaryReportFile = g.getSummaryReportFile();
		 
		 if (Fail>0)
	     {
	     	overallFail++;
	     	status = "FAIL";
	     	statuscolor = "<font color = red><B>";
	     	Fail=0;
	     	
	     }
	     else if (Pass > 0)
	     {
	     	overallPass++;
	     	status = "PASS";
	     	statuscolor = "<font color = green><B>";
	     	Pass=0;
	     	
	     }
	     else
	     {
	     	status = "INFO";
	     	statuscolor = "<font color = black><B>";
	     }
		 
	      try 
	      {
	    	  //Opening the SummaryReport File in append mode to add the steps of test summary
	    	  	bw = new BufferedWriter(new FileWriter(SummaryReportFile, true));
	    	  	step = "<tr bgcolor=#ffffff>" +
	    	  				"<td><a href='"+Reporter.getCurrentTestResult().getName()+".html" + "'" + "target=" + "about_blank" + ">"+Reporter.getCurrentTestResult().getName()+"</a></td>" +
	    	  				"<td>"+testDescription+"</td>" +
	    	  				"<td>"+statuscolor+status+"</td>" +
	    	  				"<td>"+duration+"</td>" +
	    	  			"</tr>";
	    	  	
	    	  	bw.write(step);
	    	  	bw.newLine();
	      }
	      catch (Exception e)
	      {
	    	System.out.println("Error While Adding a Log infor in Summary Report File");
	    	System.out.println(e.getMessage());
	      }
	      finally
	      {
	    	  try
	    	  {
	    		  bw.close();
	    	  }
	    	  catch (Exception e)
	    	  {
	    		  System.out.println("Error While Closing the file object of Summary Report file");
	  	    	System.out.println(e.getMessage());
	    	  }
	      }
	}
	public static void createSummaryReportFooter()
	{
		 BufferedWriter bw = null;
		 String step = null;
		 
		 //Getting the SummaryReportFile from GlobalVariables Class
		 GlobalVariables g = new GlobalVariables();
		 String SummaryReportFile = g.getSummaryReportFile();
		 
		 
	      try 
	      {
	    	  //Opening the SummaryReport File in append mode to add the steps of test summary
	    	  	bw = new BufferedWriter(new FileWriter(SummaryReportFile, true));
				step="<tr>"+
						"<td COLSPAN = 4 bgcolor = #ffffff>"+
							"<p align=right><b><font color=GREEN size=2 face= Verdana>Total Pass: "+overallPass+" </b>"+
							"<p align=right><b><font color=RED size=2 face= Verdana>Total Fail: "+overallFail+" </b>"+
							"<p align=right><b><font color=BLACK size=2 face= Verdana>Total Execution Time: "+getFormattedTime(intOverallDuration)+"</b>"+
							"</td>"+
					 "</tr>";
				
	    	  	bw.write(step);
	    	  	bw.newLine();
	      }
	      catch (Exception e)
	      {
	    	System.out.println("Error while creating footer in Summary Report File");
	    	System.out.println(e.getMessage());
	      }
	      finally
	      {
	    	  try
	    	  {
	    		  bw.close();
	    	  }
	    	  catch (Exception e)
	    	  {
	    		  
	    	  }
	      }
	      
	}


	public static void createTestCaseReportHeader()
	{
		String topHeader="#ffffff";
		String fontColor = "BLACK";
		String TableHeader = "ffffff";
		Fail = 0;
		
		FileOutputStream out;
        PrintStream p; 

        
        try
        {
        	GlobalVariables g = new GlobalVariables();
            out = new FileOutputStream(g.getResultFolderPath()+"\\"+ Reporter.getCurrentTestResult().getName()+".html");
            
            
   		 	g.setTestCaseReportFile(g.getResultFolderPath()+"\\"+Reporter.getCurrentTestResult().getName()+".html");
   		 	
            p = new PrintStream( out );
            
            String header="<html><head><title>Automation Execution Results</title></head><Body>";
            header+= "<style type=text/css>"+
           		 "body {"+
           		 "height:100%;"+
           		 "background: linear-gradient(rgba(0,165,155,1.0),rgba(0,165,155,1.0)), url(https://rfsidm.colt.net/nidp/colt/imagesV1/colt_logo_RGB_teal.svg) repeat;"+
           		
           		 "}"+
           		 "</style>";
			header +="<p align = center><table border=2 id=table1 width=65% height=31>" +
					"<tr>" +
						"<td COLSPAN = 6 bgcolor = "+topHeader+">";
			header += "<div class=grid_3>"+
            		"<img src="+g.getRelativePath()+"//src//main//resources//colt_logo.png>"+
            		"</div>";
			header+= "<p align=center><font color="+fontColor+" size=5>"+Reporter.getCurrentTestResult().getName()+" Automation Test Case Details <b> </font></p>";
			header +="</td>" +
					"</tr>";
			header+="<tr bgcolor="+TableHeader+">" +
						"<td align =center><b>Test Steps</b></td>"+
						"<td align =center><b>Description</b>	</td>"+
						"<td align =center><b>Status</b></td>"+
						"<td align =center><b>Time</b></td>"+
					"</tr>";
			
			p.println (header);

            p.close();	
            start = System.currentTimeMillis();     
        }
        catch (Exception e)
        {
        	System.err.println ("Error in creating Test Case Detailed report header");
        	System.out.println(e.getMessage());
        	
        }
        
	
	}
	
	public static void createTestCaseReportHeader(String testname)
	{
		String topHeader="#ffffff";
		String fontColor = "BLACK";
		String TableHeader = "#ffffff";
		
		FileOutputStream out;
        PrintStream p; 

        try
        {
        	GlobalVariables g = new GlobalVariables();
            out = new FileOutputStream(g.getResultFolderPath()+"\\"+testname+".html");
            
            
   		 	g.setTestCaseReportFile(g.getResultFolderPath()+"\\"+testname+".html");
   		 	
            p = new PrintStream( out );
            
            String header="<html><head><title>Automation Execution Results</title></head><Body>";
            header+= "<style type=text/css>"+
              		 "body {"+
              		 "height:100%;"+
              		 "background: linear-gradient(rgba(0,165,155,1.0),rgba(0,165,155,1.0)), url(https://rfsidm.colt.net/nidp/colt/imagesV1/colt_logo_RGB_teal.svg) repeat;"+
              		
              		 "}"+
              		 "</style>";
            header +="<p align = center><table border=2 id=table1 width=65% height=31>" +
					"<tr>" +
						"<td COLSPAN = 6 bgcolor = "+topHeader+">";
            header += "<div class=grid_3>"+
            		"<img src="+g.getRelativePath()+"//src//main//resources//colt_logo.png>"+
            		"</div>";
			header+= "<p align=center><font color="+fontColor+" size=5>"+testname+" Automation Test Case Details <b> </font></p>";
			header +="</td>" +
					"</tr>";
			header+="<tr bgcolor="+TableHeader+">" +
						"<td align =center><b>Test Steps</b></td>"+
						"<td align =center><b>Description</b>	</td>"+
						"<td align =center><b>Status</b></td>"+
						"<td align =center><b>Time</b></td>"+
					"</tr>";
			
			p.println (header);

            p.close();	
            start = System.currentTimeMillis();     
        }
        catch (Exception e)
        {
        	System.err.println ("Error in creating Test Case Detailed report header");
        	System.out.println(e.getMessage());
        	
        }
        
	
	}

	public static void LogInfo(String step,String Description, String Status)
	{
		 BufferedWriter bw = null;
		 String statusColor="";
		 String ScreenShotPath = null;
		 //Utilities ut = new Utilities();
		 //ut.getValue("Screenshot", "false");
		 //Getting the SummaryReportFile from GlobalVariables Class
		 GlobalVariables g = new GlobalVariables();
		 String TestCaseFile = g.getTestCaseReportFile();
		 
		 if (Status.trim().equalsIgnoreCase("PASS"))
		 {
			 	Pass++;
			 	if(ut.getValue("Screenshot", "false").trim().toUpperCase().equalsIgnoreCase("TRUE"))
			 	{
			 		String screenshotFileName = Reporter.getCurrentTestResult().getName() +"_"+step+"_"+counter+"Pass.jpg";;
			 		ScreenShotPath = g.getResultFolderPath()+"\\Screenshots\\"+screenshotFileName;

			 		if(g.getGridMode().trim().equalsIgnoreCase("OFF"))
					{
						takeScreenShots(webDriver,ScreenShotPath,"webdriver");					
					}else
					{
						takeScreenShots(webDriver,ScreenShotPath,"remote");
						
					}
			 		statusColor = "<a href=..\\"+timestamp+"\\Screenshots\\"+screenshotFileName+" target=_blank>";
			 		counter++;
			 	}
				statusColor =  statusColor + "<font color = green>";
			
		 }
		 else if (Status.trim().equalsIgnoreCase("FAIL"))
		 {
			Fail++;
			String screenshotFileName = Reporter.getCurrentTestResult().getName() +"_"+step+"_"+counter+"Fail.jpg";;
			ScreenShotPath = g.getResultFolderPath()+"\\Screenshots\\"+screenshotFileName;
			//takeScreenShots(webDriver, ScreenShotPath);
			statusColor = "<a href=..\\"+timestamp+"\\Screenshots\\"+screenshotFileName+" target=_blank>";
			
			if(suiteConfig.getValue("GridMode", "OFF").trim().equalsIgnoreCase("OFF"))
			{
				takeScreenShots(webDriver, ScreenShotPath,"webdriver");
			}else
			{
				takeScreenShots(webDriver, ScreenShotPath,"remote");
			}
			
			statusColor =statusColor + "<font color = red>";
			counter++;
		 }
		 else if(Status.trim().equalsIgnoreCase("WARNING"))
		 {
			 statusColor ="<font color = #8A4B08>";
		 }
		 else
		 {
			 statusColor ="<font color = black>";
		 }
		 
	      try 
	      {
	    	  //Opening the SummaryReport File in append mode to add the steps of test summary
	    	  	bw = new BufferedWriter(new FileWriter(TestCaseFile, true));
	    	  	step = "<tr bgcolor=#ffffff>" +
	    	  				"<td>"+step+"</a></td>" +
	    	  				"<td>"+Description+"</td>" +
	    	  				"<td><b>"+statusColor+Status+"</b></td>" +
	    	  				"<td>"+suiteConfig.getCurrentDatenTime("H:mm:ss")+"</td>" +
	    	  			"</tr>";
	    	  	
	    	  	bw.write(step);
	    	  	bw.newLine();
	      }
	      catch (Exception e)
	      {
	    	System.out.println("Error While Adding a Log info in Test Case Report File");
	    	System.out.println(e.getMessage());
	      }
	      finally
	      {
	    	  try
	    	  {
	    		  bw.close();
	    	  }
	    	  catch (Exception e)
	    	  {
	    		  System.out.println("Error While Closing the file object of Test Case Report file");
	  	    	System.out.println(e.getMessage());
	    	  }
	      }
	}
	
	public static void createTestCaseReportFooter()
	{
		
		
		end = System.currentTimeMillis();
		duration=getFormattedTime(end-start);
		intOverallDuration = intOverallDuration + (end-start);
		
		BufferedWriter bw = null;
		String step = null;
		 
		 //Getting the SummaryReportFile from GlobalVariables Class
		 GlobalVariables g = new GlobalVariables();
		 String TestCaseFile = g.getTestCaseReportFile();
		 
		 
	      try 
	      {
	    	  	bw = new BufferedWriter(new FileWriter(TestCaseFile, true));
				step="<tr>"+
						"<td COLSPAN = 4 bgcolor = #ffffff>"+
							"<p align=right><b><font color=GREEN size=2 face= Verdana>Total Verification Pass: "+Pass+" </b>"+
							"<p align=right><b><font color=RED size=2 face= Verdana>Total Verification Fail: "+Fail+" </b>"+
							"<p align=right><b><font color=BLACK size=2 face= Verdana>Total Execution Time: "+duration+" </b>"+
						 "</td>"+
					 "</tr>";
				
	    	  	bw.write(step);
	    	  	bw.newLine();
	      }
	      catch (Exception e)
	      {
	    	System.out.println("Error While creating footer in Test Case Report File");
	    	System.out.println(e.getMessage());
	      }
	      finally
	      {
	    	  try
	    	  {
	    		  bw.close();
	    	  }
	    	  catch (Exception e)
	    	  {
	    		  
	    	  }
	      }
	      
	      
	}
	
	public static String getFormattedTime(long time)
    {
    	long timeMillis = time;   
    	long time1 = timeMillis / 1000;   
    	String seconds = Integer.toString((int)(time1 % 60));   
    	String minutes = Integer.toString((int)((time1 % 3600) / 60));   
    	String hours = Integer.toString((int)(time1 / 3600));   
    	for (int i = 0; i < 2; i++) {   
    	if (seconds.length() < 2) {   
    	seconds = "0" + seconds;   
    	}   
    	if (minutes.length() < 2) {   
    	minutes = "0" + minutes;   
    	}   
    	if (hours.length() < 2) {   
    	hours = "0" + hours;   
    	}   
    	}  
    	return hours+":Hr "+minutes+":Min "+seconds+":Sec";

    }
	
	 
	public static void takeScreenShots(WebDriver driver,String path,String type)
	{
	        try
	        {
	        	if(type.trim().toUpperCase().equalsIgnoreCase("REMOTE"))
	        	{
		        	
		        	WebDriver augmentedDriver = new Augmenter().augment(driver);
			        File screenshotFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
			        FileUtils.copyFile(screenshotFile, new File(path));
	        	}else
	        	{
		    		File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		        	FileUtils.copyFile(screenshotFile, new File(path));
	        	}
	        }
	        catch (IOException e)
	        {
				e.printStackTrace();
			}
	}	 
}

