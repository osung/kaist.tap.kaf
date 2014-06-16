package kaist.tap.kaf.component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import org.eclipse.ui.views.properties.IPropertySource;

public abstract class ComponentElement implements IPropertySource {

	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(
			this);

	public synchronized void addPropertyChangeListener(PropertyChangeListener l) {
		if (l == null) {
			throw new IllegalArgumentException();
		}

		pcsDelegate.addPropertyChangeListener(l);
	}

	protected void firePropertyChange(String property, Object oldValue,
			Object newValue) {
		if (pcsDelegate.hasListeners(property)) {
			pcsDelegate.firePropertyChange(property, oldValue, newValue);
		}
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		pcsDelegate = new PropertyChangeSupport(this);
	}

	public synchronized void removePropertyChangeListener(
			PropertyChangeListener l) {
		if (l != null) {
			pcsDelegate.removePropertyChangeListener(l);
		}
	}

	public void addPropertyChangeListener(String name, PropertyChangeListener l) {
		pcsDelegate.addPropertyChangeListener(name, l);
	}

	public void removePrepertyChangeListener(String name,
			PropertyChangeListener l) {
		pcsDelegate.removePropertyChangeListener(name, l);
	}
}
