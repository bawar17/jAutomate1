package custom.selenium;

import java.util.Properties;

import javax.swing.JOptionPane;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SelectIndex
{
	private WebDriver webDriver=null;

	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: SelectIndex Index");
			return false;
		}

		String index=commandParameters[0].trim();

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
				org.openqa.selenium.support.ui.Select select=new org.openqa.selenium.support.ui.Select(currentElement);
				select.selectByIndex(Integer.parseInt(index));
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
		String text = JOptionPane.showInputDialog(null, "Enter index");
		if (text != null)
		{
			return "SelectIndex \"" + text + "\"";
		}
		else
		{
			return null;
		}
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/12/23/selectindex";
	}

	public String getTooltip()
	{
		return "Select an item in the drop-down or list in focus using an index (0=first)";
	}

	public void setWebDriver(WebDriver webDriver)
	{
		this.webDriver=webDriver;
	}
}
