package kaist.tap.kaf.manager;

import kaist.tap.kaf.views.*;
import kaist.tap.kaf.component.Component;
import kaist.tap.kaf.io.XMLReader;
import kaist.tap.kaf.io.XMLWriter;
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

		for (int i = 0; i < size; ++i) {
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
	
	
	public void WriteXML(String file) {
		XMLWriter writer = new XMLWriter("test");
		
		for (int i = 0; i < mRepos.size(); ++i) {
			ComponentRepository repo = mRepos.get(i);
			writer.addView(repo);
		}
		
		writer.write(file);
	}
	
	
	public void ReadXML(String file) {
		// clear all repositories
		while(mRepos.size() > 0) {
			ComponentRepository repo = mRepos.get(0);
	
			while(repo.getNumberOfComponents() > 0) {
				repo.remove(0);
			}
			
			mRepos.remove(repo);
		}
		
		XMLReader reader = new XMLReader(file);
		
		Vector<ComponentRepository> repos = reader.getRepositories();
		
		for (int i = 0; i < repos.size(); ++i) {
			ComponentRepository cr = repos.get(i);
			
			mRepos.add(cr);
		}
	}
}
