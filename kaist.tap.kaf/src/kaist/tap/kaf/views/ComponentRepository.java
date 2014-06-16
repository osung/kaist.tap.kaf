package kaist.tap.kaf.views;

import java.util.*;

import kaist.tap.kaf.component.*;

import org.eclipse.swt.graphics.*;

public class ComponentRepository {

	public class Pair<F, S> {
		public F first;
		public S second;

		public Pair(F f, S s) {
			this.first = f;
			this.second = s;
		}
	}

	private LinkedList<Component> components;
	private LinkedList<Pair<Integer, Integer>> connectivity;
	private String name = null;

	public ComponentRepository() {
		components = new LinkedList<Component>();
	}

	public ComponentRepository(String n) {
		components = new LinkedList<Component>();
		name = n;
	}

	public void setName(String n) {
		name = n;
	}

	public String getName() {
		if (name == null)
			return new String("untitled");
		return name;
	}

	public void register(Component comp) {
		components.add(comp);
		comp.setId(components.indexOf(comp));
	}

	public void remove(Component comp) {
		int idx = components.indexOf(comp);
		components.remove(comp);
		comp.setId(-1);
		for (int i = idx; i < components.size(); ++i) {
			Component c = components.get(i);
			c.setId(i);
		}
	}

	public void remove(int idx) {
		components.remove(idx);
		for (int i = idx; i < components.size(); ++i) {
			Component c = components.get(i);
			c.setId(i);
		}
	}

	public void remove(Component[] comps, int size) {
		for (int i = 0; i < size; ++i) {
			components.remove(comps[i]);
		}

		for (int i = 0; i < components.size(); ++i) {
			Component c = components.get(i);
			c.setId(i);
		}
	}

	public void remove(Vector<Component> comps) {
		while (comps.size() > 0) {
			Component comp = comps.lastElement();
			components.remove(comp);
			if (comp instanceof Group) {
				for (int i = 0; i < ((Group) comp).getSize(); ++i) {
					Component c = ((Group) comp).getComponent(i);
					components.remove(c);
				}
			}
			comps.remove(comp);
		}

		for (int i = 0; i < components.size(); ++i) {
			Component c = components.get(i);
			c.setId(i);
		}
	}

	public void raise(Component comp) {
		int idx = components.indexOf(comp);
		if (idx < components.size() - 1) {
			idx++;
			components.remove(comp);
			components.add(idx, comp);
			comp.setId(idx);
			Component c = components.get(idx - 1);
			c.setId(idx - 1);
		}
	}

	public void lower(Component comp) {
		int idx = components.indexOf(comp);
		if (idx > 0) {
			idx--;
			components.remove(comp);
			components.add(idx, comp);
			comp.setId(idx);
			Component c = components.get(idx + 1);
			c.setId(idx + 1);
		}
	}

	public int getNumberOfComponents() {
		return components.size();
	}

	public Component get(int idx) {
		return components.get(idx);
	}

	public boolean connect(int f, int s) {
		int size = this.getNumberOfComponents();

		if (f < 0 || f >= size || s < 0 || s >= size) {
			return false;
		}

		Pair<Integer, Integer> pair = new Pair<Integer, Integer>(f, s);
		connectivity.add(pair);

		return true;
	}

	public void draw(GC gc) {
		for (int i = 0; i < components.size(); ++i) {
			components.get(i).draw(gc);
		}
	}
}
