package kaist.tap.kaf.component;

public class Rectangle extends Component {
	protected int mWidth;
	protected int mHeight;
	
	public int getWidth() {
		return mWidth;
	}
	public void setWidth(int width) {
		mWidth = width;
		//mEndPosition.x = mPosition.x + mWidth;
	}
	public int getHeight() {
		return mHeight;
	}
	public void setHeight(int height) {
		mHeight = height;
		//mEndPosition.y = mPosition.y + mHeight;
	}
}
