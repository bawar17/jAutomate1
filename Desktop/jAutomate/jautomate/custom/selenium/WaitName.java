package custom.selenium;

import jautomate.ScriptRunner;

import java.util.Properties;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitName
{
	private WebDriver webDriver=null;
	ScriptRunner scriptRunner=null;

	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: WaitName Name");
			return false;
		}

		String name=commandParameters[0].trim();

		if(webDriver==null)
		{
			scriptParameters.put("Error", "No browser open");
			return false;
		}

		try
		{
			WebDriverWait wait = new WebDriverWait(webDriver, scriptRunner.getVizionEngine().getTimeout());
			wait.until(ExpectedConditions.elementToBeClickable(By.name(name)));
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
		String text = JOptionPane.showInputDialog(null, "Enter name");
		if (text != null)
		{
			return "WaitName \"" + text + "\"";
		}
		else
		{
			return null;
		}
	}

	public String getTooltip()
	{
		return "Wait for a widget identified by a name";
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
