package custom.selenium;

import jautomate.ScriptRunner;

import java.util.Properties;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ClickId
{
	private WebDriver webDriver=null;
	ScriptRunner scriptRunner=null;

	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: ClickId Id");
			return false;
		}

		String id=commandParameters[0].trim();

		if(webDriver==null)
		{
			scriptParameters.put("Error", "No browser open");
			return false;
		}

		try
		{
			WebDriverWait wait = new WebDriverWait(webDriver, scriptRunner.getVizionEngine().getTimeout());
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
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
		return new String[]{"Error"};
	}

	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter id");
		if (text != null)
		{
			return "ClickId \"" + text + "\"";
		}
		else
		{
			return null;
		}
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/12/23/clickid";
	}

	public String getTooltip()
	{
		return "Clicks on a widget identified by an id";
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
