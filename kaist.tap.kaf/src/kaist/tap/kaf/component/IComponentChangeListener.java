package kaist.tap.kaf.component;

import java.util.EventListener;

public interface IComponentChangeListener extends EventListener {
	public void componentChanged(ComponentChangeEvent e);
}
