package kaist.tap.kaf.component;

import kaist.tap.kaf.component.Component.SelectMode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;

public class Text extends Rectangle {
	
	protected String text;
	protected int fontColor;
	protected int fontSize;
	protected int fontStyle;
	
	public Text() {
		setName("Text");
		text = new String();
		fontColor = SWT.COLOR_BLACK;
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
		fontColor = SWT.COLOR_BLACK;
		fontSize = 20;
		fontStyle = SWT.NORMAL;
	}
	
	
	public void setText(String str) {
		text = str;
	}
	
	public String getText() {
		return text;
	}
	
	public void setFontColor(int color) {
		fontColor = color;
	}
	
	public int getFontColor() {
		return fontColor;
	}
	
	public void setFontSize(int size) {
		fontSize = size;
	}
	
	public int getFontSize() {
		return fontSize;
	}
	
	public void draw(GC gc) {
		gc.setForeground(getColor());		
		gc.setBackground(getFillColor());
		gc.setLineWidth(mLineThickness);
		gc.setLineStyle(mLineStyle);
		gc.drawRectangle(mPosition.x, mPosition.y, mWidth, mHeight);
		if (mFill == true) gc.fillRectangle(mPosition.x+1, mPosition.y+1, mWidth-1, mHeight-1);
		
		FontData[] fd = gc.getFont().getFontData();
		fd[0].setHeight(fontSize);
		fd[0].setStyle(fontStyle);
		gc.setFont(new Font(gc.getDevice(), fd[0]));
		
		Point p = gc.stringExtent(text);
				
		gc.setForeground(SWTResourceManager.getColor(fontColor));
		gc.drawText(text, (int) (mPosition.x+(mWidth-p.x)*0.5), (int) (mPosition.y+(mHeight-p.y)*0.5));
		
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
		text.setText(this.text);
		text.setFontColor(this.getFontColor());
		
		return text;
	}

	
	
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		TextPropertyDescriptor nameDiscriptor = new TextPropertyDescriptor("Name", "Type");
		TextPropertyDescriptor posxDiscriptor = new TextPropertyDescriptor("Position_X", "X");
		posxDiscriptor.setCategory("Position");
		TextPropertyDescriptor posyDiscriptor = new TextPropertyDescriptor("Position_Y", "Y");
		posyDiscriptor.setCategory("Position");
		TextPropertyDescriptor lcDiscriptor = new TextPropertyDescriptor("Color", "Color");
		lcDiscriptor.setCategory("Line");
		TextPropertyDescriptor ltDiscriptor = new TextPropertyDescriptor("Line_Thickness", "Thickness");
		ltDiscriptor.setCategory("Line");
		TextPropertyDescriptor lsDiscriptor = new TextPropertyDescriptor("Line_Style", "Style");
		lsDiscriptor.setCategory("Line");
		TextPropertyDescriptor pcDiscriptor = new TextPropertyDescriptor("FillColor", "Color");
		pcDiscriptor.setCategory("Polygon");
		TextPropertyDescriptor pfDiscriptor = new TextPropertyDescriptor("Fill", "Fill");
		pfDiscriptor.setCategory("Polygon");
		TextPropertyDescriptor tsDiscriptor = new TextPropertyDescriptor("Text", "String");
		tsDiscriptor.setCategory("Text");
		TextPropertyDescriptor tcDiscriptor = new TextPropertyDescriptor("FontColor", "Color");
		tcDiscriptor.setCategory("Text");
		TextPropertyDescriptor tsiDiscriptor = new TextPropertyDescriptor("FontSize", "Size");
		tsiDiscriptor.setCategory("Text");
		TextPropertyDescriptor tsyDiscriptor = new TextPropertyDescriptor("FontStyle", "Style");
		tsyDiscriptor.setCategory("Text");
		
		return new IPropertyDescriptor[] {
				nameDiscriptor, posxDiscriptor, posyDiscriptor, lcDiscriptor, ltDiscriptor, lsDiscriptor, 
				pcDiscriptor, pfDiscriptor,
				tsDiscriptor, tcDiscriptor, tsiDiscriptor, tsyDiscriptor
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
		else if ("FontColor".equals(id)) return getColorByString(fontColor);
		else if ("FontSize".equals(id)) return Integer.toString(fontSize);
		else if ("FontStyle".equals(id)) {
			switch(fontStyle) {
			case SWT.NORMAL :
				return "Normal";
			case SWT.ITALIC :
				return "Italic";
			case SWT.BOLD :
				return "Bold";
			case SWT.ITALIC | SWT.BOLD :
				return "BoldItalic";
			default :
				return "Normal";
			}
		}
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
		else if ("FontColor".equals(id)) {
			int color = getColorFromString(tmp);
			if (color != 0) {
				fontColor = getColorFromString(tmp);
			}
		}
		else if ("FontSize".equals(id)) {
			fontSize = Integer.parseInt(tmp);
		}
		else if ("FontStyle".equals(id)) {
			switch(tmp) {
			case "Italic" :
			case "italic" :
				fontStyle = SWT.ITALIC;
				break;
			case "Bold" :
			case "bold" :
				fontStyle = SWT.BOLD;
				break;
			case "Normal" :
			case "normal" :
				fontStyle = SWT.NORMAL;
				break;
			case "BoldItalic" :
			case "bolditalic" :
				fontStyle = SWT.ITALIC | SWT.BOLD;
				break;
			}
		}
	}
}

