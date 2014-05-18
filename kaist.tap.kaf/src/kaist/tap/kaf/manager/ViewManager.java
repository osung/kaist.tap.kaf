package kaist.tap.kaf.manager;

import kaist.tap.kaf.views.*;
import kaist.tap.kaf.manager.View.viewType;

import java.util.*;


public class ViewManager {
	protected ArrayList<View> mViews;
	protected ArrayList<ComponentRepository> mRepos;
	
	public ViewManager() {
		mViews = new ArrayList<View>();
		mRepos = new ArrayList<ComponentRepository>();
	}
	
	public void addView(View v) {
		mViews.add(v);
	}
	
	public void removeView(View v) {
		mViews.remove(v);
	}
	
	
	protected View getView(viewType type) {
		int size = mViews.size();
		
		for (int i = 0; i < size; ++i) {
			View v = mViews.get(i);
			if (v.getViewType() == type) {
				return v;
			}
 		}
		
		return null;
	}
	
	
	public View getView(String name) {
		int size = mViews.size();
		
		for (int i=0; i<size; ++i) {
			View v = mViews.get(i);
			
			if (v.getName() == name) {
				return v;
			}
		}
		
		return null;
	}
	
	public void addRepo(ComponentRepository repo) {
		mRepos.add(repo);
	}
	
	public void removeRepo(ComponentRepository repo) {
		mRepos.remove(repo);
	}
	
	public ComponentRepository getRepo(viewType type) {
		int size = mViews.size();
		for (int i = 0; i < size; ++i) {
			View v = mViews.get(i);
			if (v.getViewType() == type) {
				return mRepos.get(i);
			}
 		}
		
		return null;
	}
}
