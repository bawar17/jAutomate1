package custom.selenium;

import jautomate.ScriptRunner;

import java.util.Properties;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ClickXpath
{
	private WebDriver webDriver=null;
	ScriptRunner scriptRunner=null;

	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: ClickXpath Xpath");
			return false;
		}

		String xpath=commandParameters[0].trim();

		if(webDriver==null)
		{
			scriptParameters.put("Error", "No browser open");
			return false;
		}

		try
		{
			WebDriverWait wait = new WebDriverWait(webDriver, scriptRunner.getVizionEngine().getTimeout());
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			element.click();
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

	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter xpath");
		if (text != null)
		{
			return "ClickXpath \"" + text + "\"";
		}
		else
		{
			return null;
		}
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/12/23/clickxpath";
	}

	public String getTooltip()
	{
		return "Clicks on a widget identified by an XPath";
	}

	public void setWebDriver(WebDriver webDriver)
	{
		this.webDriver=webDriver;
	}

	public void setScriptRunner(ScriptRunner scriptRunner)
	{
		this.scriptRunner=scriptRunner;
	}
}
