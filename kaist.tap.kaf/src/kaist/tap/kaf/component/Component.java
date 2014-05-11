package kaist.tap.kaf.component;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;

public abstract class Component {

	public class Point {
		public int x;
		public int y;
		
		public Point() {
			x = y = 0;
		}
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	protected String mName;
	protected Point mPosition;
	protected Color mColor;
	protected int mLineThickness;
	protected int mLineStyle;
	protected String mPortList;
	protected String mPortAvailability;
	protected Point mEndPosition;
	protected boolean mDrawn;
	
	public Component() {
		mDrawn = false;
		mPosition = new Point();
		mEndPosition = new Point();
		mLineThickness = 1;
		mLineStyle = SWT.LINE_SOLID;
		mColor = null;
	}
	
	public Point getEndPosition() {
		return mEndPosition;
	}
	
	public void setEndPosition(Point endPosition) {
		mEndPosition = endPosition;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}
	public Point getPosition() {
		return mPosition;
	}
	public void setPosition(Point position) {
		mPosition = position;
	}
	public Color getColor() {
		return mColor;
	}
	public void setColor(Color color) {
		mColor = color;
	}
	public int getLineThickness() {
		return mLineThickness;
	}
	public void setLineThickness(int lineThickness) {
		mLineThickness = lineThickness;
	}
	public int getLineStyle() {
		return mLineStyle;
	}
	public void setLineStyle(int lineStyle) {
		mLineStyle = lineStyle;
	}
	public String getPortList() {
		return mPortList;
	}
	public void setPortList(String portList) {
		mPortList = portList;
	}
	public String getPortAvailability() {
		return mPortAvailability;
	}
	public abstract void draw(GC gc);
}
