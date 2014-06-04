package kaist.tap.kaf.component;

import kaist.tap.kaf.component.Component.Selection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;

public class Rectangle extends Component {
		
	protected int width;
	protected int height;
	
	public Rectangle() {
		this.setName("Rectangle");
		position.x = position.y = endPosition.x = endPosition.y = width = height = 0;
	}
	
	public Rectangle(int x, int y, int w, int h) {
		setName("Rectangle");
		position.x = x;
		position.y = y;
		width = w;
		height = h;
		endPosition.x = x + w;
		endPosition.y = y + h;
	}
	
	public void setPosition(Point p) {
		super.setPosition(p);
		
		width = Math.abs(endPosition.x-position.x);
		height = Math.abs(endPosition.y-position.y);
	}
	
	public void setEndPosition(Point p) {
		endPosition.x = p.x;
		endPosition.y = p.y;
		
		width = Math.abs(endPosition.x-position.x);
		height = Math.abs(endPosition.y-position.y);
	}
	
	public void updatePosition(int x, int y) {
		if (endPosition.x < x) {
			position.x = endPosition.x;
			endPosition.x = x;
		}
		else position.x = x;
		
		if (endPosition.y < y) {
			position.y = endPosition.y;
			endPosition.y = y;
		}
		else position.y = y;
		
		width = Math.abs(endPosition.x-position.x);
		height = Math.abs(endPosition.y-position.y);
	}
	
	public void updatePosition(Point p) {
		if (endPosition.x < p.x) {
			position.x = endPosition.x;
			endPosition.x = p.x;
		}
		else position.x = p.x;
		
		if (endPosition.y < p.y) {
			position.y = endPosition.y;
			endPosition.y = p.y;
		}
		else position.y = p.y;
		
		width = Math.abs(endPosition.x-position.x);
		height = Math.abs(endPosition.y-position.y);
	}
	
	public void updateEndPosition(int x, int y) {
		if (position.x > x) {
			endPosition.x = position.x;
			position.x = x;
		}
		else endPosition.x = x;
		
		if (position.y > y) {
			endPosition.y = position.y;
			position.y = y;
		}
		else endPosition.y = y;
		
		width = Math.abs(endPosition.x-position.x);
		height = Math.abs(endPosition.y-position.y);
	}
	
	public void updateEndPosition(Point p) {
		if (position.x > p.x) {
			endPosition.x = position.x;
			position.x = p.x;
		}
		else endPosition.x = p.x;
		
		if (position.y > p.y) {
			endPosition.y = position.y;
			position.y = p.y;
		}
		else endPosition.y = p.y;

		
		width = Math.abs(endPosition.x-position.x);
		height = Math.abs(endPosition.y-position.y);
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int w) {
		width = w;
		endPosition.x = position.x + w;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int h) {
		height = h;
		endPosition.y = position.y + h;
	}
	
	public void draw(GC gc) {
		gc.setForeground(getColor());		
		gc.setBackground(getFillColor());
		gc.setLineWidth(lineThickness);
		gc.setLineStyle(lineStyle);
		gc.drawRectangle(position.x, position.y, width, height);
		if (fill == true) gc.fillRectangle(position.x+1, position.y+1, width-1, height-1);
		
		if (selectMode == SelectMode.SELECTED) {
			gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			// draw control	
			gc.fillRectangle(position.x-contSize, position.y-contSize, contSize*2, contSize*2);
			gc.fillRectangle(endPosition.x-contSize, endPosition.y-contSize, contSize*2, contSize*2);
			gc.fillRectangle(position.x-contSize, endPosition.y-contSize, contSize*2, contSize*2);
			gc.fillRectangle(endPosition.x-contSize, position.y-contSize, contSize*2, contSize*2);
		}
	}
	
	public Selection containSelection(int x, int y) {
		selection = Selection.FALSE;
		
		if (Math.abs(x-position.x) < contSize) {
			if (Math.abs(y-position.y) < contSize) {
				selection = Selection.UL;
				return Selection.UL;
			}
			else if (Math.abs(y-endPosition.y) < contSize) {
				selection = Selection.LL;
				return Selection.LL;
			}
			else return Selection.FALSE;
		}
		else if (Math.abs(x-endPosition.x) < contSize) {
			if (Math.abs(y-position.y) < contSize) {
				selection = Selection.UR;
				return Selection.UR;
			}
			else if (Math.abs(y-endPosition.y) < contSize) {
				selection = Selection.LR;
				return Selection.LR;
			}
			else return Selection.FALSE;
		}
		else return Selection.FALSE;
	}
				
	public boolean contains(int x, int y) {
		
		if (grouped==true) return false;
		
		selection = containSelection(x, y);
		
		if (this.isSelected()==true) {
			if (selection != Selection.FALSE) return true;
		}
		
		if (x < position.x || x > endPosition.x || y < position.y || y > endPosition.y) {
			return false;
		}
		
		return true;
	}

	public void move(int x, int y) {
		position.x += x;
		position.y += y;
		endPosition.x += x;
		endPosition.y += y;
	}
	
	public void resize(int x, int y) {
		if (selection == Selection.UL) {
			this.updatePosition(x, y);
		}
		else if (selection == Selection.LR) {
			this.updateEndPosition(x, y);
		}
		else if (selection == Selection.LL) {
			this.updatePosition(x, position.y);
			this.updateEndPosition(endPosition.x, y);
		}
		else if (selection == Selection.UR) {
			this.updatePosition(position.x, y);
			this.updateEndPosition(x, endPosition.y);
		}
	}
	
	public Rectangle clone() {
		Rectangle rect = new Rectangle(position.x, position.y, width, height);
		rect.setColor(color);
		rect.setFillColor(fillColor);
		rect.setFill(fill);
		rect.setDrawn(drawn);
		rect.setLineStyle(lineStyle);
		rect.setLineThickness(lineThickness);
		
		return rect;
	}
	
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		TextPropertyDescriptor nameDiscriptor = new TextPropertyDescriptor("Name", "Type");
		TextPropertyDescriptor posxDiscriptor = new TextPropertyDescriptor("Position_X", "X");
		posxDiscriptor.setCategory("Position");
		TextPropertyDescriptor posyDiscriptor = new TextPropertyDescriptor("Position_Y", "Y");
		posyDiscriptor.setCategory("Position");
		TextPropertyDescriptor sizewDiscriptor = new TextPropertyDescriptor("Width", "Width");
		sizewDiscriptor.setCategory("Size");
		TextPropertyDescriptor sizehDiscriptor = new TextPropertyDescriptor("Height", "Height");
		sizehDiscriptor.setCategory("Size");
		ColorPropertyDescriptor lcDiscriptor = new ColorPropertyDescriptor("Color", "Color");
		lcDiscriptor.setCategory("Line");
		TextPropertyDescriptor ltDiscriptor = new TextPropertyDescriptor("Line_Thickness", "Thickness");
		ltDiscriptor.setCategory("Line");
		String[] lsvalues = {"Solid", "Dot", "Dash", "Dashdot", "Dashdotdot"};
		ComboBoxPropertyDescriptor lsDiscriptor = new ComboBoxPropertyDescriptor("Line_Style", "Line Style", lsvalues);
		lsDiscriptor.setCategory("Line");
		ColorPropertyDescriptor pcDiscriptor = new ColorPropertyDescriptor("FillColor", "Color");
		pcDiscriptor.setCategory("Polygon");
		String[] fillvalues = {"On", "Off"};
		ComboBoxPropertyDescriptor pfDiscriptor = new ComboBoxPropertyDescriptor("Fill", "Fill", fillvalues);
		pfDiscriptor.setCategory("Polygon");
		
		return new IPropertyDescriptor[] {
				nameDiscriptor, posxDiscriptor, posyDiscriptor, sizewDiscriptor, sizehDiscriptor,
				lcDiscriptor, ltDiscriptor, lsDiscriptor, 
				pcDiscriptor, pfDiscriptor
		};
	}

	public Object getPropertyValue(Object id) {
		if ("Width".equals(id)) return Integer.toString(width);
		else if ("Height".equals(id)) return Integer.toString(height);
		else return super.getPropertyValue(id);
	}

	public void setPropertyValue(Object id, Object value) {
		if ("Width".equals(id)) {
			width = Integer.parseInt((String) value);
			endPosition.x = position.x+width;
		}
		else if ("Height".equals(id)) {
			height = Integer.parseInt((String) value);
			endPosition.y = position.y+height;
		}
		else super.setPropertyValue(id, value);
	}

	public Point[] getBounds() {
		Point[] bounds = new Point[2];
		
		bounds[0] = new Point(0,0);
		bounds[1] = new Point(0,0);
		
		bounds[0].x = position.x;
		bounds[1].x = endPosition.x;
		bounds[0].y = position.y;
		bounds[1].y = endPosition.y;
		
		return bounds;
	}
}
