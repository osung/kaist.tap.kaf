package kaist.tap.kaf.component;

import java.util.List;

import kaist.tap.kaf.component.Component.SelectMode;
import kaist.tap.kaf.component.Rectangle.Edge;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;
import org.jdom2.Element;

public class Parallelogram extends Rectangle {
	public enum ParallelType {
		LEFT, RIGHT, TOP, BOTTOM;

		public String toString() {
			switch (this) {
			case LEFT:
				return "Left";
			case RIGHT:
				return "Right";
			case TOP:
				return "Top";
			case BOTTOM:
				return "Bottom";
			default:
				return "";
			}
		}
		
		static public ParallelType parse(String str) {
			if (str == null) return null;
			
			if (str.compareTo("Left") == 0) {
				return LEFT;
			}
			else if (str.compareTo("Right") == 0) {
				return RIGHT;
			}
			else if (str.compareTo("Top") == 0) {
				return TOP;
			}
			else if (str.compareTo("Bottom") == 0) {
				return BOTTOM;
			}
			else return null;
		}
	}

	protected int controlPoint;
	protected ParallelType type;
	protected Point[] realPoints;

	public Parallelogram() {
		setName("Parallelogram");
		realPoints = new Point[4];
		for (int i = 0; i < 4; ++i)
			realPoints[i] = new Point(0, 0);
		setParallelType(ParallelType.LEFT);
		setControlPoint(10);
		portable = true;
	}

	public Parallelogram(Element el) {
		setName("Parallelogram");
		portable = true;
		
		Element posel = el.getChild("POSITION");
		position.x = Integer.parseInt(posel.getChildText("X"));
		position.y = Integer.parseInt(posel.getChildText("Y"));
		
		//line
		Element lc = el.getChild("LINECOLOR");
		setColor(new RGB(Integer.parseInt(lc.getChildText("R")), 
				         Integer.parseInt(lc.getChildText("G")),
				         Integer.parseInt(lc.getChildText("B"))));
		setLineStyle(Integer.parseInt(el.getChildText("LINESTYLE")));
		setLineThickness(Integer.parseInt(el.getChildText("LINETHICKNESS")));
		
		//fill
		setFill(Boolean.parseBoolean(el.getChildText("FILL")));
		Element fc = el.getChild("FILLCOLOR");
		setFillColor(new RGB(Integer.parseInt(fc.getChildText("R")), 
				             Integer.parseInt(fc.getChildText("G")),
				             Integer.parseInt(fc.getChildText("B"))));
		
		realPoints = new Point[4];
		Element rp = el.getChild("REALPOINTS");
		if (rp != null) {
			List<Element> points = rp.getChildren("POINT");
		
			for (int i = 0; i < points.size(); ++i) {
				Element p = points.get(i);
				int id = Integer.parseInt(p.getAttributeValue("id"));
				int x = Integer.parseInt(p.getChildText("X"));
				int y = Integer.parseInt(p.getChildText("Y"));
				realPoints[id] = new Point(x, y);
			}
		}
		else {
			for (int i = 0; i < 4; ++i) {
				realPoints[i] = new Point(0,0);
			}
		}
		
		super.setWidth(Integer.parseInt(el.getChildText("WIDTH")));
		super.setHeight(Integer.parseInt(el.getChildText("HEIGHT")));
		
		type = ParallelType.parse(el.getChildText("PARALLELTYPE"));
		controlPoint = Integer.parseInt(el.getChildText("CONTROLPOINT"));
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

		realPoints[0].x = position.x;
		realPoints[0].y = position.y;
		realPoints[1].x = endPosition.x;
		realPoints[1].y = position.y;
		realPoints[2].x = endPosition.x;
		realPoints[2].y = endPosition.y;
		realPoints[3].x = position.x;
		realPoints[3].y = endPosition.y;

		switch (type) {
		case LEFT:
			realPoints[0].x = position.x + controlPoint;
			realPoints[2].x = endPosition.x - controlPoint;
			break;

		case RIGHT:
			realPoints[1].x = endPosition.x - controlPoint;
			realPoints[3].x = position.x + controlPoint;
			break;

		case TOP:
			realPoints[0].y = position.y + controlPoint;
			realPoints[2].y = endPosition.y - controlPoint;
			break;

		case BOTTOM:
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
		if ((type == ParallelType.LEFT || type == ParallelType.RIGHT)
				&& width < controlPoint) {
			controlPoint = width;
		}
		super.setWidth(width);
		setParallelType(type);
	}

	public void setHeight(int height) {
		if ((type == ParallelType.TOP || type == ParallelType.BOTTOM)
				&& height < controlPoint) {
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

		int polys[] = { realPoints[0].x, realPoints[0].y, realPoints[1].x,
				realPoints[1].y, realPoints[2].x, realPoints[2].y,
				realPoints[3].x, realPoints[3].y };
		gc.drawPolygon(polys);

		if (fill == true) {
			polys[0]++;
			polys[1]++;
			polys[2]--;
			polys[3]++;
			polys[4]--;
			polys[5]--;
			polys[6]++;
			polys[7]--;
			gc.fillPolygon(polys);
		}

		if (selectMode == SelectMode.SELECTED) {
			gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

			// draw control
			for (int i = 0; i < 4; ++i) {
				gc.fillRectangle(realPoints[i].x - contSize, realPoints[i].y
						- contSize, contSize * 2, contSize * 2);
			}
		}
	}

	public boolean contains(int x, int y) {

		if (grouped == true)
			return false;

		if (selectMode == SelectMode.SELECTED) {

		}

		if (x < position.x || x > endPosition.x || y < position.y
				|| y > endPosition.y) {
			return false;
		}

		return true;
	}

	public Point getConnectedPoint(Line line) {
		int idx = connections.indexOf(line);
		if (idx == -1)
			return null;

		Point opposite = line.getOppositePosition(this);

		int zone = 0;
		Edge edge = Edge.NONE;

		if (opposite.x <= position.x)
			zone = 1;
		else if (opposite.x >= endPosition.x)
			zone = 7;
		else
			zone = 4;

		if (opposite.y <= position.y)
			zone += 0;
		else if (opposite.y >= endPosition.y)
			zone += 2;
		else
			zone += 1;

		switch (zone) {
		case 1:
			if (position.x - opposite.x < position.y - opposite.y)
				edge = Edge.TOP;
			else
				edge = Edge.LEFT;
			break;
		case 2:
			edge = Edge.LEFT;
			break;
		case 3:
			if (position.x - opposite.x < opposite.y - position.y)
				edge = Edge.BOTTOM;
			else
				edge = Edge.LEFT;
			break;
		case 4:
			edge = Edge.TOP;
			break;
		case 5:
			edge = Edge.NONE;
			break;
		case 6:
			edge = Edge.BOTTOM;
			break;
		case 7:
			if (opposite.x - position.x < position.y - opposite.y)
				edge = Edge.TOP;
			else
				edge = Edge.RIGHT;
			break;
		case 8:
			edge = Edge.RIGHT;
			break;
		case 9:
			if (opposite.x - position.x < opposite.y - position.y)
				edge = Edge.BOTTOM;
			else
				edge = Edge.RIGHT;
			break;
		}

		switch (edge) {
		case LEFT:
			return new Point((int) ((realPoints[0].x + realPoints[3].x) * 0.5),
					(int) ((realPoints[0].y + realPoints[3].y) * 0.5));
		case RIGHT:
			return new Point((int) ((realPoints[1].x + realPoints[2].x) * 0.5),
					(int) ((realPoints[1].y + realPoints[2].y) * 0.5));
		case TOP:
			return new Point((int) ((realPoints[0].x + realPoints[1].x) * 0.5),
					position.y);
		case BOTTOM:
			return new Point((int) ((realPoints[3].x + realPoints[2].x) * 0.5),
					endPosition.y);
		case NONE:
		default:
			return null;
		}
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
		TextPropertyDescriptor nameDiscriptor = new TextPropertyDescriptor(
				"Name", "Type");
		TextPropertyDescriptor posxDiscriptor = new TextPropertyDescriptor(
				"Position_X", "X");
		posxDiscriptor.setCategory("Position");
		TextPropertyDescriptor posyDiscriptor = new TextPropertyDescriptor(
				"Position_Y", "Y");
		posyDiscriptor.setCategory("Position");
		TextPropertyDescriptor sizewDiscriptor = new TextPropertyDescriptor(
				"Width", "Width");
		sizewDiscriptor.setCategory("Size");
		TextPropertyDescriptor sizehDiscriptor = new TextPropertyDescriptor(
				"Height", "Height");
		sizehDiscriptor.setCategory("Size");

		ColorPropertyDescriptor lcDiscriptor = new ColorPropertyDescriptor(
				"Color", "Color");
		lcDiscriptor.setCategory("Line");
		TextPropertyDescriptor ltDiscriptor = new TextPropertyDescriptor(
				"Line_Thickness", "Thickness");
		ltDiscriptor.setCategory("Line");
		String[] lsvalues = { "Solid", "Dot", "Dash", "Dashdot", "Dashdotdot" };
		ComboBoxPropertyDescriptor lsDiscriptor = new ComboBoxPropertyDescriptor(
				"Line_Style", "Line Style", lsvalues);
		lsDiscriptor.setCategory("Line");
		ColorPropertyDescriptor pcDiscriptor = new ColorPropertyDescriptor(
				"FillColor", "Color");
		pcDiscriptor.setCategory("Polygon");
		String[] fillvalues = { "On", "Off" };
		ComboBoxPropertyDescriptor pfDiscriptor = new ComboBoxPropertyDescriptor(
				"Fill", "Fill", fillvalues);
		pfDiscriptor.setCategory("Polygon");

		TextPropertyDescriptor ptDiscriptor = new TextPropertyDescriptor(
				"ParallelType", "Type");
		ptDiscriptor.setCategory("Parallelogram");
		TextPropertyDescriptor pcpDiscriptor = new TextPropertyDescriptor(
				"ControlPoint", "Control Point");
		pcpDiscriptor.setCategory("Parallelogram");

		return new IPropertyDescriptor[] { nameDiscriptor, posxDiscriptor,
				posyDiscriptor, sizewDiscriptor, sizehDiscriptor, lcDiscriptor,
				ltDiscriptor, lsDiscriptor, pcDiscriptor, pfDiscriptor,
				ptDiscriptor, pcpDiscriptor };
	}

	public Object getPropertyValue(Object id) {
		if ("ControlPoint".equals(id))
			return Integer.toString(controlPoint);
		else if ("ParallelType".equals(id))
			return type.toString();
		else
			return super.getPropertyValue(id);
	}

	public void setPropertyValue(Object id, Object value) {
		if ("ControlPoint".equals(id))
			setControlPoint(Integer.parseInt((String) value));
		else if ("ParallelType".equals(id)) {
			switch ((String) value) {
			case "Left":
				type = ParallelType.LEFT;
				break;
			case "Right":
				type = ParallelType.RIGHT;
				break;
			case "Top":
				type = ParallelType.TOP;
				break;
			case "Bottom":
				type = ParallelType.BOTTOM;
				break;
			}
		} else
			super.setPropertyValue(id, value);

		setParallelType(type);
	}

	@Override
	public Element getXMLElement(int id) {
		Element el = new Element("PARALLELOGRAM");
		el.setAttribute("id", Integer.toString(id));
		
		el.addContent(getPositionXMLElement());
		
		Element w = new Element("WIDTH");
		w.setText(Integer.toString(width));
		el.addContent(w);
		
		Element h = new Element("HEIGHT");
		h.setText(Integer.toString(height));
		el.addContent(h);
		
		Element cp = new Element("CONTROLPOINT");
		cp.setText(Integer.toString(controlPoint));
		el.addContent(cp);
		
		Element ct = new Element("PARALLELTYPE");
		ct.setText(type.toString());
		el.addContent(ct);
		
		Element rp = new Element("REALPOINTS");
		for (int i = 0; i < 4; ++i) {
			Element rpp = new Element("POINT");
			rpp.setAttribute("id", Integer.toString(i));
			Element rppx = new Element("X");
			Element rppy = new Element("Y");
			rppx.setText(Integer.toString(realPoints[i].x));
			rppy.setText(Integer.toString(realPoints[i].y));
			rpp.addContent(rppx);
			rpp.addContent(rppy);
			rp.addContent(rpp);
		}
		el.addContent(rp);
		
		el.addContent(getLineColorXMLElement());
		el.addContent(getLineStyleXMLElement());
		el.addContent(getLineThicknessXMLElement());
		
		el.addContent(getFillXMLElement());		
		el.addContent(getFillColorXMLElement());
		
		Element conn = getConnectionXMLElement();
		if (conn != null) el.addContent(conn);
		
		return el;
	}
}
