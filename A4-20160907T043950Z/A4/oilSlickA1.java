import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 * Created by Derek Irvin
 * CSC 133 Spring 2015
 * Project 1
 * Dr. John Clevenger
 */
public class oilSlickA1 extends fixedObjects implements IDrawable, ICollider
{
    // variables special to oil slick
    private int oilWidth;
    private int oilLength;
    private int size;
    private AffineTransform myRotation, myTranslation, myScale;
    
    public oilSlickA1(float x, float y, Color color)
    {
        super(x, y, color);
        Random rand = new Random();
        // These Guys are too small. LARGER!!!
        oilWidth = rand.nextInt(80);
        oilLength = oilWidth;
        size = oilWidth;
        
        myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScale = new AffineTransform();
		
		translate((double)x, (double) y);
    }
    
    public void rotate(double radians){
		myRotation.rotate(radians);
	}
	
	public void translate(double dx,double dy){
		myTranslation.translate(dx,dy);
	}
	
    public int getSize()
    {
    	return size;
    }
    
    public int getWidth()
    {
    	return oilWidth;
    }
    
    public int getLength()
    {
    	return oilLength;
    }

    public String toString()
    {
        return ("Oil Slick: " + super.toString() + " width: " +
        getWidth() + "Length " + getLength());
    }

	@Override
	public void draw(Graphics2D g) 
	{
		AffineTransform AT = g.getTransform();
		// TODO Auto-generated method stub
		g.setColor(this.getColor());
		g.transform(myTranslation);
		g.transform(myRotation);
		g.transform(myScale);
		g.fillOval(0, 0, this.getLength(), this.getWidth());
	
		g.setTransform(AT);
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
		// Still working details if we want to pass an array list, or we want to just handle in the car atm. 
	}

}
