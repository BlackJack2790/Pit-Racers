import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.Timer;

/**
 * Created by Derek Irvin
 * CSC 133 Spring 2015
 * Project 1
 * Doctor John Clevenger
 */


public class game extends JFrame implements ActionListener
{
	private static gameWorld gw;	//Create instance of object GameWorld
	private scoreView sv;
	private mapView mv;
	
	// Working around with this idea atm. 
	private int newFuelValue; 
	
	// cybermen
	private static JButton deleteButton;
	private static JButton addPylonButton;
	private static JButton addFuelCanButton;
	
	// Timer Creation and Initialization
	private static Timer timer;
	private final int DELAY_IN_MSEC = 1000/60; //30 fps test. 
	
	
	// Initialize the game world
	@SuppressWarnings("static-access")
	public game()
	{
		gw = new gameWorld();	// Encapsulation from gameWorld
		sv = new scoreView();
		mv = new mapView(gw);
		gw.initLayout();	// initialize the layout
		gw.register(sv);	// Register Observer scoreView
		gw.register(mv);	// Register Observer MapView
		
		//start timer
		timer = new Timer(DELAY_IN_MSEC, this);
		timer.setInitialDelay(1000);
		timer.start();
		timer.setRepeats(true);
		
		setTitle("Vroom Vroom");
		setSize(1500, 1500);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Menu Bar
		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);
		this.setVisible(true);
		
		// Create File Commands
		QuitAction quitCommand = new QuitAction();
		SaveAction saveCommand = new SaveAction();
		SoundAction soundCommand = new SoundAction();
		AboutAction aboutCommand = new AboutAction();
		NewAction newCommand = new NewAction();
		
		// Commands Commands
		FuelAction fuelCommand = new FuelAction();
		SlickAction slickCommand = new SlickAction();
		ColorAction colorCommand = new ColorAction();
		
		// Button Actions
		PylonCollisionAction pylonCommand = new PylonCollisionAction();
		NpcCollisionAction npcCommand = new NpcCollisionAction();
		BirdCollisionAction birdCommand = new BirdCollisionAction();
		// for now i dont think we need fuel. 
		InSlickAction inSlickCommand = new InSlickAction();
		OutSlickAction outSlickCommand = new OutSlickAction();
		// Switch Strategy will go here when we get to it. 
		
		
		// Key Binding Commands
		AccelAction accelCommand = new AccelAction();
		BreakAction brakeCommand = new BreakAction();
		LeftAction leftCommand = new LeftAction();
		RightAction rightCommand = new RightAction();
		TickAction tickCommand = new TickAction();
		
		//Other Commands
		NpcStrategyAction strategyCommand = new NpcStrategyAction();
		
		
		// Assignment 3 Commands
		pauseActionA3 pauseCommand = new pauseActionA3();
		deleteActionA3 deleteCommand = new deleteActionA3();
		pylonActionA3 newPylonCommand = new pylonActionA3();
		fuelActionA3 newFuelCommand = new fuelActionA3();
		
		// File Menu
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem newGame = new JMenuItem("New");
		fileMenu.add(newGame);
		newGame.addActionListener(newCommand.getNewCommand());
		
		JMenuItem saveGame = new JMenuItem("Save");
		fileMenu.add(saveGame);
		saveGame.addActionListener(saveCommand.getSaveCommand());
		
		JMenuItem soundGame = new JMenuItem("Sound");
		fileMenu.add(soundGame);
		
		JCheckBox soundValue = new JCheckBox("Sound");
		if(gw.soundValue())
		{
			soundValue.setSelected(true);
		}
		else
		{
			soundValue.setSelected(false);
		}
		soundGame.add(soundValue);
		soundValue.addActionListener(soundCommand.getSoundCommand());
		
		
		JMenuItem aboutGame = new JMenuItem("About");
		fileMenu.add(aboutGame);
		aboutGame.addActionListener(aboutCommand.getAboutCommand());
		
		JMenuItem quitGame = new JMenuItem("Quit");
		quitGame.addActionListener(quitCommand.getQuitCommand());
		fileMenu.add(quitGame);
		
		bar.add(fileMenu);
		
		
		// Command Menu
		
		JMenu commandMenu = new JMenu("Command");
		
		JMenuItem fuelMenu = new JMenuItem("Fuel Pickup");
		commandMenu.add(fuelMenu);
		fuelMenu.addActionListener(fuelCommand.getFuelCommand());
		
		JMenuItem slickMenu = new JMenuItem("Add OilSlick");
		commandMenu.add(slickMenu);
		slickMenu.addActionListener(slickCommand.getSlickCommand());
		
		JMenuItem colorMenu = new JMenuItem("New Color");
		commandMenu.add(colorMenu);
		colorMenu.addActionListener(colorCommand.getColorCommand());
		
		bar.add(commandMenu);
		
		// Top Score Panel Not this may not be needed we will find out
		
		JPanel topPanel = new JPanel();
		this.add(sv.getPanel(), BorderLayout.NORTH);
		

		JLabel timeLabel = new JLabel("Time: ");
		topPanel.add(timeLabel);
		JLabel livesLabel = new JLabel("Lives: ");
		topPanel.add(livesLabel);
		JLabel pylonLabel = new JLabel("Highest Pylon: ");
		topPanel.add(pylonLabel);
		JLabel fuelLabel = new JLabel("Fuel Level: ");
		topPanel.add(fuelLabel);
		JLabel damageLabel = new JLabel("Damage Level: ");
		topPanel.add(damageLabel);
		JLabel soundLabel = new JLabel("Sound: ");
		topPanel.add(soundLabel);
		
		
		// Left Control Panel AKA: ALOT OF BUTTONS
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new TitledBorder("Options: "));
		leftPanel.setBorder(new LineBorder(Color.red, 2));
		leftPanel.setLayout(new GridLayout (10,1));
		this.add(leftPanel, BorderLayout.WEST);
		
		JButton pauseButton = new JButton ("Pause");
		leftPanel.add(pauseButton);
		pauseButton.addActionListener(pauseCommand.getPauseCommand());
		pauseButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "");
		
		deleteButton = new JButton("Delete");
		deleteButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		leftPanel.add(deleteButton);
		deleteButton.addActionListener(deleteCommand.getDeleteCommand());
		deleteButton.setEnabled(false);
		
		addPylonButton = new JButton("Add Pylon");
		addPylonButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		leftPanel.add(addPylonButton);
		addPylonButton.addActionListener(newPylonCommand.getPylonCommand());
		addPylonButton.setEnabled(false);
		
		addFuelCanButton = new JButton("Add Fuel Can");
		addFuelCanButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		leftPanel.add(addFuelCanButton);
		addFuelCanButton.addActionListener(newFuelCommand.getFuelCommand());
		addFuelCanButton.setEnabled(false);
		
		JButton quitButton = new JButton ("Quit");
		leftPanel.add(quitButton);
		quitButton.addActionListener(quitCommand.getQuitCommand());
		quitButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "");
		
		// add a colored bordered panel at the center of this frame
		JPanel centerPanel = new JPanel();
		centerPanel.setSize(100, 100);
		centerPanel.setBorder (new EtchedBorder());
		this.add(mv,BorderLayout.CENTER);
	    centerPanel.setFocusable(true);
	    
	    int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap imap = mv.getInputMap(mapName);
		
		
		// Was unsure about these however i put them just in case 
		KeyStroke upKey = KeyStroke.getKeyStroke("UP");
		imap.put(upKey, "a");
		
		KeyStroke rightKey = KeyStroke.getKeyStroke("RIGHT");
		imap.put(rightKey, "r");
		
		KeyStroke downKey = KeyStroke.getKeyStroke("DOWN");
		imap.put(downKey, "b");
		
		KeyStroke leftKey = KeyStroke.getKeyStroke("LEFT");
		imap.put(leftKey,  "l");
		
		// Key Bindings
		KeyStroke aKey = KeyStroke.getKeyStroke('a');
		imap.put(aKey,  "a");
		
		KeyStroke bKey = KeyStroke.getKeyStroke('b');
		imap.put(bKey,  "b");
		
		KeyStroke rKey = KeyStroke.getKeyStroke('r');
		imap.put(rKey,  "r");
		
		KeyStroke lKey = KeyStroke.getKeyStroke('l');
		imap.put(lKey,  "l");
		
		KeyStroke oKey = KeyStroke.getKeyStroke('o');
		imap.put(oKey,  "o");
		
		KeyStroke qKey = KeyStroke.getKeyStroke('q');
		imap.put(qKey,  "q");
		
		KeyStroke tKey = KeyStroke.getKeyStroke('t');
		imap.put(tKey,  "t");
		
		
		//get the action map for the panel
		ActionMap amap = mv.getActionMap();
		
		// LOTS OF KEYBINDINGS
		amap.put("a", accelCommand.getAccelCommand());
		amap.put("b", brakeCommand.getBreakCommand());
		amap.put("l", leftCommand.getLeftCommand());
		amap.put("r", rightCommand.getRightCommand());
		amap.put("c", npcCommand.getCollisionCommand());
		amap.put("g", fuelCommand.getFuelCommand());
		amap.put("e", inSlickCommand.getInSlickCommand());
		amap.put("x", outSlickCommand.getOutSlickCommand());
		amap.put("n", colorCommand.getColorCommand());
		amap.put("t", tickCommand.getTickCommand());
		amap.put("q", quitCommand.getQuitCommand());
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "space");
		amap.put("space", strategyCommand.getStrategyCommand());
		
		
		setVisible(true);
	}
	
	@SuppressWarnings("serial")
	public static class QuitAction extends AbstractAction
	{
		private static QuitAction quitCommand;
		
		private QuitAction()
		{}

		public static QuitAction getQuitCommand()
		{
			if(quitCommand == null)
				quitCommand = new QuitAction();
			
			return quitCommand;
		}
		
		public void actionPerformed (ActionEvent e)
		{
			// display the source of the request
			System.out.println ("Quit?");
			// verify intent before exiting by displaying a Yes/No dialog
			int result = JOptionPane.showConfirmDialog
					(null, // source of event
							"Are you sure you want to exit ?", // display message
							"Confirm Exit", // Title bar text
							JOptionPane.YES_NO_OPTION, // button choices
							JOptionPane.QUESTION_MESSAGE); // prompt icon
			if (result == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			return; // we get here if "No" was chosen
		}
	}

	@SuppressWarnings("serial")
	public static class SaveAction extends AbstractAction
	{
		private static SaveAction save;
		
		private SaveAction()
		{}

		public static SaveAction getSaveCommand()
		{
			if(save == null)
				save = new SaveAction();
			
			return save;
		}
		
		public void actionPerformed(ActionEvent e) 
		{
			System.out.println("Game Sa... oh wait nope. nows not the time to use that");	
		}

	}
	
	@SuppressWarnings("serial")
	public static class NewAction extends AbstractAction
	{
		private gameWorld gw;
		
		private static NewAction newCommand;
		
		private NewAction()
		{}

		public static NewAction getNewCommand()
		{
			if(newCommand == null)
				newCommand = new NewAction();
			
			return newCommand;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gw.initLayout();
			timer.start();
			gw.notifyObserver();
			
		}

	}
	
	@SuppressWarnings("serial")
	public static class AboutAction extends AbstractAction
	{
		private static AboutAction aboutCommand;
		
		private AboutAction()
		{}

		public static AboutAction getAboutCommand()
		{
			if(aboutCommand == null)
				aboutCommand = new AboutAction();
			
			return aboutCommand;
		}

		public void actionPerformed (ActionEvent e) {
			// verify intent before exiting by displaying a Yes/No dialog
		     JOptionPane.showConfirmDialog	
			(null, // source of event
			"Snake Assignment 2, Programmed By Derek Irvin, CSC 133, Doctor Clevenger", // display message
			"Confirm Thats Cool or Not. Thats Cool Too", // Title bar text
			JOptionPane.OK_OPTION); // button choices);
			return; 
		}

	}
	
	@SuppressWarnings("serial")
	public static class SoundAction extends AbstractAction
	{
		private static SoundAction soundCommand;
		
		private SoundAction()
		{}

		public static SoundAction getSoundCommand()
		{
			if(soundCommand == null)
				soundCommand = new SoundAction();
			
			return soundCommand;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gw.switchSoundValue();
		}

	}
	
	@SuppressWarnings("serial")
	public static class FuelAction extends AbstractAction
	{
		private static FuelAction fuelCommand;
		
		private FuelAction()
		{}

		public static FuelAction getFuelCommand()
		{
			if(fuelCommand == null)
				fuelCommand = new FuelAction();
			
			return fuelCommand;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gw.gasUp();
		}

	}
	
	@SuppressWarnings("serial")
	public static class SlickAction extends AbstractAction
	{
		private static SlickAction slickCommand;
		
		private SlickAction()
		{}

		public static SlickAction getSlickCommand()
		{
			if(slickCommand == null)
				slickCommand = new SlickAction();
			
			return slickCommand;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gw.newSlick();
		}

	}
	
	@SuppressWarnings("serial")
	public static class ColorAction extends AbstractAction
	{
		private static ColorAction colorCommand;
		
		private ColorAction()
		{}

		public static ColorAction getColorCommand()
		{
			if(colorCommand == null)
				colorCommand = new ColorAction();
			
			return colorCommand;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gw.newColor();
		}

	}
	
	@SuppressWarnings("serial")
	public static class NpcCollisionAction extends AbstractAction
	{
		private static NpcCollisionAction collisionCommand;
		
		private NpcCollisionAction()
		{}

		public static NpcCollisionAction getCollisionCommand()
		{
			if(collisionCommand == null)
				collisionCommand = new NpcCollisionAction();
			
			return collisionCommand;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gw.carCollision();
		}

	}
	
	@SuppressWarnings("serial")
	public static class BirdCollisionAction extends AbstractAction
	{
		private static BirdCollisionAction birdCommand;
		
		private BirdCollisionAction()
		{}

		public static BirdCollisionAction BirdCollisionCommand()
		{
			if(birdCommand == null)
				birdCommand = new BirdCollisionAction();
			
			return birdCommand;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gw.gummedUp();
		}

	}
	
	@SuppressWarnings("serial")
	public static class InSlickAction extends AbstractAction
	{
		private static InSlickAction inSlickCommand;
		
		private InSlickAction()
		{}

		public static InSlickAction getInSlickCommand()
		{
			if(inSlickCommand == null)
				inSlickCommand = new InSlickAction();
			
			return inSlickCommand;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gw.oilSlick();
		}

	}
	
	@SuppressWarnings("serial")
	public static class OutSlickAction extends AbstractAction
	{
		private static OutSlickAction outSlickCommand;
		
		private OutSlickAction()
		{}

		public static OutSlickAction getOutSlickCommand()
		{
			if(outSlickCommand == null)
				outSlickCommand = new OutSlickAction();
			
			return outSlickCommand;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gw.exitSlick();
		}

	}
	
	@SuppressWarnings("serial")
	public static class AccelAction extends AbstractAction
	{
		private static AccelAction accelCommand;
		
		private AccelAction()
		{}

		public static AccelAction getAccelCommand()
		{
			if(accelCommand == null)
				accelCommand = new AccelAction();
			
			return accelCommand;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gw.carAccelerate();
		}

	}
	
	@SuppressWarnings("serial")
	public static class BreakAction extends AbstractAction
	{
		private static BreakAction breakCommand;
		
		private BreakAction()
		{}

		public static BreakAction getBreakCommand()
		{
			if(breakCommand == null)
				breakCommand = new BreakAction();
			
			return breakCommand;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gw.carBreak();
		}

	}
	
	@SuppressWarnings("serial")
	public static class LeftAction extends AbstractAction
	{
		private static LeftAction leftCommand;
		
		private LeftAction()
		{}

		public static LeftAction getLeftCommand()
		{
			if(leftCommand == null)
				leftCommand = new LeftAction();
			
			return leftCommand;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gw.steerLeft();
		}

	}
	
	@SuppressWarnings("serial")
	public static class RightAction extends AbstractAction
	{
		private static RightAction rightCommand;
		
		private RightAction()
		{}

		public static RightAction getRightCommand()
		{
			if(rightCommand == null)
				rightCommand = new RightAction();
			
			return rightCommand;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gw.steerRight();
		}

	}
	
	@SuppressWarnings("serial")
	public static class TickAction extends AbstractAction
	{
		private static TickAction tickCommand;
		
		private TickAction()
		{}

		public static TickAction getTickCommand()
		{
			if(tickCommand == null)
				tickCommand = new TickAction();
			
			return tickCommand;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gw.gameWorldTick();
		}

	}
	
	@SuppressWarnings("serial")
	public static class NpcStrategyAction extends AbstractAction
	{
		private static NpcStrategyAction strategyCommand;
		
		private NpcStrategyAction()
		{}

		public static NpcStrategyAction getStrategyCommand()
		{
			if(strategyCommand == null)
				strategyCommand = new NpcStrategyAction();
			
			return strategyCommand;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			gw.switchStrategy();
		}
	}
	
	@SuppressWarnings("serial")
	public static class PylonCollisionAction extends AbstractAction
	{
		private static PylonCollisionAction pylonCommand; // i dont know why its doing it. truly no clue why is capitalizing the L
		
		private PylonCollisionAction()
		{}

		public static PylonCollisionAction getPylonCommand()
		{
			if(pylonCommand == null)
				pylonCommand = new PylonCollisionAction();
			
			return pylonCommand;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String input = JOptionPane.showInputDialog(null, "Enter Pylon Number");	
			if(input.length() >= 1)
			{
				int i;
				for (i = 0; i < input.length(); i++)
				{
					char d = input.charAt(i);
					if('0' <= d && d <= '9')
						break;
				}
				String pylon = input.substring(i);
				
				int pyValue = Integer.valueOf(pylon);
				gw.pylonCollision(pyValue);
			}
			else
			{
				 JOptionPane.showConfirmDialog	
					(null, // source of event
					"Invalid Pylon Selected", // display message
					"Confirm to Continue", // Title bar text
					JOptionPane.OK_OPTION); // button choices);
			}
			return;
		}
		}
	
	// Assignment 3
	
	@SuppressWarnings("serial")
	public static class fuelActionA3 extends AbstractAction
	{
		private static fuelActionA3 newFuelCommand;
		
		private fuelActionA3(){}
		
		public static fuelActionA3 getFuelCommand()
		{
			if(newFuelCommand == null)
				newFuelCommand = new fuelActionA3();
			
			return newFuelCommand;
		}
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated method stub
			gw.setFuelFlag(); // set the fuel flag. on Click.
			gw.gameWorldTick();
			
		}
		
	}
	
	@SuppressWarnings("serial")
	public static class pylonActionA3 extends AbstractAction
	{
		private static pylonActionA3 pylonCommand;
		
		private pylonActionA3(){}
		
		public static pylonActionA3 getPylonCommand()
		{
			if(pylonCommand == null)
				pylonCommand = new pylonActionA3();
			
			return pylonCommand;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gw.setPylonFlag(); // Set the Pylon Flag.
			gw.gameWorldTick();
		}
		
	}
	
	@SuppressWarnings("serial")
	public static class deleteActionA3 extends AbstractAction
	{
		private static deleteActionA3 deleteCommand;
		
		private deleteActionA3(){}
		
		public static deleteActionA3 getDeleteCommand()
		{
			if(deleteCommand == null)
				deleteCommand = new deleteActionA3();
			
			return deleteCommand;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			gw.deleteObjects();
			gw.gameWorldTick();
			
		}
		
	}
	
	@SuppressWarnings("serial")
	public static class pauseActionA3 extends AbstractAction
	{
		private static pauseActionA3 pauseCommand;
		
		private pauseActionA3(){};
		
		public static pauseActionA3 getPauseCommand()
		{
			if(pauseCommand == null)
				pauseCommand = new pauseActionA3();
			
			return pauseCommand;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(gw.getPauseValue() == false)
			{
				timer.stop();
				
				// Pause Music If It is On. 
				if(gw.soundValue())
				{
					gw.pauseMusic();
				}
				
				deleteButton.setEnabled(true);
				addPylonButton.setEnabled(true);
				addFuelCanButton.setEnabled(true);
				gw.setPauseValue(true);
				gw.notifyObserver();
			}
			else if (gw.getPauseValue() == true)
			{
				timer.start();
				gw.removeSelection();
				gw.setPauseValue(false);
				deleteButton.setEnabled(false);
				addPylonButton.setEnabled(false);
				addFuelCanButton.setEnabled(false);
				if(gw.soundValue())
				{
					gw.resumeMusic();
				}
				
				gw.notifyObserver();
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		IIterator gwo = gw.getIterator();
		
		while(gwo.hasNext())
		{
			gameWorldObjects go = (gameWorldObjects) gwo.getNext();
			if(go instanceof movableObjects)
			{
				((movableObjects) go).move(DELAY_IN_MSEC);
			}
		}
		gw.gameWorldTick();
		
		
		gwo = gw.getIterator();
		
		while(gwo.hasNext())
		{
			gameWorldObjects go = (gameWorldObjects) gwo.getNext();
			if(go instanceof ICollider)
			{	
				IIterator gwo2 = gw.getIterator();
				while(gwo2.hasNext())
				{
					gameWorldObjects otherObject = (gameWorldObjects) gwo2.getNext();
					if(otherObject instanceof ICollider){

						if(otherObject != go){
							if(((ICollider) go).collidesWith((ICollider) otherObject)){
								((ICollider) go).handleCollision((ICollider)otherObject);

							}
						}

					}
				}
			}
		}
		
			gw.notifyObserver();
		}
	
}

