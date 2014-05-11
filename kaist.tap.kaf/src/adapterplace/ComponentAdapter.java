package adapterplace;

import kaist.tap.kaf.component.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
 
public class ComponentAdapter implements IPropertySource {
	private final Component component;
 
	public ComponentAdapter(Component component) {
		this.component = component;
	}
 
 
	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}
 
	@Override
	public Object getEditableValue() {
		return this;
	}
 
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
 
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor("type", "Type"),
				new TextPropertyDescriptor("position", "Position"),
				new TextPropertyDescriptor("width", "Width"),
				new TextPropertyDescriptor("height", "Height"),
				new TextPropertyDescriptor("linestyle", "Line Style"),
				new TextPropertyDescriptor("linethickness", "Line Thickness"),
				new TextPropertyDescriptor("color", "Color")};
	}
 
	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals("type")) {
			return component.getName();
		}
		if (id.equals("position")) {
			return component.getPosition();
		}
		/*
		if (id.equals("width")) {
			return component.getWidth();
		}
		if (id.equals("height")) {
			return component.getHeight();
		} */
		if (id.equals("linestyle")) {
			return component.getLineStyle();
		}
		if (id.equals("linethickness")) {
			return component.getLineThickness();
		}
		if (id.equals("color")) {
			return component.getColor();
		}	
		return null;
	}
 
	@Override
	public void resetPropertyValue(Object id) {
 
	}
 
	@Override
	public void setPropertyValue(Object id, Object value) {
		String s = (String) value;
		if (id.equals("type")) {
			component.setName(s);
		}
		if (id.equals("position")) {
			Component.Point p = (Component.Point) value;
			component.setPosition(p);
		} /*
		if (id.equals("width")) {
			component.setWidth(s);
		}	
		if (id.equals("height")) {
			component.setHeight(s);
		} */
		if (id.equals("linestyle")) {
			component.setLineStyle((int) value);
		}
		if (id.equals("linethickness")) {
			component.setLineThickness((int) value);
		}
		if (id.equals("color")) {
			Color c = (Color) value;
			component.setColor(c);
		}			
	}
}
