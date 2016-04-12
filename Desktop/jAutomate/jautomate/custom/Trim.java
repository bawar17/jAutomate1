package custom;

import java.util.Properties;

import javax.swing.JOptionPane;

public class Trim
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: Trim Text");
			return false;
		}

		String text=commandParameters[0];

		scriptParameters.put("Trimmed", text.trim());
		return true;
	}

	public String getHelp()
	{
		return "http://jautomate.com/2014/06/15/trim";
	}

	public String getTooltip()
	{
		return "Trims the text from whitespace";
	}

	public String[] getParameters()
	{
		return new String[]{"Error", "Trimmed"};
	}
	
	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter a text");
		if (text != null)
		{
			return "Trim " + text;
		}
		return null;
	}
}
