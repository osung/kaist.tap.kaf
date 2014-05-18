package kaist.tap.kaf.component;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;

public abstract class Component extends ComponentElement implements ISelection {
	
	public enum SelectMode {
		UNSELECTED, SELECTED
	}
	
	protected String mName;
	protected Point mPosition;
	protected int mColor;
	protected boolean mFill;
	protected int mFillColor;
	protected int mLineThickness;
	protected int mLineStyle;
	protected String mPortList;
	protected String mPortAvailability;
	protected Point mEndPosition;
	protected boolean mDrawn;
	protected SelectMode mSelectMode;
	protected final int contSize = 3;  // half size of control point
	
	public Component() {
		mDrawn = false;
		mPosition = new Point(0,0);
		mEndPosition = new Point(0,0);
		mLineThickness = 1;
		mLineStyle = SWT.LINE_SOLID;
		mColor = SWT.COLOR_BLACK;
		mFillColor = SWT.COLOR_WHITE;
		mFill = false;
		mSelectMode = SelectMode.UNSELECTED;
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
		return SWTResourceManager.getColor(mColor);
	}
	public Color getFillColor() {
		return SWTResourceManager.getColor(mFillColor);
	}
	public void setColor(int color) {
		mColor = color;
	}
	public void setFillColor(int color) {
		mFillColor = color;
	}
	public int getColorFromString(String color) {
		switch (color) {
		case "Black" :
		case "black" :	
			return SWT.COLOR_BLACK;
		case "White" :
		case "white" :
			return SWT.COLOR_WHITE;
		case "Red" :
		case "red" :
			return SWT.COLOR_RED;
		case "Blue" :
		case "blue" :
			return SWT.COLOR_BLUE;
		case "Green" :
		case "green" :
			return SWT.COLOR_GREEN;
		case "Yellow" :
		case "yellow" :
			return SWT.COLOR_YELLOW;
		case "Magenta" :
		case "magenta" :
			return SWT.COLOR_MAGENTA;
		case "Cyan" :
		case "cyan" :
			return SWT.COLOR_CYAN;
		case "Gray" :
		case "gray" :
			return SWT.COLOR_GRAY;
		default :
			return 0;
		}
	}
	public String getColorByString(int color) {
		switch (color) {
		case SWT.COLOR_BLACK : 
			return "Black";
		case SWT.COLOR_WHITE : 
			return "White";
		case SWT.COLOR_RED : 
			return "Red";
		case SWT.COLOR_GREEN :
			return "Green";
		case SWT.COLOR_BLUE :
			return "Blue";
		case SWT.COLOR_CYAN :
			return "Cyan";
		case SWT.COLOR_YELLOW :
			return "Yellow";
		case SWT.COLOR_MAGENTA :
			return "Magenta";
		case SWT.COLOR_GRAY :
			return "Gray";
		default : 
			return "N/A";
		}
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
	public boolean getDrawn() {
		return mDrawn;
	}
	public void setDrawn(boolean b) {
		mDrawn = b;
	}
	
	public void select() {
		mSelectMode = SelectMode.SELECTED;
	}
	
	public void unselect() {
		mSelectMode = SelectMode.UNSELECTED;
	}
	
	public void setFill() {
		mFill = true;
	}
	
	public void unsetFill() {
		mFill = false;
	}
	
	public void reset() {
		mDrawn = false;
		mPosition = new Point(0,0);
		mEndPosition = new Point(0,0);
		mLineThickness = 1;
		mLineStyle = SWT.LINE_SOLID;
		mColor = SWT.COLOR_BLACK;
		mFillColor = SWT.COLOR_WHITE;
		mSelectMode = SelectMode.UNSELECTED;
		mFill = false;
	}

	public abstract boolean contains (int x, int y);
	
	public abstract void move(int x, int y);
	
	public abstract void draw(GC gc);
	
	@Override
	public Object getEditableValue() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		// TODO Auto-generated method stub
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor("Name", "Name"),
				new TextPropertyDescriptor("Position_X", "Position X"), 
				new TextPropertyDescriptor("Position_Y", "Position Y"),
				new TextPropertyDescriptor("Color", "Color"),
				new TextPropertyDescriptor("FillColor", "Fill Color"),
				new TextPropertyDescriptor("Fill", "Fill"), 
				new TextPropertyDescriptor("Line_Thickness", "Line Thickness"),
				new TextPropertyDescriptor("Line_Style", "Line Style")
		};
	}

	@Override
	public Object getPropertyValue(Object id) {
		// TODO Auto-generated method stub
		if ("Name".equals(id)) {
			if (mName != null) return mName;
			else return "None";
		}
		else if ("Position_X".equals(id)) return Integer.toString(mPosition.x);
		else if ("Position_Y".equals(id)) return Integer.toString(mPosition.y);
		else if ("Color".equals(id)) return getColorByString(mColor);
		else if ("FillColor".equals(id)) return getColorByString(mFillColor);
		else if ("Fill".equals(id)) return Boolean.toString(mFill);
		else if ("Line_Thickness".equals(id)) return Integer.toString(mLineThickness);
		else if ("Line_Style".equals(id)) return Integer.toString(mLineStyle);
		return "N/A";
	}

	@Override
	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
		String tmp = (String) value;
		System.out.println("value is" + tmp);
		if ("Name".equals(id)) mName = (String) value;
		else if ("Position_X".equals(id)) {
			if (mPosition == null) mPosition = new Point(0, 0);
			mPosition.x = Integer.parseInt(tmp);
		}
		else if ("Position_Y".equals(id)) {
			if (mPosition == null) mPosition = new Point(0, 0);
			mPosition.y = Integer.parseInt(tmp);
		}
		else if ("Color".equals(id)) {
			int color = getColorFromString(tmp);
			if (color != 0) {
				mColor = getColorFromString(tmp);
			}
		}
		else if ("FillColor".equals(id)) {
			int color = getColorFromString(tmp);
			if (color != 0) {
				mFillColor = getColorFromString(tmp);
			}
		}
		else if ("Fill".equals(id)) mFill = Boolean.parseBoolean(tmp);
		else if ("Line_Thickness".equals(id)) mLineThickness = Integer.parseInt(tmp);
		else if ("Line_Style".equals(id)) mLineStyle = Integer.parseInt(tmp);
	}
}
