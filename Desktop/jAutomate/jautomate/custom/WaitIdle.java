package custom;

import java.util.Properties;

import javax.swing.JOptionPane;

import jautomate.HttpServiceCaller;
import jautomate.ScriptRunner;

public class WaitIdle
{
	ScriptRunner scriptRunner=null;
	
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<2)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: WaitIdle Address TimeoutSeconds");
			return false;
		}
		try
		{
			String address=commandParameters[0].trim();
			String timeoutStr=commandParameters[1].trim();
			int timeout=Integer.parseInt(timeoutStr);
			HttpServiceCaller service=new HttpServiceCaller();
			long startTimestamp=System.currentTimeMillis();
			while(true)
			{
				long currentTimestamp=System.currentTimeMillis();
				if(currentTimestamp>=startTimestamp+timeout*1000)
				{
					scriptParameters.put("Error", "Timeout");
					return false;
				}
				String request=addServerAndCommand(address, "isidle");
				String response=service.executeGetRequest(request);
				if(response==null)
				{
					scriptParameters.put("Error", "Failed to call: "+address);
					return false;
				}
				else
				{
					if("yes".equalsIgnoreCase(response))
					{
						request=addServerAndCommand(address, "getlastreport");
						response=service.executeGetRequest(request);
						if(response!=null)
						{
							String reportLink=addServerAndCommand(address, response);
							scriptRunner.setCommandResultText(reportLink);
						}
						return true;
					}
				}
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					return false;
				}
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
		return "http://jautomate.com/2015/06/09/waitidle";
	}

	public String getTooltip()
	{
		return "Waits until a web service is idle";
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
			String timeout = JOptionPane.showInputDialog(null, "Enter seconds to timeout");
			if (timeout != null)
			{
				return "WaitIdle " + text+ " " + timeout;
			}
		}
		return null;
	}

	public void setScriptRunner(ScriptRunner scriptRunner)
	{
		this.scriptRunner=scriptRunner;
	}
}
