package kaist.tap.kaf.component;

import kaist.tap.kaf.component.Component.SelectMode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;

public class Parallelogram extends Rectangle {
	public enum ParallelType {
		LEFT, RIGHT, TOP, BOTTOM;
		
		public String toString() {
			switch(this) {
			case LEFT :
				return "Left";
			case RIGHT :
				return "Right";
			case TOP :
				return "Top";
			case BOTTOM :
				return "Bottom";
			default :
				return "";
			}
		}
	}
	
	protected int controlPoint;
	protected ParallelType type;
	protected Point[] realPoints;
	
	public Parallelogram () {
		setName("Parallelogram");
		realPoints = new Point[4];
		for (int i = 0; i < 4; ++i) realPoints[i] = new Point(0, 0);
		setParallelType(ParallelType.LEFT);
		setControlPoint(10);
	}
	
	public void setControlPoint(int cp) {
		if (cp < 0 || cp > width) {
			return;
		}
		controlPoint = cp;
	}
	
	public int getControlPoint() {
		return controlPoint;
	}
	
	public void setParallelType(ParallelType type) {
		this.type = type;
		
		realPoints[0].x = position.x;  	realPoints[0].y = position.y;  
		realPoints[1].x = endPosition.x;   realPoints[1].y = position.y;
		realPoints[2].x = endPosition.x;	realPoints[2].y = endPosition.y;
		realPoints[3].x = position.x; 		realPoints[3].y = endPosition.y;
		
		switch(type) {
		case LEFT :
			realPoints[0].x = position.x + controlPoint;
			realPoints[2].x = endPosition.x - controlPoint;
			break;
			
		case RIGHT :
			realPoints[1].x = endPosition.x - controlPoint;
			realPoints[3].x = position.x + controlPoint;
			break;
			
		case TOP :
			realPoints[0].y = position.y + controlPoint;
			realPoints[2].y = endPosition.y - controlPoint;
			break;
			
		case BOTTOM :
			realPoints[1].y = position.y - controlPoint;
			realPoints[3].y = endPosition.y + controlPoint;
			break;
		}
	}
	
	public ParallelType getParallelType() {
		return type;
	}
	
	public void setPosition(Point p) {
		super.setPosition(p);
		setParallelType(type);
	}
	
	public void setEndPosition(int x, int y) {
		super.setEndPosition(new Point(x, y));
		setParallelType(type);
	}
	
	public void setEndPosition(Point p) {
		super.setEndPosition(p);
		setParallelType(type);
	}
	
	public void setWidth(int width) {
		if ((type == ParallelType.LEFT || type == ParallelType.RIGHT) && width < controlPoint) {
			controlPoint = width;
		}
		super.setWidth(width);
		setParallelType(type);
	}
	
	public void setHeight(int height) {
		if ((type == ParallelType.TOP || type == ParallelType.BOTTOM) && height < controlPoint) {
			controlPoint = height;
		}
		super.setHeight(height);
		setParallelType(type);
	}
	
	public void draw(GC gc) {
		gc.setForeground(getColor());		
		gc.setBackground(getFillColor());
		gc.setLineWidth(lineThickness);
		gc.setLineStyle(lineStyle);
		
		int polys[] = {realPoints[0].x, realPoints[0].y, realPoints[1].x, realPoints[1].y, 
				       realPoints[2].x, realPoints[2].y, realPoints[3].x, realPoints[3].y};
		gc.drawPolygon(polys);
		
		if (fill == true) {
			polys[0]++; polys[1]++; polys[2]--; polys[3]++; polys[4]--; polys[5]--; polys[6]++; polys[7]--;
			gc.fillPolygon(polys);
		}
		
		if (selectMode == SelectMode.SELECTED) {
			gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

			// draw control	
			for (int i = 0; i < 4; ++i) {
				gc.fillRectangle(realPoints[i].x-contSize, realPoints[i].y-contSize, contSize*2, contSize*2);
			}
		}
	}
	
	public boolean contains(int x, int y) {
		
		if (grouped==true) return false;

		if (selectMode == SelectMode.SELECTED) {
			
		}
		
		if (x < position.x || x > endPosition.x || y < position.y || y > endPosition.y) {
			return false;
		}
		
		System.out.println("Parallelogram : in");
		return true;
	}

	public void move(int x, int y) {
		super.move(x, y);
		setParallelType(type);
	}
	
	
	public Parallelogram clone() {
		Parallelogram para = new Parallelogram();
		para.setPosition(position);
		para.setWidth(width);
		para.setHeight(height);
		para.setControlPoint(controlPoint);
		para.setColor(color);
		para.setFillColor(fillColor);
		para.setFill(getFill());
		para.setDrawn(drawn);
		para.setLineStyle(lineStyle);
		para.setLineThickness(lineThickness);
		para.setParallelType(type);
		
		return para;
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
		
		TextPropertyDescriptor ptDiscriptor = new TextPropertyDescriptor("ParallelType", "Type");
		ptDiscriptor.setCategory("Parallelogram");
		TextPropertyDescriptor pcpDiscriptor = new TextPropertyDescriptor("ControlPoint", "Control Point");
		pcpDiscriptor.setCategory("Parallelogram");
		
		return new IPropertyDescriptor[] {
				nameDiscriptor, posxDiscriptor, posyDiscriptor, sizewDiscriptor, sizehDiscriptor,
				lcDiscriptor, ltDiscriptor, lsDiscriptor, 
				pcDiscriptor, pfDiscriptor, ptDiscriptor, pcpDiscriptor
		};
	}
	

	public Object getPropertyValue(Object id) {
		if ("ControlPoint".equals(id)) return Integer.toString(controlPoint);
		else if ("ParallelType".equals(id)) return type.toString();
		else return super.getPropertyValue(id);
	}

	public void setPropertyValue(Object id, Object value) {
		if ("ControlPoint".equals(id)) setControlPoint(Integer.parseInt((String) value));
		else if ("ParallelType".equals(id)) {
			switch((String) value) {
			case "Left" :
				type = ParallelType.LEFT;
				break;
			case "Right" :
				type = ParallelType.RIGHT;
				break;
			case "Top" :
				type = ParallelType.TOP;
				break;
			case "Bottom" :
				type = ParallelType.BOTTOM;
				break;
			}
		}
		else super.setPropertyValue(id, value);
		
		setParallelType(type);
	}
}
