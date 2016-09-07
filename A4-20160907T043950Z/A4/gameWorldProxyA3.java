
public class gameWorldProxyA3 implements IGameWorld, IObservable{

	private gameWorld realGameWorld;
	
	public gameWorldProxyA3(gameWorld gw)
	{
		realGameWorld = gw;
	}
	
	@Override
	public void addObserver(IObserver o)
	{
		// TODO Auto-generated method stub
		realGameWorld.register(o);
	}

	@Override
	public void notifyObserver() 
	{
		// TODO Auto-generated method 
		realGameWorld.notifyObserver();
	}

	@Override
	public int userLives()
	{
		// TODO Auto-generated method stub
		return realGameWorld.userLives();
	}

	@Override
	public int gameTime() {
		// TODO Auto-generated method stub
		return realGameWorld.gameTime();
	}

	@Override
	public IIterator getIterator() 
	{
		// TODO Auto-generated method stub
		return realGameWorld.getIterator();
	}

	@Override
	public boolean soundValue() {
		// TODO Auto-generated method stub
		return realGameWorld.soundValue();
	}

	@Override
	public int lastPylon() {
		// TODO Auto-generated method stub
		return realGameWorld.lastPylon();
	}

	@Override
	public int fuelLevel() {
		// TODO Auto-generated method stub
		return realGameWorld.fuelLevel();
	}

	@Override
	public int playerDamage() {
		// TODO Auto-generated method stub
		return realGameWorld.playerDamage();
	}

	@Override
	public boolean getPauseValue() {
		// TODO Auto-generated method stub
		return realGameWorld.getPauseValue();
	}

}
