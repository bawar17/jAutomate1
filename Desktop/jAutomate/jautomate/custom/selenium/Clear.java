package custom.selenium;

import java.util.Properties;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Clear
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
				currentElement.clear();
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
		return new String[]{"Error"};
	}

	public String getCommand()
	{
		return "Clear";
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/12/23/clickid";
	}

	public String getTooltip()
	{
		return "Clears the text field in focus";
	}

	public void setWebDriver(WebDriver webDriver)
	{
		this.webDriver=webDriver;
	}
}
