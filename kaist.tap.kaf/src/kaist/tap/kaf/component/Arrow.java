package kaist.tap.kaf.component;

import java.awt.Polygon;
import java.awt.geom.AffineTransform;

import kaist.tap.kaf.component.Component.SelectMode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;
import org.jdom2.Element;

public class Arrow extends Line {

	public enum HeadType {
		LINEARROW, TRIANGLE, NONE
	}

	public class Head {
		public HeadType type;
		public int size;

		public Head(HeadType t, int s) {
			type = t;
			size = s;
		}
	}

	protected Head[] arrowHead;

	public Arrow() {
		setName("Arrow");
		arrowHead = new Head[2];
		arrowHead[0] = new Head(HeadType.NONE, 1);
		arrowHead[1] = new Head(HeadType.LINEARROW, 1);
	}

	public Arrow(int x, int y, int x2, int y2) {
		setName("Arrow");
		arrowHead = new Head[2];
		arrowHead[0] = new Head(HeadType.NONE, 1);
		arrowHead[1] = new Head(HeadType.LINEARROW, 1);

		position.x = x;
		position.y = y;
		endPosition.x = x2;
		endPosition.y = y2;
	}

	public Arrow(Element el) {
		super(el);
		setName("Arrow");
		portable = false;
	}

	
	
	public void setArrowHeadSize(int index, int size) {
		if (index > 1)
			return;

		arrowHead[index].size = size;
	}

	public void setArrowHeadType(int index, HeadType type) {
		if (index > 1)
			return;

		arrowHead[index].type = type;
	}

	public int getArrowHeadSize(int index) {
		if (index > 1)
			return 0;

		return arrowHead[index].size;
	}

	public HeadType getArrowHeadType(int index) {
		if (index > 1)
			return null;

		return arrowHead[index].type;
	}

	public String headTypeToString(HeadType type) {
		switch (type) {
		case LINEARROW:
			return "Line Arrow";
		case TRIANGLE:
			return "Triangle";
		case NONE:
			return "None";
		}
		return "N/A";
	}

	public HeadType stringToHeadType(String type) {
		switch (type) {
		case "Line Arrow":
			return HeadType.LINEARROW;
		case "Triangle":
			return HeadType.TRIANGLE;
		case "None":
		default:
			return HeadType.NONE;
		}
	}

	public void drawArrowHead(GC gc, int x, int y, float angle) {
		Transform tx = new Transform(gc.getDevice());

		int arrowHead[] = new int[] { 0, 5, -5, -5, 5, -5 };

		tx.identity();
		// tx.rotate(angle-(float) Math.PI/2f);

		tx.translate(x, y);
		tx.rotate(angle);

		gc.setTransform(tx);
		gc.fillPolygon(arrowHead);

		tx.identity();
		gc.setTransform(tx);
		tx.dispose();
	}

	public void draw(GC gc) {
		gc.setForeground(getColor());
		gc.setBackground(getColor());
		gc.setLineWidth(lineThickness);
		gc.setLineStyle(lineStyle);
		gc.drawLine(position.x, position.y, endPosition.x, endPosition.y);

		float a = endPosition.x - position.x;
		float b = endPosition.y - position.y;
		float angle = (float) Math.acos(b / (Math.sqrt(a * a + b * b)));
		angle = (float) Math.toDegrees(angle);

		drawArrowHead(gc, position.x, position.y, 180 - angle);
		drawArrowHead(gc, endPosition.x, endPosition.y, -angle);

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

	public Arrow clone() {
		Arrow arrow = new Arrow(position.x, position.y, endPosition.x,
				endPosition.y);
		arrow.setColor(color);
		arrow.setDrawn(drawn);
		arrow.setLineThickness(lineThickness);
		arrow.setLineStyle(lineStyle);
		arrow.setArrowHeadSize(0, getArrowHeadSize(0));
		arrow.setArrowHeadSize(1, getArrowHeadSize(1));
		arrow.setArrowHeadType(0, getArrowHeadType(0));
		arrow.setArrowHeadType(1, getArrowHeadType(1));

		return arrow;
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

	@Override
	public Element getXMLElement(int id) {
		Element el = new Element("ARROW");
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
