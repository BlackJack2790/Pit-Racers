import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;


public class NPCA2 extends movableObjects implements iSteerable, IDrawable, ICollider
{
	private iNPCStrategy npcStrategy;
	private int npcSize;
	private int nextPylon = 1; // initialize last pylon to 0. 
	private int steeringChange = 0; // Initialize to no change. 
	private int npcDamage = 0;
	private float targetX;
	private float targetY;
	private int timeCount = 0; // initalize to 0 because atm these fuckers are too fast. 
	private boolean oilSlick = false; // initialize to Not being in a oilSlick
	private int length = 20;
	private int width = 40;
	
	private boolean oilFlag = false;
	private boolean collisionFlag = false;
	private boolean birdFlag = false;
	
	// array list of currentCollisions. 
	
	
	Random rand = new Random();
	
	private AffineTransform myTranslation, myRotation, myScale;
	
	public NPCA2(float x, float y, int h, int s, Color color) {
		super(x, y, h, s, color);
		// TODO Auto-generated constructor stub
		
		
		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScale = new AffineTransform();
		
		npcSize = 20;
		// Initialize a random strategy based on a 0 or 1. 
		int strategy = rand.nextInt((1 - 0) + 1) + 0;
		if (strategy == 1)
		{
			npcStrategy = new npcRollerDerby();
		}
		else 
		{
			npcStrategy = new npcRacer();
		}
		
	//	myRotation.rotate(Math.toRadians(h));
	//	myTranslation.translate((int) x, (int) y);
	//	myScale.scale(1, -1);
		
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
	
	public int getSize()
	{
		return npcSize;
	}
	
	// for the Racer strategy when deciding what pylon to go for. 
	public int getNextPylon()
	{
		return nextPylon;
	}
	
	public boolean getSlick()
	{
		return oilSlick;
	}
	
	public void setSlick()
	{
		oilSlick = !oilSlick;
	}
	
	// For when we pass a pylon
	public void updatePylon()
	{
		nextPylon++;
	}
	
	public void setSize(int size)
	{
		npcSize = size;
	}
	
	public void setColor(Color c)
	{
		// Override
	}
	
	// maybe now it will think i am overriding if else i may just have the strategy only affect the target. 
	public void move(int delay)
	{
		timeCount = timeCount + 30;
		if (timeCount % delay == 0)
		{
			npcStrategy.move();
		}
		else
		{
			//slow down
		}
	}

	public int getDamage()
	{
		return npcDamage;
	}
	
	public void setDamage(int x)
	{
		npcDamage = x;
	}
	
	public void collision()
	{
		int npcCurrentDamage = getDamage();
		npcCurrentDamage = npcCurrentDamage + 20;
		if (npcCurrentDamage >= 300)
		{
			setSpeed(0);
		}
		else
		{
			setDamage(npcCurrentDamage);
		}
	}
	
	public void birdCollision()
	{
		int npcCurrentDamage = getDamage();
		npcCurrentDamage = npcCurrentDamage + 10;
		if (npcCurrentDamage >= 300)
		{
			setSpeed(0);
		}
		else
		{
			setDamage(npcCurrentDamage);
		}
	}
	
	public String toString()
	{
		return "NPC Car: " + super.toString() + " size: " + npcSize + " Last Pylon: " + getNextPylon()
				+ "npc Damage " + getDamage() + "Strategy " + getStrategy();
	}
	
	public void turnLeft()
	{
		// TODO Auto-generated method stub
				steeringChange = steeringChange - 5; // Turn to the left 5 degrees
				if(steeringChange <= -40)
				{
					steeringChange = -40;
				}
				steeringDirection();
	}
	
	public void turnRight()
	{
		// TODO Auto-generated method stub
				steeringChange = steeringChange + 5; // Turn to the left 5 degrees
				if(steeringChange <= 40)
				{
					steeringChange = 40;
				}
				steeringDirection();
	}
	@Override
	public void steeringDirection()
	{
		// We are going to change the heading of the npc cars here. 
		int carHeading = getHeading();
		carHeading = carHeading + steeringChange;
    	setHeading(carHeading);
		
	}
	
	public void npcDamage(boolean otherCar)
	{
		boolean hit = otherCar;
		// TODO Auto-generated method stub
		// If a other car we will say it means 20 damage
		if (hit == true)
		{
			npcDamage = npcDamage + 20;
		}
		else
		{
		// else we hit a bird meaning 10 damage(half a car damage)
			npcDamage = npcDamage + 10;
		}
		
		// If the npc reaches his super armor limit. hes done. make his speed 0 for now
		if (npcDamage >= 300)
		{
			setSpeed(0); // set the npc's speed to 0. 
		}
	}
	
	public iNPCStrategy getStrategy()
	{
		return npcStrategy; 
	}
	
	public void setStrategy (iNPCStrategy npcStrategy)
	{
		this.npcStrategy = npcStrategy;
		
	}
	
	public class npcRollerDerby implements iNPCStrategy
	{

		@Override
		public void move() 
		{
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			float carX = getTargetX();
			float currentX = getXLocation();
			
			float carY = getTargetY();
			float currentY = getYLocation();
			
			float yDifference = carY - currentY;
			float xDifference = carX - currentX;
			
			int newHeading = (int) Math.toDegrees(Math.atan2(
					xDifference, yDifference));
			
			if (xDifference > 0 && yDifference > 0)
			{
				setHeading(newHeading);
			}
			else if (xDifference > 0 && yDifference < 0)
			{
				setHeading((360 - newHeading));
			}
			else if (xDifference < 0 && yDifference > 0)
			{
				setHeading((360 + newHeading));
			}
			else if (xDifference < 0 && yDifference < 0)
			{
				setHeading((360 + newHeading));
			}
			
			int objectHeading = getHeading();
	     	int objectSpeed = getSpeed();
	     	// Degrees
	        float rad = (float) Math.toRadians(90 - objectHeading);
	         
	         // DeltaX and DeltaY
	         double dx = Math.cos(rad)*objectSpeed;
	         double dy = Math.sin(rad)*objectSpeed;
	         
	         // New Location
	     	 // Set the new Speed and Heading
	      	 setSpeed(objectSpeed);
	      	 setHeading(objectHeading);
	         setXLocation((float) (getXLocation() + dx));
	         setYLocation((float) (getYLocation() + dy));
			
		}
	}
	
	public class npcRacer implements iNPCStrategy
	{

		@Override
		public void move() 
		{
			// TODO Auto-generated method stub
						// TODO Auto-generated method stub
						float pylonX = getTargetX();
						float currentX = getXLocation();
						
						float pylonY = getTargetY();
						float currentY = getYLocation();
						
						float yDifference = pylonY - currentY;
						float xDifference = pylonX - currentX;
						
						int newHeading = (int) Math.toDegrees(Math.atan2(
								xDifference, yDifference));
						
						if (xDifference > 0 && yDifference > 0)
						{
							setHeading(newHeading);
						}
						else if (xDifference > 0 && yDifference < 0)
						{
							setHeading((360 - newHeading));
						}
						else if (xDifference < 0 && yDifference > 0)
						{
							setHeading((360 + newHeading));
						}
						else if (xDifference < 0 && yDifference < 0)
						{
							setHeading((360 + newHeading));
						}
						
						int objectHeading = getHeading();
				     	int objectSpeed = getSpeed();
				     	// Degrees
				        float rad = (float) Math.toRadians(90 - objectHeading);
				         
				         // DeltaX and DeltaY
				         double dx = Math.cos(rad)*objectSpeed;
				         double dy = Math.sin(rad)*objectSpeed;
				         
				         // New Location
				     	 // Set the new Speed and Heading
				      	 setSpeed(objectSpeed);
				      	 setHeading(objectHeading);
				         setXLocation((float) (getXLocation() + dx));
				         setYLocation((float) (getYLocation() + dy));
						
			}
			
	}
		

	// From my understanding of the strategy pattern we have two options. 
	public void updateTarget(float x, float y) 
	{
		// TODO Auto-generated method stub
		targetX = x;
		targetY = y;
	}

	public float getTargetX()
	{
		return targetX;
	}
	
	public float getTargetY()
	{
		return targetY;
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
		//AffineTransform AT = g.getTransform();
		// g.transform(myTranslate);
		// g.transform(myScale);
		// g.transform(myRotate);
		
		g.drawRect( 0, 0, getLength(), getWidth());
	
		//g.setTransform(AT);
		g.setTransform(new AffineTransform());
	}

	public int getLength() {
		// TODO Auto-generated method stub
		return length;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public boolean collidesWith(ICollider obj) 
	{
		// TODO Auto-generated method stub
		// Left and Right 
		int R1 = (int)this.getXLocation() + (getSize()/2);
		int L1 = (int)this.getXLocation() - (getSize()/2);
		
		// Top and Bottom
		int T1 = (int)this.getYLocation() + (getSize()/2);
		int B1 = (int)this.getYLocation() - (getSize()/2);
		
		// Radius of a Square. Yes it is annoying as it sounds. 
		int radius = getSize() / 2;
		
		
		// Radius Way
		if(obj instanceof pylonsA1)
		{
			int pylonRadius = (((pylonsA1) obj).getSize())/2;
			
			int pylonX = (int) ((pylonsA1) obj).getXLocation();
			int pylonY = (int) ((pylonsA1) obj).getYLocation();
			
			int dSquared = (int) (((pylonY - this.getYLocation())*(pylonY - this.getYLocation()))
					+ (pylonX - this.getXLocation())*(pylonX - this.getXLocation()));
			
			int rSquared = ((pylonRadius + radius)*(pylonRadius + radius));
			
			if(dSquared <= rSquared)
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		
		// Square Way, Car will handle collision with npc's
		if(obj instanceof NPCA2)
		{
			// TODO Auto-generated method stub
			// Left and Right 
			int R2 = (int) ((NPCA2) obj).getXLocation() + ((((NPCA2) obj).getSize())/2);
			int L2 = (int) ((NPCA2) obj).getXLocation() - ((((NPCA2) obj).getSize())/2);
			
			// Top and Bottom
			int T2 = (int) ((NPCA2) obj).getYLocation() + ((((NPCA2) obj).getSize())/2);
			int B2 = (int) ((NPCA2) obj).getYLocation() - ((((NPCA2) obj).getSize())/2);
			
			boolean conditionOne = (R1 > L2) && (L1 < R2);
			boolean conditionTwo = (T2 > B1) && (T1 > B2);
			
			if (conditionOne == true && conditionTwo == true)
			{
				return true;
			}
			else
			{
				setCollisionFlag(false);
				return false;
			}
			
		}
		
		// Radius Way
		if(obj instanceof oilSlickA1)
		{
			int slickRadius = (((oilSlickA1) obj).getSize())/2;
			
			int slickX = (int) ((oilSlickA1) obj).getXLocation();
			int slickY = (int) ((oilSlickA1) obj).getYLocation();
			
			int dSquared = (int) (((slickY - this.getYLocation())*(slickY - this.getYLocation()))
					+ (slickX - this.getXLocation())*(slickX - this.getXLocation()));
			
			int rSquared = ((slickRadius + radius)*(slickRadius + radius));
			
			if(dSquared <= rSquared)
			{
				return true;
			}
			else
			{
				setOilFlag(false);
				return false;
			}
		}
		
		// Radius Way for the instance of a bird
		if(obj instanceof birdA1)
		{
			int birdRadius = (((birdA1) obj).getSize())/2;
					
			int birdX = (int) ((birdA1) obj).getXLocation();
			int birdY = (int) ((birdA1) obj).getYLocation();
					
			int dSquared = (int) (((birdY - this.getYLocation())*(birdY - this.getYLocation()))
				+ (birdX - this.getXLocation())*(birdX - this.getXLocation()));
					
			int rSquared = ((birdRadius + radius)*(birdRadius + radius));
					
			if(dSquared <= rSquared)
			{
				return true;
			}
			else
			{
				setBirdFlag(false);
				return false;
			}
		}
		return false;
	}

	@Override
	public void handleCollision(ICollider obj) 
	{
		// TODO Auto-generated method stub
		
		if (obj instanceof pylonsA1)
		{
			int collidedPylon = ((pylonsA1) obj).getPylonNumber();
			int currentPylon = getNextPylon();
			if(collidedPylon == currentPylon)
			{
				updatePylon();
			}
			else
			{
				// do nothing
			}
		}
		
		if (obj instanceof birdA1)
		{
			if(getBirdFlag() == true)
			{
				
			}
			else
			{
				birdCollision();
				//gw.makeWave(getXLocation(), getYLocation());
				setBirdFlag(true);
			}
		}
		
		if (obj instanceof oilSlickA1)
		{
			if(getOilFlag() == true)
			{
				
			}
			else
			{
				setSlick();
				setOilFlag(true);
			}	
		}
		
		if (obj instanceof NPCA2)
		{
			if(getCollisionFlag() == true)
			{
				
			}
			else
			{
				collision();
				setCollisionFlag(true);
			}
		}	
		
	}
	
	public boolean getCollisionFlag()
	{
		return collisionFlag;
	}
	
	public boolean getBirdFlag()
	{
		return birdFlag;
	}
	
	public boolean getOilFlag()
	{
		return oilFlag;
	}
	
	public void setCollisionFlag(boolean value)
	{
		collisionFlag = value;
	}
	
	public void setOilFlag(boolean value)
	{
		oilFlag = value;
	}
	
	public void setBirdFlag(boolean value)
	{
		birdFlag = value;
	}
}