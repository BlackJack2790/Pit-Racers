import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;


@SuppressWarnings("serial")
public class mapView extends JPanel implements IObserver, MouseListener, MouseMotionListener, MouseWheelListener
{
	// Trials for Zoom and Pan 
	private Point2D oldPoint;
	private Point2D newPoint;
	private Point2D mouseLoc;

	// World Transformations and Corners. 
	private AffineTransform worldToND, ndToScreen, theVTM, inverseVTM;
	private double winLeft, winRight, winTop, winBot;

	private double height;
	private double width; 

	private int x;
	private int y;
	private Point startPoint = null;
	private boolean pauseValue = false;

	//gameWorld.gameWorldIterator gameCollection;
	gameWorld.gameWorldIterator checkCollection;
	gameWorld gw;	// we are going to need an instance now to add the pylons and the fuelCans
	private gameWorldProxyA3 gwProxy;

	//private gameWorldObject map;

	public void setGameWorldProxy(gameWorldProxyA3 newProxy) {
		gwProxy = newProxy;
	}

	public mapView(gameWorld gameWorld)
	{
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);

		gw = gameWorld;

		// Set boundaries of window. 
		winLeft = 0;
		winRight = 1500;
		winBot = 0;
		winTop = 1500;  

		// Height and Width of the Map. 
		height = winTop - winBot;
		width = winRight - winLeft;
	}


	public double getWindowRight(){
		return winRight;
	}

	public double getWindowTop(){
		return winTop;
	}

	public double getWindowLeft(){
		return winLeft;
	}

	public double getWindowBottom(){
		return winBot;
	}

	// Zoom and Panning 
	public void zoomIn()
	{	
		System.out.println("Zoom In\n");
		winLeft += width*0.05;
		winRight -= width*0.05;
		winTop -= height*0.05;
		winBot += height*0.05;
		this.repaint();
	}

	public void zoomOut()
	{
		System.out.println("Zoom Out\n");
		winLeft -= width*0.05;
		winRight += width*0.05;
		winTop += height*0.05;
		winBot -= height*0.05;
		this.repaint();
	}

	public void panLeft()
	{
		System.out.println("Pan Left\n");
		winLeft -= width*0.05;
		winRight -= width*0.05;
		this.repaint();
	}

	public void panRight()
	{
		System.out.println("Pan Right\n");
		winLeft += width*0.05;
		winRight += width*0.05;
		this.repaint();
	}

	public void panUp()
	{
		System.out.println("Pan Up\n");
		winTop += height*0.05;
		winBot += height*0.05;
		this.repaint();
	}

	public void panDown()
	{
		System.out.println("Pan Down\n");
		winTop -= height*0.05;
		winBot -= height*0.05;
		this.repaint();
	}

	// World Transformations
	public AffineTransform buildWorldToNDXform(double width,double height, double left, double bottom)
	{
		AffineTransform AT = new AffineTransform();
		AT.scale(1/width, 1/height);
		AT.translate(0-left,0-bottom);
		return AT;
	}

	public AffineTransform buildNDToScreenXform(double swidth, double sheight)
	{
		AffineTransform AT = new AffineTransform();
		AT.scale(swidth, 0-sheight);
		AT.translate(0, sheight);
		return AT;
	}

	public void getPanel()
	{

		if(checkCollection != null)
		{
			if(checkCollection.hasNext() == false)
			{
				this.setBackground(Color.BLACK);					
			}
		}
	}

	@Override
	public void update(IObservable o) 
	{
		gwProxy = (gameWorldProxyA3)o;

		// Display the game collection through the iterator here
		//this.setBackground(Color.WHITE);
		pauseValue = gw.getPauseValue();
		repaint();
		//this.getPanel();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		// Building the 2D World
		Graphics2D twoD = (Graphics2D)g;
		AffineTransform AT = twoD.getTransform();

		worldToND = buildWorldToNDXform(winRight,winTop,winLeft,winBot);
		ndToScreen = buildNDToScreenXform(height, width);
		theVTM = (AffineTransform) ndToScreen.clone();
		theVTM.concatenate(worldToND);
		twoD.transform(theVTM);

		if (gwProxy == null) {
			return;
		}

		IIterator iter = gwProxy.getIterator();

		while(iter.hasNext() == true)
		{
			gameWorldObjects gwo2 = (gameWorldObjects) iter.getNext();
			if(gwo2 instanceof IDrawable)
			{
				((IDrawable) gwo2).draw(twoD);
			}
		}

		twoD.setTransform(AT);
	}

	// Zoom and Pan. 
	@Override
	public void mouseDragged(MouseEvent e)
	{
		Point2D mouseOldLoc;
		double x, y;
		try {
			
			worldToND = buildWorldToNDXform(winRight,winTop,winLeft,winBot);
			ndToScreen = buildNDToScreenXform(width,height);
			theVTM = (AffineTransform) ndToScreen.clone();
			theVTM.concatenate(worldToND);
			inverseVTM = theVTM.createInverse();
			mouseOldLoc = oldPoint;
			oldPoint = inverseVTM.transform(e.getPoint(), null);
			
			if(gw.getPauseValue())
			{
			if (oldPoint != null && mouseOldLoc != null)
			 {
				 
				x = mouseOldLoc.getX() - oldPoint.getX();
				y = mouseOldLoc.getY() - oldPoint.getY();
				
				if(Math.abs(x) > Math.abs(y)){
					if(x < 0){
						this.panRight();
					}
					else{
						this.panLeft();
					}
				}
				else{
					if(y < 0){
						this.panUp();
					}else{
						this.panDown();
					}
				}
				
				this.repaint();
			 }
			}
			
			} catch (NoninvertibleTransformException ea)
			{
				System.out.println(ea);
			}

		
	}


	@Override
	public void mouseMoved(MouseEvent e) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	// Selection
	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		try{
			inverseVTM = theVTM.createInverse();

			Point p = e.getPoint();
			Point2D worldLoc = inverseVTM.transform(p,  null);

			x = e.getX();
			y = e.getY();
			startPoint = e.getPoint();

			if(gwProxy.getPauseValue() == true)
			{

				// May snap if it tells me x and y are null. just saying. 
				if (gw.getFuelFlag() == true && gw.getPylonFlag() == true) // if BOTH are set. They are stupid. reset both
				{
					gw.setFuelFlag();
					gw.setPylonFlag();
				}
				else if(gw.getFuelFlag() == true && gw.getPylonFlag() == false) // so if fuel is set and pylon is NOT
				{
					// Ask for the fuel size, use the junk from A1 for the pylons. 
					String string = JOptionPane.showInputDialog(null, "Enter Fuel Value");	
					int size = Integer.parseInt(string);
					gw.setFuelCan(x, y, size); // place the fuel can
					gw.setFuelFlag();	// reset the fuel flag
				}
				else if (gw.getPylonFlag() == true && gw.getFuelFlag() == false)
				{
					gw.setPylon(x, y); // place the new pylon
					gw.setPylonFlag(); // reset the pylon flag
				}
				else 
				{
					// nothing to do here. neither is set.  
				}

				IIterator gCol = gwProxy.getIterator();
				while(gCol.hasNext() == true)
				{
					gameWorldObjects gwo = (gameWorldObjects) gCol.getNext();
					if(gwo instanceof ISelectable)
					{
						// Selecting multiple objects at once. 
						if(((ISelectable) gwo).contains(p) && e.isControlDown())
						{
							((ISelectable) gwo).setSelected(true);
							repaint();
						}

						// One Object at a time
						else if(((ISelectable)gwo).contains(p) && !e.isControlDown())
						{
							// deselect Everything
							((ISelectable)gwo).setSelected(true);
							repaint();
						}
						else if(!e.isControlDown())
						{
							// deselect Everything
							((ISelectable)gwo).setSelected(false);
							repaint();
						}
					}
				}
			}
		}catch(NoninvertibleTransformException ed)
		{
			System.out.println(ed);
		}


	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		// nothing to do here
	}

	public boolean getPauseValue()
	{
		return pauseValue;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) 
	{
			if(e.getWheelRotation() > 0)
			{
				this.zoomOut();
			}
			else if (e.getWheelRotation() < 0)
			{
				this.zoomIn();
			}
	}
}