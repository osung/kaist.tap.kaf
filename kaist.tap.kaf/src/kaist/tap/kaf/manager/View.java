package kaist.tap.kaf.manager;

import java.util.Vector;

import org.eclipse.jface.viewers.ISelection;

public class View implements ISelection {
	protected String name;
	protected ViewType viewType;
	protected Vector<String> restrictions;

	public enum ViewType {
		LOGICAL_VIEW, RUNTIME_VIEW
	}

	public View() {
		restrictions = new Vector<String>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String mName) {
		this.name = mName;
	}

	public ViewType getViewType() {
		return viewType;
	}

	public void setViewType(ViewType type) {
		this.viewType = type;
	}
	
	public void addRestriction(String string) {
		restrictions.add(string);
	}
	
	
	public boolean isRestricted(String string) {
		for (int i = 0; i < restrictions.size(); ++i) {
			if (restrictions.get(i).compareTo(string) == 0) {
				return true;
			}
		}
		
		return false;
	}
	

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
}
