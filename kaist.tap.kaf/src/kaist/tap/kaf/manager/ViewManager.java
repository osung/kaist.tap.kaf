package kaist.tap.kaf.manager;

import java.util.*;

public class ViewManager {
	protected ArrayList<View> mViews;
	
	public void AddView(View v) {
		mViews.add(v);
	}
	
	public void RemoveView(View v) {
		mViews.remove(v);
	}
	
	public View GetView(String name) {
		int size = mViews.size();
		
		for (int i=0; i<size; ++i) {
			View v = mViews.get(i);
			
			if (v.getName() == name) {
				return v;
			}
		}
		
		return null;
	}
}
