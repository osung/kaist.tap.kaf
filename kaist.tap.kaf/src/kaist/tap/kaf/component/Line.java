package kaist.tap.kaf.component;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;
import org.jdom2.Element;

import kaist.tap.kaf.component.Component;

public class Line extends Component {

	boolean connected;
	Component startComponent;
	Component endComponent;

	public Line() {
		connected = false;
		this.setName("Line");
		position.x = position.y = endPosition.x = endPosition.y = 0;
		startComponent = endComponent = null;
	}

	public Line(int x1, int y1, int x2, int y2) {
		setName("Line");
		connected = false;
		position.x = x1;
		position.y = y1;
		endPosition.x = x2;
		endPosition.y = y2;
	}
	
	public Line(Element el) {
		setName("Line");
		portable = false;
		
		Element posel = el.getChild("POSITION");
		position.x = Integer.parseInt(posel.getChildText("X"));
		position.y = Integer.parseInt(posel.getChildText("Y"));
		
		Element eposel = el.getChild("ENDPOSITION");
		endPosition.x = Integer.parseInt(eposel.getChildText("X"));
		endPosition.y = Integer.parseInt(eposel.getChildText("Y"));
		
		//line
		Element lc = el.getChild("LINECOLOR");
		setColor(new RGB(Integer.parseInt(lc.getChildText("R")), 
				         Integer.parseInt(lc.getChildText("G")),
				         Integer.parseInt(lc.getChildText("B"))));
		setLineStyle(Integer.parseInt(el.getChildText("LINESTYLE")));
		setLineThickness(Integer.parseInt(el.getChildText("LINETHICKNESS")));
	}

	public boolean isConnected() {
		return connected;
	}

	public Point getOppositePosition(Component comp) {
		if (comp == startComponent)
			return endPosition;
		else if (comp == endComponent)
			return position;
		else
			return null;
	}

	public Component getStartComponent() {
		return startComponent;
	}

	public Component getEndComponent() {
		return endComponent;
	}

	public boolean setStartComponent(Component c) {
		if (endComponent == c) return false;  // prevent self connection
		startComponent = c;
		connected = true;
		
		return true;
	}

	public boolean  setEndComponent(Component c) {
		if (startComponent == c) return false; // prevent self connection;
		endComponent = c;
		connected = true;
		
		return true;
	}

	public void removeStartComponent() {
		if (startComponent == null) return;
		
		startComponent.removeConnection(this);
		startComponent = null;
		if (endComponent == null)
			connected = false;
	}

	public void removeEndComponent() {
		if (endComponent == null) return;
		
		endComponent.removeConnection(this);
		endComponent = null;
		if (startComponent == null)
			connected = false;
	}

	public void setStartPosition(Point p) {
		position = p;
	}

	public void setStartPosition(int x, int y) {
		position.x = x;
		position.y = y;
	}

	public void setEndPosition(int x, int y) {
		endPosition.x = x;
		endPosition.y = y;
	}

	public Point getMidPosition() {
		Point mid = new Point(0, 0);

		mid.x = (int) ((position.x + endPosition.x) * 0.5);
		mid.y = (int) ((position.y + endPosition.y) * 0.5);

		return mid;
	}

	public void draw(GC gc) {
		gc.setForeground(getColor());
		gc.setLineWidth(lineThickness);
		gc.setLineStyle(lineStyle);
		gc.drawLine(position.x, position.y, endPosition.x, endPosition.y);

		if (selectMode == SelectMode.SELECTED) {
			gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

			// draw control
			gc.fillRectangle(position.x - contSize, position.y - contSize,
					contSize * 2, contSize * 2);
			gc.fillRectangle(endPosition.x - contSize,
					endPosition.y - contSize, contSize * 2, contSize * 2);
		}
	}

	// return the selected point between satart point and end point
	public Selection containSelection(int x, int y) {
		if (Math.abs(x - position.x) < contSize
				&& Math.abs(y - position.y) < contSize) {
			// selection = Selection.START;
			return Selection.START;
		} else if (Math.abs(x - endPosition.x) < contSize
				&& Math.abs(y - endPosition.y) < contSize) {
			// selection = Selection.END;
			return Selection.END;
		} else {
			// selection = Selection.FALSE;
			return Selection.FALSE;
		}
	}

	public boolean contains(int x, int y) {
		double xt, yt;

		if (grouped == true)
			return false;

		selection = containSelection(x, y);

		if (this.isSelected() == true) {
			if (selection != Selection.FALSE)
				return true;
		}
		
		if (Math.abs(endPosition.x-position.x) <= 2) {
		   if (Math.abs((double) x - position.x) <= 2) {
			   double min = Math.min(position.y, endPosition.y);
			   double max = Math.max(position.y, endPosition.y);
			   if (y >= min && y <= max) return true;
		   }
		   
		   return false;
		}
		else if (Math.abs(endPosition.y-position.y) <= 2) {
			   if (Math.abs((double) y - position.y) <= 2) {
				   double min = Math.min(position.x, endPosition.x);
				   double max = Math.max(position.x, endPosition.x);
				   if (x >= min && x <= max) return true;
			   }
			   
			   return false;
		}
		else {
		
			xt = ((double) x - position.x) / ((double) endPosition.x - position.x);
			yt = ((double) y - position.y) / ((double) endPosition.y - position.y);

			if (xt < -0.05 || xt > 1.05 || yt < -0.05 || yt > 1.05)
				return false;
			if (Math.abs(xt - yt) > 1.0)
				return false;
		}
		
		return true;
	}

	public void resize(int x, int y) {
		if (selection == Selection.FALSE)
			return;

		if (selection == Selection.START) {
			if (startComponent != null) {
				startComponent.removeConnection(this);
				startComponent = null;
			}
			position.x = x;
			position.y = y;
		} else {
			if (endComponent != null) {
				endComponent.removeConnection(this);
				endComponent = null;
			}
			endPosition.x = x;
			endPosition.y = y;
		}

		if (startComponent == null && endComponent == null)
			connected = false;
	}

	public void move(int x, int y) {
		if (x < 2 && y < 2)
			return;
		
		position.x += x;
		position.y += y;

		if (startComponent != null) {
			startComponent.removeConnection(this);
			startComponent = null;
			removeStartComponent();
		}

		endPosition.x += x;
		endPosition.y += y;

		if (endComponent != null) {
			endComponent.removeConnection(this);
			endComponent = null;
			removeEndComponent();
		}

		connected = false;
	}

	public Line clone() {
		Line line = new Line(position.x, position.y, endPosition.x,
				endPosition.y);
		line.setColor(color);
		line.setDrawn(drawn);
		line.setLineThickness(lineThickness);
		line.setLineStyle(lineStyle);

		return line;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		TextPropertyDescriptor nameDiscriptor = new TextPropertyDescriptor(
				"Name", "Type");
		TextPropertyDescriptor posxDiscriptor = new TextPropertyDescriptor(
				"Position_X", "X");
		posxDiscriptor.setCategory("Start Position");
		TextPropertyDescriptor posyDiscriptor = new TextPropertyDescriptor(
				"Position_Y", "Y");
		posyDiscriptor.setCategory("Start Position");
		TextPropertyDescriptor posexDiscriptor = new TextPropertyDescriptor(
				"EndPosition_X", "X");
		posexDiscriptor.setCategory("End Position");
		TextPropertyDescriptor poseyDiscriptor = new TextPropertyDescriptor(
				"EndPosition_Y", "Y");
		poseyDiscriptor.setCategory("End Position");

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

		return new IPropertyDescriptor[] { nameDiscriptor, posxDiscriptor,
				posyDiscriptor, posexDiscriptor, poseyDiscriptor, lcDiscriptor,
				ltDiscriptor, lsDiscriptor };
	}

	@Override
	public Object getPropertyValue(Object id) {
		// TODO Auto-generated method stub
		if ("EndPosition_X".equals(id))
			return Integer.toString(endPosition.x);
		else if ("EndPosition_Y".equals(id))
			return Integer.toString(endPosition.y);
		else
			return super.getPropertyValue(id);
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if ("EndPosition_X".equals(id))
			endPosition.x = Integer.parseInt((String) value);
		else if ("EndPosition_Y".equals(id))
			endPosition.y = Integer.parseInt((String) value);
		else
			super.setPropertyValue(id, value);
	}

	public Point[] getBounds() {
		Point[] bounds = new Point[2];

		bounds[0] = new Point(0, 0);
		bounds[1] = new Point(0, 0);

		bounds[0].x = Math.min(position.x, endPosition.x);
		bounds[1].x = Math.max(position.x, endPosition.x);
		bounds[0].y = Math.min(position.y, endPosition.y);
		bounds[1].y = Math.max(position.y, endPosition.y);

		return bounds;
	}

	@Override
	public void movePort(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Element getXMLElement(int id) {
		Element el = new Element("LINE");
		el.setAttribute("id", Integer.toString(id));
		
		el.addContent(getPositionXMLElement());
		
		Element ep = new Element("ENDPOSITION");
		Element epx = new Element("X");
		Element epy = new Element("Y");
		epx.setText(Integer.toString(endPosition.x));
		epy.setText(Integer.toString(endPosition.y));
		ep.addContent(epx);
		ep.addContent(epy);
		el.addContent(ep);
		
		el.addContent(getLineColorXMLElement());
		el.addContent(getLineStyleXMLElement());
		el.addContent(getLineThicknessXMLElement());
		
		if (connected == true) {
			Element conn = new Element("CONNECTION");
			if (startComponent != null) {
				Element sc = new Element("START");
				sc.setText(Integer.toString(startComponent.getId()));
				conn.addContent(sc);
			}
			if (endComponent != null) {
				Element ec = new Element("END");
				ec.setText(Integer.toString(endComponent.getId()));
				conn.addContent(ec);
			}
			el.addContent(conn);
		}

		return el;
	}

}
