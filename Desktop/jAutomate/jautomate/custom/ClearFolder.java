package custom;

import java.awt.Frame;
import java.io.File;
import java.util.Properties;

import javax.swing.JFileChooser;

/**
 * Removes all the files in one or more folders
 * @author Jörgen Damberg, Claremont
 */
public class ClearFolder
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: ClearFolder FilePath1 [FilePath2] ...");
			return false;
		}

		try
		{
			for (int i = 0; i < commandParameters.length; i++)
			{
				File dir = new File(commandParameters[i]);
				purgeDirectory(dir);
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	private void purgeDirectory(File dir)
	{
		for (File file : dir.listFiles())
		{
			if (file.isDirectory())
			{
				purgeDirectory(file);
			}
			file.delete();
		}
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/04/16/clearfolder";
	}

	public String getTooltip()
	{
		return "Deletes all files in a folder";
	}
	
	public String[] getParameters()
	{
		return new String[]{"Error"};
	}

	public String getCommand()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select folder to clear");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
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
						return "ClearFolder " + "\"" + name + "\"";
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
