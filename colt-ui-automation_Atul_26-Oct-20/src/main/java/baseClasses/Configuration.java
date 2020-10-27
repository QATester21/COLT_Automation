package baseClasses;


import java.util.HashMap;


public class Configuration {


	private static Utilities util = new Utilities();


	public static String URL_INFO = null;
	// public static String PASSWORD= properties.getProperty("password");
	private static GlobalVariables glb = new GlobalVariables();



	public static String C4C_URL, SalesUser1_Username, SalesUser1_Password, SalesUser2_Username, SalesUser2_Password, CPQ_URL, SEUser_Username, SEUser_Password, CSTUser_Username, CSTUser_Password, ContactDetails_URL;

	public static void setUrlInfoDataSet(HashMap<String, Object> basicURLinfo) {

		C4C_URL = (String) basicURLinfo.get("C4C_URL");
		SalesUser1_Username = (String) basicURLinfo.get("SalesUser1_Username");
		SalesUser1_Password = (String) basicURLinfo.get("SalesUser1_Password");
		SalesUser2_Username = (String) basicURLinfo.get("SalesUser2_Username");
		SalesUser2_Password = (String) basicURLinfo.get("SalesUser2_Password");

		CPQ_URL = (String) basicURLinfo.get("CPQ_URL");
		SEUser_Username = (String) basicURLinfo.get("SEUser_Username");
		SEUser_Password = (String) basicURLinfo.get("SEUser_Password");
		CSTUser_Username = (String) basicURLinfo.get("CSTUser_Username");
		CSTUser_Password = (String) basicURLinfo.get("CSTUser_Password");
		
		ContactDetails_URL = (String) basicURLinfo.get("ContactDetails_URL");
	}

	// ====================================================================================================
	// FunctionName : getURL
	// Description : Function to get all the URL information
	// Input Parameter : None
	// Return Value :
	// ====================================================================================================
	public static String getURL()
	{
		String Environment = util.getValue("Environment", "RFS").trim();
		glb.setEnvironment(Environment);
		if(Environment.equalsIgnoreCase("RFS"))
		{
			URL_INFO = "RFS";

		}else if(Environment.equalsIgnoreCase("SIT"))
		{
			URL_INFO = "SIT";

		}else if(Environment.equalsIgnoreCase("UAT"))
		{
			URL_INFO = "UAT";

		}
		return URL_INFO;
	}
}