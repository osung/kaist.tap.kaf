package kaist.tap.kaf.component;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.graphics.*;

import kaist.tap.kaf.component.Component;

public class Line extends Component {
	
		boolean mConnected;
		Component mStartComponent;
		Component mEndComponent;
		
		public Line()
		{
			mConnected = false;
			this.setName("Line");
			mPosition.x = mPosition.y = mEndPosition.x = mEndPosition.y = 0;
		}
		
		
		
		
		public boolean IsConnected() {
			return mConnected;
		}
		
		public Component GetStartComponent() {
			return mStartComponent;
		}
		
		public Component GetEndComponent() {
			return mEndComponent;
		}
		
		public void SetStartComponent(Component c) {
			mStartComponent = c;
			if (mEndComponent != null) {
				mConnected = true;
			}
		}
		
		public void SetEndComponent(Component c) {
			mEndComponent = c;
			if (mStartComponent != null) {
				mConnected = true;
			}
		}
		
		public void setStartPosition(Point p) {
			mPosition = p;
		}
		
		public void setStartPosition(int x, int y) {
			mPosition.x = x;
			mPosition.y = y;
		}
		
		public void setEndPosition(int x, int y) {
			mEndPosition.x = x;
			mEndPosition.y = y;
		}
		
		public Point getMidPosition() {
			Point mid = new Point(0, 0);
		
			mid.x = (int) ((mPosition.x + mEndPosition.x) * 0.5);
			mid.y = (int) ((mPosition.y + mEndPosition.y) * 0.5);
		
			return mid;
		}
		
		public void draw(GC gc) {
			if (mColor != null) {
				gc.setBackground(mColor);
			}
			
			gc.setLineWidth(mLineThickness);
			gc.setLineStyle(mLineStyle);
			gc.drawLine(mPosition.x, mPosition.y, mEndPosition.x, mEndPosition.y);
		}
		
		public boolean contains(int x, int y) {
			
			
			
			return false;
		}

		public void move(int x, int y) {
			mPosition.x += x;
			mPosition.y += y;
			
			mEndPosition.x += x;
			mEndPosition.y += y;
		}


		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}
}
