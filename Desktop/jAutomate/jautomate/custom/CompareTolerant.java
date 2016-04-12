package custom;

import jautomate.Box;
import jautomate.ScriptRunner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CompareTolerant
{
	ScriptRunner scriptRunner=null;
	
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: CompareTolerant Image");
			return false;
		}

		String filename=commandParameters[0].trim();

		BufferedImage image=scriptRunner.loadImage(filename);
		if(image==null)
		{
			scriptParameters.put("Error", "Failed to load image");
			return false;
		}
		
		List<Box> targetAreas=scriptRunner.getVizionEngine().createBoxes(image, null, 10);
		for(Box targetArea:targetAreas)
		{
			Point point=scriptRunner.getVizionEngine().findImage(image, targetArea);
			if(point!=null)
			{
				// Found the location of the image to compare
				BufferedImage capture = scriptRunner.getVizionEngine().createScreenCapture();
				BufferedImage imageCut = getSubimage(capture, (int)point.getX(), (int)point.getY(), image.getWidth(), image.getHeight());

				targetAreas=scriptRunner.getVizionEngine().createBoxes(image, null, 100);
				scriptRunner.getVizionEngine().evaluateBoxes(capture, targetAreas, (int)point.getX(), (int)point.getY());
				
				List<Rectangle> diffs=new ArrayList<Rectangle>();
				for(Box box:targetAreas)
				{
					if(box.getMatch()==0)
					{
						// No match
						diffs.add(new Rectangle(box.getX(), box.getY(), box.getWidth(), box.getHeight()));
					}
				}

				// Compare actual image against provided
				targetAreas=scriptRunner.getVizionEngine().createBoxes(imageCut, null, 100);
				scriptRunner.getVizionEngine().evaluateBoxes(image, targetAreas, 0, 0);
				
				for(Box box:targetAreas)
				{
					if(box.getMatch()==0)
					{
						// No match
						diffs.add(new Rectangle(box.getX(), box.getY(), box.getWidth(), box.getHeight()));
					}
				}

				if(diffs.size()==0)
				{
					return true;
				}
				
				diffs=mergeAllRectangles(diffs);
				
				BufferedImage imageDiff = createDiffImage(imageCut, diffs);

				// Use this image as screenshot
				scriptRunner.setScreenshot(imageDiff);
				
				scriptParameters.put("Error", "Image not identical");
				return false;
			}
		}
		
		scriptParameters.put("Error", "Image not found");
		return false;
	}

	private List<Rectangle> mergeAllRectangles(List<Rectangle> rects)
	{
		List<Rectangle> merged=rects;
		for(int i=0; i<20; i++)
		{
			int count=merged.size();
			merged=mergeRectangles(merged);
			if(merged.size()==count)
			{
				// No merge - done
				return merged;
			}
		}
		return merged;
	}

	private List<Rectangle> mergeRectangles(List<Rectangle> rects)
	{
		List<Rectangle> merged=new ArrayList<Rectangle>();
		
		for(Rectangle rect:rects)
		{
			Rectangle rectLarger=new Rectangle((int)rect.getX()-3, (int)rect.getY()-3, (int)rect.getWidth()+6, (int)rect.getHeight()+6);
			Rectangle intersecting=findIntersectingRectangle(rects, rectLarger, rect);
			if(intersecting!=null)
			{
				Rectangle union=intersecting.union(rect);
				if(findIntersectingRectangle(merged, union, null)==null)
				{
					merged.add(union);
				}
			}
			else
			{
				if(findIntersectingRectangle(merged, rect, null)==null)
				{
					merged.add(rect);
				}
			}
		}
		
		return merged;
	}

	private Rectangle findIntersectingRectangle(List<Rectangle> rects, Rectangle thisRectangle, Rectangle notThisRectangle)
	{
		for(Rectangle rect:rects)
		{
			if(rect!=notThisRectangle && rect.intersects(thisRectangle))
			{
				return rect;
			}
		}
		return null;
	}

	private static BufferedImage getSubimage(BufferedImage capture, int x, int y, int width, int height)
	{
		int startX=Math.min(Math.max(0, x), capture.getWidth()-1);
		int startY=Math.min(Math.max(0, y), capture.getHeight()-1);
		int w=Math.min(width, capture.getWidth()-startX);
		int h=Math.min(height, capture.getHeight()-startY);
		return capture.getSubimage(startX, startY, w, h);
	}

	private BufferedImage createDiffImage(BufferedImage image, List<Rectangle> diffs)
	{
		BufferedImage analysisImage=copyImage(image);
		return drawRectangles(analysisImage, diffs, Color.red);
	}

	public BufferedImage drawRectangles(BufferedImage image, List<Rectangle> diffs, Color color)
	{
		Graphics2D g2 = image.createGraphics();
		int boxNo=1;
		for(Rectangle diff:diffs)
		{
			int width=(int)diff.getWidth()-1;
			int height=(int)diff.getHeight()-1;
			g2.setColor(color);
			g2.drawRect((int)diff.getX(), (int)diff.getY(), width, height);
			boxNo++;
		}
		g2.dispose();
		return image;
	}

	private static BufferedImage copyImage(BufferedImage image)
	{
		GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage newImage = configuration.createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);
		Graphics graphics = newImage.createGraphics();      
		graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);      
		graphics.dispose();
		return newImage;
	}

	public String getHelp()
	{
		return "http://jautomate.com/2016/01/27/comparetolerant";
	}

	public String getTooltip()
	{
		return "Compares an image against a portion of the screen";
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}

	public String getCommand()
	{
		return "CompareTolerant \"<Image>\"";
	}

	public void setScriptRunner(ScriptRunner scriptRunner)
	{
		this.scriptRunner=scriptRunner;
	}
}
