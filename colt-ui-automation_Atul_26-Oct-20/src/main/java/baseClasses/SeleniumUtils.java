package baseClasses;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.CookieStore;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import javax.swing.JOptionPane;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;
import org.yaml.snakeyaml.Yaml;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import com.opera.core.systems.OperaDriver;


public class SeleniumUtils {
	public static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap<ITestResult, List<Throwable>>();
	public static WebDriver webDriver=null;
	public static final String EMPTY_KEYWORD = "{empty}";
	public static final String SPACE_KEYWORD = "{space}";
	private final long waitForWebElementTimeOut = 35000l;
	private final long waitForWebElementNotExistTimeOut = 1l;

	public SendEmail email=new SendEmail();
	static PrintWriter write = new PrintWriter(System.out, true); 
	public static ReadingAndWritingTextFile file=new ReadingAndWritingTextFile();
	DesiredCapabilities capability;

	private static GlobalVariables g;
	private static Utilities suiteConfig = new Utilities();
	private static String gridMode =null;
	protected static String timestamp=null;
	protected static Utilities util = new Utilities();
	
	SoftAssert softAssert = new SoftAssert();
	static SoftAssert sAssert = new SoftAssert();
	

	public static final ThreadLocal<WebDriver> WEB_DRIVER_THREAD_LOCAL = new InheritableThreadLocal<>();

	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> setBasicInfomationForTestData(String fileName) 
	{

		HashMap<String, Object> basicInfoDataset=null;
		try
		{
			//Report.LogInfo("ReadBasicInfo", "Reading basic url information", "INFO");
			FileInputStream input = new FileInputStream(new File("./src/main/resources/URL_information.yml"));
			Yaml yaml = new Yaml();
			basicInfoDataset = ((HashMap<String, Object>) ((HashMap<String, Object>) yaml.load(input)).get(fileName));

		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
			JOptionPane.showMessageDialog(null, "Error in YML file loading,Check the syntax");
		}
		return basicInfoDataset;
	}

	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> getUrlInfoDataset(HashMap<String, Object> ymlurlDataset, String urlTagName) throws FileNotFoundException 
	{
		return ((HashMap<String, Object>) ymlurlDataset.get(urlTagName));
	}

	@BeforeSuite(alwaysRun = true)
	public void setUp() throws Exception
	{

		//	Report.LogInfo("BeforeSuite", "", "INFO");
		String path = new File(".").getCanonicalPath();
		g = new GlobalVariables();
		g.setRelativePath(path);

		//String brwArray[] =util.getValue("Browsers", "firefox").trim().split(",");
		g.setBrowser(util.getValue("Browsers", "firefox").trim().split(",")[0]);
		//g.setBrowser(util.getValue("Browsers", "firefox").trim());

		g.setGridMode(util.getValue("GridMode", "off").trim());
		g.setHubAddress("http://localhost:4444/wd/hub");

		gridMode = g.getGridMode();

		timestamp = "RegressionSuite_"+suiteConfig.getCurrentDatenTime("MM-dd-yyyy")+"_"+suiteConfig.getCurrentDatenTime("hh-mm-ss_a");

		String resultPath = path+"//CTAFResults//"+timestamp;
		String ScreenshotsPath = resultPath+"//Screenshots";

		g.setResultFolderPath(resultPath);
		g.setTestSuitePath(path+"//src//test//resources//TestSuite.xls");

		new File(resultPath).mkdirs();
		new File(ScreenshotsPath).mkdirs();

		String SummaryReportfile = resultPath+"\\Index.html";

		Report.createSummaryReportHeader(SummaryReportfile);
		Report.createTestCaseReportHeader();

		//Return an Object of HashMap with All values stored from URL_information yml file
		HashMap<String, Object> basicInfoDataset = setBasicInfomationForTestData("url_information");

		//Setting all the Required public variable with values from YML file for Specific Environment
		Configuration.setUrlInfoDataSet(getUrlInfoDataset(basicInfoDataset,Configuration.getURL()));

		//file.setEmailFlag(false);
		//String Automessage=email.getExecutionStartMessage();
		//email.sendMessageWithAttachment("CTAF Automation Regression Test Run Started",Automessage,"",false);
		iniliseBrowser();


		Report.createTestCaseReportFooter();
		Report.SummaryReportlog("Before Suite Run");
	}

	@AfterSuite(alwaysRun=true)
	public void tearDownWebDriver() throws Exception
	{
		softAssert.assertAll();
		sAssert.assertAll();
		Report.createTestCaseReportHeader();
		/**************************Zip the Output generated HTML Files********************************/
		ZipDirectory zip = new ZipDirectory();
		File directoryToZip = new File(g.getResultFolderPath());
		List<File> fileList = new ArrayList<File>();
		System.out.println("---Getting references to all files in: " + directoryToZip.getCanonicalPath());
		zip.getAllFiles(directoryToZip, fileList);
		System.out.println("---Creating zip file");

		String zipOut=g.getResultFolderPath() +"//"+directoryToZip.getName() + ".zip";
		System.out.println(zipOut);
		zip.writeZipFile(directoryToZip, fileList,zipOut);
		System.out.println("---Done");

		//text.setEmailFlag(true);

		//create the summary report footer
		//Report.createSummaryReportFooter();

		//file.setEmailFlag(true);
		closeWebdriver();
	
		zipOut=g.getResultFolderPath() +"//"+directoryToZip.getName() + ".zip";
		Report.createTestCaseReportFooter();
		Report.SummaryReportlog("After Suite Run");
		Report.createSummaryReportFooter();
		/**************************Send the mail with zip output HTML Files********************************/
		//email.sendMessageWithAttachment("CTAF Automated Regression Test Run Completed",file.readEntireFile(g.getResultFolderPath() +"//Index.html"),zipOut,true );

	}

	public void iniliseBrowser() throws FileNotFoundException, IOException, InterruptedException 
	{
		openBrowser(g.getBrowser());
		open("");
		//selenium = new WebDriverBackedSelenium(webDriver,Configuration.url);
	}
	@SuppressWarnings("deprecation")
	public void openBrowser(String browser) {
		try {

			if (browser.equalsIgnoreCase("firefox"))
			{
				System.setProperty("webdriver.gecko.driver",g.getRelativePath()+"\\src\\main\\resources\\geckodriver.exe");
				
				/*FirefoxProfile prof2 = new FirefoxProfile(); 				
				capability= DesiredCapabilities.firefox();
				capability.setCapability("firefox_profile", prof2);
				capability.setCapability("marionette", true); */
				
				File pathToBinary = new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
				FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
				FirefoxProfile firefoxProfile = new FirefoxProfile();

				if(gridMode.equalsIgnoreCase("OFF"))
				{

					webDriver = new FirefoxDriver(ffBinary,firefoxProfile);
				}else
				{
					webDriver= new RemoteWebDriver(new URL(g.getHubAddress()), capability);

				}
				webDriver.manage().window().maximize();

			}
			else if(browser.equalsIgnoreCase("chrome"))
			{
				String driverPath = g.getRelativePath()+"\\src\\main\\resources\\chromedriver.exe";
							
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				Map<String, Object> prefs = new HashMap<String, Object>();
				// Set the notification setting it will override the default setting
				prefs.put("profile.default_content_setting_values.notifications", 2);
				prefs.put("profile.managed_default_content_settings.popups", 2);
				prefs.put("download.default_directory", System.getProperty("user.dir")+"\\src\\Data\\Downloads");
				
				// Create object of ChromeOption class
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);
				options.addArguments("--start-maximized");
				options.addArguments("disable-infobars");
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--force-device-scale-factor=1");
				//options.addArguments("--disable-web-security");
				//options.addArguments("user-data-dir=C:/Users/madha/AppData/Local/Google/Chrome/User Data");
								
				HashMap<String, Object> chromeLocalStatePrefs = new HashMap<String, Object>();
				List<String> experimentalFlags = new ArrayList<String>();                        
				experimentalFlags.add("same-site-by-default-cookies@2");
				experimentalFlags.add("cookies-without-same-site-must-be-secure@1");
				experimentalFlags.add("enable-removing-all-third-party-cookies@2");                        
				chromeLocalStatePrefs.put("browser.enabled_labs_experiments", experimentalFlags);
				options.setExperimentalOption("localState", chromeLocalStatePrefs);
				
				//options.setExperimentalOption("excludeSwitches", "disable-popup-blocking");
				capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "none");
				//capabilities.setCapability(CapabilityType.l, "none");
				LoggingPreferences logs = new LoggingPreferences(); 
			    logs.enable(LogType.DRIVER, Level.ALL); 
				capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				capabilities.setCapability("applicationCacheEnabled", false);
				
				System.setProperty("webdriver.chrome.driver",driverPath);
				
			    if(gridMode.equalsIgnoreCase("OFF"))
				{
					webDriver =  new ChromeDriver(capabilities);
				}else
				{
					capability.setBrowserName("chrome");
					capability.setPlatform(Platform.ANY);
					RemoteWebDriver remoDriver= new RemoteWebDriver(new URL(g.getHubAddress()), capabilities);	
					webDriver = remoDriver;
				}
			}
			else if(browser.equalsIgnoreCase("ie")) 
			{
				System.setProperty("webdriver.ie.driver",g.getRelativePath()+"//src//main//resources//IEDriverServer.exe");
				capability = DesiredCapabilities.internetExplorer();
				capability.setBrowserName("IE");
				capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				capability.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				capability.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
				capability.acceptInsecureCerts();
				//capability.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, Configuration.getConfig("My URL"));
				capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				if(gridMode.equalsIgnoreCase("OFF"))
				{
					webDriver= new InternetExplorerDriver(capability);
				}else
				{
					RemoteWebDriver RemWebDrive= new RemoteWebDriver(new URL(g.getHubAddress()), capability);
					webDriver = RemWebDrive;
				}
			}
			else if(browser.equalsIgnoreCase("edge")) 
			{
				System.setProperty("webdriver.edge.driver",g.getRelativePath()+"\\src\\main\\resources\\msedgedriver.exe");

				if(gridMode.equalsIgnoreCase("OFF"))
				{
					webDriver= new EdgeDriver();
				}else
				{

					RemoteWebDriver RemWebDrive= new RemoteWebDriver(new URL(g.getHubAddress()), capability);
					webDriver = RemWebDrive;
				}
			}
			else if(browser.equalsIgnoreCase("safari")) 
			{
				capability= DesiredCapabilities.safari();
				capability.setBrowserName("safari");
				capability.setJavascriptEnabled(true);
				capability.setPlatform(org.openqa.selenium.Platform.ANY);
				capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
				webDriver= new SafariDriver(capability);		 
			}

			else if(browser.equalsIgnoreCase("opera")) 
			{		
				capability= DesiredCapabilities.opera();
				capability.setCapability("opera.port", "-1");
				capability.setCapability("opera.profile", "");
				webDriver= new OperaDriver(capability);					 
			}

			webDriver.manage().window().maximize();
			WEB_DRIVER_THREAD_LOCAL.set(webDriver);
			webDriver.manage().deleteAllCookies();
			//allowPopups();
		} catch (Exception e) {
			System.out.println("Could not obtain webdriver for browser \n"	+ e.getMessage());
		}
	}
	
	public void allowPopups() throws InterruptedException, AWTException
	{
		Thread.sleep(2000);
		webDriver.get("chrome://settings/content/popups");
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.mouseMove(885,163); 
		robot.delay(1500);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(1500);
		Thread.sleep(2000);	
	    robot.keyPress(KeyEvent.VK_ENTER);
	    Thread.sleep(2000);	
	    robot.keyPress(KeyEvent.VK_TAB);
	    Thread.sleep(2000);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    Thread.sleep(2000);
	}
	

	public void closeWebdriver()
	{
		quit();
	}

	public static void info(String message)
	{
		write.println(message);
		//System.out.println(message);
	}

	public WebDriver getWebDriver() {

		return webDriver;
	}


	public void setWebDriver(WebDriver webDriver) {
		SeleniumUtils.webDriver = webDriver;
	}

	/**
	 * This method will open the url given in param
	 * @param url
	 * @throws Exception 
	 */
	public void open(String url) 
	{
		try {
			if(url.endsWith("/"))
			{
				webDriver.get(Configuration.C4C_URL+url);
			}
			else
			{
				webDriver.get(Configuration.C4C_URL+url);
			}
			setImplicitWaitTimeout(50000);
		}
		catch(RuntimeException e) {
			//throw new Exception("WebDriver unable to handle the request to open url [url='" + url + "']: "+e.getMessage());
		}

	}

	public static void openurl(String url) {

		try {
			webDriver.manage().deleteAllCookies();
			webDriver.navigate().to(url);			 
		}
		catch(RuntimeException e) {
			//	throw new Exception("WebDriver unable to handle the request to open url [url='" + url + "']: "+e.getMessage());
		}
	}


	public void goBack()
	{
		webDriver.navigate().back();
	}

	public void refreshPage()
	{
		webDriver.navigate().refresh();
	}

	public String getTitleOfAnotherWindow(String currentHandle)
	{
		for (String handle : webDriver.getWindowHandles()) 
		{

			if (!currentHandle.equals(handle))
			{
				return webDriver.switchTo().window(handle).getTitle();
			}
		}
		return currentHandle; 
	}

	public void close() {
		try {
			webDriver.close();
		}
		catch(RuntimeException e) {
			//	throw new Exception("WebDriver unable to handle the request to close : "+e.getMessage());
		}
	}

	/**
	 * @throws Exception 
	 * @see #quit()
	 */
	public void quit() {
		try {
			webDriver.quit();
		}
		catch(RuntimeException e) {
			//	throw new Exception("WebDriver unable to handle the request to close browser!"+e.getMessage());
		}
	}

	/**
	 * @see Browser#setImplicitWaitTimeout(int)
	 */
	public void setImplicitWaitTimeout(long milliseconds) {
		if (milliseconds > 0) {
			webDriver.manage().timeouts().implicitlyWait(milliseconds, TimeUnit.MILLISECONDS);
		} else {
			webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		}
	}


	/**
	 * MouseHover on specified locator
	 * Example
	 * 
	 * @param locator
	 * @throws Exception
	 */
	public void mouseOverAction(String locator) {
		WebElement element = findWebElement(locator);

		new Actions(webDriver).moveToElement(element).build().perform();
		//log.debug("Move mouse on locator [element='"+element+"']");
		//	info("Move mouse on locator [locator='"+locator+"']");
		//log.trace("Exiting method mouseOver");
	}


	public void mouseOver(String locator)
	{
		try
		{
			WebElement element = findWebElement(locator);
			Locatable hoverItem = (Locatable) element;
			Report.LogInfo("MouseOver","Mouse over on "+locator +" is done Successfully", "INFO");

		}catch(Exception e)
		{
			Report.LogInfo("MouseOver",locator +" Is not Present on Screen", "FAIL");
			softAssert.fail(locator +" Is not Present on Screen");
		}
	}

	/**
	 * click on specified locator
	 * Example
	 * 
	 * @param locator
	 * @throws Exception
	 */
	public void click(String locator) 
	{
		try

		{
			WebElement element = findWebElement(locator);
			if(g.getBrowser().trim().equalsIgnoreCase("edge"))
			{
				((JavascriptExecutor)webDriver).executeScript("arguments[0].click();",  element);

			}
			else
			{
				element.click();
			}
			locator = element.getAttribute("id");
			Report.LogInfo("Click","\""+locator +"\" Is Clicked Successfully", "INFO");
		}catch(Exception e)
		{
			Report.LogInfo("Click","<font color=red>"+locator +" Is not Present on Screen</font>", "INFO");
		}
	}
	public void javaScriptclick(String locator) 
	{
		try
		{
			WebElement element = findWebElement(locator);
			if(g.getBrowser().equalsIgnoreCase("firefox"))
			{
				element.click();
			}
			else
			{
				((JavascriptExecutor)webDriver).executeScript("arguments[0].click();",  element);
			}			

			Report.LogInfo("Click","\""+locator +"\" Is Clicked Successfully", "INFO");
		}catch(Exception e)
		{
			Report.LogInfo("Click",locator +" Is not Present on Screen", "FAIL");
			softAssert.fail(locator +" Is not Present on Screen");
		}
	}

	public void javaScriptclick(String locator,String Object) 
	{

		try
		{
			WebElement element = findWebElement(locator);
			if(g.getBrowser().equalsIgnoreCase("firefox"))
			{
				element.click();
			}
			else if(g.getBrowser().equalsIgnoreCase("chrome"))
			{
				element.click();
			}
			else 
			{
				((JavascriptExecutor)webDriver).executeScript("arguments[0].click();",  element);
			}			

			Report.LogInfo("Click","\""+Object +"\" Is Clicked Successfully", "INFO");
		}catch(Exception e)
		{
			Report.LogInfo("Click",Object +" Is not Present on Screen", "FAIL");
			softAssert.fail(Object +" Is not Present on Screen");
		}
	}

	public void javaScriptclick1(String locator,String Object) 
	{

		try
		{
			WebElement element = findWebElement(locator);
			((JavascriptExecutor)webDriver).executeScript("arguments[0].click();",  element);			
			Report.LogInfo("Click","\""+locator +"\" Is Clicked Successfully", "INFO");
		}catch(Exception e)
		{
			Report.LogInfo("Click",locator +" Is not Present on Screen", "FAIL");
			softAssert.fail(locator +" Is not Present on Screen");
			throw new SkipException("<b><i>"+locator +"</i></b> Element not found so skipping this exception");

		}
	}
	
	public void javaScriptClickWE(WebElement locator,String Object) 
	{

		try
		{
			//WebElement element = findWebElement(locator);
			((JavascriptExecutor)webDriver).executeScript("arguments[0].click();",  locator);			
			Report.LogInfo("Click","\""+locator +"\" Is Clicked Successfully", "INFO");
		}catch(Exception e)
		{
			Report.LogInfo("Click",locator +" Is not Present on Screen", "FAIL");
			softAssert.fail(locator +" Is not Present on Screen");
			throw new SkipException("<b><i>"+locator +"</i></b> Element not found so skipping this exception");

		}
	}
	
	static String sException = null; static int j = 0;
	public void scrollToViewNClick(String locator,String Object) 
	{
		WebElement element = findWebElement(locator);
		
		try {
				element.click();
    			
    			
		} catch (Exception e1) {
			for (int i = 0; i <= 10; ++i) {
				try {
						Thread.sleep(3000);
						waitToElementVisible(locator);
						scrollIntoView(element);
						Thread.sleep(250);
						element.click();
		    			break;
				} catch (Exception e) {
					sException = e.toString();
					j = i+1;
					continue;
				}	
			}
		}
		if (j > 10) {
			
		}
		
		
	}
	
	 public static boolean scrollIntoView(WebElement webElement) {
		 boolean status = false;
	    	
		 try {
			 	//waitForElementToBeVisible(webElement, 60);
			 	//increases the timeout from 60 to 120
			 	//fluentWaitForElementToBeVisible(webElement, 120);
				String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                        + "var elementTop = arguments[0].getBoundingClientRect().top;"
                        + "window.scrollBy(0, elementTop-(viewPortHeight/2));";
			 	((JavascriptExecutor) webDriver).executeScript(scrollElementIntoMiddle, webElement);
			 	isElementEnabled(webElement);
			 	Thread.sleep(250);
			 	status = true;
			 } catch (Exception e) {
				
			 }
			
		 return status;
	 }

	public void click(String locator,String ObjectName) 
	{
		try
		{
			WebElement element = findWebElement(locator);
			WebDriverWait wait = new WebDriverWait(webDriver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			if(g.getBrowser().trim().equalsIgnoreCase("EDGE"))
			{
				JavascriptExecutor js = (JavascriptExecutor) webDriver;
				((JavascriptExecutor)webDriver).executeScript("arguments[0].click();",  element);
				Report.LogInfo("click","<b><i>"+ObjectName +"</i></b> Is Clicked Successfully", "INFO");	

			}
			else
			{
				element.click();
				Report.LogInfo("click","<b><i>"+ObjectName +"</i></b> Is Clicked Successfully", "INFO");
			}

		}catch(Exception e)
		{
			Report.LogInfo("click","<font color=red"+ObjectName +" Is not Present on Screen</font>", "INFO");
			throw new SkipException("<b><i>"+ObjectName +"</i></b> Element not found so skipping this exception");

		}
	}

	public boolean isClickable(String locator) 
	{
		try
		{
			WebElement element = findWebElement(locator);
			
			if(element.isDisplayed() && element.isEnabled())
			{
				return true;
			}
			else
			{
				return false;
			}

		}catch(Exception e)
		{
			return false;
		}
	}

	public void clickAndWait(String locator) 
	{
		try
		{
			WebElement element = findWebElement(locator);
			element.click();
			javaScriptWait();
			Report.LogInfo("click",locator +" Is Clicked Successfully", "INFO");
		}catch(Exception e)
		{
			Report.LogInfo("Exception", "Exception in clickAndWait "+e.getMessage(), "FAIL");
			softAssert.fail("Exception in clickAndWait "+e.getMessage());
		}
	}


	/**
	 * doubleClick on specified locator
	 * Example
	 * 
	 * @param locator
	 * @throws Exception
	 */
	public void doubleClick(String locator) 
	{
		webDriver.manage().timeouts().setScriptTimeout(waitForWebElementTimeOut, TimeUnit.MILLISECONDS);
		//log.trace("Entering method doubleClick [locator="+locator+"]");
		Report.LogInfo("dobuleClick",locator +" Is Double Clicked Successfully", "INFO");
		WebElement element = findWebElement(locator);

		//log.debug("Found element for locator [element='"+element+"']");
		new Actions(webDriver).moveToElement(element).doubleClick();		
		//info("Click on locator [locator='"+locator+"']");
		//log.trace("Exiting method doubleClick");
	}


	/**
	 * submit on specified locator
	 * Example
	 * 
	 * @param locator
	 * @throws Exception
	 */
	public void submit(String locator)
	{
		try
		{
			WebElement element = findWebElement(locator);
			element.submit();
			Report.LogInfo("submit",locator +" Is Submitted Successfully", "INFO");

		}catch(Exception e)
		{
			Report.LogInfo("submit", locator +" Is not Submitted", "FAIL");
			softAssert.fail(locator +" Is not Submitted");
		}
	}

	public void submitAndWait(String locator)
	{
		try
		{
			WebElement element = findWebElement(locator);
			element.submit();
			javaScriptWait();
			Report.LogInfo("submit",locator +" Is Submitted Successfully", "INFO");
		}catch(Exception e)
		{
			Report.LogInfo("submit",locator +" Is not Submitted Successfully", "FAIL");
			softAssert.fail(locator +" Is not Submitted Successfully");
		}
	}

	/**
	 * Select one option from a single select drop down list
	 * Single Selection: Select option
	 * 
	 * @param locator (@id=option) && xpath
	 * @param field   (@id)
	 * @throws Exception
	 */
	public void select(String locator, String field)
	{
		try
		{
			Select element = new Select(findWebElement(locator));
			selectValue(element, field);
			Report.LogInfo("dropdown",locator +" Is Selected Successfully with option " +field, "INFO");
		}
		catch(Exception e)
		{
			Report.LogInfo("dropdown",locator +" Is not Selected Successfully with option " +field, "FAIL");
			softAssert.fail(locator +" Is not Selected Successfully with option " +field);
		}
	}

	public void selectByIndex(String locator, int index,String ObjectName) 
	{
		try
		{
			Select element = new Select(findWebElement(locator));
			element.selectByIndex(index);
			Report.LogInfo("dropDown",index +" item is Selected in "+ObjectName+" successfully", "INFO");

		}catch(Exception e)
		{
			Report.LogInfo("dropDown", ObjectName + " Not present on Screen", "FAIL");
			softAssert.fail(ObjectName + " Not present on Screen");
		}
	}
	public void selectByValue(String locator, String field,String ObjectName) 
	{
		try
		{
			Select element = new Select(findWebElement(locator));
			element.selectByValue(field);
			Report.LogInfo("dropdown",ObjectName +" Is Selected Successfully with option " +field, "INFO");
		}
		catch(Exception e)
		{
			Report.LogInfo("dropdown",ObjectName +" Is not Selected Successfully with option " +field, "FAIL");
			softAssert.fail(ObjectName +" Is not Selected Successfully with option " +field);
		}
	}

	public void selectByVisibleText(String locator, String field,String ObjectName) 
	{
		try
		{
			Select element = new Select(findWebElement(locator));
			element.selectByVisibleText(field);
			Report.LogInfo("dropdown",ObjectName +" Is Selected Successfully with option " +field, "INFO");
		}
		catch(Exception e)
		{
			Report.LogInfo("dropdown",ObjectName +" Is not Selected Successfully with option " +field, "FAIL");
			softAssert.fail(ObjectName +" Is not Selected Successfully with option " +field);
		}
	}

	/**
	 * @param singleSelect
	 * @param field
	 * @throws Exception 
	 */
	protected void selectValue(Select singleSelect, String field)
	{
		try
		{
			if(field.startsWith("@value")){
				String value = removeStart(field, "@value=");
				if (!isBlankOrNull(value)){
					singleSelect.selectByValue(removeStart(field, "@value="));
					Report.LogInfo("SelectOptionByValue",field +"  value is selected in dropdown", "INFO");
				}
			} else if(field.startsWith("@index")){
				String index = removeStart(field, "@index=");
				if (!isBlankOrNull(index)){
					singleSelect.selectByIndex(Integer.parseInt(index));
					Report.LogInfo("SelectOptionByIndex",field +" index is selected in dropdown ", "INFO");
				}
			} else if (field.startsWith("@visibleText")){
				String text = removeStart(field, "@visibleText=");
				if (!isBlankOrNull(text)){
					singleSelect.selectByVisibleText(removeStart(field, "@visibleText="));
					Report.LogInfo("SelectOptionByVisibleText",field +" visible text is selected in dropdown", "INFO");
				}
			} else
			{
				//	throw new Exception("Invalid format of the field [field='" + field+"']");
			}
		}catch(Exception e)
		{
			Report.LogInfo("SelectValue",field +"is not selected in "+singleSelect , "FAIL");
			softAssert.fail(field +"is not selected in "+singleSelect);
		}
		//log.trace("Exiting method selectValue");
	}

	/**
	 * @throws Exception 
	 * @see selectFromMultipleSelect
	 */
	public void selectMultiple(String locator, String values) {
		//log.trace("Entering method selectMultiple with parameters [locator='"+locator+"', values='"+values+"']");
		if(isBlankOrNull(locator) || 
				(!locator.startsWith("/") && !locator.startsWith("@name=") && !locator.startsWith("@id="))) {
		}
		Select element = new Select(findWebElement(locator));
		if(isValueEmpty(values)) {
			element.deselectAll();
			return;
		}
		String[] args = values.split(",");
		for ( String field : args) {
			selectValue(element, field);
		}
	}

	/**
	 * @param locator
	 * @param value
	 * @throws Exception
	 */
	public void selectRadio(String locator, String value) {

		if(isBlankOrNull(locator) || (!locator.startsWith("@xpath=") && !locator.startsWith("@name=") )) {
			//	throw new Exception("Invalid locator format [locator='"+locator+"']");
		}

		List<WebElement> webElements = findWebElements(locator);
		if(value.startsWith("@id=")){
			String id = removeStart(value,"@id=");
			for ( WebElement element : webElements ) {
				if(id.equals(element.getAttribute("id"))) {
					element.click();
					//log.debug("Element to select identified with id [value='"+value+"']");
				}
			}
		} else if(value.startsWith("@value=")){
			String id = removeStart(value,"@value=");
			for ( WebElement element : webElements ) {
				if(id.equals(element.getAttribute("value"))) {
					element.click();
					//log.debug("Element to select identified with value [value='"+value+"']");
				}
			}
		}
		else {
			//	throw new Exception("Invalid value format [value='"+value+"']");
		}
	}

	/**
	 * @param locator
	 * @param value
	 * @throws Exception
	 */
	public void check(String locator, String value) {
		//log.trace("Entering method check [locator='"+locator+"', value='"+value+"']");
		clickCheckBox(locator, value, true);
		//info("Checked checkbox [locator='"+locator+"', value='"+value+"']");
		//log.trace("Exiting method check");
	}

	public void checkbox(String locator,Keys key) {

		if(verifyExists(locator))
		{
			findWebElement(locator).sendKeys(key);
			Report.LogInfo("isChecked",locator , "INFO");
		}
		else
		{
			Report.LogInfo("isChecked", locator+ "is not found", "FAIL");
			softAssert.fail(locator+ "is not found");
		}

	}

	/**
	 * @param locator
	 * @param value
	 * @throws Exception
	 */
	public boolean isSelected(String locator,String infoMessage) {

		Report.LogInfo("isChecked", infoMessage, "Info");

		if(verifyExists(locator))
		{
			return (findWebElement(locator).isSelected());
		}
		else

		{
			Report.LogInfo("isChecked", locator+ "is not found", "FAIL");
			softAssert.fail(locator+ "is not found");
			return false;
		}

	}

	/**
	 * @param locator
	 * @param value
	 * @throws Exception
	 */
	public void uncheck(String locator, String value) {
		clickCheckBox(locator, value, false);

	}

	/**
	 * @param isChecked
	 * @param element
	 * @return
	 */
	protected boolean canClickElement(boolean isCheckCommand, WebElement element) {
		boolean canClick = (isCheckCommand && !element.isSelected()) ||
				(!isCheckCommand && element.isSelected());
		return canClick;
	}


	protected void clickCheckBox(String locator, String value, boolean isCheckCommand){
		if(isBlankOrNull(locator) || (!locator.startsWith("/") && !locator.startsWith("@name=") )) {
			//	throw new Exception("Invalid locator format [locator='"+locator+"']");
		}

		List<WebElement> webElements = findWebElements(locator);
		if("all".equalsIgnoreCase(value)) {
			for ( WebElement element : webElements ) {
				if( canClickElement(isCheckCommand, element) ) {
					//log.debug("Checkbox clicked [value='"+value+"']");
					element.click();
				}
			}
		} else if(value.startsWith("@id=")){
			String id = removeStart(value,"@id=");
			for ( WebElement element : webElements ) {
				if(id.equals(element.getAttribute("id")) && canClickElement(isCheckCommand, element)) {
					//log.debug("Checkbox clicked [value='"+value+"']");
					element.click();
					break;
				}
			}
		} else if(value.startsWith("@value=")){
			String valueToSet = removeStart(value,"@value=");
			for ( WebElement element : webElements ) {
				if(valueToSet.equals(element.getAttribute("value")) && canClickElement(isCheckCommand, element)) {
					//log.debug("Checkbox clicked [value='"+value+"']");
					element.click();
					break;
				}
			}
		}
		else {
			//	throw new Exception("Invalid Value format [value='"+value+"']");
		}
	}


	/**
	 * The method returns true, if the value just has the empty keyword.
	 * 
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	protected boolean isValueEmpty(String value) {
		if(StringUtils.containsIgnoreCase(value,EMPTY_KEYWORD)) {
			if(EMPTY_KEYWORD.equalsIgnoreCase(value.trim())) {
				return true;
			}
			else {
				//	throw new Exception("Invalid empty value format! [value='" + value + "']");
			}
		}
		return false;
	}

	/**
	 * The method replaces ${space} with " " and returns the string.  The end result after processing 
	 * should be all spaces if not, throws an Exception.
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	protected String processSpaceValues(String value) {
		if(StringUtils.containsIgnoreCase(value,SPACE_KEYWORD)) {
			value = value.replace(SPACE_KEYWORD, " ");
			value = value.replace(SPACE_KEYWORD.toUpperCase(), " ");
			if("".equals(value.trim())) {
				//info("Set the value in text box as [value='"+value+"']");
				//log.trace("Exit method processSpaceValues");
				return value;
			}
			else {
				//	throw new Exception("Invalid space value format! [value='" + value + "']");
			}
		}
		return value;
	}

	/**
	 * Verify if the page contains specified element
	 * 
	 * @param elementLocator
	 * @Modified on 
	 * @Modified By 
	 */
	public boolean verifyExists(String locator) {
		if(!isBlankOrNull(locator)){
			try {
				setImplicitWaitTimeout(2);
				WebElement element=findWebElement(locator);
				setImplicitWaitTimeout(50000);
				if(element != null)
				{
					//Report.LogInfo("verifyExists","verify locator \""+locator+"\" is present", "PASS");
					return true;
				}
				else
				{
					//Report.LogInfo("verifyExists","verify locator \""+locator+"\" is not present", "FAIL");
					//softAssert.fail("verify locator \""+locator+"\" is not present");
					return false;
				}


			} catch (Exception e) {

				setImplicitWaitTimeout(50000);
				//Report.LogInfo("verifyExists","verify locator \""+locator+"\" is not present", "FAIL");
				//softAssert.fail("verify locator \""+locator+"\" is not present");
				return false;
			} 
		}else{

			Report.LogInfo("verifyExists","verify locator \""+locator+"\" is not present", "INFO");
			return false;
		}


	}

	public boolean verifyExists(String locator,String Object) throws InterruptedException {
		if(g.getBrowser().equalsIgnoreCase("edge") || g.getBrowser().equalsIgnoreCase("firefox"))
		{
			waitForElementToAppear(locator,5);
		}
		if(!isBlankOrNull(locator)){
			try {
				waitForElementToAppear(locator,5);
				WebElement element=findWebElement(locator);
				WebDriverWait wait = new WebDriverWait(webDriver, 30);
				wait.until(ExpectedConditions.visibilityOf(element));
				wait.until(ExpectedConditions.elementToBeClickable(element));
				if(element != null)
				{
					Report.LogInfo("verifyExists","<b><i>"+Object +"<i></b> is present on screen", "PASS");
					return true;
				}
				else
				{
					Report.LogInfo("verifyExists","<b><i>"+Object +"</i></b> is not present on screen", "FAIL");
					softAssert.fail(Object +" is not present on screen");
					return false;
				}				
			} catch (Exception e) {

				setImplicitWaitTimeout(20000);
				//Report.LogInfo("getTextFrom","<b><i>'"+Object +"'</i></b> is not present on Screen", "FAIL");
				Report.LogInfo("verifyExists","<b><i>"+Object +"</i></b> is not present on screen", "FAIL");
				softAssert.fail(Object +" is not present on screen");
				throw new SkipException("<b><i>"+Object +"</i></b> Element not found so skipping the execution");
				//return false;
			} 
		}else{

			Report.LogInfo("verifyExists","<b><i>"+Object +"</i></b> is not present on screen", "FAIL");
			softAssert.fail(Object +" is not present on screen");
			throw new SkipException("<b><i>"+Object +"</i></b> Element not found so skipping this exception");
			//return false;
		}
	}



	/**
	 * Set page to accept/reject all confirm boxes
	 * 
	 * @param elementLocator
	 */
	public void clickConfirmBox(String option) {
		Alert alert = SeleniumUtils.webDriver.switchTo().alert();
		if(!isBlankOrNull(option)){
			if(option.equalsIgnoreCase("OK")) {
				alert.accept();
			} else {
				alert.dismiss();
			}
		}
	}

	/**
	 * Set window as current active window for specified title
	 * 
	 * @param title - 
	 * @throws Exception 
	 */
	public void setWindow(String title) {
		String currentHandle = SeleniumUtils.webDriver.getWindowHandle();

		for (String handle : SeleniumUtils.webDriver.getWindowHandles()) {

			if (title.equals(SeleniumUtils.webDriver.switchTo().window(handle).getTitle())) {
				return;
			}
		} 

		SeleniumUtils.webDriver.switchTo().window(currentHandle);
	}

	/**
	 * This method will find the webElement based on id/name/xpath/linkText
	 * 
	 * @param locator
	 * @param webElement
	 * @return
	 * @throws Exception 
	 */
	protected WebElement findWebElement(String locator){

		webDriver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);

		WebElement webElement = null;
		if(!isBlankOrNull(locator)){
			try {

				//Report.LogInfo("findWebElement","Web element '"+locator+ "' is finding", "INFO");
				if(locator.startsWith("@id")){ // e.g @id='elementID'
					// Find the text input element by its id
					webElement = SeleniumUtils.webDriver.findElement(By.id(removeStart(locator, "@id=")));

				}else if(locator.startsWith("@name")){
					// Find the text input element by its name
					webElement = SeleniumUtils.webDriver.findElement(By.name(removeStart(locator, "@name=")));
				}else if(locator.startsWith("@linkText")){
					// Find the text input element by its link text
					webElement = SeleniumUtils.webDriver.findElement(By.linkText(removeStart(locator, "@linkText=")));
				}else if(locator.startsWith("@partialLinkText")){
					// Find the text input element by its link text
					webElement = SeleniumUtils.webDriver.findElement(By.partialLinkText(removeStart(locator, "@partialLinkText=")));
				}else if(locator.startsWith("@xpath")){
					//using XPATH locator.
					webElement = SeleniumUtils.webDriver.findElement(By.xpath(removeStart(locator, "@xpath="))); 
				}else if(locator.startsWith("@css")){
					// Find the text input element by its css locator
					webElement = SeleniumUtils.webDriver.findElement(By.cssSelector(removeStart(locator, "@css=")));
				}else if(locator.startsWith("@className")){
					// Find the text input element by its class Name
					webElement = SeleniumUtils.webDriver.findElement(By.className(removeStart(locator, "@className=")));
				}
				else if(locator.startsWith("@tagName")){
					// Find the text input element by its class Name
					webElement = SeleniumUtils.webDriver.findElement(By.className(removeStart(locator, "@tagName=")));
				}

			} catch(NoSuchElementException e) { 
				//Report.LogInfo("findWebElement", "Exception encountered while trying to find element [locator='"+locator+"']: "+e.getMessage(), "FAIL");
			} catch(RuntimeException e) { 
				//Report.LogInfo("findWebElement", "Element does not exist [locator='"+locator+"']: "+e.getMessage(), "FAIL");	
				((JavascriptExecutor)webDriver).executeScript("arguments[0].style.border='3px solid red'", webElement);
			}
		}
		((JavascriptExecutor)webDriver).executeScript("arguments[0].style.border='3px solid green'", webElement);

		//log.trace("Exiting method findWebElement");
		return webElement;
	}


	/**
	 * This method will find the webElement based on id/name/xpath/linkText
	 * 
	 * @param locator
	 * @param webElement
	 * @return
	 * @throws Exception 
	 */
	protected List<WebElement> findWebElements(String locator)
	{
		//log.trace("Entering method findWebElements [locator='"+locator+"']");
		////info("Find web elements [locator='"+locator+"']");
		List<WebElement> webElements = null;
		if(!isBlankOrNull(locator)){
			try {
				if(locator.startsWith("@id")){ // e.g @id='elementID'
					// Find the text input element by its id
					webElements = SeleniumUtils.webDriver.findElements(By.id(removeStart(locator, "@id=")));
				}else if(locator.startsWith("@name")){
					// Find the text input element by its name
					webElements = SeleniumUtils.webDriver.findElements(By.name(removeStart(locator, "@name=")));
				}else if(locator.startsWith("@linkText")){
					// Find the text input element by its link text
					webElements = SeleniumUtils.webDriver.findElements(By.linkText(removeStart(locator, "@linkText=")));
				}else if(locator.startsWith("@partialLinkText")){
					// Find the text input element by its link text
					webElements = SeleniumUtils.webDriver.findElements(By.partialLinkText(removeStart(locator, "@partialLinkText=")));
				}else if(locator.startsWith("@xpath")){
					//using XPATH locator.
					webElements = SeleniumUtils.webDriver.findElements(By.xpath(removeStart(locator, "@xpath="))); 
				}else if(locator.startsWith("@css")){
					// Find the text input element by its link text
					webElements = SeleniumUtils.webDriver.findElements(By.cssSelector(removeStart(locator, "@css=")));
				}
				else if(locator.startsWith("@className")){
					// Find the text input element by its class Name
					webElements = SeleniumUtils.webDriver.findElements(By.className(removeStart(locator, "@className=")));
				}
			} catch(NoSuchElementException e) { 
				//log.trace("Did not find specified HTML element [locator="+locator+"] \n "+e.getMessage());
				//	throw new Exception("Element does not exist [locator='"+locator+"']: "+e.getMessage());
			} catch(RuntimeException e) { 
				//log.trace(e.getMessage());
				//	throw new Exception("Exception encountered while trying to find elements [locator='"+locator+"']: "+e.getMessage());
			}
		}
		if(webElements == null) {
			//	throw new Exception("Element not found [locator='"+locator+"']");
		}
		//log.trace("Exiting method findWebElements ");
		return webElements;
	}

	/**
	 * @return boolean
	 * @throws Exception
	 */
	public boolean verifyDoesNotExist(String locator) {
		//log.trace("Entering method verifyDoesNotExist [locator='"+locator+"']");
		boolean result = false;
		if(!isBlankOrNull(locator)){
			try {
				setImplicitWaitTimeout(waitForWebElementNotExistTimeOut);
				findWebElement(locator);
			} catch (Exception e) {
				if(e.getCause().getClass().equals(NoSuchElementException.class)){
					result= true;
				}else {
					//throw e;
				}
			} finally{
				setImplicitWaitTimeout(waitForWebElementTimeOut);
			}
			//info("Verify field exists [locator='"+locator+"']");

		}
		//log.trace("Exiting method verifyDoesNotExist");
		return result;
	}



	/**
	 * @throws Exception 
	 * 
	 */
	public String getAttributeFrom(String locator, String attributeName) {
		//log.trace("Entering method getAttributeValue [locator='"+locator+"']");
		String returnValue = null;
		if(!isBlankOrNull(locator) && !isBlankOrNull(attributeName)){
			try {

				WebElement webElement = findWebElement(locator);
				returnValue = webElement.getAttribute(attributeName).trim();// This should be parameter
				Report.LogInfo("getAttributeValue","Attribute <B>'"+ attributeName +"'</B> in locator : <I>'"+locator+"'</I> is :"+returnValue,"INFO");
			}catch(NoSuchElementException e) { 
				//log.trace("Did not find specified HTML element [locator="+locator+"] \n "+e.getMessage());
				//	throw new Exception("Element does not exist [locator='"+locator+"']: "+e.getMessage());
			}catch(Exception e){
				//	throw new Exception("Exception encountered while trying to getAttributeValue [locator='"+locator+"']: "+e.getMessage(), e);
			}
		}
		return returnValue;
	}


	public String getAttributeFrom(String locator, String attributeName,String objectName)
	{
		String returnValue = null;
		if(!isBlankOrNull(locator) && !isBlankOrNull(attributeName))
		{
			try {
				WebElement webElement = findWebElement(locator);
				returnValue = webElement.getAttribute(attributeName).trim();// This should be parameter
				Report.LogInfo("getAttributeValue","Value of attribute <B><i>'"+ attributeName +"'</i></B> of Object '"+objectName+"' is :'"+returnValue+"'","INFO");
			}catch(NoSuchElementException e) { 
			}catch(Exception e)
			{
				Report.LogInfo("Exception","Exception in getAttributeFrom "+e.getMessage(),"FAIL");	
				softAssert.fail("Exception in getAttributeFrom "+e.getMessage());
			}
		}
		return returnValue;
	}


	public String getTextFrom(String locator) {
		String returnText = null;
		setImplicitWaitTimeout(2);
		if(!isBlankOrNull(locator))
		{

			WebElement webElement = findWebElement(locator);

			try {

				returnText = webElement.getText().trim();
				Report.LogInfo("getTextFrom","getting text from locator : ' "+ locator+" is "+returnText, "INFO");
			}catch(NoSuchElementException e) { 
				Report.LogInfo("getTextFrom","Text isn't found for locator: ' "+ locator, "FAIL");
				softAssert.fail("Text isn't found for locator: ' "+ locator);
				//log.trace("Did not find specified HTML element [locator="+locator+"] \n "+e.getMessage());
				//	throw new Exception("Element does not exist [locator='"+locator+"']: "+e.getMessage());
			}catch(Exception e){
				Report.LogInfo("getTextFrom","Text isn't found for locator: ' "+ locator, "FAIL");
				softAssert.fail("Text isn't found for locator: ' "+ locator);
				//	throw new Exception("Exception encountered while trying to getText [locator='"+locator+"']: "+e.getMessage(), e);
			}
		}
		return returnText;
	}

	public String getTextFrom(String locator,String Object) {
		String returnText = null;
		setImplicitWaitTimeout(2);
		if(!isBlankOrNull(locator)){
			try {

				WebElement webElement = findWebElement(locator);

				returnText = webElement.getText().trim();
				Report.LogInfo("getTextFrom","Text from object : '<b><i> "+ Object+"</i></b>' is '<i>"+returnText+"</i>'","INFO");
			}catch(NoSuchElementException e) { 
				Report.LogInfo("getTextFrom","<b><i>'"+Object +"'</i></b> is not present on Screen", "FAIL");
				softAssert.fail(Object +" is not present on Screen");
				//log.trace("Did not find specified HTML element [locator="+locator+"] \n "+e.getMessage());
				//	throw new Exception("Element does not exist [locator='"+locator+"']: "+e.getMessage());
			}catch(Exception e){
				Report.LogInfo("getTextFrom","<b><i>'"+Object +"'</i></b> is not present on Screen", "FAIL");
				softAssert.fail(Object +" is not present on Screen");
				//	throw new Exception("Exception encountered while trying to getText [locator='"+locator+"']: "+e.getMessage(), e);
			}
		}

		return returnText;
	}

	public void javaScriptWait()
	{
		String  str;
		try{
			do
			{
				JavascriptExecutor js = (JavascriptExecutor) webDriver;
				str =(String) js.executeScript("return document.readyState");

			}while(!str.equals("complete"));
		}catch(Exception e)
		{
			Report.LogInfo("Exception",e.getMessage(), "FAIL");
			softAssert.fail(e.getMessage());
		}
	}

	public boolean isElementPresent(By locator) 
	{
		String loc=null;

		try {
			setImplicitWaitTimeout(1);
			webDriver.findElement(locator);
			return true;

			//log.trace("Exiting method isTextPreset returning result [TRUE]");
		} catch (NoSuchElementException e) {
			//log.trace("Exiting method isTextPreset returning result [FALSE]");
			//Report.LogInfo("isElementPresent","verify locator : \""+locator+"\" is not present", "INFO");
			return false;
			//throw e;
		}catch (Exception e) {
			//log.trace("Exiting method isTextPreset returning result [FALSE]");
			//Report.LogInfo("isElementPresent","verify locator : \""+loc+"\" is not present", "INFO");
			return false;
			//throw e;
		}

	}

	public boolean isElementPresent(By locator,String object) 
	{
		String loc=null;

		try {
			setImplicitWaitTimeout(1);
			webDriver.findElement(locator);

			//loc = webDriver.findElement(locator).getAttribute("id");
			Report.LogInfo("isElementPresent",object +": is present on screen", "PASS");
			return true;

			//log.trace("Exiting method isTextPreset returning result [TRUE]");
		} catch (NoSuchElementException e) {
			//log.trace("Exiting method isTextPreset returning result [FALSE]");
			Report.LogInfo("isElementPresent","verify locator : \""+locator+"\" is not present", "INFO");
			return false;
			//throw e;
		}catch (Exception e) {
			//log.trace("Exiting method isTextPreset returning result [FALSE]");
			Report.LogInfo("isElementPresent",object +": is not present on screen", "INFO");
			return false;
			//throw e;
		}

	}


	public void clearTextBox(String locator)
	{
		WebElement webElement = findWebElement(locator);
		webElement.clear();
	}

	public void sendKeys(String locator, String textValue) 
	{ 

		WebElement webElement = null;
		if(!isBlankOrNull(locator)){
			webElement = findWebElement(locator);
			if(isBlankOrNull(textValue)) {
				return;
			}

			if(isValueEmpty(textValue)) {
				//info("Clear the field contents [text='"+textValue+"', locator='"+locator+"']");
				webElement.clear();
				return;
			}

			textValue = processSpaceValues(textValue);

			webElement.clear();
			webElement.sendKeys(textValue);
			Report.LogInfo("sendKeys",textValue +" Entered into "+ locator, "INFO");
			//info("Type text into field [text='"+textValue+"', locator='"+locator+"']");
		}
		else {
			//	throw new Exception("Invalid locator format [locator='"+locator+"']");
		}
	}
	public void sendKeys1(String locator, String textValue) 
	{ 

		WebElement webElement = null;
		if(!isBlankOrNull(locator)){
			webElement = findWebElement(locator);
			if(isBlankOrNull(textValue)) {
				return;
			}

			if(isValueEmpty(textValue)) {
				//info("Clear the field contents [text='"+textValue+"', locator='"+locator+"']");
				webElement.clear();
				return;
			}

			textValue = processSpaceValues(textValue);

			//webElement.clear();
			webElement.sendKeys(textValue);
			Report.LogInfo("sendKeys",textValue +" Entered into "+ locator, "INFO");
			//info("Type text into field [text='"+textValue+"', locator='"+locator+"']");
		}
		else {
			//	throw new Exception("Invalid locator format [locator='"+locator+"']");
		}
	}

	public void sendKeys(String locator, String textValue,String message) 
	{ 
		try
		{
			WebElement webElement = null;
			if(!isBlankOrNull(locator)){
				webElement = findWebElement(locator);
				if(isBlankOrNull(textValue)) {
					return;
				}

				if(isValueEmpty(textValue)) {
					//info("Clear the field contents [text='"+textValue+"', locator='"+locator+"']");
					webElement.clear();
					return;
				}

				textValue = processSpaceValues(textValue);

				webElement.clear();
				webElement.sendKeys(textValue);
				Report.LogInfo("sendKeys","<i>"+textValue +"</i> entered in :<b><i>"+message+"</i></b>", "INFO");
				//info("Type text into field [text='"+textValue+"', locator='"+locator+"']");
			}
			else {
				//	throw new Exception("Invalid locator format [locator='"+locator+"']");
			}
		}
		catch(Exception e)
		{
			Report.LogInfo("sendKeys",locator +"Not present on Screen", "FAIL");
			softAssert.fail(locator +" Not present on Screen");
		}
	}

	public ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator) {
		return new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement toReturn = driver.findElement(locator);
				if (toReturn.isDisplayed()) {
					return toReturn;
				}
				return null;
			}
		};
	}

	public boolean isVisible(String locator) throws NoSuchElementException {
		boolean result = true;
		/*WebElement element=findWebElement(locator);
		WebDriverWait wait = new WebDriverWait(webDriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(element));*/
		if(!isBlankOrNull(locator)){
			try {

				result=findWebElement(locator).isDisplayed();

			} catch (Exception ignored) {
				result= false;
				//throw e;
			} 
		}
		else{
			result = false;
		}
		//info("Exiting method isVisible returning result="+result);
		//log.trace("Exiting method isVisible returning result="+result);
		return result;
	}

	public boolean isVisible(By locator) {
		//log.trace("Entering method isVisible [locator='"+locator+"']");
		boolean result = true;

		try {

			result =webDriver.findElement(locator).isDisplayed();

		} catch (Exception e) {
			result= false;
			//throw e;
		} 
		//info("Exiting method isVisible returning result="+result);
		//log.trace("Exiting method isVisible returning result="+result);
		return result;
	}
	public void waitForElementToAppear(String locator,int waitTimeInSeconds) throws InterruptedException
	{
		for(int i=0;i<waitTimeInSeconds;i++)
		{
			if(isVisible(locator))
			{
				break;
			}
			else
			{
			}
		}
		
	}

	public void waitForElementToPresent(By locator,int waitTimeInSeconds)
	{
		for(int i=0;i<waitTimeInSeconds;i++)
		{
			if(isElementPresent(locator))
			{
				break;
			}
			else
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void waitForElementToAppear(String locator,int waitTimeInSeconds,int sleepTimeInMillSeconds)
	{
		for(int i=0;i<waitTimeInSeconds;i++)
		{
			if(isVisible(locator))
			{
				break;
			}
			else
			{
				sleep(sleepTimeInMillSeconds);
			}
		}
	}

	public void sleep(int sleepTimeInMillSeconds)
	{
		try {
			Thread.sleep(sleepTimeInMillSeconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isAlertPresent() {
		try {
			webDriver.switchTo().alert();
			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		} // catch
	}
	public void waitForElementToDisappear(String locator,int waitTimeInSeconds)
	{
		for(int i=0;i<waitTimeInSeconds;i++)
		{
			if(!isVisible(locator))
			{
				break;
			}
			else
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void waitForElementToDisappear(By locator,int waitTimeInSeconds)
	{
		for(int i=0;i<waitTimeInSeconds;i++)
		{
			if(!isVisible(locator))
			{
				break;
			}
			else
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static void assertTrue(boolean condition, String message) {
		Assert.assertTrue(condition, message);
	}

	public static void assertFalse(boolean condition, String message) {
		Assert.assertFalse(condition, message);
	}
	public static void assertEquals(Object actual, Object expected, String message) {
		Assert.assertEquals(actual, expected, message);
	}

	public static void verifyTrue(boolean condition, String message) {
		try {
			assertTrue(condition, message);
			Report.LogInfo("verifyTrue",message, "PASS");
		} catch(Throwable e) {
			addVerificationFailure(e);
			Report.LogInfo("verifyTrue",message, "FAIL");
			sAssert.fail(message);
			failureLog(message, "verifyTrue: " + condition);
		}
	}

	public static void verifyFalse(boolean condition) {
		verifyFalse(condition, "");
	}

	public static void verifyFalse(boolean condition, String message) {
		try {
			assertFalse(condition, message);
			Report.LogInfo("verifyFalse",message, "PASS");
		} catch(Throwable e) {
			addVerificationFailure(e);
			failureLog(message, "verifyFalse: " + condition);
			Report.LogInfo("verifyFalse",message, "FAIL");
			sAssert.fail(message);
		}
	}

	public static void verifyEquals(boolean actual, boolean expected) {
		verifyEquals(actual, expected, "");
	}

	public static void verifyEquals(Object actual, Object expected) {
		verifyEquals(actual, expected, "");
	}

	public static void verifyEquals(Object actual, Object expected, String message) {
		try {
			//log.trace("Entering method verifyEquals [actual object='"+actual +"'][expected object='"+expected +"']");
			assertEquals (actual, expected, message);
			if(message.isEmpty())
			{

				Report.LogInfo("verifyEquals","ActualDisplay text <i>'"+actual +"'</i> equal to Expected Text '<i>" +expected+"</i>'", "PASS");
			}
			else
			{
				Report.LogInfo("verifyEquals","ActualDisplay text <i>'"+actual +"'</i> equal to Expected Text '<i>" +expected+"</i>'", "PASS");
				//Report.LogInfo("verifyEquals",message, "PASS");
			}
		} catch(Throwable e) {
			//   addVerificationFailure(e);
			if(message.isEmpty())
			{

				Report.LogInfo("verifyEquals","ActualDisplay text <i>'"+actual +"'</i> not equal to Expected Text '<i>" +expected+"</i>'", "FAIL");
				sAssert.fail("ActualDisplay text "+actual +" not equal to Expected Text " +expected);
			}
			else
			{
				Report.LogInfo("verifyEquals","ActualDisplay text <i>'"+actual +"'</i> not equal to Expected Text '<i>" +expected+"</i>'", "FAIL");
				sAssert.fail("ActualDisplay text "+actual +" not equal to Expected Text " +expected);
			}
			failureLog(message, "verifyEquals: " + actual + " NOT EQUAL to " + expected);
		}
		//log.trace("Exiting method verifyEquals [actual object='"+actual +"'][expected object='"+expected +"']");
	}

	public static void verifyEquals(Object[] actual, Object[] expected) {
		verifyEquals(actual, expected, "");
	}

	public static void verifyEquals(Object[] actual, Object[] expected, String message) {
		try {
			assertEquals(actual, expected,"");
			Report.LogInfo("verifyEquals",actual +" Object Is verified with " +expected, "PASS");
		} catch(Throwable e) {
			String failureReason = "verifyEquals: " + actual + " NOT EQUAL to " + expected;
			Report.LogInfo("verifyEquals",actual +" Object Is not verified with " +expected, "FAIL");
			sAssert.fail(actual +" Object Is not verified with " +expected);
			
			failureReason += "\n\t\tExpected:";
			for (int i=0; i<expected.length; i++)
			{
				failureReason += (String) expected[i] + "|";
			}

			failureReason += "\n\t\tActual  : |";
			for (int i=0; i<actual.length; i++)
			{
				failureReason += (String) actual[i] + "|";
			}
			addVerificationFailure(e);
			failureLog(message, failureReason);
		}
	}


	public static void logVerificationFailure (Exception e, String message) {

		addVerificationFailure(e);
		failureLog(message, "Failure due to exception: " + e.getMessage());


	}

	public static void fail(String message) {
		Assert.fail(message);
	}

	public static List<Throwable> getVerificationFailures() {
		List<Throwable> verificationFailures = verificationFailuresMap.get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList<Throwable>() : verificationFailures;
	}


	public static void addVerificationFailure(Throwable e)  {

		try{
			List<Throwable> verificationFailures = getVerificationFailures();
			verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);

			verificationFailures.add(e);
			failureLog (Reporter.getCurrentTestResult().getName(), "Verification Failure # " + verificationFailures.size());
			verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);

			// takeSnapshot(Reporter.getCurrentTestResult().getName() + " Failure # " + verificationFailures.size());
		}catch (Exception ex) {
			ex.getMessage();
		}
	}



	protected static void failureLog (String customMessage, String failureReason) {

		if (customMessage == "")
		{
			//log.debug("\n\t" + failureReason);
			info("\n\t" + failureReason);
			//log.trace("\n\t" + failureReason);
		}
		else
		{
			//log.debug("\n\t" + customMessage + "\n\t\t" + failureReason);

			info("\n\t" + customMessage + "\n\t\t" + failureReason);
			//log.trace("\n\t" + customMessage + "\n\t\t" + failureReason);
		}
	}


	public String getFirstSelectedOptionFromDropdown(String locator)
	{

		//log.trace("Entering method getFirstSelectedOptionFromDropdown [locator="+locator+"]");

		WebElement element = findWebElement(locator);

		//log.debug("Found element for locator [element='"+element+"']");

		Select select = new Select(element);
		String getSelectedOptionText=select.getFirstSelectedOption().toString();


		info("Select locator [locator='"+locator+"']");
		//log.trace("Exiting method getFirstSelectedOptionFromDropdown");

		return getSelectedOptionText;
	}

	public int getXPathCount(String locator) {

		setImplicitWaitTimeout(2);
		return findWebElements(locator).size();
	} 

	public static String removeStart(String str, String remove)
	{
		//log.trace((new StringBuilder()).append("Entering method removeStart [str=").append(str).append(", remove=").append(remove).append("]").toString());
		String returnStr = "";
		if(isBlankOrNull(str) || isBlankOrNull(remove))
		{
			//log.debug((new StringBuilder()).append("Returned value is [str='").append(str).append("']").toString());
			returnStr = str;
		}
		if(str.startsWith(remove))
			returnStr = str.substring(remove.length());
		//log.trace((new StringBuilder()).append("Exiting method removeStart [returnStr='").append(returnStr).append("']").toString());
		return returnStr;


	}
	public static boolean isEmptyOrNull(String str)
	{
		//log.debug((new StringBuilder()).append("Inside isEmptyOrNull [str=").append(str).append("]").toString());
		return str == null || str.length() == 0;
	}

	public static boolean isFieldNone(String str)
	{
		//log.debug((new StringBuilder()).append("Inside isEmptyOrNull [str=").append(str).append("]").toString());
		return str.equalsIgnoreCase("None");
	}

	public static boolean isBlankOrNull(String str)
	{
		//log.debug((new StringBuilder()).append("Inside isBlankOrNull [str=").append(str).append("]").toString());
		return str == null || str.trim().length() == 0;
	}

	public static boolean isAlphanumeric(String str)
	{
		//log.trace((new StringBuilder()).append("Entering method isAlphanumeric [str=").append(str).append("]").toString());
		if(isBlankOrNull(str))
		{
			//log.debug("returning false");
			return false;
		}
		int sz = str.length();
		for(int i = 0; i < sz; i++)
			if(!Character.isLetterOrDigit(str.charAt(i)))
			{
				//log.debug("returning false");
				return false;
			}

		//log.trace("Exiting method isAlphanumeric");
		return true;
	}

	public static boolean validateT(Object spattern, String text, WebElement webElement) {

		String patternToBeMatched = (String) spattern;
		Pattern pattern = Pattern.compile(patternToBeMatched);

		Matcher matcher = pattern.matcher(text);
		if (!matcher.matches()) {
			//					System.out.println("FAIL");
			((JavascriptExecutor)webDriver).executeScript("arguments[0].style.border='3px solid red'", webElement);
			Report.LogInfo("validateStringPatternMatch", "Text \"" + text + "\" is not matched with pattern : \""+ spattern + "\"", "FAIL");
			sAssert.fail("Text \"" + text + "\" is not matched with pattern : \""+ spattern + "\"");
		}
		else
		{
			//					System.out.println("PASS");
			((JavascriptExecutor)webDriver).executeScript("arguments[0].style.border='3px solid green'", webElement);
			Report.LogInfo("validateStringPatternMatch", "Text \"" + text + "\" is matched with pattern : \""+ spattern + "\"", "PASS");
		}
		return matcher.matches();
	}

	public static boolean validate(Object spattern, String text) {

		String patternToBeMatched = (String) spattern;
		Pattern pattern = Pattern.compile(patternToBeMatched);

		Matcher matcher = pattern.matcher(text);
		if (!matcher.matches()) {
			//					System.out.println("FAIL");
			Report.LogInfo("validateStringPatternMatch", "Text \"" + text + "\" is not matched with pattern : \""+ spattern + "\"", "FAIL");
			sAssert.fail("Text \"" + text + "\" is not matched with pattern : \""+ spattern + "\"");
		}
		else
		{
			//					System.out.println("PASS");
			Report.LogInfo("validateStringPatternMatch", "Text \"" + text + "\" is matched with pattern : \""+ spattern + "\"", "PASS");
		}
		return matcher.matches();
	}

	public static void main(String[] args)
	{

		validate(".*Primary.* Mr. Visa Sahu 620 West 42nd Street #56D TSS Bremen, 30110 .* Ends with xxx.*", "(Primary) Mr. Visa Sahu 620 West 42nd Street #56D TSS Bremen, 30110 Germany Visa Ends with xxx-0004");

	}

	public static boolean isNumeric(String str)
	{
		//log.trace((new StringBuilder()).append("Entering method isNumeric [str=").append(str).append("]").toString());
		if(str == null)
		{
			//log.debug("returning false");
			return false;
		}
		int sz = str.length();
		for(int i = 0; i < sz; i++)
			if(!Character.isDigit(str.charAt(i)))
			{
				//log.debug("returning false");
				return false;
			}

		//log.debug("returning true");
		//log.trace("Exiting method isNumeric");
		return true;
	}

	public boolean switchWindow(String urlContent, String windowName)
	{
		boolean flag=false;
		Set<String> handlers = webDriver.getWindowHandles();  
		if (webDriver.getWindowHandles().size()>= 1)
		{  
			for(String handler : handlers)
			{  
				webDriver.switchTo().window(handler);  
				if (webDriver.getCurrentUrl().contains(urlContent))
				{  
					Report.LogInfo("switchWindow", "Window switch to "+windowName, "INFO");
					flag=true;
					break;  
				}  
			}  
		}  
		else Report.LogInfo("switchWindow", "Window "+windowName+" not present", "FAIL");
		softAssert.fail("Window "+windowName+" not present");
		return flag;

	}	    

	public void setWin(String title){

		String currentHandle =webDriver.getWindowHandle();


		for (String handle : webDriver.getWindowHandles()) {

			if (title.equals(webDriver.switchTo().window(handle).getTitle())) {
				return;
			}
		} 
		webDriver.switchTo().window(currentHandle);


	}

	public String getTitle(String currentHandle)
	{
		for (String handle : webDriver.getWindowHandles()) 
		{

			if (!currentHandle.equals(handle))
			{
				return webDriver.switchTo().window(handle).getTitle();
			}
		}
		return currentHandle; 
	}


	public void isChecked(String locator, String Object)
	{


		boolean checked =findWebElement(locator).isSelected();

		if(checked != true)
		{
			Report.LogInfo("Unchecked", Object+" Is unchecked", "INFO");
		}
		else
		{
			Report.LogInfo("Checked", Object+" Is checked", "INFO");
		}
	}



	public void theSearch(String value, String locator) throws Exception
	{
		// String xpathExpression = "//*[starts-with(@id,'searchResultsTable:')]";
		List<WebElement>  elementTable= webDriver.findElements(By.xpath(locator));



		for (WebElement listofElement : elementTable)
		{
			String theElement= listofElement.getText();

			if (theElement.contains(value)){
				Assert.assertEquals(value, theElement);
				// System.out.println("The Expected Value " + value + " Equals the actual " + theElement);;
			}

		}

	}

	public String getURLFromPage()
	{
		//WebDriver driver = new WebDriver();
		String url = webDriver.getCurrentUrl();
		return url;
	}



	public void waitToElementVisible(String locator1) throws InterruptedException
	{
		/*WebElement menu = webDriver.findElement(By.xpath(locator1));

				Actions builder = new Actions(webDriver);

				builder.keyDown(Keys.ARROW_DOWN).click(menu).keyUp(Keys.ARROW_UP);

				Action selectSubmenu = builder.build();
				selectSubmenu.perform();
		 */
		waitForElementToAppear(locator1, 20000);
	}


	public void waitToPageLoad()
	{
		JavascriptExecutor js = (JavascriptExecutor)webDriver;
		js.executeScript("return document.readyState").toString().equals("complete");
	}
	public void waitToFrameVisible()
	{
		try
		{
			sleep(8000);
			WebDriverWait driverWait = new WebDriverWait(webDriver, 20);
			driverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
			Thread.sleep(2000);
			//webDriver.switchTo().frame(0);
			Report.LogInfo("Element is available","Mouse over on  is done Successfully", "INFO");

		}catch(Exception e)
		{
			Report.LogInfo("Element is", "Is not Present on Screen", "FAIL");
			softAssert.fail("Is not Present on Screen");
		}
	}

	public void mouseMoveOn(String locator) 
	{
		//log.trace("Entering method mouseOver [locator="+locator+"]");
		sleep(2000);
		WebElement element1 = findWebElement(locator);
		Actions builder = new Actions(webDriver);
		//builder.build().perform();
		builder.moveToElement(element1).perform();
		sleep(2000);
	}

	public void keyPressOn(String locator) 
	{

		WebElement element1 = findWebElement(locator);
		Actions builder = new Actions(webDriver);

		//builder.build().perform();
		builder.keyDown(element1, Keys.CONTROL).perform();

	}
	
	public void keyPress(String locator, Keys command) 
	{

		WebElement element1 = findWebElement(locator);
		element1.sendKeys(command);

	}




	public void clickHiddenElement(String locator)
	{
		JavascriptExecutor js = (JavascriptExecutor)webDriver;

		WebElement hiddenEle = (WebElement) js.executeScript("return document.getElementBy.Xpath(locator).mouseOver()");

		hiddenEle.click();
	}

	public static String toToggleCase(String inputString) {
		String result = "";
		for (int i = 0; i < inputString.length(); i++) {
			char currentChar = inputString.charAt(i);
			if (Character.isUpperCase(currentChar)) {
				char currentCharToLowerCase = Character.toLowerCase(currentChar);
				result = result + currentCharToLowerCase;
			} else {
				char currentCharToUpperCase = Character.toUpperCase(currentChar);
				result = result + currentCharToUpperCase;
			}
		}
		return result;
	}

	public void scrollUp() 
	{
		try{
			JavascriptExecutor jse = (JavascriptExecutor)webDriver;
			jse.executeScript("scroll(0, -250);");
		}
		catch(Exception e)
		{
			Report.LogInfo("ScrollUp","Page not able to scroll up" + e , "INFO");
		}
	}

	/*public void scrollDown(String locator)  
	{
		try{
		sleep(2000);
		WebElement element1 = findWebElement(locator);
		element1.sendKeys(Keys.PAGE_DOWN);
		sleep(2000);
		}
		catch(Exception e)
		{
			Report.LogInfo("ScrollDown","Page not able to scroll down" + e , "INFO");
		}
	}*/

	public void scrollDown(String locator)  
	{
		sleep(2000);
		WebElement webElement = findWebElement(locator);

		/*if(g.getBrowser().equalsIgnoreCase("edge") ||(g.getBrowser().equalsIgnoreCase("firefox")))
		{*/
		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(false);", webElement);
		/*}
		else
		{
			((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", webElement);
		}*/
	}

	public boolean isEnable(String locator) throws NoSuchElementException {
		boolean result = true;
		if(!isBlankOrNull(locator)){
			try {

				result=findWebElement(locator).isEnabled();

			} catch (Exception ignored) {
				result= false;
			} 
		}
		else{
			result = false;
		}
		return result;
	}

	 @SuppressWarnings({ "resource", "deprecation" })
	public void uploadFile(String url, File file) throws IOException
	{
		 HttpResponse response = null;
		 Header[] cookie = null;
		 HttpClient httpclient = new DefaultHttpClient(); 
         HttpPost httppost = new HttpPost(url);

        
         httppost.addHeader("Accept", "*/*"); 
         httppost.addHeader("Content-type", "application/json"); 

         //File fileToUse = new File("/path_to_file/YOLO.jpg"); //e.g. /temp/dinnerplate-special.jpg 
         FileBody data = new FileBody(file); 
         String file_type = "JPG" ; 

         MultipartEntity reqEntity = new MultipartEntity(); 
         /*reqEntity.addPart("file_name", new StringBody( fileToUse.getName() ) ); 
         reqEntity.addPart("folder_id", new StringBody(folder_id)); 
         reqEntity.addPart("description", new StringBody(description)); 
         reqEntity.addPart("source", new StringBody(source)); */
         reqEntity.addPart("file_type", new StringBody(file_type)); 
         reqEntity.addPart("data", data); 
         
         httppost.setEntity(reqEntity); 
         cookie = (Header[]) response.getHeaders("Set-Cookie");
         for(int h=0; h<cookie.length; h++){
             System.out.println(cookie[h]);
         }
        httpclient.execute(httppost); 
        System.out.println( response ) ; 
       
        HttpEntity resEntity = response.getEntity(); 
        System.out.println( resEntity ) ; 
         System.out.println( EntityUtils.toString(resEntity) ); 

         EntityUtils.consume(resEntity); 
         httpclient.getConnectionManager().shutdown(); 
 } 	 
	 
	 public void waitForAjax() {

		    try {
		        WebDriverWait driverWait = new WebDriverWait(WEB_DRIVER_THREAD_LOCAL.get(), 10);

		        ExpectedCondition<Boolean> expectation;   
		        expectation = new ExpectedCondition<Boolean>() {

		            public Boolean apply(WebDriver driverjs) {

		                JavascriptExecutor js = (JavascriptExecutor) driverjs;
		                return js.executeScript("return((window.jQuery != null) && (jQuery.active === 0))").equals("true");
		            }
		        };
		        driverWait.until(expectation);
		    }       
		    catch (TimeoutException exTimeout) {

		       // fail code
		    }
		    catch (WebDriverException exWebDriverException) {

		       // fail code
		    }
		    return;
		}
	 
	 public static boolean ClickonElementByString(String xPath, int timeOut) throws IOException, InterruptedException {
				
			boolean status = false; boolean sFlag = false;
					
			int i = 1;
			Thread.sleep(2500);   
			
	          try {
	        	  while (webDriver.findElement(By.xpath(xPath)).isDisplayed()) {   
	        		  if (i > timeOut) { status = false; break; }
	                      Thread.sleep(2000);
	                      webDriver.findElement(By.xpath(xPath)).isEnabled();
	                      scrollIntoView(webDriver.findElement(By.xpath(xPath)));
	                      webDriver.findElement(By.xpath(xPath)).click();
//	                      System.out.println("Waiting for an element "+xPath+" to get clicked");
	                      sFlag = true;
	                      i = i+1;
	                } 
	          } catch(Exception e) {
	              //System.out.println("Element "+xPath+" Click is done");
	              status = true;
	          }
	          
	          return status;
		}
	 
	 public static boolean fluentWaitForElementToBeVisible(WebElement webElement, int timeOut) {
	    	boolean status = false;
	    	
	    	try {							
					FluentWait<WebDriver> fluentWait = new FluentWait<>(webDriver)    		     
	    				.withTimeout(timeOut, TimeUnit.SECONDS)
	    		        .pollingEvery(1000, TimeUnit.MILLISECONDS)
	    		        .ignoring(NoSuchElementException.class);  
	    				fluentWait.until(ExpectedConditions.elementToBeClickable(webElement));
	    				status = true;
			} catch (Exception e) {
				
			}
			
			return status;
		}
	 
		public static boolean isElementEnabled(WebElement webElement) {
		   	boolean status = false;
		   	
		   	try {
	   			status = webElement.isEnabled();	
		   	} catch(Exception e) {
		   	}
			  
	       return status;
	   	} 
		
		public static boolean clickByJS(WebElement webElement) {
	    	 boolean status = false;     
	    	 JavascriptExecutor js = null;
	    	 
	    	 try {
	    		 	js = (JavascriptExecutor) WEB_DRIVER_THREAD_LOCAL.get();
	    		 	js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid blue;');", webElement);
	    		 	js.executeScript("arguments[0].removeAttribute('style', 'border: 2px solid blue;');", webElement);
	     			js.executeScript("arguments[0].click();", webElement);
	     			status = true;
	     			
		     } catch (Exception e) {		    	
		     }
			 
			 return status;
	     }
	
}
