package kaist.tap.kaf.manager;

public class RunTimeView extends View {
	public RunTimeView() {
		setName("RunTime View");
		setViewType(ViewType.RUNTIME_VIEW);
		
		addRestriction("Port");
		addRestriction("Rectangle");
	}
}
