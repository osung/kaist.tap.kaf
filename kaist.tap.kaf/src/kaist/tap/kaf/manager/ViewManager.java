package kaist.tap.kaf.manager;

import kaist.tap.kaf.io.XMLReader;
import kaist.tap.kaf.io.XMLWriter;
import kaist.tap.kaf.manager.View.ViewType;
import kaist.tap.kaf.views.ComponentRepository;

import java.util.*;

import org.eclipse.swt.widgets.Canvas;

public class ViewManager {
	protected ArrayList<View> views;
	protected ArrayList<ComponentRepository> repos;

	public ViewManager() {
		views = new ArrayList<View>();
		repos = new ArrayList<ComponentRepository>();
	}

	public void addView(View v) {
		views.add(v);
	}

	public void removeView(View v) {
		views.remove(v);
	}

	protected View getView(ViewType type) {
		int size = views.size();

		for (int i = 0; i < size; ++i) {
			View v = views.get(i);
			if (v.getViewType() == type) {
				return v;
			}
		}

		return null;
	}

	public View getView(String name) {
		int size = views.size();

		for (int i = 0; i < size; ++i) {
			View v = views.get(i);

			if (v.getName() == name) {
				return v;
			}
		}

		return null;
	}

	public void addRepo(ComponentRepository repo) {
		repos.add(repo);
	}

	public void removeRepo(ComponentRepository repo) {
		repos.remove(repo);
	}
	
	public void clear() {
		for (int i = 0; i < repos.size(); ++i) {
			ComponentRepository repo = repos.get(i);
			
			while (repo.getNumberOfComponents() > 0) {
				repo.remove(0);
			}
		}
	}

	public ComponentRepository getRepo(ViewType type) {
		int size = views.size();
		for (int i = 0; i < size; ++i) {
			View v = views.get(i);
			if (v.getViewType() == type) {
				return repos.get(i);
			}
		}

		return null;
	}

	public void WriteXML(String file) {
		XMLWriter writer = new XMLWriter("test");

		for (int i = 0; i < repos.size(); ++i) {
			ComponentRepository repo = repos.get(i);
			writer.addView(repo);
		}

		writer.write(file);
	}

	public void ReadXML(String file, Canvas canvas) {
		// clear all repositories
		while (repos.size() > 0) {
			ComponentRepository repo = repos.get(0);

			while (repo.getNumberOfComponents() > 0) {
				repo.remove(0);
			}

			repos.remove(repo);
		}

		XMLReader reader = new XMLReader(file);

		Vector<ComponentRepository> vrepos = reader.getRepositories(canvas);

		for (int i = 0; i < vrepos.size(); ++i) {
			ComponentRepository cr = vrepos.get(i);

			repos.add(cr);
		}
	}
}
