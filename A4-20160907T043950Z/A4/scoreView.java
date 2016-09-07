import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;



@SuppressWarnings("serial")
public class scoreView extends JPanel implements IObserver
{
	
	private int userLives;
	private int gameTime;
	private int userPylon;
	private int playerFuel;
	private int playerDamage;
	private boolean soundValue;
	
	private JPanel displayPanel;
	private JLabel timeLabel = new JLabel();
	private JLabel livesLabel = new JLabel();
	private JLabel soundLabel = new JLabel();
	private JLabel pylonLabel = new JLabel();
	private JLabel damageLabel = new JLabel();
	private JLabel fuelLabel = new JLabel();
	
	
	
	public scoreView()
	{
		displayPanel = new JPanel();
		displayPanel.setBorder(new LineBorder(Color.red, 2));
		updateDisplayPanel();
	}
	
	public JPanel getPanel()
	{
		return displayPanel;
	}
	
	private void updateDisplayPanel()
	{
		timeLabel.setText(("Time: " + gameTime));
		displayPanel.add(timeLabel);
		
		livesLabel.setText(("Lives: " + userLives));
		displayPanel.add(livesLabel);
		
		pylonLabel.setText(("Highest Pylon: " + userPylon));
		displayPanel.add(pylonLabel);

		fuelLabel.setText(("Fuel Level: " + playerFuel));
		displayPanel.add(fuelLabel);
		
		damageLabel.setText(("Damage Level: " + playerDamage));
		displayPanel.add(damageLabel);
		
		soundLabel.setText(("Sound: " + soundValue));
		displayPanel.add(soundLabel);
	}

	public void update(IObservable o) 
	{
		gameTime = ((IGameWorld)o).gameTime();
		userLives = ((IGameWorld)o).userLives();
		userPylon = ((IGameWorld)o).lastPylon();
		playerFuel = ((IGameWorld)o).fuelLevel();
		playerDamage = ((IGameWorld)o).playerDamage();
	    soundValue = ((IGameWorld)o).soundValue();
		
		this.updateDisplayPanel();
	}

	

}
