import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Created by Derek Irvin
 * CSC 133 Spring 2015
 * Project 1
 * Dr. John Clevenger
 */
public class carA1 extends movableObjects implements iSteerable, IDrawable, ICollider
{
	private int width = 40;
	private int height = 20;
	private int fuelLevel = 100; // Initial fuel is 40. lets say it goes down 2 a tick
	private int damageLevel;
	private int carSpeed;
	private int carHeading;
	private int steeringChange = 0; // Initial Change
	private int currentTime = 0;
	private float rot; 
	private float m, n; 
	private boolean oilSlick = false;

	// Flags For Collisions With Multiple Occurences
	private boolean collisionFlag = false; 
	private boolean birdFlag = false; 
	private boolean oilFlag = false;

	private gameWorld gw;


	private AffineTransform myTranslation, myRotation, myScale;

	public carA1(float x, float y, int heading, int speed, Color color, gameWorld gameWorld)
	{
		super(x, y, heading, speed, color);
		setSpeed(speed);
		setHeading(heading);
		gw = gameWorld;

		myTranslation = new AffineTransform();
		myRotation = new AffineTransform();
		myScale = new AffineTransform();

		//translate((double) x, (double) y);
		//rotate(Math.toRadians(90-heading));
	}


	public void rotate (double radians){
		myRotation.rotate(radians);

	}

	public void translate(double dx, double dy){
		myTranslation.translate(dx, dy);
	}

	public void scale(double sx, double sy){
		myScale.scale(sx, sy);
	}

	public void gasUp(int x)
	{
		fuelLevel = fuelLevel + x;
		// check if the fuel level is at the max
		if (fuelLevel > 100)
		{
			fuelLevel = 100;
		}
	}

	public void gasDrain()
	{
		fuelLevel = fuelLevel - 2; // fuel goes down by 1 per tick
	}

	public int getFuel()
	{
		return fuelLevel;
	}

	public void accelerate()
	{
		int accelSpeed = getSpeed();
		accelSpeed = accelSpeed + 5;
		System.out.println("Current Speed =: " + accelSpeed);
		setSpeed(accelSpeed);
	}

	public int getDamage()
	{
		return damageLevel;
	}

	public int getHeading()
	{
		return carHeading;
	}

	public int getSize()
	{
		return 30;
	}
	public int getSpeed()
	{
		float damageReduction;
		int testDamage = getDamage();
		if(testDamage == 0)
		{
			// No damage carry on. 
			return carSpeed;
		}
		else
		{
			damageReduction = (float) (damageLevel * 0.1); // get the percentage 
			carSpeed = (int)(carSpeed * damageReduction);
			return carSpeed;
		}
	}

	// Having some issue here to fix. 
	public void setSpeed(int s)
	{
		carSpeed = s;
	}

	public boolean getSlick()
	{
		return oilSlick;
	}

	public void oilSlick()
	{
		oilSlick = !oilSlick; // change slick flag
	}

	public void steeringDirection()
	{
		carHeading = carHeading + steeringChange;
		setHeading(carHeading);
		//rotate(Math.toRadians(getHeading()));
	}

	public String toString()
	{
		return "Car: " + super.toString() + "Width: " + width + " Height: "
				+ height + "Damage Level: " + damageLevel + " Fuel Level: " + fuelLevel + 
				" Oil Slick: " + oilSlick;
	}


	public void decelerate() 
	{
		// TODO Auto-generated method stub
		int decelSpeed = getSpeed();
		decelSpeed = decelSpeed - 2;
		setSpeed(decelSpeed);
	}


	public void turnLeft()
	{
		// TODO Auto-generated method stub
		// get the current steering change
		int currentChange = getSteeringChange();
		// Turn left 5 degrees from this value
		currentChange = currentChange - 1; // Turn to the left 5 degrees
		if(currentChange <= -40)
		{
			System.out.println("Turning more than 40 Degrees is not allowed");
			currentChange = -40;
		}
		setSteeringChange(currentChange);
		steeringDirection();
	}

	public void turnRight() 
	{
		// TODO Auto-generated method stub
		// get the current steering direction
		int currentChange = getSteeringChange();
		// turn right by adding 5
		currentChange = currentChange + 1; // turn to the right 5 degrees
		if(currentChange >= 40)
		{
			System.out.println("Turning more than 40 Degrees is not allowed");
			currentChange = 40;
		}
		setSteeringChange(currentChange);
		steeringDirection();
	}

	public int getSteeringChange()
	{
		return steeringChange;
	}

	public void setSteeringChange(int x)
	{
		steeringChange = x;
	}

	public void carDamage(boolean otherCar)
	{
		int currentDamage = getDamage();
		boolean hit = otherCar;
		// TODO Auto-generated method stub
		// If a other car we will say it means 20 damage
		if (hit == true)
		{
			currentDamage = currentDamage + 4;
		}
		else
		{
			// else we hit a bird meaning 10 damage(half a car damage)
			currentDamage = currentDamage + 2;
		}

		setDamage(currentDamage);
	}

	public void setDamage(int x)
	{
		damageLevel = damageLevel + x;
	}

	//override. gives me a headache and nightmares
	// *Polymorphism
	public void move(int delay) // did it not think it was polymorphism again?

	{
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

		// Update Translation
		//translate((double) (getXLocation() + dx), (double) (getYLocation() + dy));     
	}


	@Override
	public void draw(Graphics2D g) 
	{
		AffineTransform AT = new AffineTransform();

		AT.translate((int) getXLocation() - (getHeight() / 2), (int) getYLocation() - (getWidth() / 2));
		AT.scale(1, -1);
		AT.rotate(Math.toRadians(getHeading()));

		g.setTransform(AT);
		// TODO Auto-generated method stub
		g.setColor(this.getColor());
		g.fillRect(0, 0, getHeight(), getWidth());
		g.setTransform(new AffineTransform());
	}


	@Override
	public boolean collidesWith(ICollider obj) 
	{	
		// Left and Right 
		int R1 = (int)this.getXLocation() + (getWidth()/2);
		int L1 = (int)this.getXLocation() - (getWidth()/2);

		// Top and Bottom
		int T1 = (int)this.getYLocation() + (getHeight()/2);
		int B1 = (int)this.getYLocation() - (getHeight()/2);

		// Radius of a Square. Yes it is annoying as it sounds. 
		int radius = getSize()/2; 

		// Collisions
		// Radius Way
		if(obj instanceof pylonsA1)
		{
			int pylonRadius = (((pylonsA1) obj).getRadius());

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
				setCollisionFlagFalse();
				return false;
			}

		}

		// Radius Way
		if(obj instanceof oilSlickA1)
		{	
			int slickRadius = (((oilSlickA1) obj).getSize())/2;

			int slickX = (int) ((oilSlickA1) obj).getXLocation();
			int slickY = (int) ((oilSlickA1) obj).getYLocation();

			int dY = (slickY - (int) this.getYLocation())*(slickY - (int) this.getYLocation());
			int dX = (slickX - (int) this.getXLocation())*(slickX - (int) this.getXLocation());

			int dSquared = dY + dX;

			int rSquared = ((slickRadius + radius)*(slickRadius + radius));

			if(dSquared <= rSquared)
			{
				return true;
			}
			else
			{
				setOilFlagFalse();
				oilSlick();
				return false;
			}
		}

		// Radius Way for the instance of a bird
		if(obj instanceof birdA1)
		{
			int birdRadius = (((birdA1) obj).getSize())/2;

			int birdX = (int) ((birdA1) obj).getXLocation();
			int birdY = (int) ((birdA1) obj).getYLocation();

			int dY = (birdY - (int) this.getYLocation())*(birdY - (int) this.getYLocation());
			int dX = (birdX - (int) this.getXLocation())*(birdX - (int) this.getXLocation());

			int dSquared = dY + dX;
			int rSquared = ((birdRadius + radius)*(birdRadius + radius));

			if(dSquared <= rSquared)
			{
				return true;
			}
			else
			{
				setBirdFlagFalse();
				return false;
			}
		}

		if(obj instanceof fuelCanA1)
		{
			// TODO Auto-generated method stub
			// Left and Right 
			int R2 = (int) ((fuelCanA1) obj).getXLocation() + ((((fuelCanA1) obj).getSize())/2);
			int L2 = (int) ((fuelCanA1) obj).getXLocation() - ((((fuelCanA1) obj).getSize())/2);

			// Top and Bottom
			int T2 = (int) ((fuelCanA1) obj).getYLocation() + ((((fuelCanA1) obj).getSize())/2);
			int B2 = (int) ((fuelCanA1) obj).getYLocation() - ((((fuelCanA1) obj).getSize())/2);

			boolean conditionOne = (R1 > L2) && (L1 < R2);
			boolean conditionTwo = (T2 > B1) && (T1 > B2);

			if (conditionOne == true && conditionTwo == true)
			{
				return true;
			}
			else
			{
				return false;
			}
		}


		return false; 
	}

	public void setCollisionFlagFalse()
	{
		// TODO Auto-generated method stub
		collisionFlag = false;
	}


	public void setBirdFlagFalse() 
	{
		// TODO Auto-generated method stub
		birdFlag = false;
	}


	public void setOilFlagFalse()
	{
		// TODO Auto-generated method stub
		oilFlag = false;
	}


	@Override
	public void handleCollision(ICollider obj)
	{
		// TODO Auto-generated method stub

		if (obj instanceof pylonsA1)
		{
			System.out.println("Collision has happened with Pylon");
			gw.pylonCollision(((pylonsA1) obj).getPylonNumber());
		}

		if (obj instanceof fuelCanA1)
		{
			System.out.println("Collision has happened with Fuel");
			gw.gasUp();
		}

		if (obj instanceof oilSlickA1)
		{
			// if we already have an instance of the collided object
			// dont take the collision again
			if(getOilFlag())
			{
				System.out.println("Collision ALready In Progress");
			}
			else
			{
				System.out.println("Collision has happened with an Oil Slick");
				oilSlick();
				setOilFlag();
			}

		}

		if (obj instanceof birdA1)
		{

			if(getBirdFlag())
			{
				System.out.println("Collision ALready In Progress");
			}
			else
			{
				System.out.println("Collision has happened with a Bird. Is the Word.");
				gw.gummedUp();
				gw.makeShockwave(getXLocation(), getYLocation());
				setBirdFlag();
			}

		}

		if (obj instanceof NPCA2)
		{

			if(getCollisionFlag())
			{
				System.out.println("Collision ALready In Progress");
			}
			else
			{
				System.out.println("Collision has happened with a NPC.");
				gw.carCollision();
				gw.makeShockwave(getXLocation(), getYLocation());
				setCollisionFlag();
			}

		}

	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
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

	public void setCollisionFlag()
	{
		collisionFlag = true;
	}

	public void setOilFlag()
	{
		oilFlag = true;
	}

	public void setBirdFlag()
	{
		birdFlag = true;
	}

}
