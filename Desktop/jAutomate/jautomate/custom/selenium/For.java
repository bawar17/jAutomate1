package custom.selenium;

import jautomate.CustomCommand;
import jautomate.ScriptRunner;

import java.util.Properties;

import javax.swing.JOptionPane;

public class For
{
	ScriptRunner scriptRunner=null;
	
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: For XPath/CSS");
			return false;
		}

		String xpath=commandParameters[0].trim();
		CustomCommand command=new CustomCommand(xpath);
		scriptRunner.setBlockCommand(command);
		scriptRunner.increaseCommandLevel();
		return true;
	}

	public String getTooltip()
	{
		return "Click for each XPath/CSS result";
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}

	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter XPath or CSS");
		if (text != null)
		{
			return "For \"" + text + "\"\nEndFor";
		}
		else
		{
			return null;
		}
	}

	public String getBeginCommand()
	{
		return "For";
	}

	public String getEndCommand()
	{
		return "EndFor";
	}

	public void setScriptRunner(ScriptRunner scriptRunner)
	{
		this.scriptRunner=scriptRunner;
	}
}
