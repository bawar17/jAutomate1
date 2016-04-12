package custom;

import java.util.Properties;

import javax.swing.JOptionPane;

public class Replace
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<3)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: Replace Text From To");
			return false;
		}

		String text=commandParameters[0];
		String from=commandParameters[1];
		String to=commandParameters[2];
		String replaced=text.replace(from, to);
		scriptParameters.put("Replaced", replaced);
		return true;
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/03/03/replace";
	}

	public String getTooltip()
	{
		return "Replaces each subtext with another";
	}

	public String[] getParameters()
	{
		return new String[]{"Error", "Replaced"};
	}

	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter the original text");
		if (text != null)
		{
			String from = JOptionPane.showInputDialog(null, "Enter text to replace");
			if (from != null)
			{
				String to = JOptionPane.showInputDialog(null, "Enter text to insert");
				if (to != null)
				{
					return "Replace \""+text+"\" \""+from+"\" \""+to+"\"";
				}
			}
		}
		return null;
	}
}
