package kaist.tap.kaf.component;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.graphics.*;

public class Rectangle extends Component {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
//		this.firePropertyChange("WIDTH_PROP", null, width);
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
	}
	
	public boolean contains(int x, int y) {
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
	
	
}
