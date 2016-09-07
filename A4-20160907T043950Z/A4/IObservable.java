

// Subject Interface

public interface IObservable 
{
	public void addObserver(IObserver o);
	public void notifyObserver();

}
