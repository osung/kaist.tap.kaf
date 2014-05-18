package kaist.tap.kaf.component;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;

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
		
		public Line(int x1, int y1, int x2, int y2) {
			setName("Line");
			mConnected = false;
			mPosition.x = x1;
			mPosition.y = y1;
			mEndPosition.x = x2;
			mEndPosition.y = y2;
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
			gc.setForeground(getColor());
			gc.setLineWidth(mLineThickness);
			gc.setLineStyle(mLineStyle);
			gc.drawLine(mPosition.x, mPosition.y, mEndPosition.x, mEndPosition.y);
			
			if (mSelectMode == SelectMode.SELECTED) {
				gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
				gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
				
				// draw control			
				gc.fillRectangle(mPosition.x-contSize, mPosition.y-contSize, contSize*2, contSize*2);
				gc.fillRectangle(mEndPosition.x-contSize, mEndPosition.y-contSize, contSize*2, contSize*2);
			}
		}
		
		public boolean contains(int x, int y) {
			double xt, yt;		
			
			xt = ((double) x - mPosition.x) / ((double) mEndPosition.x - mPosition.x);
			yt = ((double) y - mPosition.y) / ((double) mEndPosition.y - mPosition.y);
			
			if (xt < 0 || xt > 1 || yt < 0 || yt > 1) return false;
			if (Math.abs(xt-yt) > 0.05) return false;
			
			return true;
		}

		public void move(int x, int y) {
			mPosition.x += x;
			mPosition.y += y;
			
			mEndPosition.x += x;
			mEndPosition.y += y;
		}

		public Line clone() {
			Line line = new Line(mPosition.x, mPosition.y, mEndPosition.x, mEndPosition.y);
			line.setColor(mColor);
			line.setDrawn(mDrawn);
			line.setLineThickness(mLineThickness);
			line.setLineStyle(mLineStyle);
			
			return line;
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
					new TextPropertyDescriptor("Line_Style", "Line Style")
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
			// TODO Auto-generated method stub
			String tmp = (String) value;
	
			if ("EndPosition_X".equals(id)) mEndPosition.x = Integer.parseInt(tmp);
			else if ("EndPosition_Y".equals(id)) mEndPosition.y = Integer.parseInt(tmp);
			else super.setPropertyValue(id, value);
		}
		
}
