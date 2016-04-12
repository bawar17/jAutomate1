package custom;

import java.util.Properties;

import javax.swing.JOptionPane;

public class StartsWith
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<2)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: StartsWith Text StartText");
			return false;
		}

		String text=commandParameters[0];
		String startText=commandParameters[1];
		if(text.startsWith(startText))
		{
			scriptParameters.put("StartsWith", "Yes");
		}
		else
		{
			scriptParameters.put("StartsWith", "No");
		}
		return true;
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/11/17/startswith";
	}

	public String getTooltip()
	{
		return "Determines if a text starts with a given string";
	}

	public String[] getParameters()
	{
		return new String[]{"Error", "StartsWith"};
	}

	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter the text");
		if (text != null)
		{
			String startText = JOptionPane.showInputDialog(null, "Enter the starting text");
			if (startText != null)
			{
				return "StartsWith \""+text+"\" \""+startText+"\"";
			}
		}
		return null;
	}
}
