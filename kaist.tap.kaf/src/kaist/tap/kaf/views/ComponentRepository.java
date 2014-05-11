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
	
	public void Register(Component comp)
	{
		mComponents.add(comp);	
		
	}
	
	public void Remove(Component comp)
	{
		mComponents.remove(comp);
	}
	
	public void Remove(int idx)
	{
		mComponents.remove(idx);
	}
	
	public int GetNumberOfComponents()
	{
		return mComponents.size();
	}
	
	public Component Get(int idx)
	{
		return mComponents.get(idx);
	}
	
	public boolean Connect(int f, int s)
	{
		int size = this.GetNumberOfComponents();
		
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
