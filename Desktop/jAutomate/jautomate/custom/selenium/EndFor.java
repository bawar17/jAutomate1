package custom.selenium;

import jautomate.Command;
import jautomate.CustomCommand;
import jautomate.ScriptRunner;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EndFor
{
	private ScriptRunner scriptRunner=null;
	private WebDriver webDriver=null;
	
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(webDriver==null)
		{
			scriptParameters.put("Error", "No browser open");
			return false;
		}

		Command blockCommand=scriptRunner.getBlockCommand();
		
		if(blockCommand!=null && scriptRunner.getCommandLevel()==1)
		{
			if(blockCommand instanceof CustomCommand)
			{
				CustomCommand customCommand=(CustomCommand)blockCommand;
				String xpath=customCommand.getText();
				try
				{
					List<WebElement> elements = findXpath(xpath);
					if(elements==null || elements.size()==0)
					{
						elements = findCss(xpath);
						if(elements==null || elements.size()==0)
						{
							scriptParameters.put("Error", "Failed to locate uisng XPath or CSS");
							return false;
						}
					}
					
					int iteration=1;
					for(WebElement element:elements)
					{
						element.click();

						if(!scriptRunner.callChildScript(iteration, customCommand.getScript(), null))
						{
							return false;
						}
						
						iteration++;
					}
					return true;
				}
				catch(Exception e)
				{
					scriptParameters.put("Error", "Exception: "+e.toString());
					return false;
				}
			}
		}
		scriptRunner.decreaseCommandLevel();
		
		return true;
	}
	
	private List<WebElement> findXpath(String xpath)
	{
		try
		{
			List<WebElement> elements = webDriver.findElements(By.xpath(xpath));
			return elements;
		}
		catch(Exception e)
		{
			return null;
		}
	}

	private List<WebElement> findCss(String css)
	{
		try
		{
			List<WebElement> elements = webDriver.findElements(By.cssSelector(css));
			return elements;
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public String getTooltip()
	{
		return "End of click for each XPath/CSS result";
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}

	public void setScriptRunner(ScriptRunner scriptRunner)
	{
		this.scriptRunner=scriptRunner;
	}

	public void setWebDriver(WebDriver webDriver)
	{
		this.webDriver=webDriver;
	}
}
