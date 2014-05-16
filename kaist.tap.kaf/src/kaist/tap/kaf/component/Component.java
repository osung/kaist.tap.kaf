package kaist.tap.kaf.component;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;

//public abstract class Component extends ComponentElement implements IAdaptable {
public abstract class Component implements IAdaptable, ISelection {
		
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
		mPosition = new Point(0,0);
		mEndPosition = new Point(0,0);
		mLineThickness = 1;
		mLineStyle = SWT.LINE_SOLID;
		mColor = null;
	}
	
	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
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
//		this.firePropertyChange("POSITION_PROP", null, position);
	}
	public Color getColor() {
		return mColor;
	}
	public void setColor(Color color) {
		mColor = color;
//		this.firePropertyChange("COLOR_PROP", null, color);
	}
	public int getLineThickness() {
		return mLineThickness;
	}
	public void setLineThickness(int lineThickness) {
		mLineThickness = lineThickness;
//		this.firePropertyChange("LINETHICK_PROP", null, lineThickness);
	}
	public int getLineStyle() {
		return mLineStyle;
	}
	public void setLineStyle(int lineStyle) {
		mLineStyle = lineStyle;
//		this.firePropertyChange("LINESTYLE_PROP", null, lineStyle);
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
	public abstract boolean contains (int x, int y);
	
	public abstract void draw(GC gc);
}
