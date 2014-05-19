package kaist.tap.kaf.component;

import java.awt.geom.AffineTransform;

import kaist.tap.kaf.component.Component.SelectMode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
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
	}
	
	protected Head[] arrowHead;
	
	public Arrow() {
		setName("Arrow");
		arrowHead = new Head[2];
		arrowHead[0].type = HeadType.NONE;
		arrowHead[0].size = 1;
		arrowHead[1].type = HeadType.LINEARROW;
		arrowHead[1].size = 1;
	}
	
	
	public Arrow(int x, int y, int x2, int y2) {
		setName("Arrow");
		arrowHead = new Head[2];
		arrowHead[0].type = HeadType.NONE;
		arrowHead[0].size = 1;
		arrowHead[1].type = HeadType.LINEARROW;
		arrowHead[1].size = 1;
		
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
	
	
	public void drawArrowHead(GC gc, double theta, int x, int y) {
		
	}
	
	
	public void draw(GC gc) {
		gc.setForeground(getColor());
		gc.setLineWidth(mLineThickness);
		gc.setLineStyle(mLineStyle);
		gc.drawLine(mPosition.x, mPosition.y, mEndPosition.x, mEndPosition.y);
		
		//drawArrowHead(gc);
		
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
	
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		// TODO Auto-generated method stub
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor("Name", "Name"),
				new TextPropertyDescriptor("Position_X", "Start Position X"), 
				new TextPropertyDescriptor("Position_Y", "Start Position Y"),
				new TextPropertyDescriptor("EndPosition_X", "End Position X"),
				new TextPropertyDescriptor("EndPosition_Y", "End Position Y"),
				new TextPropertyDescriptor("Color", "Color"), 
				new TextPropertyDescriptor("Line_Thickness", "Line Thickness"),
				new TextPropertyDescriptor("Line_Style", "Line Style"),
				new TextPropertyDescriptor("ArrowHeadType_Start", "Start Arrow Head Type"),
				new TextPropertyDescriptor("ArrowHeadSize_Start", "Start Arrow Head Size"),
				new TextPropertyDescriptor("ArrowHeadType_End", "End Arrow Head Type"),
				new TextPropertyDescriptor("ArrowHeadSize_End", "End Arrow Head Size")
		};
	}

	@Override
	public Object getPropertyValue(Object id) {
		// TODO Auto-generated method stub
		if ("ArrowHeadType_Start".equals(id)) return headTypeToString(arrowHead[0].type);
		else if ("ArrowHeadType_End".equals(id)) return headTypeToString(arrowHead[1].type);
		else if ("ArrowHeadSize_Start".equals(id)) return Integer.toString(arrowHead[0].size);
		else if ("ArrowHeadSize_End".equals(id)) return Integer.toString(arrowHead[1].size);
		else return super.getPropertyValue(id);
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
		String tmp = (String) value;

		if ("ArrowHeadType_Start".equals(id)) arrowHead[0].type = stringToHeadType(tmp);
		else if ("ArrowHeadType_End".equals(id)) arrowHead[1].type = stringToHeadType(tmp);
		else if ("ArrowHeadSize_Start".equals(id)) arrowHead[0].size = Integer.parseInt(tmp); 
		else if ("ArrowHeadSize_End".equals(id)) arrowHead[1].size = Integer.parseInt(tmp); 
		else super.setPropertyValue(id, value);
	}
	
	
}


