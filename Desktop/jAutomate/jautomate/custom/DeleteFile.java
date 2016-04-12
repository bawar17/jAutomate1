package custom;

import java.awt.Frame;
import java.io.File;
import java.util.Properties;

import javax.swing.JFileChooser;

/**
 * Removes all the files in one or more folders
 * @author Jörgen Damberg, Claremont
 */
public class DeleteFile
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: DeleteFile FilePath");
			return false;
		}

		String fileName = commandParameters[0];
		File f = new File(fileName);

		// Make sure the file or directory exists and isn't write protected
		if (!f.exists())
		{
			scriptParameters.put("Error", "No such file or directory: "+fileName);
			return false;
		}

		if (!f.canWrite())
		{
			scriptParameters.put("Error", "Write protected: "+fileName);
			return false;
		}

		// If it is a directory, make sure it is empty
		if (f.isDirectory())
		{
			String[] files = f.list();
			if (files.length > 0)
			{
				scriptParameters.put("Error", "Directory not empty: "+fileName);
				return false;
			}
		}

		if (!f.delete())
		{
			scriptParameters.put("Error", "Failed to delete: "+fileName);
			return false;
		}

		return true;
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/04/16/deletefile";
	}

	public String getTooltip()
	{
		return "Deletes a file";
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}

	public String getCommand()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select file to delete");
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog((Frame)null) == JFileChooser.APPROVE_OPTION)
		{
			if (chooser.getSelectedFile() != null)
			{
				try
				{
					String name = chooser.getSelectedFile().getCanonicalPath();
					if (name != null && !"".equals(name.trim()))
					{
						return "DeleteFile " + "\"" + name + "\"";
					}
				}
				catch (Exception ex)
				{
				}
			}
		}
		return null;
	}
}
