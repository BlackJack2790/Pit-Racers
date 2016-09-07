import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

/**
 * Created by Derek Irvin
 * CSC 133 Spring 2015
 * Project 1
 * Dr. John Clevenger
 */
public class birdA1 extends movableObjects implements IDrawable, ICollider
{
    // Size is just to the bird so we keep this here.
    private int birdSize; // testing a big ass fucking bird. 
    private birdWings[] wings; // bird wings. 
    private birdBeak[] beak;	// Bird Beak
    
    private double wingOffset = 0;
    private double wingIncrement = 5;
    private double maxWingOffset = 15;
    private double wingRotOffset = 0;
	private double wingMotion = .05;
	private double wingRotation = 1;
	private int timeCount;
	
    private AffineTransform myRotation, myTranslation, myScale;
    
    // Initialization of the bird.
    public birdA1(float x, float y, int h, int s, int sz, Color color)
    {
        super(x, y, h, s, color); // All movable objects have these qualities
        birdSize = sz; // Size is set up in the initial initiation
    
        myTranslation = new AffineTransform();
        myRotation = new AffineTransform();
		myScale = new AffineTransform();
		
		myTranslation.translate((double)x, (double) y);
		
		beak = new birdBeak[1];
		
		wings = new birdWings[2]; 
		
		birdBeak beak1 = new birdBeak();
		beak[0] = beak1;
		//beak[0].translate(this.getSize(), this.getSize()/2);
		//beak[0].scale(getSize()*0.009, getSize()*0.009);
		
		
		//Wings
		birdWings wing0 = new birdWings();
		wing0.translate(this.getSize()/2, this.getSize());
		wing0.scale(this.getSize()*0.008,this.getSize()*0.008);
		wings[0] = wing0;
		
		
		birdWings wing1 = new birdWings();
		wing1.translate(this.getSize()/2, -this.getSize());
		wing1.scale(this.getSize()*0.008,this.getSize()*0.008);
		wing1.rotate(180);
		
		wings[1]=wing1;
    }

    public void rotate(double radians){
		myRotation.rotate(radians);
	}
	
	public void translate(double dx,double dy){
		myTranslation.translate(dx,dy);
	}
	
    public int getSize()
    {
        return birdSize;
    }

    public void setH(int h)
    {
        // Override
    }

    public void setSize(int size)
    {
        // Overriden since we set this in the initial initiation
    }

      public String toString()
    {
        // Extension class will have all other information other than
        // getSize();
        return "Bird: " + super.toString() + " size: " + getSize();
    }
     
      public void move(int delay)
      {
      	// DO NOT TOUCH. 
      	timeCount = timeCount + 30; // keep track of each in world game tick. This runs smoother here. 
      	
      	if((timeCount % delay) == 0)
      	{
      		// Degrees
      		float rad = (float) Math.toRadians(90 - getHeading());
      		// DeltaX and DeltaY
      		double dx = Math.cos(rad)*getSpeed();
      		double dy = Math.sin(rad)*getSpeed();
      		// New Location
      		this.setXLocation((float) (this.getXLocation() + dx) );
      		this.setYLocation((float) (this.getYLocation() + dy));
      	}
      	else
      	{
      		//moving too fast. slow down silver. does it not think i am overriding anymore? if it is. i should fix that now. 
      	}
      	
      	update();
      }

	@Override
	public void draw(Graphics2D g) 
	{
		
		
		AffineTransform AT = new AffineTransform();
		
		AT.translate((int) getXLocation() - (getSize() / 2), (int) getYLocation() - (getSize() / 2));
		AT.scale(1, -1);
		AT.rotate(Math.toRadians(getHeading()));
		
		g.setTransform(AT);
		
		g.setColor(this.getColor());
		
		
		g.drawOval(0, 0, getSize(), getSize());
		
		for(birdWings w: wings)
		{
			w.draw(g);
			this.update();
		}
		
		for(birdBeak b: beak)
		{
			b.draw(g);
			this.update();
		}
		g.setTransform(new AffineTransform());
	}

	public void update() 
	{
		wingRotOffset += wingMotion;
		wingOffset += wingIncrement;
		
		for (birdWings w: wings)
		{
			w.translate(0, wingOffset);
		}
		
		// OffSets
		if(Math.abs(wingRotOffset) >= wingRotation){
			wingMotion *= -1;
		}
		
		
		if(Math.abs(wingOffset) >= maxWingOffset){
			wingIncrement *= -1;
		}
			
	}

	@Override
	public boolean collidesWith(ICollider obj)
	{
		// TODO Auto-generated method stub
		// Collisions are handled in NPC and Car
		return false;
	}

	@Override
	public void handleCollision(ICollider obj)
	{
		// TODO Auto-generated method stub
		
	}

	private class birdBeak
	{
		private Point top, bottomRight, bottomLeft;
		private Color myColor;
		private AffineTransform myTranslation, myRotation, myScale;
		
		public birdBeak()
		{
			top = new Point(0,12);
			bottomRight = new Point(4, 12);
			bottomLeft = new Point(-4, -12);
			myColor = Color.ORANGE;
			myTranslation = new AffineTransform();
			myRotation = new AffineTransform();
			myScale = new AffineTransform();
			myScale.scale(1, 1);
		}
		
		public void rotate(double angle)
		{
			myRotation.rotate(Math.toRadians(angle));
		}
		
		public void scale(double sx, double sy)
		{
			myScale.scale(sx, sy);
		}
		
		public void translate(double dx, double dy)
		{
			myTranslation.translate(dx, dy);
		}
		
		public void draw(Graphics2D g)
		{
			AffineTransform AT = g.getTransform();
			
			g.transform(myRotation);
			g.transform(myScale);
			g.transform(myTranslation);
			
			g.setColor(myColor);
			g.drawLine(top.x, top.y, bottomLeft.x, bottomLeft.y);
			g.drawLine(bottomLeft.x, bottomLeft.x, bottomRight.x, bottomRight.y);
			g.drawLine(bottomRight.x, bottomRight.y, top.x, top.y);
			g.setTransform(AT);
		}
	}
}


