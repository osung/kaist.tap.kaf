package kaist.tap.kaf.manager;

public class View {
	protected String mName;
	protected int mViewType;
	protected int mRestrictedComponent;
	
	public String getName() {
		return mName;
	}
	public void setName(String mName) {
		this.mName = mName;
	}
	public int getViewType() {
		return mViewType;
	}
	public void setViewType(int mViewType) {
		this.mViewType = mViewType;
	}
	public int getRestrictedComponent() {
		return mRestrictedComponent;
	}
	public void setRestrictedComponent(int mRestrictedComponent) {
		this.mRestrictedComponent = mRestrictedComponent;
	}
}
