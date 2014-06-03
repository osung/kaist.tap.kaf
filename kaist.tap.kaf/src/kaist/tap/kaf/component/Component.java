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
	
	public enum Contains {
		INSIDE, SELECTION, OUTSIDE
	}
	
	public enum Selection {
		START, END, LL, LR, UL, UR, FALSE
	}
	
	protected String mName;
	protected Point mPosition;
	protected Color mColor;
	protected boolean mFill;
	protected Color  mFillColor;
	protected int mLineThickness;
	protected int mLineStyle;
	protected String mPortList;
	protected String mPortAvailability;
	protected Point mEndPosition;
	protected boolean mDrawn;
	protected SelectMode mSelectMode;
	protected final int contSize = 3;  // half size of control point
	protected boolean mUpdated;
	protected boolean mGrouped;
	protected Selection mSelection;
	
	public Component() {
		mDrawn = false;
		mPosition = new Point(0,0);
		mEndPosition = new Point(0,0);
		mLineThickness = 1;
		mLineStyle = SWT.LINE_SOLID;
		mColor = SWTResourceManager.getColor(SWT.COLOR_BLACK);
		mFillColor = SWTResourceManager.getColor(SWT.COLOR_WHITE);
		mFill = true;
		mSelectMode = SelectMode.UNSELECTED;
		mUpdated = false;
		mGrouped = false;
		mSelection = Selection.FALSE;
	}
	
	public boolean getUpdated() {
		if (mUpdated == true) {
			mUpdated = false;
			return true;
		}
		
		return false;
	}
	
	public void setGrouped() {
		mGrouped = true;
	}
	
	public void unsetGrouped() {
		mGrouped = false;
	}
	
	
	public boolean getGrouped() {
		return mGrouped;
	}
	
	
	public Point getEndPosition() {
		return mEndPosition;
	}
	
	
	public void setEndPosition(Point endPosition) {
		mEndPosition = endPosition;
	}
	
	public Selection getSelection() {
		return mSelection;
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
		mPosition = new Point(position.x, position.y);
	}
	public Color getColor() {
		return mColor;
	}
	public Color getFillColor() {
		return mFillColor;
	}
	public void setColor(int color) {
		mColor = SWTResourceManager.getColor(color);
	}
	public void setFillColor(int color) {
		mFillColor = SWTResourceManager.getColor(color);
	}
	public void setColor(Color color) {
		mColor = color;
	}
	public void setFillColor(Color color) {
		mFillColor = color;
	}
	public void setColor(RGB color) {
		mColor = SWTResourceManager.getColor(color);
	}
	public void setFillColor(RGB color) {
		mFillColor = SWTResourceManager.getColor(color);
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
		mSelection = Selection.FALSE;
	}
	
	public boolean isSelected() {
		if (mSelectMode == SelectMode.SELECTED) {
			return true;
		}
		else return false;
	}
	
	public void setFill() {
		mFill = true;
	}
	
	public void unsetFill() {
		mFill = false;
	}
	
	
	public void setFill(boolean b) {
		mFill = b;
	}
	
	public boolean getFill() {
		return mFill;
	}
	
	public void reset() {
		mDrawn = false;
		mPosition = new Point(0,0);
		mEndPosition = new Point(0,0);
		mLineThickness = 1;
		mLineStyle = SWT.LINE_SOLID;
		mColor = SWTResourceManager.getColor(SWT.COLOR_BLACK);
		mFillColor = SWTResourceManager.getColor(SWT.COLOR_WHITE);
		mSelectMode = SelectMode.UNSELECTED;
		mFill = false;
		mSelection = Selection.FALSE;
	}

	public Selection containSelection(int x, int y) {
		return Selection.FALSE;
	}
	
	public abstract boolean contains (int x, int y);
	
	public abstract void move(int x, int y);
	
	public void resize(int x, int y) {
		
	}
	
	public abstract Point[] getBounds();
	
	public abstract void draw(GC gc);
	
	public abstract Component clone(); 
	
	@Override
	public Object getEditableValue() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		// TODO Auto-generated method stub
		String[] lsvalues = {"Solid", "Dot", "Dash", "Dashdot", "Dashdotdot"};
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor("Name", "Name"),
				new TextPropertyDescriptor("Position_X", "Position X"), 
				new TextPropertyDescriptor("Position_Y", "Position Y"),
				new TextPropertyDescriptor("Color", "Color"),
				new TextPropertyDescriptor("FillColor", "Fill Color"),
				new TextPropertyDescriptor("Fill", "Fill"), 
				new TextPropertyDescriptor("Line_Thickness", "Line Thickness"),
				//new ComboBoxPropertyDescriptor("Line_Style", "Line Style", lsvalues)
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
		else if ("Color".equals(id)) return mColor.getRGB();
		else if ("FillColor".equals(id)) return mFillColor.getRGB();
		else if ("Fill".equals(id)) {
			if (mFill == true) return 0;
			else return 1;
		}
		else if ("Line_Thickness".equals(id)) return Integer.toString(mLineThickness);
		else if ("Line_Style".equals(id)) {
		switch (mLineStyle) {
			case SWT.LINE_SOLID :
				return 0;
			case SWT.LINE_DOT :
				return 1;
			case SWT.LINE_DASH :
				return 2;
			case SWT.LINE_DASHDOT :
				return 3;
			case SWT.LINE_DASHDOTDOT :
				return 4;
			default :
				return 0;
			} 
		} 
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

	public void setPropertyValue(Object id, Object value) {
		if ("Name".equals(id)) mName = (String) value;
		else if ("Position_X".equals(id)) {
			if (mPosition == null) mPosition = new Point(0, 0);
			mPosition.x = Integer.parseInt((String) value);
		}
		else if ("Position_Y".equals(id)) {
			if (mPosition == null) mPosition = new Point(0, 0);
			mPosition.y = Integer.parseInt((String) value);
		}
		else if ("Color".equals(id)) {
			setColor((RGB) value);
		}
		else if ("FillColor".equals(id)) {
			setFillColor((RGB) value);
		}
		else if ("Fill".equals(id)) {
			if ((int) value == 0) mFill = true;
			else mFill = false;
		}
		else if ("Line_Thickness".equals(id)) mLineThickness = Integer.parseInt((String) value);
		else if ("Line_Style".equals(id)) { 
			switch((int) value) {
			case 0 :
				mLineStyle = SWT.LINE_SOLID;
				break;
			case 1:
				mLineStyle = SWT.LINE_DOT;
				break;
			case 2:
				mLineStyle = SWT.LINE_DASH;
				break;
			case 3:
				mLineStyle = SWT.LINE_DASHDOT;
				break;
			case 4:
				mLineStyle = SWT.LINE_DASHDOTDOT;
				break;
			}
		} 
		
		mUpdated = true;
	}
}
