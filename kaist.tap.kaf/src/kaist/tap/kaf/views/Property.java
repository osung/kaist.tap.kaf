package kaist.tap.kaf.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;

public class Property extends ViewPart {

	public static final String ID = "kaist.tap.kaf.views.Property"; //$NON-NLS-1$
	private Table table;

	public Property() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		final TableViewer tv = new TableViewer (parent, SWT.BORDER | SWT.FULL_SELECTION);
		
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(33, true));
		layout.addColumnData(new ColumnWeightData(33, true));
		layout.addColumnData(new ColumnWeightData(33, true));
		layout.addColumnData(new ColumnWeightData(33, true));
		
		tv.getTable().setLayout(layout);
		tv.getTable().setLinesVisible(true);
		tv.getTable().setHeaderVisible(true);
		
		TableColumn column1 = new TableColumn(tv.getTable(), SWT.CENTER);
		column1.setText("Property");
		column1.setMoveable(true);
		column1.setResizable(true);
		
		TableColumn column2 = new TableColumn(tv.getTable(), SWT.CENTER);
		column2.setText("Value");
		column2.setMoveable(true);
		column2.setResizable(true);
		
		
		/*
		tv.setContentProvider(new IStructuredContentProvider() ) {
			public 
		}
		*/
		
		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}
