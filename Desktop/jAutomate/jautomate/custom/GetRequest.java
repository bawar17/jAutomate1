package custom;

import java.util.Properties;

import javax.swing.JOptionPane;

import jautomate.HttpServiceCaller;

public class GetRequest
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: GetRequest HttpRequest");
			return false;
		}
		try
		{
			String address=commandParameters[0].trim();
			HttpServiceCaller service=new HttpServiceCaller();
			String response=service.executeGetRequest(address);
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
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/03/03/getrequest";
	}

	public String getTooltip()
	{
		return "Calls a web service";
	}

	public String[] getParameters()
	{
		return new String[]{"Error", "Response"};
	}
	
	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter HTTP request");
		if (text != null)
		{
			return "GetRequest " + text;
		}
		else
		{
			return null;
		}
	}
}
