package custom.selenium;

import java.util.Properties;

import org.openqa.selenium.WebDriver;

public class CloseBrowser
{
	private WebDriver webDriver=null;

	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(webDriver==null)
		{
			scriptParameters.put("Error", "No browser open");
			return false;
		}

		try
		{
			webDriver.close();
			webDriver.quit();
			webDriver=null;
			return true;
		}
		catch(Exception e)
		{
			scriptParameters.put("Error", "Exception: "+e.toString());
			return false;
		}
	}

	public String[] getParameters()
	{
		return new String[]{"Error", "Response"};
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/12/23/closebrowser";
	}

	public String getTooltip()
	{
		return "Closes the browser window";
	}

	public void setWebDriver(WebDriver webDriver)
	{
		this.webDriver=webDriver;
	}

	public WebDriver getWebDriver()
	{
		return webDriver;
	}
}
