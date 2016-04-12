package custom;

import java.util.Properties;

import javax.swing.JOptionPane;

import jautomate.HttpServiceCaller;

public class IsIdle
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: IsIdle Address");
			return false;
		}
		try
		{
			String address=commandParameters[0].trim();
			HttpServiceCaller service=new HttpServiceCaller();
			String request=addServerAndCommand(address, "isidle");
			String response=service.executeGetRequest(request);
			if(response==null)
			{
				scriptParameters.put("Error", "Failed to call: "+address);
				return false;
			}
			else
			{
				scriptParameters.put("Response", response);
				return true;
			}
		}
		catch(Exception e)
		{
			scriptParameters.put("Error", "Call failed: "+e.toString());
			return false;
		}
	}

	private static String addServerAndCommand(String serverAddress, String command)
	{
		String address = serverAddress;
		if (serverAddress.endsWith("/") || serverAddress.endsWith("\\"))
		{
			// Remove trailing slash
			address = serverAddress.substring(0, serverAddress.length() - 1);
		}
		return address + "/" + command;
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2015/06/09/isidle";
	}

	public String getTooltip()
	{
		return "Checks if web service is idle";
	}

	public String[] getParameters()
	{
		return new String[]{"Error", "Response"};
	}
	
	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter Address", "http://localhost:1234");
		if (text != null)
		{
			return "IsIdle " + text;
		}
		else
		{
			return null;
		}
	}
}
