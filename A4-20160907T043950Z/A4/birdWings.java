
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class birdWings implements IDrawable
{
	private Point top, bottomLeft, bottomRight;
	
	private AffineTransform myTranslation, myRotation, myScale;
	
	public birdWings()
	{
		// Playing with bird wing size. 
		top = new Point (0, 40);
		bottomLeft = new Point(-20,-40);
		bottomRight = new Point (20, -40);
		
		myTranslation = new AffineTransform();
		myRotation = new AffineTransform();
		myScale = new AffineTransform();
		
	}
	
	public void rotate (double degrees){
		myRotation.rotate(Math.toRadians(degrees));
	
	}
	
	public void translate(double dx, double dy){
		myTranslation.translate(dx, dy);
	}
	
	public void scale(double sx, double sy){
		myScale.scale(sx, sy);
	}
	
	@Override
	public void draw(Graphics2D g) 
	{
		AffineTransform AT = g.getTransform();
		g.transform(myTranslation);
		g.transform(myScale);
		g.transform(myRotation);
		
		g.setColor(Color.BLUE);
		//The Wings As Triangles.  
		g.drawLine((int)top.x, top.y, (int)bottomLeft.x, (int)bottomLeft.y);
		g.drawLine((int)bottomLeft.x, (int)bottomLeft.x, (int)bottomRight.x, (int)bottomRight.y);
		g.drawLine((int)bottomRight.x, (int)bottomRight.y, (int)top.x, (int)top.y);
		
		g.setTransform(AT);
		
	}
}

