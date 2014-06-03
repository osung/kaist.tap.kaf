package kaist.tap.kaf.component;

import java.awt.Polygon;
import java.awt.geom.AffineTransform;

import kaist.tap.kaf.component.Component.SelectMode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;

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
		
		mPosition.x = x;
		mPosition.y = y;
		mEndPosition.x = x2;
		mEndPosition.y = y2;
	}
	
	public void setArrowHeadSize(int index, int size) {
		if (index > 1) return;
		
		arrowHead[index].size = size;
	}
	
	public void setArrowHeadType(int index, HeadType type) {
		if (index > 1) return;
		
		arrowHead[index].type = type;
	}
	
	public int getArrowHeadSize(int index) {
		if (index > 1) return 0;
		
		return arrowHead[index].size;
	}
	
	
	public HeadType getArrowHeadType(int index) {
		if (index > 1) return null;
		
		return arrowHead[index].type;
	}
	
	public String headTypeToString(HeadType type) {
		switch (type) {
		case LINEARROW :
				return "Line Arrow";
		case TRIANGLE :
				return "Triangle";
		case NONE :
				return "None";
		}
		return "N/A";
	}
	
	
	public HeadType stringToHeadType(String type) {
		switch (type) {
			case "Line Arrow" : return HeadType.LINEARROW;
			case "Triangle" : return HeadType.TRIANGLE;
			case "None" : 
			default :
				return HeadType.NONE;
		}
	}
	
	
	
	public void drawArrowHead(GC gc, int x, int y, float angle) {
		Transform tx = new Transform(gc.getDevice());
		
		int arrowHead[] = new int[] {0, 5, -5, -5, 5, -5};
				
		tx.identity();
		//tx.rotate(angle-(float) Math.PI/2f);
		
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
		gc.setLineWidth(mLineThickness);
		gc.setLineStyle(mLineStyle);
		gc.drawLine(mPosition.x, mPosition.y, mEndPosition.x, mEndPosition.y);
		
		float a = mEndPosition.x - mPosition.x;
		float b = mEndPosition.y - mPosition.y;
		float angle = (float) Math.acos(b/(Math.sqrt(a*a+b*b)));
		angle = (float) Math.toDegrees(angle);
		
		drawArrowHead(gc, mPosition.x, mPosition.y, 180-angle);
		drawArrowHead(gc, mEndPosition.x, mEndPosition.y, -angle);
		
		if (mSelectMode == SelectMode.SELECTED) {
			gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			
			// draw control			
			gc.fillRectangle(mPosition.x-contSize, mPosition.y-contSize, contSize*2, contSize*2);
			gc.fillRectangle(mEndPosition.x-contSize, mEndPosition.y-contSize, contSize*2, contSize*2);
		}
	}
	
	public Arrow clone() {
		Arrow arrow = new Arrow(mPosition.x, mPosition.y, mEndPosition.x, mEndPosition.y);
		arrow.setColor(mColor);
		arrow.setDrawn(mDrawn);
		arrow.setLineThickness(mLineThickness);
		arrow.setLineStyle(mLineStyle);
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
		TextPropertyDescriptor nameDiscriptor = new TextPropertyDescriptor("Name", "Type");
		TextPropertyDescriptor posxDiscriptor = new TextPropertyDescriptor("Position_X", "X");
		posxDiscriptor.setCategory("Start Position");
		TextPropertyDescriptor posyDiscriptor = new TextPropertyDescriptor("Position_Y", "Y");
		posyDiscriptor.setCategory("Start Position");
		TextPropertyDescriptor posexDiscriptor = new TextPropertyDescriptor("EndPosition_X", "X");
		posexDiscriptor.setCategory("End Position");
		TextPropertyDescriptor poseyDiscriptor = new TextPropertyDescriptor("EndPosition_Y", "Y");
		poseyDiscriptor.setCategory("End Position");
		
		ColorPropertyDescriptor lcDiscriptor = new ColorPropertyDescriptor("Color", "Color");
		lcDiscriptor.setCategory("Line");
		TextPropertyDescriptor ltDiscriptor = new TextPropertyDescriptor("Line_Thickness", "Thickness");
		ltDiscriptor.setCategory("Line");
		
		String[] lsvalues = {"Solid", "Dot", "Dash", "Dashdot", "Dashdotdot"};
		ComboBoxPropertyDescriptor lsDiscriptor = new ComboBoxPropertyDescriptor("Line_Style", "Line Style", lsvalues);
		lsDiscriptor.setCategory("Line");
		
		return new IPropertyDescriptor[] {
				nameDiscriptor, posxDiscriptor, posyDiscriptor, posexDiscriptor, poseyDiscriptor,
				lcDiscriptor, ltDiscriptor, lsDiscriptor
		};
	}
	
	@Override
	public Object getPropertyValue(Object id) {
		// TODO Auto-generated method stub
		if ("EndPosition_X".equals(id)) return Integer.toString(mEndPosition.x);
		else if ("EndPosition_Y".equals(id)) return Integer.toString(mEndPosition.y);
		else return super.getPropertyValue(id);
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if ("EndPosition_X".equals(id)) mEndPosition.x = Integer.parseInt((String) value);
		else if ("EndPosition_Y".equals(id)) mEndPosition.y = Integer.parseInt((String) value);
		else super.setPropertyValue(id, value);
	}
}


