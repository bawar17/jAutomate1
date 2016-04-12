package custom;

import jautomate.Box;
import jautomate.ScriptRunner;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Properties;

public class Move
{
	ScriptRunner scriptRunner=null;
	
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length==1)
		{
			// An image
			String filename = commandParameters[0];
			BufferedImage image=scriptRunner.loadImage(filename);
			if(image==null)
			{
				scriptParameters.put("Error", "Failed to load image");
				return false;
			}
			if(scriptRunner.getVizionEngine().waitMouseMove(image))
			{
				// Moved to that image
				return true;
			}
		}
		else if (commandParameters.length==2)
		{
			// X and Y images or coordinates
			String x = commandParameters[0];
			String y = commandParameters[1];
			int nextX=0;
			int nextY=0;
			int lastX=(int)scriptRunner.getVizionEngine().getLastPosition().getX();
			int lastY=(int)scriptRunner.getVizionEngine().getLastPosition().getY();
			if(isRelativeIntString(x))
			{
				lastX+=Integer.parseInt(x);
				nextX=lastX;
			}
			else if (isIntString(x))
			{
				nextX = Integer.parseInt(x);
			}
			else
			{
				BufferedImage imageXToFind=scriptRunner.loadImage(x);
				if(imageXToFind==null)
				{
					scriptParameters.put("Error", "Failed to load image");
					return false;
				}
				Point pointX = scriptRunner.getVizionEngine().waitAndFindImage(imageXToFind);
				if (pointX == null)
				{
					scriptParameters.put("Error", "Image not found");
					return false;
				}
				double relativePosX = imageXToFind.getWidth()/2;
				nextX = (int) pointX.getX() + (int)relativePosX;
			}
			if(isRelativeIntString(y))
			{
				lastY+=Integer.parseInt(y);
				nextY=lastY;
			}
			else if (isIntString(y))
			{
				nextY = Integer.parseInt(y);
			}
			else
			{
				BufferedImage imageYToFind=scriptRunner.loadImage(x);
				if(imageYToFind==null)
				{
					scriptParameters.put("Error", "Failed to load image");
					return false;
				}
				Point pointY = scriptRunner.getVizionEngine().waitAndFindImage(imageYToFind);
				if (pointY == null)
				{
					scriptParameters.put("Error", "Image not found");
					return false;
				}
				double relativePosY = imageYToFind.getHeight()/2;
				nextY = (int) pointY.getY() + (int)relativePosY;
			}
			scriptRunner.getVizionEngine().mouseMove(nextX, nextY);
		}
		else if (commandParameters.length==3)
		{
			// Image and relative position
			try
			{
				String filename = commandParameters[0];
				String xs = commandParameters[1];
				String ys = commandParameters[2];
	
				BufferedImage image=scriptRunner.loadImage(filename);
				if(image==null)
				{
					scriptParameters.put("Error", "Failed to load image");
					return false;
				}
				
				int relativePosX=0;
				int relativePosY=0;
				if(isRelativeIntString(xs))
				{
					int rel = Integer.parseInt(xs);
					relativePosX = image.getWidth()/2 + (int) rel;
				}
				else
				{
					int moveRelativeXTemp = Integer.parseInt(xs);
					if (moveRelativeXTemp < 0 || moveRelativeXTemp > 100)
					{
						scriptParameters.put("Error", "Specify a RelativePosition between 0 and 100");
						return false;
					}
					relativePosX = (int)(image.getWidth() * (moveRelativeXTemp / 100.0));
				}
				if(isRelativeIntString(ys))
				{
					int rel = Integer.parseInt(ys);
					relativePosY = image.getHeight()/2 + (int) rel;
				}
				else
				{
					int moveRelativeYTemp = Integer.parseInt(ys);
					if (moveRelativeYTemp < 0 || moveRelativeYTemp > 100)
					{
						scriptParameters.put("Error", "Specify a RelativePosition between 0 and 100");
						return false;
					}
					relativePosY = (int)(image.getHeight() * (moveRelativeYTemp / 100.0));
				}
				if (scriptRunner.getVizionEngine().waitMouseMove(image, relativePosX, relativePosY, new Box(relativePosX, relativePosY)) == false)
				{
					scriptParameters.put("Error", "Image not found");
					return false;
				}
			}
			catch(Exception e)
			{
				scriptParameters.put("Error", "Specify coordinates and size using integer values");
				return false;
			}
		}
		else if (commandParameters.length==5)
		{
			// Image and a rectangle
			try
			{
				String filename = commandParameters[0];
				int x = Integer.parseInt(commandParameters[1]);
				int y = Integer.parseInt(commandParameters[2]);
				int width = Integer.parseInt(commandParameters[3]);
				int height = Integer.parseInt(commandParameters[4]);
				Box box=new Box(x, y, x+width-1, y+height-1);
				BufferedImage image=scriptRunner.loadImage(filename);
				if(image==null)
				{
					scriptParameters.put("Error", "Failed to load image");
					return false;
				}
				int relativePosX=x+width/2;
				int relativePosY=y+height/2;
				if (scriptRunner.getVizionEngine().waitMouseMove(image, relativePosX, relativePosY, box) == false)
				{
					scriptParameters.put("Error", "Image not found");
					return false;
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
			scriptParameters.put("Error", "Missing parameter in command. Usage: Move [Image/Text] [PercentX|RelativeX] [PercentY|RelativeY] [Width] [Height]");
			return false;
		}
		return true;
	}

	/**
	 * Determine if s is an integer (may begin with a - or + sign)
	 * @param s
	 * @return true if s is an integer
	 */
	private static boolean isIntString(String s)
	{
		String text = s.trim();
		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			if (i==0)
			{
				// First char may contain - or +
				if (!(Character.isDigit(c) || c == '-' || c == '+'))
				{
					return false;
				}
			}
			else
			{
				if (!Character.isDigit(c))
				{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Determine if s is an integer that begins with a - or + sign
	 * @param s
	 * @return true if s is an integer that begins with - or +
	 */
	private static boolean isRelativeIntString(String s)
	{
		String text = s.trim();
		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			if (i==0)
			{
				// First char must contain - or +
				if (!(c == '-' || c == '+'))
				{
					return false;
				}
			}
			else
			{
				if (!Character.isDigit(c))
				{
					return false;
				}
			}
		}
		return true;
	}

	public String getHelp()
	{
		return "http://jautomate.com/wp-content/uploads/JAutomateManual13.html#h.guv0vmhdchhw";
	}

	public String getTooltip()
	{
		return "Moves the mouse to an image";
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}

	public String getCommand()
	{
		return "Move \"<Image>\"";
	}

	public void setScriptRunner(ScriptRunner scriptRunner)
	{
		this.scriptRunner=scriptRunner;
	}
}
