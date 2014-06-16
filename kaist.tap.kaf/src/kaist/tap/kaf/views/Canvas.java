package kaist.tap.kaf.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;

public class Canvas extends ViewPart {

	public static final String ID = "kaist.tap.kaf.views.Canvas"; //$NON-NLS-1$
	protected ShapeCanvas canvas;

	public Canvas() {

	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		final ShapeCanvas canvas = new ShapeCanvas(parent, SWT.H_SCROLL
				| SWT.V_SCROLL);

		canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		canvas.setFocus();
		getSite().setSelectionProvider(canvas);

		createActions();
		initializeToolBar();
		initializeMenu();

		canvas.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("widgetSelected");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				System.out.println("widgetDefaultSelected");
			}
		});

		ComponentSelectionListener selectionListener = new ComponentSelectionListener(
				canvas, getSite().getPart());
		getSite().getWorkbenchWindow().getSelectionService()
				.addSelectionListener(selectionListener);
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
