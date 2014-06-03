package kaist.tap.kaf.views;

import java.util.*;

import kaist.tap.kaf.component.*;

import org.eclipse.swt.graphics.*;

public class ComponentRepository {
	
	public class Pair <F, S>
	{
		public F first;
		public S second;
		
		public Pair(F f, S s)
		{
			this.first = f;
			this.second = s;
		}
	}
	
	private LinkedList<Component> mComponents;
	private LinkedList<Pair<Integer,Integer>> mConnectivity;
	
	ComponentRepository()
	{
		mComponents = new LinkedList<Component>();
	}
	
	public void register(Component comp)
	{
		mComponents.add(comp);	
		
	}
	
	public void remove(Component comp)
	{
		mComponents.remove(comp);
	}
	
	public void remove(int idx)
	{
		mComponents.remove(idx);
	}
	
	public void remove(Component[] comps, int size) 
	{
		for (int i = 0; i < size; ++i) {
			mComponents.remove(comps[i]);
		}
	}
	
	public void remove(Vector<Component> comps)
	{
		while (comps.size() > 0) {
			Component comp = comps.lastElement();
			mComponents.remove(comp);
			if (comp instanceof Group) {
				for (int i = 0; i < ((Group)comp).getSize(); ++i) {
					Component c = ((Group) comp).getComponent(i);
					mComponents.remove(c);
				}
			} 
			comps.remove(comp);
		}
	}
	
	public void raise(Component comp) {
		int idx = mComponents.indexOf(comp);
		if (idx < mComponents.size()-1) {
			idx++;
			mComponents.remove(comp);
			mComponents.add(idx, comp);
		}
	}
	
	
	
	public void lower(Component comp) {
		int idx = mComponents.indexOf(comp);
		if (idx > 0) {
			idx--;
			mComponents.remove(comp);
			mComponents.add(idx, comp);
		}
	}
	
	
	public int getNumberOfComponents()
	{
		return mComponents.size();
	}
	
	public Component get(int idx)
	{
		return mComponents.get(idx);
	}
	
	public boolean connect(int f, int s)
	{
		int size = this.getNumberOfComponents();
		
		if (f < 0 || f >= size || s < 0 || s >= size)
		{
			return false;
		}
		
		Pair<Integer,Integer> pair = new Pair<Integer,Integer>(f,s);
		mConnectivity.add(pair); 
		
		return true;
	}
	
	public void draw(GC gc)
	{
		for (int i = 0; i < mComponents.size(); ++i) {
			mComponents.get(i).draw(gc);
		}
	}
}
