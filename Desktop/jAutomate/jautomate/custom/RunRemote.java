package custom;

import jautomate.HttpServiceCaller;

import java.util.Properties;

import javax.swing.JOptionPane;

public class RunRemote implements Runnable
{
	private String address=null;
	
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: RunRemote HttpRequest");
			return false;
		}
		address=commandParameters[0].trim();
		try
		{
			Thread thread = new Thread(this);
			thread.start();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2015/06/09/runremote";
	}

	public String getTooltip()
	{
		return "Runs a remote script";
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}
	
	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter HTTP request");
		if (text != null)
		{
			return "RunRemote " + text;
		}
		else
		{
			return null;
		}
	}

	public void run()
	{
		HttpServiceCaller service=new HttpServiceCaller();
		service.executeGetRequest(address);
	}
}
