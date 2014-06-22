package kaist.tap.kaf.component;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;
import org.jdom.Element;

public class Rectangle extends Component {

	enum Edge {
		NONE, LEFT, RIGHT, TOP, BOTTOM
	}

	protected int width;
	protected int height;

	public Rectangle() {
		this.setName("Rectangle");
		position.x = position.y = endPosition.x = endPosition.y = width = height = 0;
		portable = true;
		canvas = null;
	}

	public Rectangle(Canvas c) {
		this.setName("Rectangle");
		position.x = position.y = endPosition.x = endPosition.y = width = height = 0;
		portable = true;
		canvas = c;
	}

	public Rectangle(int x, int y, int w, int h) {
		setName("Rectangle");
		position.x = x;
		position.y = y;
		width = w;
		height = h;
		endPosition.x = x + w;
		endPosition.y = y + h;
		portable = true;
	}

	public Rectangle(Element el) {
		portable = true;
		setName("Rectangle");

		Element posel = el.getChild("POSITION");
		position.x = Integer.parseInt(posel.getChildText("X"));
		position.y = Integer.parseInt(posel.getChildText("Y"));

		// line
		Element lc = el.getChild("LINECOLOR");
		setColor(new RGB(Integer.parseInt(lc.getChildText("R")),
				Integer.parseInt(lc.getChildText("G")), Integer.parseInt(lc
						.getChildText("B"))));
		setLineStyle(Integer.parseInt(el.getChildText("LINESTYLE")));
		setLineThickness(Integer.parseInt(el.getChildText("LINETHICKNESS")));

		// fill
		setFill(Boolean.parseBoolean(el.getChildText("FILL")));
		Element fc = el.getChild("FILLCOLOR");
		setFillColor(new RGB(Integer.parseInt(fc.getChildText("R")),
				Integer.parseInt(fc.getChildText("G")), Integer.parseInt(fc
						.getChildText("B"))));

		setWidth(Integer.parseInt(el.getChildText("WIDTH")));
		setHeight(Integer.parseInt(el.getChildText("HEIGHT")));
	}

	@Override
	public void setPosition(Point p) {
		super.setPosition(p);

		width = Math.abs(endPosition.x - position.x);
		height = Math.abs(endPosition.y - position.y);
	}

	@Override
	public void setEndPosition(Point p) {
		endPosition.x = p.x;
		endPosition.y = p.y;

		width = Math.abs(endPosition.x - position.x);
		height = Math.abs(endPosition.y - position.y);
	}

	public void updatePosition(int x, int y) {
		if (endPosition.x < x) {
			position.x = endPosition.x;
			endPosition.x = x;
		} else
			position.x = x;

		if (endPosition.y < y) {
			position.y = endPosition.y;
			endPosition.y = y;
		} else
			position.y = y;

		width = Math.abs(endPosition.x - position.x);
		height = Math.abs(endPosition.y - position.y);
	}

	public void updatePosition(Point p) {
		if (endPosition.x < p.x) {
			position.x = endPosition.x;
			endPosition.x = p.x;
		} else
			position.x = p.x;

		if (endPosition.y < p.y) {
			position.y = endPosition.y;
			endPosition.y = p.y;
		} else
			position.y = p.y;

		width = Math.abs(endPosition.x - position.x);
		height = Math.abs(endPosition.y - position.y);
	}

	public void updateEndPosition(int x, int y) {
		if (position.x > x) {
			endPosition.x = position.x;
			position.x = x;
		} else
			endPosition.x = x;

		if (position.y > y) {
			endPosition.y = position.y;
			position.y = y;
		} else
			endPosition.y = y;

		width = Math.abs(endPosition.x - position.x);
		height = Math.abs(endPosition.y - position.y);
	}

	public void updateEndPosition(Point p) {
		if (position.x > p.x) {
			endPosition.x = position.x;
			position.x = p.x;
		} else
			endPosition.x = p.x;

		if (position.y > p.y) {
			endPosition.y = position.y;
			position.y = p.y;
		} else
			endPosition.y = p.y;

		width = Math.abs(endPosition.x - position.x);
		height = Math.abs(endPosition.y - position.y);
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

	@Override
	public void draw(GC gc) {
		gc.setForeground(getColor());
		gc.setBackground(getFillColor());
		gc.setLineWidth(lineThickness);
		gc.setLineStyle(lineStyle);
		gc.drawRectangle(position.x, position.y, width, height);
		if (fill == true)
			gc.fillRectangle(position.x + 1, position.y + 1, width - 1,
					height - 1);

		// draw ports
		for (int i = 0; i < ports.size(); ++i) {
			Port port = ports.get(i);
			port.draw(gc);
		}

		if (selport != null)
			return;

		if (selectMode == SelectMode.SELECTED) {
			gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			// draw control
			gc.fillRectangle(position.x - contSize, position.y - contSize,
					contSize * 2, contSize * 2);
			gc.fillRectangle(endPosition.x - contSize,
					endPosition.y - contSize, contSize * 2, contSize * 2);
			gc.fillRectangle(position.x - contSize, endPosition.y - contSize,
					contSize * 2, contSize * 2);
			gc.fillRectangle(endPosition.x - contSize, position.y - contSize,
					contSize * 2, contSize * 2);
		}
	}

	@Override
	public Selection containSelection(int x, int y) {
		selection = Selection.FALSE;

		if (Math.abs(x - position.x) < contSize) {
			if (Math.abs(y - position.y) < contSize) {
				selection = Selection.UL;
				return Selection.UL;
			} else if (Math.abs(y - endPosition.y) < contSize) {
				selection = Selection.LL;
				return Selection.LL;
			} else
				return Selection.FALSE;
		} else if (Math.abs(x - endPosition.x) < contSize) {
			if (Math.abs(y - position.y) < contSize) {
				selection = Selection.UR;
				return Selection.UR;
			} else if (Math.abs(y - endPosition.y) < contSize) {
				selection = Selection.LR;
				return Selection.LR;
			} else
				return Selection.FALSE;
		} else
			return Selection.FALSE;
	}

	public Point getConnectedPointFromPorts(Line line) {
		Port minport = null;
		float mindist = Float.MAX_VALUE;
		Point opp = line.getOppositePosition(this);

		for (int i = 0; i < ports.size(); ++i) {
			Port port = ports.get(i);
			Point p = port.getPosition();
			float dist = (opp.x - p.x) * (opp.x - p.x) + (opp.y - p.y)
					* (opp.y - p.y);
			if (mindist > dist) {
				mindist = dist;
				minport = port;
			}
		}

		if (minport == null)
			return null;

		minport.addConnection(line);
		Point p = minport.getPosition();
		return new Point(p.x, p.y);
	}

	public Point getConnectedPointByMidPoint(Line line) {
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
			return new Point(position.x,
					(int) ((position.y + endPosition.y) * 0.5));
		case RIGHT:
			return new Point(endPosition.x,
					(int) ((position.y + endPosition.y) * 0.5));
		case TOP:
			return new Point((int) ((position.x + endPosition.x) * 0.5),
					position.y);
		case BOTTOM:
			return new Point((int) ((position.x + endPosition.x) * 0.5),
					endPosition.y);
		case NONE:
		default:
			return null;
		}
	}

	public Point getConnectedPoint(Line line) {
		int idx = connections.indexOf(line);
		if (idx == -1)
			return null;
		if (portable == true && ports.size() > 0) {
			return getConnectedPointFromPorts(line);
		} else
			return getConnectedPointByMidPoint(line);
	}

	@Override
	public boolean contains(int x, int y) {
		if (grouped == true)
			return false;

		if (portable == true) {
			selport = containPort(x, y);
			if (selport != null)
				return true;
		}

		selport = null;
		selection = containSelection(x, y);

		if (this.isSelected() == true) {
			if (selection != Selection.FALSE)
				return true;
		}

		if (x < position.x || x > endPosition.x || y < position.y
				|| y > endPosition.y) {
			return false;
		}

		return true;
	}

	@Override
	public void move(int x, int y) {
		if (selport != null) {
			movePort(x, y);
			return;
		}

		position.x += x;
		position.y += y;
		endPosition.x += x;
		endPosition.y += y;

		for (int i = 0; i < connections.size(); ++i) {
			Line line = connections.get(i);
			if (line.startComponent == this) {
				Point p = line.getPosition();
				line.setPosition(p.x + x, p.y + y);
			} else {
				Point p = line.getEndPosition();
				line.setEndPosition(p.x + x, p.y + y);
			}
		}

		for (int i = 0; i < ports.size(); ++i) {
			Port port = ports.get(i);
			port.move(x, y);
		}
	}

	public void updatePorts(int x, int y, Selection sel) {
		if (sel == Selection.UL) {
			for (int i = 0; i < ports.size(); ++i) {
				Port port = ports.get(i);
				Point p = port.getPosition();
				if (p.y < position.y)
					p.y = position.y;
				if (p.x < position.x)
					p.x = position.x;

				port.updateConnection();
			}
		} else if (sel == Selection.LR) {
			for (int i = 0; i < ports.size(); ++i) {
				Port port = ports.get(i);
				Point p = port.getPosition();
				if (p.y > endPosition.y)
					p.y = endPosition.y;
				if (p.x > endPosition.x)
					p.x = endPosition.x;

				port.updateConnection();
			}
		} else if (sel == Selection.LL) {
			for (int i = 0; i < ports.size(); ++i) {
				Port port = ports.get(i);
				Point p = port.getPosition();
				if (p.y > endPosition.y)
					p.y = endPosition.y;
				if (p.x < position.x)
					p.x = position.x;

				port.updateConnection();
			}
		} else if (sel == Selection.UR) {
			for (int i = 0; i < ports.size(); ++i) {
				Port port = ports.get(i);
				Point p = port.getPosition();
				if (p.y < position.y)
					p.y = position.y;
				if (p.x > endPosition.x)
					p.x = endPosition.x;

				port.updateConnection();
			}
		}
	}

	@Override
	public void resize(int x, int y) {
		if (portable == true) {
			updatePorts(x, y, selection);
		}
		if (selection == Selection.UL) {
			this.updatePosition(x, y);
		} else if (selection == Selection.LR) {
			this.updateEndPosition(x, y);
		} else if (selection == Selection.LL) {
			this.updatePosition(x, position.y);
			this.updateEndPosition(endPosition.x, y);
		} else if (selection == Selection.UR) {
			this.updatePosition(position.x, y);
			this.updateEndPosition(x, endPosition.y);
		}
	}

	@Override
	public Rectangle clone() {
		Rectangle rect = new Rectangle(position.x, position.y, width, height);
		rect.setCanvas(canvas);
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

	@Override
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

		return new IPropertyDescriptor[] { nameDiscriptor, posxDiscriptor,
				posyDiscriptor, sizewDiscriptor, sizehDiscriptor, lcDiscriptor,
				ltDiscriptor, lsDiscriptor, pcDiscriptor, pfDiscriptor };
	}

	@Override
	public Object getPropertyValue(Object id) {
		if ("Width".equals(id))
			return Integer.toString(width);
		else if ("Height".equals(id))
			return Integer.toString(height);
		else
			return super.getPropertyValue(id);
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if (canvas != null) {
			canvas.redraw();
		}

		if ("Width".equals(id)) {
			width = Integer.parseInt((String) value);
			endPosition.x = position.x + width;
		} else if ("Height".equals(id)) {
			height = Integer.parseInt((String) value);
			endPosition.y = position.y + height;
		} else
			super.setPropertyValue(id, value);
	}

	@Override
	public Point[] getBounds() {
		Point[] bounds = new Point[2];

		bounds[0] = new Point(0, 0);
		bounds[1] = new Point(0, 0);

		bounds[0].x = position.x;
		bounds[1].x = endPosition.x;
		bounds[0].y = position.y;
		bounds[1].y = endPosition.y;

		return bounds;
	}

	@Override
	public void addPort() {
		addPort(position.x, position.y + (int) (height * 0.5));
	}

	@Override
	public void movePort(int x, int y) {
		int dx, dy;
		if (selport == null)
			return;

		dx = x;
		dy = y;
		Point pos = selport.getPosition();
		if (pos.x == position.x || pos.x == endPosition.x)
			dx = 0;
		if (pos.y == position.y || pos.y == endPosition.y)
			dy = 0;

		if ((pos.x == position.x || pos.x == endPosition.x)
				&& (pos.y == position.y || pos.y == endPosition.y)) {
			if (Math.abs(x) > Math.abs(y))
				dx = x;
			else
				dy = y;
		}

		Point newpos = new Point(pos.x, pos.y);

		newpos.x += dx;
		newpos.y += dy;

		if (newpos.x < position.x)
			newpos.x = position.x;
		if (newpos.x > endPosition.x)
			newpos.x = endPosition.x;
		if (newpos.y < position.y)
			newpos.y = position.y;
		if (newpos.y > endPosition.y)
			newpos.y = endPosition.y;

		selport.move(newpos.x - pos.x, newpos.y - pos.y);
	}

	@Override
	public Element getXMLElement(int id) {
		Element el = new Element("RECT");
		el.setAttribute("id", Integer.toString(id));

		el.addContent(getPositionXMLElement());

		Element w = new Element("WIDTH");
		w.setText(Integer.toString(width));
		el.addContent(w);

		Element h = new Element("HEIGHT");
		h.setText(Integer.toString(height));
		el.addContent(h);

		el.addContent(getLineColorXMLElement());
		el.addContent(getLineStyleXMLElement());
		el.addContent(getLineThicknessXMLElement());

		el.addContent(getFillXMLElement());
		el.addContent(getFillColorXMLElement());

		Element conn = getConnectionXMLElement();
		if (conn != null)
			el.addContent(conn);

		if (ports.size() > 0)
			el.addContent(getPortXMLElement());

		return el;
	}
}
