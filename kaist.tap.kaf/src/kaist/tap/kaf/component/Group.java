package kaist.tap.kaf.component;

import java.util.Vector;

import kaist.tap.kaf.component.Component.SelectMode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wb.swt.SWTResourceManager;

public class Group extends Rectangle {

	Vector<Component> components;
	
	public Group() {
		components = new Vector<Component>();
		mPosition.x = mPosition.y = mEndPosition.x = mEndPosition.y = mWidth = mHeight = -1;
	}
	
	public void update() {
		mPosition.x = mPosition.y = 65535;
		mEndPosition.x = mEndPosition.y = 0;
		
		for (int i = 0; i < components.size(); ++i) {
			Point [] bounds = components.get(i).getBounds(); 
			mPosition.x = Math.min(mPosition.x, bounds[0].x);
			mPosition.y = Math.min(mPosition.y, bounds[0].y);
			mEndPosition.x = Math.max(mEndPosition.x, bounds[1].x);
			mEndPosition.y = Math.max(mEndPosition.y, bounds[1].y);
		}
		
		mWidth = mEndPosition.x - mPosition.x;
		mHeight = mEndPosition.y - mPosition.y;
	}
	
	public void addComponent(Component c) {
		components.add(c);
		Point [] bounds = c.getBounds();
		
		if (mPosition.x == -1) mPosition.x = bounds[0].x;
		else mPosition.x = Math.min(mPosition.x, bounds[0].x);
		
		if (mPosition.y == -1) mPosition.y = bounds[0].y;
		else mPosition.y = Math.min(mPosition.y, bounds[0].y);
		
		mEndPosition.x = Math.max(mEndPosition.x, bounds[1].x);
		mEndPosition.y = Math.max(mEndPosition.y, bounds[1].y);
		
		mWidth = mEndPosition.x - mPosition.x;
		mHeight = mEndPosition.y - mPosition.y;
	}
	
	public Component getComponent(int idx) {
		return components.get(idx);
	}
	
	public int getSize() {
		return components.size();
	}
	
	public void removeComponent(Component c) {
		components.remove(c);
		// update bounding box
		update();
	}
	
	public void clear() {
		for (int i = 0; i < components.size(); ++i) {
			components.get(i).unsetGrouped();
		}
		
		components.clear();
	}
	
	public void move(int x, int y) {
		super.move(x, y);
		
		for (int i = 0; i < components.size(); ++i) {
			components.get(i).move(x,  y);
		}
	}
	
	public void draw(GC gc) {
		gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));		
		gc.setLineWidth(1);
		gc.setLineStyle(SWT.LINE_DOT);
		gc.drawRectangle(mPosition.x, mPosition.y, mWidth, mHeight);
		
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

	public Group clone() {
		Group group = new Group();
		
		group.setPosition(mPosition);
		group.setWidth(mWidth);
		group.setHeight(mHeight);
	
		for (int i = 0; i < components.size(); ++i) {
			group.addComponent(components.get(i));
		}
		
		group.setDrawn(mDrawn);
		
		return group;
	}	
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		TextPropertyDescriptor nameDiscriptor = new TextPropertyDescriptor("Name", "Type");
		
		return new IPropertyDescriptor[] {
				nameDiscriptor
		};
	}

	public Object getPropertyValue(Object id) {
		return super.getPropertyValue(id);
	}

	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
	}	
	
	
	
}
