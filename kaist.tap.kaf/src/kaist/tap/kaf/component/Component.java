package kaist.tap.kaf.component;

import java.util.Vector;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;

public abstract class Component extends ComponentElement implements ISelection {

	public enum Contains {
		INSIDE, OUTSIDE, SELECTION
	}

	public enum Selection {
		END, FALSE, LL, LR, START, UL, UR
	}

	public enum SelectMode {
		SELECTED, UNSELECTED
	}

	protected final int contSize = 3; // half size of control point
	protected final int portSize = 6;
	protected Color color;
	protected boolean drawn;
	protected Point endPosition;
	protected boolean fill;
	protected Color fillColor;
	protected boolean grouped;
	protected int lineStyle;
	protected int lineThickness;
	protected String name;
	protected Point position;
	protected Selection selection;
	protected SelectMode selectMode;
	protected boolean updated;
	protected Vector<Line> connections;
	protected boolean portable;
	protected Vector<Port> ports;
	protected Port selport;  // selected port

	public Component() {
		drawn = false;
		position = new Point(0, 0);
		endPosition = new Point(0, 0);
		lineThickness = 1;
		lineStyle = SWT.LINE_SOLID;
		color = SWTResourceManager.getColor(SWT.COLOR_BLACK);
		fillColor = SWTResourceManager.getColor(SWT.COLOR_WHITE);
		fill = true;
		selectMode = SelectMode.UNSELECTED;
		updated = false;
		grouped = false;
		selection = Selection.FALSE;
		connections = new Vector<Line>();
		portable = false;
		ports = new Vector<Port>();
		selport = null;
	}

	public abstract Component clone();

	public abstract boolean contains(int x, int y);

	public Selection containSelection(int x, int y) {
		return Selection.FALSE;
	}

	public abstract void draw(GC gc);

	public abstract Point[] getBounds();

	public Color getColor() {
		return color;
	}

	public boolean getDrawn() {
		return drawn;
	}

	@Override
	public Object getEditableValue() {
		// TODO Auto-generated method stub
		return this;
	}

	public Point getEndPosition() {
		return endPosition;
	}

	public boolean getFill() {
		return fill;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public boolean getGrouped() {
		return grouped;
	}

	public int getLineStyle() {
		return lineStyle;
	}

	public int getLineThickness() {
		return lineThickness;
	}

	public String getName() {
		return name;
	}

	public Point getPosition() {
		return position;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		String[] lsvalues = { "Solid", "Dot", "Dash", "Dashdot", "Dashdotdot" };
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor("Name", "Name"),
				new TextPropertyDescriptor("Position_X", "Position X"),
				new TextPropertyDescriptor("Position_Y", "Position Y"),
				new TextPropertyDescriptor("Color", "Color"),
				new TextPropertyDescriptor("FillColor", "Fill Color"),
				new TextPropertyDescriptor("Fill", "Fill"),
				new TextPropertyDescriptor("Line_Thickness", "Line Thickness"),
				new ComboBoxPropertyDescriptor("Line_Style", "Line Style",
						lsvalues) };
	}

	@Override
	public Object getPropertyValue(Object id) {
		if ("Name".equals(id)) {
			if (name != null)
				return name;
			else
				return "None";
		} else if ("Position_X".equals(id))
			return Integer.toString(position.x);
		else if ("Position_Y".equals(id))
			return Integer.toString(position.y);
		else if ("Color".equals(id))
			return color.getRGB();
		else if ("FillColor".equals(id))
			return fillColor.getRGB();
		else if ("Fill".equals(id)) {
			if (fill == true)
				return 0;
			else
				return 1;
		} else if ("Line_Thickness".equals(id))
			return Integer.toString(lineThickness);
		else if ("Line_Style".equals(id)) {
			switch (lineStyle) {
			case SWT.LINE_SOLID:
				return 0;
			case SWT.LINE_DOT:
				return 1;
			case SWT.LINE_DASH:
				return 2;
			case SWT.LINE_DASHDOT:
				return 3;
			case SWT.LINE_DASHDOTDOT:
				return 4;
			default:
				return 0;
			}
		}
		return "N/A";
	}

	public Selection getSelection() {
		return selection;
	}

	public void setSelection(Selection sel) {
		selection = sel;
	}

	public boolean getUpdated() {
		if (updated == true) {
			updated = false;
			return true;
		}

		return false;
	}

	@Override
	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSelected() {
		if (selectMode == SelectMode.SELECTED) {
			return true;
		} else
			return false;
	}

	public abstract void move(int x, int y);

	public abstract void movePort(int x, int y);
	
	public void addPort() {
		addPort(position.x, position.y);
	}
	
	public void addPort(int x, int y) {
		if (portable == false) return;
		
		Port port = new Port(this);
		port.setPosition(x, y);
		
		ports.add(port);
	}
	
	public void reset() {
		drawn = false;
		position = new Point(0, 0);
		endPosition = new Point(0, 0);
		lineThickness = 1;
		lineStyle = SWT.LINE_SOLID;
		color = SWTResourceManager.getColor(SWT.COLOR_BLACK);
		fillColor = SWTResourceManager.getColor(SWT.COLOR_WHITE);
		selectMode = SelectMode.UNSELECTED;
		fill = false;
		selection = Selection.FALSE;
	}

	@Override
	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub

	}

	public void resize(int x, int y) {

	}

	public void select() {
		selectMode = SelectMode.SELECTED;
	}

	public void setColor(Color c) {
		color = c;
	}

	public void setColor(int c) {
		color = SWTResourceManager.getColor(c);
	}

	public void setColor(RGB c) {
		color = SWTResourceManager.getColor(c);
	}

	public void setDrawn(boolean b) {
		drawn = b;
	}

	public void setEndPosition(Point pos) {
		endPosition = pos;
	}

	public void setFill() {
		fill = true;
	}

	public void setFill(boolean b) {
		fill = b;
	}

	public void setFillColor(Color color) {
		fillColor = color;
	}

	public void setFillColor(int color) {
		fillColor = SWTResourceManager.getColor(color);
	}

	public void setFillColor(RGB color) {
		fillColor = SWTResourceManager.getColor(color);
	}

	public void setGrouped() {
		grouped = true;
	}

	public void setLineStyle(int style) {
		lineStyle = style;
	}

	public void setLineThickness(int thickness) {
		lineThickness = thickness;
	}

	public void setName(String n) {
		name = n;
	}

	public void setPosition(Point pos) {
		position = new Point(pos.x, pos.y);
	}

	public void setPosition(int x, int y) {
		position.x = x;
		position.y = y;
	}

	public void setPropertyValue(Object id, Object value) {
		if ("Name".equals(id))
			name = (String) value;
		else if ("Position_X".equals(id)) {
			if (position == null)
				position = new Point(0, 0);
			position.x = Integer.parseInt((String) value);
		} else if ("Position_Y".equals(id)) {
			if (position == null)
				position = new Point(0, 0);
			position.y = Integer.parseInt((String) value);
		} else if ("Color".equals(id)) {
			setColor((RGB) value);
		} else if ("FillColor".equals(id)) {
			setFillColor((RGB) value);
		} else if ("Fill".equals(id)) {
			if ((int) value == 0)
				fill = true;
			else
				fill = false;
		} else if ("Line_Thickness".equals(id))
			lineThickness = Integer.parseInt((String) value);
		else if ("Line_Style".equals(id)) {
			switch ((int) value) {
			case 0:
				lineStyle = SWT.LINE_SOLID;
				break;
			case 1:
				lineStyle = SWT.LINE_DOT;
				break;
			case 2:
				lineStyle = SWT.LINE_DASH;
				break;
			case 3:
				lineStyle = SWT.LINE_DASHDOT;
				break;
			case 4:
				lineStyle = SWT.LINE_DASHDOTDOT;
				break;
			}
		}

		updated = true;
	}

	public void unselect() {
		selectMode = SelectMode.UNSELECTED;
		selection = Selection.FALSE;
	}

	public void unsetFill() {
		fill = false;
	}

	public void unsetGrouped() {
		grouped = false;
	}

	public void addConnection(Line line) {
		connections.add(line);
	}

	public int getNumberOfConnections() {
		return connections.size();
	}

	public void removeConnection(Line line) {
		connections.remove(line);
		
		for (int i = 0; i < ports.size(); ++i) {
			ports.get(i).removeConnection(line);			
		}
	}

	public Line getConnection(int idx) {
		if (idx >= connections.size())
			return null;

		return connections.get(idx);
	}
	
	public Port containPort(int x, int y) {
		if (portable == false || ports.size() == 0) return null;
		
		for (int i = 0; i < ports.size(); ++i) {
			Port port = ports.get(i);
			
			if (port.contains(x,  y) == true) {
				return port;
			}
		}
		
		return null;
	}
}