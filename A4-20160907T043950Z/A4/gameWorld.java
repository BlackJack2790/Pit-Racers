
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
/**
 * Created by Derek Irvin
 * CSC 133 Spring 2015
 * Project 1
 * Dr. John Clevenger
 */

public class gameWorld implements IGameWorld, IGameWorldCollection
{
	// Array Lists For movable and fixed objects.
	// Will combine for assignment 2
    private ArrayList<gameWorldObjects> gameCollectionA2;
    
    // Assignment 2
    private ArrayList<IObserver> observers = new ArrayList<IObserver>();
    
	private int gameTime;	// affected with each tick and initialize to 0
	private int adjustedTime;
	private int userLives = 3;	// affected by a collision with wall or bird or self. and initialize to 3 lives 
	private Random rn;			// Random number for a few of things...
	private int lastPylon = 0; 	// Place holder for the last Pylon Reached when asked
	private int nextPylon = 5; // next pylon after the initial 4. will get rid of hard code later. 
	private int playerDamage = 0; // Initialize to 0. 
	private int fuelLevel = 100; // initialize the fuel level to max so it will appear correct
	private int mSecDelay = 60; // maybe this will be nicer. edit: no. the answer was no. it did not. 
	
	private boolean soundValue = true;
	private boolean pauseValue = false;
	
	private static boolean fuelFlag = false;
	private static boolean pylonFlag = false;
	
	private int pylonNumber = 0; 
	
	
	// sound files
	private sound background;
	private sound collision;
	private sound death;
	private sound ready;
	private sound fuel;
	
	//sound directory
	
	
	public static final String soundDir = "." + File.separator
			+ "sound" + File.separator;
	// sounds
	public static final String bgm = "realBackgroundMusic.wav";
	public static final String fuelSound = "fuelCan.wav";
	public static final String readySound = "Ready.wav";
	public static final String collisionSound = "carCollision.wav";
	public static final String deathSound = "DeathCollision.wav";
	
	
	// linking sounds
	public static final String backgroundMusic = soundDir + bgm;
	public static final String fuelMusic = soundDir + fuelSound;
	public static final String readyMusic = soundDir + readySound;
	public static final String collisionMusic = soundDir + collisionSound;
	public static final String deathMusic = soundDir + deathSound;
	
	
	public gameWorld(){
		
	}
	
	public int getPylonNumber()
	{
		return pylonNumber;
	}
	
	public void upPylonNumber()
	{
		pylonNumber++;
	}
	
	// Factory Methods
	
	public carA1 makeCar()
	{
		return new carA1(250, 250, 90, 5, Color.red, this);
	}
	
	public NPCA2 makeNPC1()
	{
		return new NPCA2(250, 200, 
				90, 2, Color.black);
	}
	
	public NPCA2 makeNPC2()
	{
		return new NPCA2(200, 250, 
				90, 2, Color.black);
	}
	
	public NPCA2 makeNPC3()
	{
		return new NPCA2(200, 200, 
				90, 2, Color.black);
	}
	
	public shockwave makeWave()
	{
		return new shockwave((float)rn.nextInt(1000),
				(float)rn.nextInt(1000), rn.nextInt(360),
				rn.nextInt(10), Color.MAGENTA);
	}
	
	public birdA1 makeBird()
	{
		return new birdA1((float)rn.nextInt(1000),
                (float)rn.nextInt(1000), rn.nextInt(360),
               rn.nextInt(10), rn.nextInt(80)+1, Color.blue); 
	}
	
	public oilSlickA1 makeSlick()
	{
		return new oilSlickA1((float)rn.nextInt(1000),
	              (float)rn.nextInt(1000), Color.black);
	}
	
	public fuelCanA1 makeCan()
	{
		return new fuelCanA1((float)rn.nextInt(1000),
                (float)rn.nextInt(1000), Color.green, rn.nextInt(50));
	}
	
	public pylonsA1 makePylon()
	{
		upPylonNumber();
		return new pylonsA1((float)rn.nextInt(1000),
	              (float)rn.nextInt(1000), Color.yellow, getPylonNumber());
		
	}
	// initializing basic layout
	public void initLayout() 
	{
		// create the list of game world objects for movable and fixed location objects
		gameCollectionA2 = new ArrayList<gameWorldObjects>();
		rn = new Random();
		
		carA1 car = makeCar();
		// Object Initializations
		// Initialize the cars so they are a decent position from your car
		//carA1 car = new carA1(250 ,
        //        250, 90, 5, Color.red);
		NPCA2 npc1 = makeNPC1();
		NPCA2 npc2 = makeNPC2();
		NPCA2 npc3 = makeNPC3();
		birdA1 bird = makeBird();
		shockwave wave = makeWave();
		shockwave wave2 = makeWave();

		
		// Movable Objects
        gameCollectionA2.add(car);
        gameCollectionA2.add(bird);
        
        gameCollectionA2.add(wave2);
        gameCollectionA2.add(wave);
       
        // add Three Evil Racers
        gameCollectionA2.add(npc1);
        gameCollectionA2.add(npc2);
        gameCollectionA2.add(npc3);
        
		// Fixed Objects Initializations
        oilSlickA1 oilSlick = makeSlick();
        oilSlickA1 oilSlick2 = makeSlick();
        oilSlickA1 oilSlick3 = makeSlick();
        oilSlickA1 oilSlick4 = makeSlick();
        fuelCanA1 fuelCan = makeCan();
        fuelCanA1 fuelCan2 = makeCan();
        pylonsA1 pylonOne = makePylon();
        pylonsA1 pylonTwo = makePylon();
        pylonsA1 pylonThree = makePylon();
        pylonsA1 pylonFour = makePylon();

        gameCollectionA2.add(oilSlick);
        gameCollectionA2.add(oilSlick2);
        gameCollectionA2.add(oilSlick3);
        gameCollectionA2.add(oilSlick4);
        gameCollectionA2.add(fuelCan);
        gameCollectionA2.add(fuelCan2);
        gameCollectionA2.add(pylonOne);
        gameCollectionA2.add(pylonTwo);
        gameCollectionA2.add(pylonThree);
        gameCollectionA2.add(pylonFour);
        
        background = new sound(backgroundMusic);
    	collision = new sound(collisionMusic);
    	death = new sound(deathMusic);
    	ready = new sound(readyMusic);
    	fuel = new sound(fuelMusic);
    	
    	checkSoundValue();
    	
    	if(soundValue())
    	{
    		ready.play();
    	}
	
    	setGameTime(0); // reset gameTime
    	updateFuel(100); // reset fuelLevel;
    	
    	// Reset the Progress
    	playerDamage = 0;
    	pylonNumber = 0;
    	lastPylon = 0;
    	nextPylon = 5;
}


    // Get Functions


    // Choices
    public void carAccelerate()
    {
    	// get instance of car
        carA1 c = (carA1)gameCollectionA2.get(0); // get instance of first and only car
        // get the value of the car. 
        boolean oilSlick = c.getSlick(); // are we in a slick
        
        // Confirm if in oilSlick else accelerate
        if(oilSlick == false)
        {
        	// Check the upper speed Limit. in our case no more than 40
        	int carSpeed = c.getSpeed();
        	if (carSpeed >= 40)
        	{
        		System.out.println("TOO FAST");
        		carSpeed = 40;
        		c.setSpeed(carSpeed);
        	}
        	else
        	{
        		c.accelerate();
        	}
        }
        else
        {
        	// your in an oil slick dodo. You cant accelerate. 
        }
    }

    public void carBreak()
    {
        carA1 c = (carA1)gameCollectionA2.get(0);
        boolean oilSlick = c.getSlick();
        if(oilSlick == false)
        {
        	// Check the lower speed limit
        	int carSpeed = c.getSpeed();
        	if (carSpeed <= 0)
        	{
        		System.out.println("Too Slow");
        		carSpeed = 0;
        		c.setSpeed(carSpeed);
        		
        	}
        	else
        	{
        		c.decelerate();
        	}
        }
        else
        {
        	// your in an oil slick dodo. You cant accelerate. 
        }
    }

    public void steerLeft()
    {
        carA1 c = (carA1)gameCollectionA2.get(0);
        boolean oilSlick = c.getSlick();
        if(oilSlick == false)
        {
        		// we will check the wheel angle in the function
        		c.turnLeft();	
        }
        else
        {
        	// your in an oil slick dodo. You cant turn.
        }
    }

    public void steerRight()
    {
        carA1 c = (carA1)gameCollectionA2.get(0);
        boolean oilSlick = c.getSlick();
        if(oilSlick == false)
        {
         		c.turnRight();
        }
        else
        {
        	// your in an oil slick dodo. You cant turn.
        }
    }

    public void newSlick()
    {
        // Creation of new oilSlick
        oilSlickA1 oilSlick = new oilSlickA1((float)rn.nextInt(1000),
                (float)rn.nextInt(1000), Color.black);

        gameCollectionA2.add(oilSlick);
    }

    public void gasUp()
    {
    	if(soundValue())
    	{
    		fuel.play();
    	}
    	
    	// Check fixed objects for instance of a fuel can
        for(int i = 0; i < gameCollectionA2.size(); i++)
        {
        	 // receive the fuel can, remove fuelCan, and add the value to the gw.
            if(gameCollectionA2.get(i) instanceof fuelCanA1)
            {
                fuelCanA1 gasValue = (fuelCanA1) gameCollectionA2.get(i);
                int value = gasValue.getValue();
                gameCollectionA2.remove(i);
                ((carA1)(gameCollectionA2.get(0))).gasUp(value);

            }
        }
        
        newFuelCan();

    }
    
    public void newFuelCan()
    {
    	  // add a new fuel can
        fuelCanA1 fuelCan = new fuelCanA1((float)rn.nextInt(1000),
                (float)rn.nextInt(1000), Color.green, rn.nextInt(50));
        gameCollectionA2.add(fuelCan);
    }
    
    public void updateFuel(int x)
    {
    	fuelLevel = x;
    }

    // bird collision
    public void gummedUp()
    {
    	// so now we want to go through the collection and check 

    	for (int i = 0; i < gameCollectionA2.size(); i++)
    	{
    		if(((carA1)(gameCollectionA2.get(0))).getDamage() > 100)
    		{
    			gameWorldOver();
    		}
    		else
    		{
    			carA1 c = (carA1)gameCollectionA2.get(0);
    			c.carDamage(false);
    			updateDamage(c.getDamage());
    		}
    	}
    }

    // Same functions really. Find instance of car in object array and set the oilFlag boolean
    public void oilSlick()
    {
    
        for(int i = 0; i <gameCollectionA2.size(); i++)
        {
            if(gameCollectionA2.get(i) instanceof carA1)
            {
                ((carA1) (gameCollectionA2.get(0))).oilSlick();
            }
        }
    }

    public void exitSlick()
    {
        for(int i = 0; i <gameCollectionA2.size(); i++)
        {
            if(gameCollectionA2.get(i) instanceof carA1)
            {
                ((carA1) (gameCollectionA2.get(0))).oilSlick();
            }
        }
    }

    // Change color of applicable items. 
    public void newColor()
    {
        for(int i = 0; i < gameCollectionA2.size(); i++)
        {
            if(gameCollectionA2.get(i) instanceof carA1)
            {
                ((carA1)(gameCollectionA2.get(i))).setColor();
            }
  
            if(gameCollectionA2.get(i) instanceof fuelCanA1)
            {
                ((fuelCanA1)(gameCollectionA2.get(i))).setColor();
            }

            if(gameCollectionA2.get(i) instanceof oilSlickA1)
            {
                ((oilSlickA1)(gameCollectionA2.get(i))).setColor();
            }
        }



    }
	// Case: M
	public void map ()
	{
		//Cycle through the objects in the movable and fixed objects lists and display their locations
		for (gameWorldObjects gwo : gameCollectionA2)
		{
			System.out.println(gwo.toString());
		}
	}
	
	// Case: D
	// Display to the user the number of lives, last pylon, and the game time 
	public void display()
	{
		System.out.println("Live: " + userLives);
		System.out.println("Clock: " + gameTime);
		System.out.println("Last Pylon " + lastPylon);
		
		carA1 c = (carA1)gameCollectionA2.get(0);
		int currentFuel = c.getFuel();
		int currentDamage = c.getDamage();
		playerDamage = currentDamage;
		fuelLevel = currentFuel;
		System.out.println("Current Fuel: " + currentFuel);
		System.out.println("Current Damage: " + currentDamage);
		
	}
	
	public void gameWorldOver() 
	{		
		if(userLives == 0)
		{
			//death.play();
			
			System.out.println("Game Over");
			System.exit(0);
		}
		else
		{
			userLives--;
			gameCollectionA2.clear();
			background.stop();
			initLayout(); // reset the game
		}
	}
	
	public void carCollision()
	{	
		if(soundValue())
		{
			collision.play();
		}

		if(((carA1)(gameCollectionA2.get(0))).getDamage() > 100)
		{
			gameWorldOver();
		}
		else
		{
			carA1 c = (carA1)gameCollectionA2.get(0);
			c.carDamage(true);
			updateDamage(c.getDamage()); // update the Damage
		}


		int newSpill = rn.nextInt(10);
		if (newSpill >= 9)
		{
			oilSlickA1 oilSlick = makeSlick();
			gameCollectionA2.add(oilSlick);
		}
		else
		{
			// nothing
		}

		notifyObserver();
	}
	
	// Case: T
	// Increment the time in the game world
	// change the position of movable objects
	// check for collision
	// Change Heading if necessary
	
	public void updateDamage(int damage) 
	{
		// TODO Auto-generated method stub
		playerDamage = damage;
	}

	public void gameWorldTick()
	{
		int currentTime = getGameTime() + 1;
		
		
		// Keep this outside the loop or your switching three times. no good.
		// Update: keep it out of ALL the loops. 
		if(currentTime % 600 == 0) // 300  is about every 5 seconds. lets go higher
		{
			switchStrategy();
			birdA1 bird = makeBird();
			gameCollectionA2.add(bird); // New Bird to show they exist. 
		}
		
		
		// Here we are going to check alot of things. 
		// Unsure how rollerderby cars will move if they have fuel too or not
		// They have super armor though. Which is broken.
		for(int i = 0; i < gameCollectionA2.size(); i++)
		{
			// Check for Oil Slick
			if(gameCollectionA2.get(i) instanceof carA1)
			{
				carA1 fuelLevel = (carA1) gameCollectionA2.get(i);
				int currentFuel = fuelLevel.getFuel();
				if (currentFuel <= 0)
				{
					if(soundValue())
					{
						death.play();
					}
					System.out.println("Out of Fuel. Losing a Life");
					gameWorldOver();
				}
				else
				{
					if(currentTime % 20 == 0)
					{
						((carA1)(gameCollectionA2.get(i))).gasDrain();
				
					}
					else
					{
						// lets see if this makes us last longer.
						// Yes. Yes It Does. 
					}
				}
				
				updateFuel(currentFuel);
			}
			
			// Check Strategy for target for the move function
			if(gameCollectionA2.get(i) instanceof NPCA2)
			{
				// if my math is right this means we will be switching strategies every ten seconds?
				
				// I should change it before i get the target and stuff... 
				NPCA2 npc = (NPCA2) gameCollectionA2.get(i);
				iNPCStrategy npcStrategy = npc.getStrategy();
				int nextNPCPylon = npc.getNextPylon();
				if (npcStrategy instanceof NPCA2.npcRollerDerby)
				{
					// search for carA1 X and Y
					for(int j = 0; j < gameCollectionA2.size(); j++)
					{
						if(gameCollectionA2.get(j) instanceof carA1)
						{
							carA1 car = (carA1) gameCollectionA2.get(j);
							float carTargetX = car.getXLocation();
							float carTargetY = car.getYLocation();
							npc.updateTarget(carTargetX, carTargetY);
						}
					}
				}
				else
				{
					// Search for next Pylon X and Y
					for(int j = 0; j < gameCollectionA2.size(); j++)
					{
						if(gameCollectionA2.get(j) instanceof pylonsA1)
						{
							pylonsA1 pylon = (pylonsA1) gameCollectionA2.get(j);
							int pylonNumber = pylon.getPylonNumber();
							if (pylonNumber == nextNPCPylon)
							{
								float pylonTargetX = pylon.getXLocation();
								float pylonTargetY = pylon.getYLocation();
								npc.updateTarget(pylonTargetX, pylonTargetY);
							}
						}
					}
				}
				
			}
		}
		
		for(gameWorldObjects gwo : gameCollectionA2)
		{
			if(gwo instanceof movableObjects)
			{
				((movableObjects) gwo).move(mSecDelay); // we are just calling this guys move. which is bad code. bad bad code. 
			}
				
		}
		
		deleteObjects();
		setGameTime(currentTime);
	}

	public int getGameTime() {
		// TODO Auto-generated method stub
		return gameTime;
	}

	public void setGameTime(int currentTime) 
	{
		// TODO Auto-generated method stub
		gameTime = currentTime;
		
	}

	public void pylonCollision(int v) 
	{
		int pylonCheck = v - lastPylon;
		if (pylonCheck != 1)
		{
			System.out.println("XX Turn-Around. Wrong Checkpoint XX");
		}
		else
		{
			for(int i = 0; i < gameCollectionA2.size(); i++)
			{
				 if(gameCollectionA2.get(i) instanceof pylonsA1)
		         {
					 pylonsA1 pylonValue = (pylonsA1) gameCollectionA2.get(i);
		             int value = pylonValue.getPylonNumber();
		             if (value == v)
		             {
		            	 gameCollectionA2.remove(i);
		            	 newPylon();
		             }
		         
		         }
			}
		    lastPylon = v;	// update the highest pylon reached. 
		    
			}
	
		}
	
	public void newPylon()
	{
		 pylonsA1 anotherPylon = new pylonsA1((float)rn.nextInt(1000),
                 (float)rn.nextInt(1000), Color.yellow, nextPylon);
         gameCollectionA2.add(anotherPylon);
         nextPylon++;
	}
	
	@Override
	public int userLives() {
		// TODO Auto-generated method stub
		return userLives;
	}

	@Override
	public int gameTime() 
	{
		// TODO Auto-generated method stub
		adjustedTime = (int) (gameTime/60) % 60;
		return adjustedTime;
	}

	@Override
	public IIterator getIterator() {
		// TODO Auto-generated method stub
		return new gameWorldIterator();
	}

	@Override
	public boolean soundValue()
	{
		// TODO Auto-generated method stub
		return soundValue;
	}

	@Override
	public int lastPylon() 
	{
		// TODO Auto-generated method stub
		return lastPylon;
	}

	@Override
	public int fuelLevel() 
	{
		// TODO Auto-generated method stub
		return fuelLevel;
	}

	@Override
	public int playerDamage() 
	{
		// TODO Auto-generated method stub
		return playerDamage;
	}

	public void checkSoundValue()
	{
		// TODO Auto-generated method stub
		if(soundValue == true)
		{
			background.loop();
			soundValue = true;
		}
		else
		{
			background.stop();
			//soundValue = !soundValue;
		}
		
		this.notifyObserver();
	}
	
	public void register(IObserver o) 
	{
		observers.add(o);
		
	}
	
	public void notifyObserver()
	{
		gameWorldProxyA3 proxy = new gameWorldProxyA3(this);
		for(IObserver o: observers)
		{
			o.update(proxy);
		}
	}
	

public class gameWorldIterator implements IIterator
{
	private int currElementIndex;
	
	public gameWorldIterator()
	{
		currElementIndex = -1;
	}
	
	@Override
	public boolean hasNext() 
	{
		// TODO Auto-generated method stub
		if(gameCollectionA2.size() <= 0) return false;
		if(currElementIndex == gameCollectionA2.size() -1)
			return false;
		return true;
	}

	@Override
	public Object getNext() 
	{
		// TODO Auto-generated method stub
		currElementIndex ++;
		return(gameCollectionA2.get(currElementIndex));
	}
}


public void switchStrategy() {
	// TODO Auto-generated method stub
	IIterator x = getIterator();
	while(x.hasNext())
	{
		gameWorldObjects g = (gameWorldObjects) x.getNext();
		
		if (g instanceof NPCA2)
		{
			System.out.println("changing npc strategies");
			NPCA2 npc = (NPCA2) g;
			iNPCStrategy npcStrategy = npc.getStrategy();
			npc.updatePylon(); // upon switch update the next pylon
			if (npcStrategy instanceof NPCA2.npcRollerDerby)
			{
				npc.setStrategy(npc.new npcRacer());
			}
			else
			{
				npc.setStrategy(npc.new npcRollerDerby());
			}
			
		}
	}
}

@Override
public void add(gameWorldObjects newObject) 
{
	// TODO Auto-generated method stub
	gameCollectionA2.add(newObject);
}

public boolean getPauseValue() {
	// TODO Auto-generated method stub
	return pauseValue;
}

public void setPauseValue(boolean b)
{
	// TODO Auto-generated method stub
	pauseValue = b;
}

public boolean removeSelection()
{
	// TODO Auto-generated method stub
	// Check the game collection of objects
	IIterator gCol = getIterator();
	// use while loop
	while(gCol != null && gCol.hasNext())
	{
		gameWorldObjects gwo = (gameWorldObjects) gCol.getNext();
		if(gwo instanceof ISelectable)
		{
			((ISelectable) gwo).setSelected(false);
		}
	}
	return false;
	
}

public void deleteObjects() 
{
	// TODO Auto-generated method stub
	// Check the game collection of objects

	ArrayList<gameWorldObjects> removeList = new ArrayList<gameWorldObjects>();
	IIterator gCol = getIterator();
	// use while loop
	while(gCol != null && gCol.hasNext())
	{
		gameWorldObjects gwo2 = (gameWorldObjects) gCol.getNext();

		// if the object is an instance of a selectable item
		if(gwo2 instanceof ISelectable)
		{
			// and the item is selected
			if(((ISelectable)gwo2).isSelected())
			{
				removeList.add(gwo2);
			}
		}
	}
	
	for(gameWorldObjects item: removeList)
	{
		// delete the item from the game world collection
		gameCollectionA2.remove(item);
	}
	notifyObserver();
	
}


// sound files

public sound backgroundMusic()
{
	return background;
}

public sound collisionMusic()
{
	return collision;
}

public sound deathMusic()
{
	return death;
}

public sound fuelMusic()
{
	return fuel;
}

public sound readyMusic()
{
	return ready;
}


//getter setter flag stuff. 
public boolean getFuelFlag()
{
	return fuelFlag;
}
	
public void setFuelFlag()
{
	fuelFlag = !fuelFlag; 
}

public boolean getPylonFlag()
{
	return pylonFlag;
}
	
public void setPylonFlag()
{
	pylonFlag = !pylonFlag; 
}

public void setFuelCan(int x, int y, int size) 
{
	// TODO Auto-generated method stub
	// Prompt for the new Fuel Value
	int setSize = size;
	
	fuelCanA1 setFuel = new fuelCanA1((float) x, (float) y, Color.green, setSize); // This program is a dick. 
	gameCollectionA2.add(setFuel);


}

public void setPylon(int x, int y) 
{
	// TODO Auto-generated method stub
	pylonsA1 anotherPylon = new pylonsA1((float) x,
            (float) y, Color.yellow, nextPylon);
    gameCollectionA2.add(anotherPylon);
    nextPylon++;
	
}

public void switchSoundValue() 
{
	// TODO Auto-generated method stub
	soundValue = !soundValue;
}

public void pauseMusic() 
{
	// TODO Auto-generated method stub
	background.stop();
}

public void resumeMusic() 
{
	// TODO Auto-generated method stub
	background.play();
}

public void makeShockwave(float xLocation, float yLocation)
{
	// TODO Auto-generated method stub
	shockwave newWave = new shockwave(xLocation, yLocation, rn.nextInt(360),
			rn.nextInt(10), Color.MAGENTA);
	gameCollectionA2.add(newWave);
}


}
