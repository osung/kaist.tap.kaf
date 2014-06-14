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
		position.x = position.y = endPosition.x = endPosition.y = width = height = -1;
	}

	public void update() {
		position.x = position.y = 65535;
		endPosition.x = endPosition.y = 0;

		for (int i = 0; i < components.size(); ++i) {
			Point[] bounds = components.get(i).getBounds();
			position.x = Math.min(position.x, bounds[0].x);
			position.y = Math.min(position.y, bounds[0].y);
			endPosition.x = Math.max(endPosition.x, bounds[1].x);
			endPosition.y = Math.max(endPosition.y, bounds[1].y);
		}

		width = endPosition.x - position.x;
		height = endPosition.y - position.y;
	}

	public void addComponent(Component c) {
		components.add(c);
		Point[] bounds = c.getBounds();

		if (position.x == -1)
			position.x = bounds[0].x;
		else
			position.x = Math.min(position.x, bounds[0].x);

		if (position.y == -1)
			position.y = bounds[0].y;
		else
			position.y = Math.min(position.y, bounds[0].y);

		endPosition.x = Math.max(endPosition.x, bounds[1].x);
		endPosition.y = Math.max(endPosition.y, bounds[1].y);

		width = endPosition.x - position.x;
		height = endPosition.y - position.y;
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
			components.get(i).move(x, y);
		}
	}

	public void draw(GC gc) {
		if (selectMode == SelectMode.SELECTED) {
			gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			gc.setLineWidth(1);
			gc.setLineStyle(SWT.LINE_DASH);
			gc.drawRectangle(position.x, position.y, width, height);
			
			gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			// draw control
			gc.fillRectangle(position.x - contSize, position.y - contSize,
					contSize * 2, contSize * 2);
			gc.fillRectangle(endPosition.x - contSize,
					endPosition.y - contSize, contSize * 2, contSize * 2);
			gc.fillRectangle(position.x - contSize, endPosition.y - contSize,
					contSize * 2, contSize * 2);
			gc.fillRectangle(endPosition.x - contSize, position.y - contSize,
					contSize * 2, contSize * 2);
		}
	}

	public boolean contains(int x, int y) {
		if (selectMode == SelectMode.SELECTED) {

		}

		if (x < position.x || x > endPosition.x || y < position.y
				|| y > endPosition.y) {
			return false;
		}

		return true;
	}

	public Group clone() {
		Group group = new Group();

		group.setPosition(position);
		group.setWidth(width);
		group.setHeight(height);

		for (int i = 0; i < components.size(); ++i) {
			group.addComponent(components.get(i));
		}

		group.setDrawn(drawn);

		return group;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		TextPropertyDescriptor nameDiscriptor = new TextPropertyDescriptor(
				"Name", "Type");

		return new IPropertyDescriptor[] { nameDiscriptor };
	}

	public Object getPropertyValue(Object id) {
		return super.getPropertyValue(id);
	}

	public void setPropertyValue(Object id, Object value) {
		super.setPropertyValue(id, value);
	}

}
