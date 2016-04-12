package custom.selenium;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GetTagName
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
			WebElement currentElement = webDriver.switchTo().activeElement();
			if(currentElement!=null)
			{
				String text=currentElement.getTagName();
				scriptParameters.put("Response", text);
				return true;
			}
			else
			{
				scriptParameters.put("Error", "No active element");
				return false;
			}
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
		return "http://jautomate.com/2015/06/03/gettagname";
	}

	public String getTooltip()
	{
		return "Get the tag name from the widget in focus";
	}

	public void setWebDriver(WebDriver webDriver)
	{
		this.webDriver=webDriver;
	}
}
