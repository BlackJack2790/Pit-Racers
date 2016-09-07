import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

/**
 * Created by Derek Irvin
 * CSC 133 Spring 2015
 * Project 1
 * Dr. John Clevenger
 */
public class pylonsA1 extends fixedObjects implements IDrawable, ISelectable, ICollider
{
    private int radius;
    private int pylonNumber;
    private boolean isSelected = false; // initialize the selected boolean to false. 
    private AffineTransform myTranslation, myRotation, myScale;
    
    public pylonsA1(float x, float y, Color color, int pylonValue)
    {
        super(x, y, color);
        setRadius(40);
        pylonNumber = pylonValue;
    
        myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScale = new AffineTransform();
		
    	myTranslation.translate((double)x, (double) y);
    	myRotation.rotate(Math.toRadians(180));
    	//myScale.scale(-1, 1);
		
    }

    public void rotate (double degrees)
    {
		myRotation.rotate(Math.toRadians(degrees));
	
	}
	
	public void translate(double dx, double dy){
		myTranslation.translate(dx, dy);
	}
	
	public void scale(double sx, double sy){
		myScale.scale(sx, sy);
	}
	
    public int getRadius()
    {
        return radius;
    }
    
    public void setRadius(int x)
    {
    	radius = x;
    }

    public int getPylonNumber()
    {
        return pylonNumber;
    }
    public String toString()
    {
        return "Pylon: " + super.toString() + " radius: " + getRadius() +
                " Pylon Number: " + getPylonNumber();
    }

	@Override
	public void draw(Graphics2D g) 
	{
		AffineTransform AT = g.getTransform();
		
		// TODO Auto-generated method stub
		if(isSelected())
		{
			
			
			g.setColor(Color.PINK);
			
			g.transform(myTranslation);
			g.transform(myScale);
			// No Rotation
			
			g.fillOval(0, 0, getRadius(), getRadius());
			
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.BOLD, 20));
			g.drawString(" " + this.getPylonNumber(), 0, 0);
			
		
		}
		else
		{
			
			g.setColor(this.getColor());
			
			g.transform(myTranslation);
			g.transform(myScale);
			// No Rotation
			
			g.fillOval(0, 0, getRadius(), getRadius());
		
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.BOLD, 20));
			g.drawString(" " + this.getPylonNumber(), 0, 0);
		
		}
		
		g.setTransform(AT);
	}

	@Override
	public void setSelected(boolean selected) {
		// TODO Auto-generated method stub
		isSelected = selected;
		
	}

	@Override
	public boolean isSelected() 
	{
		// TODO Auto-generated method stub
		return isSelected;
	}

	@Override
	public boolean contains(Point2D p) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		
		Point2D localPoint = null;
		
		try
		{
			localPoint = myTranslation.createInverse().transform(p,null);
		}
		catch(NoninvertibleTransformException e1)
		{
			System.out.println("Error of type: " + e1);
		}
		
		
		// point location
		int px = (int)p.getX();
		int py = (int)p.getY();
		
		// object location
		int xLocation = (int)this.getXLocation();
		int yLocation = (int)this.getYLocation();
		if( (px >= xLocation) && (px <= xLocation + getRadius())
				&& (py >= yLocation) && (py <= yLocation + getRadius()))
			return true;
		else
			return false;
	}

	@Override
	public boolean collidesWith(ICollider obj) 
	{
		// TODO Auto-generated method stub
		
		// Collisions Handled Elsewhere
		return false;
	}

	@Override
	public void handleCollision(ICollider obj) 
	{
		// TODO Auto-generated method stub
		// Collisions Handled Elsewhere
	}
}
