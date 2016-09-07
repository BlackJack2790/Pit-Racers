import java.awt.Color;
import java.util.Random;

public abstract class gameWorldObjects
{
    private float xVariableLocation;	// Put together with the YVariableLocation will give the center of the object
    private float yVariableLocation;
    private Color color;
    private int size;
    
    public gameWorldObjects(float x, float y, Color color)
    {
        super();
        xVariableLocation = x;
        yVariableLocation = y;
        this.color = color;
    }

    
    public int getSize()
    {
    	return size;
    }
    
    public void setSize(int x)
    {
    	size = x;
    }
    
    // X Location
    public float getXLocation()
    {
        return xVariableLocation;
    }

    public void setXLocation(float xVariableLocation)
    {
        this.xVariableLocation = xVariableLocation;
    }

    // Y Location
    public float getYLocation()
    {
        return yVariableLocation;
    }

    public void setYLocation(float yVariableLocation)
    {
        this.yVariableLocation = yVariableLocation;
    }

  // Get and Set the Color of the objects
    public Color getColor()
    {
        return color;
    }


    public void setColor()
    {
        Random rand = new Random();
        color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
    }
// End getting and setting color of objects

  // toString function to display details such as location, color, etc of objects
    public String toString()
    {
        return "loc = " + (int)xVariableLocation + ", " + (int)yVariableLocation +
                " Color = " + color.getRed() + "." + color.getGreen() +
                "." + color.getBlue();
    }
}