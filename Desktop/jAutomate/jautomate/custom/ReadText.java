package custom;

import jautomate.Box;
import jautomate.ScriptRunner;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.util.Properties;

public class ReadText
{
	ScriptRunner scriptRunner=null;
	
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if (commandParameters.length==5)
		{
			// Image and a rectangle
			try
			{
				String filename = commandParameters[0];
				int x = Integer.parseInt(commandParameters[1]);
				int y = Integer.parseInt(commandParameters[2]);
				int width = Integer.parseInt(commandParameters[3]);
				int height = Integer.parseInt(commandParameters[4]);
				BufferedImage image=scriptRunner.loadImage(filename);
				if(image==null)
				{
					scriptParameters.put("Error", "Failed to load image");
					return false;
				}

				
				Box removedSelection=new Box(x, y, x+width-1, y+height-1);
				scriptRunner.getVizionEngine().setRemovedSelection(removedSelection);
				Point point = scriptRunner.getVizionEngine().waitAndFindImage(image);
				scriptRunner.getVizionEngine().resetRemovedSelection();
				if (point == null)
				{
					scriptParameters.put("Error", "Unable to find image");
					return false;
				}

				int dx=(int)point.getX()+x;
				int dy=(int)point.getY()+y;

				scriptRunner.getVizionEngine().mouseMove(dx, dy);
				scriptRunner.getVizionEngine().type("[WINDOWS_PRESS]q[WINDOWS_RELEASE]");
				scriptRunner.getVizionEngine().mouseMoveRelative(width, height);
				scriptRunner.getVizionEngine().type("[WINDOWS_PRESS]q[WINDOWS_RELEASE]");

				Thread.sleep(1000);
				
				String clip=getClip();
				if(clip!=null)
				{
					scriptParameters.put("ReadText", clip);
				}
			}
			catch(Exception e)
			{
				scriptParameters.put("Error", "Specify coordinates and size using integer values");
				return false;
			}
		}
		else
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: ReadText Image X Y Width Height");
			return false;
		}
		return true;
	}

	private String getClip()
	{
		try
		{
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable cliptran = clipboard.getContents(this);
			String sel = (String) cliptran.getTransferData(DataFlavor.stringFlavor);
			return sel;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public String getHelp()
	{
		return "http://jautomate.com/2016/01/22/readtext";
	}

	public String getTooltip()
	{
		return "Reads the text inside the selection using the Capture2Text OCR reader";
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}

	public String getCommand()
	{
		return "ReadText \"<Image>\"";
	}

	public void setScriptRunner(ScriptRunner scriptRunner)
	{
		this.scriptRunner=scriptRunner;
	}
}
