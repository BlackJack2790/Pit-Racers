import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by Derek Irvin
 * CSC 133 Spring 2015
 * Project 1
 * Dr. John Clevenger
 */
public class fuelCanA1 extends fixedObjects implements IDrawable, ISelectable, ICollider
{
    private int fuelSize;
    private boolean isSelected = false; // Initialize to not selected
    private AffineTransform myRotation, myTranslation, myScale;

    public fuelCanA1(float x, float y, Color color, int size)
    {
        super(x, y, color);
        fuelSize = size;
        
        myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScale = new AffineTransform();
		
		myTranslation.translate((double)x, (double) y);
		//myScale.scale(1, -1);
    }

    public void rotate(double radians){
		myRotation.rotate(radians);
	}
	
	public void translate(double dx,double dy){
		myTranslation.translate(dx,dy);
	}
	
	// Mikasa - Veil of Maya
	
    public int getSize()
    {
        return fuelSize;
    }

    public int getValue()
    {
        int value = fuelSize;
        return value;
    }
    
    public String toString()
    {
        return "Fuel Can:" + super.toString() + " size: " + getSize();
    }

	@Override
	public void draw(Graphics2D g) 
	{
		AffineTransform AT = g.getTransform();
		
		g.transform(myTranslation);
		g.transform(myScale);
		// No Rotation needed
		
		// TODO Auto-generated method stub
		if(isSelected())
		{
			g.setColor(Color.PINK);
			g.drawRect(0, 0, getSize()/2, getSize());
			
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.BOLD, 20));
			g.drawString(" " + this.getSize(), 0, 0);
		}
		else
		{
			g.setColor(this.getColor());
			g.drawRect(0, 0, getSize()/2, getSize());
			
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.BOLD, 20));
			g.drawString(" " + this.getSize(), 0, 0);
		}
		
		g.setTransform(AT);	
	}

	@Override
	public void setSelected(boolean selected) 
	{
		// change whether the object is selected or not
		isSelected = selected;
	}

	@Override
	public boolean isSelected() 
	{
		// TODO Auto-generated method stub
		return isSelected;
	}

	@Override
	public boolean contains(Point2D p) 
	{
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
		if( (px >= xLocation) && (px <= xLocation + getSize())
				&& (py >= yLocation) && (py <= yLocation + getSize()))
		return true;
		else
		return false;
	}

	@Override
	public boolean collidesWith(ICollider obj) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleCollision(ICollider obj) 
	{
		// TODO Auto-generated method stub
	
		
	}

}
