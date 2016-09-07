/**
 * Created by Derek Irvin
 * CSC 133 Spring 2015
 * Project 1
 * Dr. John Clevenger
 */
 import java.awt.Color;

    public abstract class movableObjects extends gameWorldObjects
    {
        private int objectSpeed;	// Equation for speed is given in the assignment handout
        private int objectHeading; // only applies to snake head to be honest
        private int timeCount = 0; //Initialize  

        public movableObjects (float x, float y, int h, int s, Color color)
        {
            super(x, y, color);
            objectHeading = h;
            objectSpeed = s;
        }

        public void move(int delay)
        {
        	// DO NOT TOUCH. 
        	timeCount = timeCount + 30; // keep track of each in world game tick. This runs smoother here. 
        	
        	if((timeCount % delay) == 0)
        	{
        		// Degrees
        		float rad = (float) Math.toRadians(90 - this.objectHeading);
        		// DeltaX and DeltaY
        		double dx = Math.cos(rad)*this.objectSpeed;
        		double dy = Math.sin(rad)*this.objectSpeed;
        		// New Location
        		this.setXLocation((float) (this.getXLocation() + dx) );
        		this.setYLocation((float) (this.getYLocation() + dy));
        	}
        	else
        	{
        		//moving too fast. slow down silver. does it not think i am overriding anymore? if it is. i should fix that now. 
        	}
        }

        public void setHeading(int h)
        {
            objectHeading = h;
        }

        public void setSpeed(int s)
        {
        	objectSpeed = s;
        }
        public int getHeading()
        {
            return objectHeading;
        }

        public int getSpeed()
        {
            return objectSpeed; 
        }

        public String toString()
        {
            return super.toString() + "speed = " + objectSpeed + " heading = " + objectHeading;
        }

    }

