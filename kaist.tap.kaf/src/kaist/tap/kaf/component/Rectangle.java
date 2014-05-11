package kaist.tap.kaf.component;

import org.eclipse.swt.graphics.*;

public class Rectangle extends Component {
	protected int mWidth;
	protected int mHeight;
	
	public Rectangle() {
		this.setName("Rectangle");
		mPosition.x = mPosition.y = mWidth = mHeight = 0;
	}
	
	public Rectangle(int x, int y, int width, int height) {
		mPosition.x = x;
		mPosition.y = y;
		mWidth = width;
		mHeight = height;
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
		gc.drawRectangle(mPosition.x, mPosition.y, mWidth, mHeight);
	}
}
