public interface IGameWorld 
{
	public int userLives();
	public int gameTime();
	public IIterator getIterator();
	public boolean soundValue();
	public boolean getPauseValue();
	public int lastPylon();
	public int fuelLevel();
	public int playerDamage();
}
