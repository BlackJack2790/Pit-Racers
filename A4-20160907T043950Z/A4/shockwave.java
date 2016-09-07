import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.util.Random;


public class shockwave extends movableObjects implements IDrawable, ISelectable
{
	Random rand = new Random();
	// Life Span of the Curve
	private int span = 700; 
	private int heading;
	private int speed; 
	private int size; 
	private Color color; 
	private int level; 

	private boolean isSelected = false; 
	// 
	// Control Points 
	Point2D[] CP1;
	Point2D[] CP2;
	Point2D[] CP3;
	//Point2D[] CP4; Dont need this. 

	private AffineTransform myRotation,myTranslation,myScale; 

	public shockwave(float x, float y, int h, int s, Color color) 
	{
		super(x, y, h, s, color); 
		this.level = 0;
		this.size = 10;

		// Set Random heading and Speed and Size 
		this.setSpeed(1);
		this.setHeading(h);
		this.setSize(20);
		this.setColor(Color.MAGENTA); // I have officially ran out of colors..

		myRotation = new AffineTransform();
		myTranslation = new AffineTransform();
		myScale = new AffineTransform();

		// 4 Control Points 
		CP1 = new Point2D [4];
		CP1[0] = new Point2D.Double(rand.nextInt(150), rand.nextInt(150));
		CP1[1] = new Point2D.Double(rand.nextInt(150), rand.nextInt(150));
		CP1[2] = new Point2D.Double(rand.nextInt(150), rand.nextInt(150));
		CP1[3] = new Point2D.Double(rand.nextInt(150), rand.nextInt(150));


		CP2 = new Point2D [4];
		CP2[0] = new Point2D.Double(rand.nextInt(150), rand.nextInt(150));
		CP2[1] = new Point2D.Double(rand.nextInt(150), rand.nextInt(150));
		CP2[2] = new Point2D.Double(rand.nextInt(150), rand.nextInt(150));
		CP2[3] = new Point2D.Double(rand.nextInt(150), rand.nextInt(150));



		CP3 = new Point2D [4];
		CP3[0] = new Point2D.Double(rand.nextInt(150), rand.nextInt(150));
		CP3[1] = new Point2D.Double(rand.nextInt(150), rand.nextInt(150));
		CP3[2] = new Point2D.Double(rand.nextInt(150), rand.nextInt(150));
		CP3[3] = new Point2D.Double(rand.nextInt(150), rand.nextInt(150));

	}

	public void rotate (double degrees)
	{
		myRotation.rotate(Math.toRadians(degrees));
	}

	public void translate (double dx, double dy)
	{
		myTranslation.translate(dx, dy);
	}

	public void scale (double sx, double sy)
	{
		myScale.scale(sx, sy);
	}

	public void setColor(Color c) 
	{
		// TODO Auto-generated method stub
		color = c; 
	}

	public int getSpan()
	{
		return span; 
	}

	// 500 should hopefully be enough time, may jump to 700 to be safe
	public void setSpan(int newSpan)
	{
		span = span - 1;
		if (span == 0)
		{
			setSelected(true);
		}
	}

	// Thank-You Notes. 
	public double lengthOf(Point2D A, Point2D B)
	{
		double dx = A.getX() - B.getX();
		double dy = A.getY() - B.getY();
		double result = ( Math.sqrt(dx*dx + dy*dy) );

		return result; 
	}

	boolean straightEnough(Point2D[] X)
	{
		double d1 = lengthOf(X[0], X[1]) + lengthOf(X[1],X[2]) + lengthOf(X[2],X[3]);
		double d2 = lengthOf(X[0], X[3]);

		if (Math.abs(d1-d2) < .001)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	void subdivideCurve (Point2D [] A, Point2D[] B, Point2D[] C)
	{
		double x, y;

		B[0] = A[0];
		x = ( A[0].getX() + A[1].getX() )/2;
		y = ( A[0].getY() + A[1].getY() )/2;
		B[1] = new Point2D.Double(x,y); 

		x = ( (B[1].getX() )/2) + ((A[1].getX()+A[2].getX() )/4);
		y = ( (B[1].getY())/2) + ((A[1].getY()+A[2].getY() )/4);
		B[2] = new Point2D.Double(x,y);

		C[3] = A[3]; 

		x = ( A[2].getX() + A[3].getX() )/2;
		y = ( A[2].getY() + A[3].getY() )/2;
		C[2] = new Point2D.Double(x,y); 

		x = ( ((A[1].getX()+A[2].getX())/4) + ((C[2].getX())/2) );
		y = ( ((A[1].getY()+A[2].getY())/4) + ((C[2].getY())/2) );	
		C[1] = new Point2D.Double(x,y); 

		x = ( B[2].getX() + C[1].getX() )/2;
		y = ( B[2].getY() + C[1].getY() )/2;
		B[3] = new Point2D.Double(x,y); 

		C[0] = B[3]; 
	}

	@Override
	public void draw(Graphics2D g)
	{
		// TODO Auto-generated method stub
		AffineTransform AT = new AffineTransform();
		AT.translate((int) getXLocation() - (getSize() / 2), (int) getYLocation() - (getSize() / 2));
		AT.scale(1, -1);
		AT.rotate(Math.toRadians(heading));
		
		g.setTransform(AT);
		g.setColor(this.getColor()); 
 
		for (int b = 0; b <=2; b++) 
		{
			Point2D p1 = CP1[b];
			Point2D p2 = CP1[b+1];
			g.setColor(this.getColor());
			g.drawLine( (int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
		}

		// draw the beziere curve
		g.setColor(this.getColor());
		drawBezierCurve(CP1, g, level);

		g.setTransform(new AffineTransform());
	} 

	public void drawBezierCurve(Point2D [] P, Graphics2D g, int Level)
	{
		int MaxLevel = 10; // Do Not make this too big. Makes things slow
		// recursive calls
		if (  (straightEnough(P) ) || (Level > MaxLevel) )
		{
			//g.setColor(c);
			g.drawLine((int) P[0].getX(), (int) P[0].getY(), (int) P[3].getX(), (int) P[3].getY());
		}
		else {
			subdivideCurve(CP1, CP2, CP3);
			drawBezierCurve(CP2, g, Level+1);
			drawBezierCurve(CP3, g, Level+1);
		}
	}

	public void move(int delay)
	{
		setSpan(5);
		
		int objectHeading = getHeading();
		int objectSpeed = getSpeed();

		// Degrees
		float rad = (float) Math.toRadians(90 - objectHeading);

		// DeltaX and DeltaY
		double dx = Math.cos(rad)*objectSpeed;
		double dy = Math.sin(rad)*objectSpeed;

			setSpeed(objectSpeed);
			setHeading(objectHeading);
			setXLocation((float) (getXLocation() + dx));
			setYLocation((float) (getYLocation() + dy));
	//	translate((double) (getXLocation() + dx), (double) (getYLocation() + dy));
	}

	@Override
	public void setSelected(boolean selected) 
	{
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
	public boolean contains(Point2D p) 
	{
		// TODO Auto-generated method stub
		return false;
	}

}
