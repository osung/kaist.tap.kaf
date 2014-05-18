package kaist.tap.kaf.manager;

import org.eclipse.jface.viewers.ISelection;

public class View implements ISelection {
	protected String mName;
	protected viewType mViewType;
	protected int mRestrictedComponent;
	
	public enum viewType {
		LOGICAL_VIEW, RUNTIME_VIEW
	}
	
	public String getName() {
		return mName;
	}
	public void setName(String mName) {
		this.mName = mName;
	}
	public viewType getViewType() {
		return mViewType;
	}
	public void setViewType(viewType type) {
		this.mViewType = type;
	}
	public int getRestrictedComponent() {
		return mRestrictedComponent;
	}
	public void setRestrictedComponent(int mRestrictedComponent) {
		this.mRestrictedComponent = mRestrictedComponent;
	}
	
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
}
