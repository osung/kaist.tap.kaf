package kaist.tap.kaf.component;

import kaist.tap.kaf.component.Component.Selection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;

public class Rectangle extends Component {
		
	protected int mWidth;
	protected int mHeight;
	
	public Rectangle() {
		this.setName("Rectangle");
		mPosition.x = mPosition.y = mEndPosition.x = mEndPosition.y = mWidth = mHeight = 0;
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
	
	public void setEndPosition(Point p) {
		mEndPosition.x = p.x;
		mEndPosition.y = p.y;
		
		mWidth = Math.abs(mEndPosition.x-mPosition.x);
		mHeight = Math.abs(mEndPosition.y-mPosition.y);
	}
	
	public void updatePosition(int x, int y) {
		if (mEndPosition.x < x) {
			mPosition.x = mEndPosition.x;
			mEndPosition.x = x;
		}
		else mPosition.x = x;
		
		if (mEndPosition.y < y) {
			mPosition.y = mEndPosition.y;
			mEndPosition.y = y;
		}
		else mPosition.y = y;
		
		mWidth = Math.abs(mEndPosition.x-mPosition.x);
		mHeight = Math.abs(mEndPosition.y-mPosition.y);
	}
	
	public void updatePosition(Point p) {
		if (mEndPosition.x < p.x) {
			mPosition.x = mEndPosition.x;
			mEndPosition.x = p.x;
		}
		else mPosition.x = p.x;
		
		if (mEndPosition.y < p.y) {
			mPosition.y = mEndPosition.y;
			mEndPosition.y = p.y;
		}
		else mPosition.y = p.y;
		
		mWidth = Math.abs(mEndPosition.x-mPosition.x);
		mHeight = Math.abs(mEndPosition.y-mPosition.y);
	}
	
	public void updateEndPosition(int x, int y) {
		if (mPosition.x > x) {
			mEndPosition.x = mPosition.x;
			mPosition.x = x;
		}
		else mEndPosition.x = x;
		
		if (mPosition.y > y) {
			mEndPosition.y = mPosition.y;
			mPosition.y = y;
		}
		else mEndPosition.y = y;
		
		mWidth = Math.abs(mEndPosition.x-mPosition.x);
		mHeight = Math.abs(mEndPosition.y-mPosition.y);
	}
	
	public void updateEndPosition(Point p) {
		if (mPosition.x > p.x) {
			mEndPosition.x = mPosition.x;
			mPosition.x = p.x;
		}
		else mEndPosition.x = p.x;
		
		if (mPosition.y > p.y) {
			mEndPosition.y = mPosition.y;
			mPosition.y = p.y;
		}
		else mEndPosition.y = p.y;

		
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
	
	public Selection containSelection(int x, int y) {
		mSelection = Selection.FALSE;
		
		if (Math.abs(x-mPosition.x) < contSize) {
			if (Math.abs(y-mPosition.y) < contSize) {
				mSelection = Selection.UL;
				return Selection.UL;
			}
			else if (Math.abs(y-mEndPosition.y) < contSize) {
				mSelection = Selection.LL;
				return Selection.LL;
			}
			else return Selection.FALSE;
		}
		else if (Math.abs(x-mEndPosition.x) < contSize) {
			if (Math.abs(y-mPosition.y) < contSize) {
				mSelection = Selection.UR;
				return Selection.UR;
			}
			else if (Math.abs(y-mEndPosition.y) < contSize) {
				mSelection = Selection.LR;
				return Selection.LR;
			}
			else return Selection.FALSE;
		}
		else return Selection.FALSE;
	}
				
	public boolean contains(int x, int y) {
		
		if (mGrouped==true) return false;
		
		mSelection = containSelection(x, y);
		
		if (this.isSelected()==true) {
			if (mSelection != Selection.FALSE) return true;
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
	
	public void resize(int x, int y) {
		if (mSelection == Selection.UL) {
			this.updatePosition(x, y);
		}
		else if (mSelection == Selection.LR) {
			this.updateEndPosition(x, y);
		}
		else if (mSelection == Selection.LL) {
			this.updatePosition(x, mPosition.y);
			this.updateEndPosition(mEndPosition.x, y);
		}
		else if (mSelection == Selection.UR) {
			this.updatePosition(mPosition.x, y);
			this.updateEndPosition(x, mEndPosition.y);
		}
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
		ColorPropertyDescriptor lcDiscriptor = new ColorPropertyDescriptor("Color", "Color");
		lcDiscriptor.setCategory("Line");
		TextPropertyDescriptor ltDiscriptor = new TextPropertyDescriptor("Line_Thickness", "Thickness");
		ltDiscriptor.setCategory("Line");
		String[] lsvalues = {"Solid", "Dot", "Dash", "Dashdot", "Dashdotdot"};
		ComboBoxPropertyDescriptor lsDiscriptor = new ComboBoxPropertyDescriptor("Line_Style", "Line Style", lsvalues);
		lsDiscriptor.setCategory("Line");
		ColorPropertyDescriptor pcDiscriptor = new ColorPropertyDescriptor("FillColor", "Color");
		pcDiscriptor.setCategory("Polygon");
		String[] fillvalues = {"On", "Off"};
		ComboBoxPropertyDescriptor pfDiscriptor = new ComboBoxPropertyDescriptor("Fill", "Fill", fillvalues);
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
		if ("Width".equals(id)) {
			mWidth = Integer.parseInt((String) value);
			mEndPosition.x = mPosition.x+mWidth;
		}
		else if ("Height".equals(id)) {
			mHeight = Integer.parseInt((String) value);
			mEndPosition.y = mPosition.y+mHeight;
		}
		else super.setPropertyValue(id, value);
	}

	public Point[] getBounds() {
		Point[] bounds = new Point[2];
		
		bounds[0] = new Point(0,0);
		bounds[1] = new Point(0,0);
		
		bounds[0].x = mPosition.x;
		bounds[1].x = mEndPosition.x;
		bounds[0].y = mPosition.y;
		bounds[1].y = mEndPosition.y;
		
		return bounds;
	}
}
