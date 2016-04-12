package custom.selenium;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class GetSelectedText
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
				Select select=new Select(currentElement);
				WebElement firstSelectedElement=select.getFirstSelectedOption();
				if(firstSelectedElement!=null)
				{
					String text=firstSelectedElement.getText();
					scriptParameters.put("Response", text);
					return true;
				}
				else
				{
					scriptParameters.put("Error", "No selected element");
					return false;
				}
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
		return "http://jautomate.com/2014/12/23/getselectedtext";
	}

	public String getTooltip()
	{
		return "Get the text from the drop-down or list in focus";
	}

	public void setWebDriver(WebDriver webDriver)
	{
		this.webDriver=webDriver;
	}
}
