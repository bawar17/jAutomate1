package custom;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * Execute a command
 */
public class Execute
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if (commandParameters.length < 1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: Execute Command");
			return false;
		}

		String command = commandParameters[0];

		try
		{
			Process p = Runtime.getRuntime().exec(command);
			StringBuffer outBuffer=new StringBuffer();
			StringBuffer errorBuffer=new StringBuffer();
	    new Thread(new SyncPipe(p.getErrorStream(), errorBuffer)).start();
	    new Thread(new SyncPipe(p.getInputStream(), outBuffer)).start();
	    PrintWriter stdin = new PrintWriter(p.getOutputStream());
	    stdin.close();
	    int returnCode = p.waitFor();
			if(returnCode==0)
			{
				scriptParameters.put("Response", outBuffer.toString());
				return true;
			}
			else
			{
				scriptParameters.put("Response", errorBuffer.toString());
				return true;
			}
		}
		catch (Exception e)
		{
			scriptParameters.put("Error", "Failed to execute command: " + e.toString());
			return false;
		}
	}

	class SyncPipe implements Runnable
	{
		private final StringBuffer ostrm_;
		private final InputStream istrm_;

		public SyncPipe(InputStream istrm, StringBuffer ostrm)
		{
			istrm_ = istrm;
			ostrm_ = ostrm;
		}

		public void run()
		{
			try
			{
				final byte[] buffer = new byte[1024];
				for (int length = 0; (length = istrm_.read(buffer)) != -1;)
				{
					ostrm_.append(new String(buffer, 0, length));
				}
			}
			catch (Exception e)
			{
			}
		}
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/04/24/execute";
	}

	public String getTooltip()
	{
		return "Executes an operating system command";
	}

	public String[] getParameters()
	{
		return new String[]{"Error", "Response"};
	}
	
	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter a command");
		if (text != null)
		{
			return "Execute " + text;
		}
		else
		{
			return null;
		}
	}
}
