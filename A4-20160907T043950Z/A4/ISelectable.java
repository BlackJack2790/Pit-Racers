import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public interface ISelectable 
{
	public void setSelected(boolean selected);
	public boolean isSelected();
	public boolean contains(Point2D p);
	public void draw(Graphics2D g);
}
