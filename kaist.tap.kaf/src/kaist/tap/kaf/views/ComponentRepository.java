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
	
	ComponentRepository() {
		components = new LinkedList<Component>();
	}

	ComponentRepository(String n) {
		components = new LinkedList<Component>();
		name = n;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	
	public String getName() {
		if (name == null) return new String("untitled");
		return name;
	}
	
	public void register(Component comp) {
		components.add(comp);

	}

	public void remove(Component comp) {
		components.remove(comp);
	}

	public void remove(int idx) {
		components.remove(idx);
	}

	public void remove(Component[] comps, int size) {
		for (int i = 0; i < size; ++i) {
			components.remove(comps[i]);
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
	}

	public void raise(Component comp) {
		int idx = components.indexOf(comp);
		if (idx < components.size() - 1) {
			idx++;
			components.remove(comp);
			components.add(idx, comp);
		}
	}

	public void lower(Component comp) {
		int idx = components.indexOf(comp);
		if (idx > 0) {
			idx--;
			components.remove(comp);
			components.add(idx, comp);
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
