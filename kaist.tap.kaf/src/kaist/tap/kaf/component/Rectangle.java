package kaist.tap.kaf.component;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;

public class Rectangle extends Component {
	
	
	protected int mWidth;
	protected int mHeight;
	
	public Rectangle() {
		this.setName("Rectangle");
		mPosition.x = mPosition.y = mWidth = mHeight = 0;
	}
	
	public Rectangle(int x, int y, int width, int height) {
		setName("Rectangle");
		mPosition.x = x;
		mPosition.y = y;
		mWidth = width;
		mHeight = height;
		mEndPosition.x = x + width;
		mEndPosition.y = y + height;
	}
	
	public void setPosition(Point p) {
		super.setPosition(p);
		mWidth = Math.abs(mEndPosition.x-mPosition.x);
		mHeight = Math.abs(mEndPosition.y-mPosition.y);
	}
	
	public void setEndPosition(int x, int y) {
		mEndPosition.x = x;
		mEndPosition.y = y;
		mWidth = Math.abs(mEndPosition.x-mPosition.x);
		mHeight = Math.abs(mEndPosition.y-mPosition.y);
	}
	
	public void setEndPosition(Point p) {
		mEndPosition = p;
		mWidth = Math.abs(mEndPosition.x-mPosition.x);
		mHeight = Math.abs(mEndPosition.y-mPosition.y);
	}
	
	public int getWidth() {
		return mWidth;
	}
	
	public void setWidth(int width) {
		mWidth = width;
		mEndPosition.x = mPosition.x + mWidth;
	}
	
	public int getHeight() {
		return mHeight;
	}
	
	public void setHeight(int height) {
		mHeight = height;
		mEndPosition.y = mPosition.y + mHeight;
	}
	
	public void draw(GC gc) {
		gc.setForeground(getColor());		
		gc.setBackground(getFillColor());
		gc.setLineWidth(mLineThickness);
		gc.setLineStyle(mLineStyle);
		gc.drawRectangle(mPosition.x, mPosition.y, mWidth, mHeight);
		if (mFill == true) gc.fillRectangle(mPosition.x+1, mPosition.y+1, mWidth-1, mHeight-1);
		
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
	
	public boolean contains(int x, int y) {
		if (mSelectMode == SelectMode.SELECTED) {
			
		}
		
		if (x < mPosition.x || x > mEndPosition.x || y < mPosition.y || y > mEndPosition.y) {
			return false;
		}
		
		return true;
	}

	public void move(int x, int y) {
		mPosition.x += x;
		mPosition.y += y;
		mEndPosition.x += x;
		mEndPosition.y += y;
	}
	
	
	public Rectangle clone() {
		Rectangle rect = new Rectangle(mPosition.x, mPosition.y, mWidth, mHeight);
		rect.setColor(mColor);
		rect.setFillColor(mFillColor);
		rect.setFill(mFill);
		rect.setDrawn(mDrawn);
		rect.setLineStyle(mLineStyle);
		rect.setLineThickness(mLineThickness);
		
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
		
		return new IPropertyDescriptor[] {
				nameDiscriptor, posxDiscriptor, posyDiscriptor, sizewDiscriptor, sizehDiscriptor,
				lcDiscriptor, ltDiscriptor, lsDiscriptor, 
				pcDiscriptor, pfDiscriptor
		};
	}

	public Object getPropertyValue(Object id) {
		if ("Width".equals(id)) return Integer.toString(mWidth);
		else if ("Height".equals(id)) return Integer.toString(mHeight);
		else return super.getPropertyValue(id);
	}

	public void setPropertyValue(Object id, Object value) {
		String tmp = (String) value;
		if ("Width".equals(id)) {
			mWidth = Integer.parseInt(tmp);
			mEndPosition.x = mPosition.x+mWidth;
		}
		else if ("Height".equals(id)) {
			mHeight = Integer.parseInt(tmp);
			mEndPosition.y = mPosition.y+mHeight;
		}
		else super.setPropertyValue(id, value);
	}
}
