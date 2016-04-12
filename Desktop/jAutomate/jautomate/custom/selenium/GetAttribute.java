package custom.selenium;

import java.util.Properties;

import javax.swing.JOptionPane;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GetAttribute
{
	private WebDriver webDriver=null;

	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: GetAttribute Attribute");
			return false;
		}

		String attribute=commandParameters[0].trim();

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
				String text=currentElement.getAttribute(attribute);
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

	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter attribute");
		if (text != null)
		{
			return "GetAttribute \"" + text + "\"";
		}
		else
		{
			return null;
		}
	}

	public String getHelp()
	{
		return "http://jautomate.com/2015/05/15/getattribute";
	}

	public String getTooltip()
	{
		return "Get the attribute from the widget in focus";
	}

	public void setWebDriver(WebDriver webDriver)
	{
		this.webDriver=webDriver;
	}
}
