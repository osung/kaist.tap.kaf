package kaist.tap.kaf.component;

import kaist.tap.kaf.component.Component.SelectMode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;

public class Text extends Rectangle {
	
	public String text;
	
	public Text() {
		setName("Text");
		text = new String();
	}
	
	public Text(int x, int y, int w, int h, String str) {
		setName("Text");
		mPosition.x = x;
		mPosition.y = y;
		mWidth = w;
		mHeight = h;
		mEndPosition.x = x + w;
		mEndPosition.y = y + h;
		text = str;
	}
	
	
	public void setText(String str) {
		text = str;
	}
	
	public String getText() {
		return text;
	}
	
	public void draw(GC gc) {
		gc.setForeground(getColor());		
		gc.setBackground(getFillColor());
		gc.setLineWidth(mLineThickness);
		gc.setLineStyle(mLineStyle);
		gc.drawRectangle(mPosition.x, mPosition.y, mWidth, mHeight);
		if (mFill == true) gc.fillRectangle(mPosition.x+1, mPosition.y+1, mWidth-1, mHeight-1);
		
		gc.drawText(text, mPosition.x, mPosition.y);
		
		if (mSelectMode == SelectMode.SELECTED) {
			gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			// draw control	
			gc.fillRectangle(mPosition.x-contSize, mPosition.y-contSize, contSize*2, contSize*2);
			gc.fillRectangle(mEndPosition.x-contSize, mEndPosition.y-contSize, contSize*2, contSize*2);
			gc.fillRectangle(mPosition.x-contSize, mEndPosition.y-contSize, contSize*2, contSize*2);
			gc.fillRectangle(mEndPosition.x-contSize, mPosition.y-contSize, contSize*2, contSize*2);
		}
	}
	
	
	public Text clone() {
		Text text = new Text(mPosition.x, mPosition.y, mWidth, mHeight, this.text);
		text.setColor(mColor);
		text.setDrawn(mDrawn);
		text.setLineStyle(mLineStyle);
		text.setLineThickness(mLineThickness);
		
		return text;
	}

	
	
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		// TODO Auto-generated method stub
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor("Name", "Name"),
				new TextPropertyDescriptor("Position_X", "Position X"), 
				new TextPropertyDescriptor("Position_Y", "Position Y"),
				new TextPropertyDescriptor("Color", "Color"),
				new TextPropertyDescriptor("FillColor", "Fill Color"),
				new TextPropertyDescriptor("Fill", "Fill"), 
				new TextPropertyDescriptor("Line_Thickness", "Line Thickness"),
				new TextPropertyDescriptor("Line_Style", "Line Style"),
				new TextPropertyDescriptor("Text", "Text")
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
		else if ("Color".equals(id)) return getColorByString(mColor);
		else if ("FillColor".equals(id)) return getColorByString(mFillColor);
		else if ("Fill".equals(id)) return Boolean.toString(mFill);
		else if ("Line_Thickness".equals(id)) return Integer.toString(mLineThickness);
		else if ("Line_Style".equals(id)) return Integer.toString(mLineStyle);
		else if ("Text".equals(id)) return text;
		return "N/A";
	}
	
	
	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
		String tmp = (String) value;
		System.out.println("value is" + tmp);
		if ("Name".equals(id)) mName = (String) value;
		else if ("Position_X".equals(id)) {
			if (mPosition == null) mPosition = new Point(0, 0);
			mPosition.x = Integer.parseInt(tmp);
		}
		else if ("Position_Y".equals(id)) {
			if (mPosition == null) mPosition = new Point(0, 0);
			mPosition.y = Integer.parseInt(tmp);
		}
		else if ("Color".equals(id)) {
			int color = getColorFromString(tmp);
			if (color != 0) {
				mColor = getColorFromString(tmp);
			}
		}
		else if ("FillColor".equals(id)) {
			int color = getColorFromString(tmp);
			if (color != 0) {
				mFillColor = getColorFromString(tmp);
			}
		}
		else if ("Fill".equals(id)) mFill = Boolean.parseBoolean(tmp);
		else if ("Line_Thickness".equals(id)) mLineThickness = Integer.parseInt(tmp);
		else if ("Line_Style".equals(id)) mLineStyle = Integer.parseInt(tmp);
		else if ("Text".equals(id)) text = tmp;
	}
}

