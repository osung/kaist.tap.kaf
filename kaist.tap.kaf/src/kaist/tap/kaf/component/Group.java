package kaist.tap.kaf.component;

import java.util.Vector;

import kaist.tap.kaf.component.Component.SelectMode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.wb.swt.SWTResourceManager;

public class Group extends Rectangle {

	Vector<Component> components;
	
	public Group() {
		components = new Vector<Component>();
	}
	
	
	public void addComponent(Component c) {
		components.add(c);
	}
	
	public void removeComponent(Component c) {
		components.remove(c);
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
		gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));		
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
	
	
}
