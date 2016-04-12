package custom;

import jautomate.ScriptRunner;

import java.util.Properties;

import javax.swing.JOptionPane;

public class Scale
{
	ScriptRunner scriptRunner=null;
	
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: Scale Percent");
			return false;
		}

		String scale=commandParameters[0].trim();

		int no = string2Int(scale, 0);
		if (no < 1 || no > 10000)
		{
			scriptParameters.put("Error", "Specify an Scale between 1 and 10000");
			return false;
		}

		scriptRunner.getVizionEngine().setScale(no);
		return true;
	}

	public String getTooltip()
	{
		return "Sets the scale of images to find";
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}

	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter a scale in percent between 1 and 10000");
		if (text != null)
		{
			return "Scale " + text;
		}
		return null;
	}

	public void setScriptRunner(ScriptRunner scriptRunner)
	{
		this.scriptRunner=scriptRunner;
	}

	/**
	 * Converts to s to an int
	 * @return An int or an alternative int if failed
	 */
	private static int string2Int(String s, int otherwise)
	{
		String text = s.trim();
		if(s.length()==0)
		{
			// Blank
			return 0;
		}
		char c = text.charAt(0);
		if (c == '+')
		{
			// Remove + sign
			text=text.substring(1);
		}
		try
		{
			return Integer.parseInt(text);
		}
		catch (Exception e)
		{
			// Couldn't convert to int
			return otherwise;
		}
	}
}
