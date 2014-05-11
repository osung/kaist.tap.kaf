package kaist.tap.kaf.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;

import kaist.tap.kaf.component.*;
import kaist.tap.kaf.component.Rectangle;

public class Canvas extends ViewPart {

	public static final String ID = "kaist.tap.kaf.views.Canvas"; //$NON-NLS-1$

	public Canvas() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		{
			org.eclipse.swt.widgets.Canvas canvas = new org.eclipse.swt.widgets.Canvas(container, SWT.H_SCROLL | SWT.V_SCROLL);
			canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
			//canvas.setBounds(0, 0, 594, 469);
		}

		createActions();
		initializeToolBar();
		initializeMenu();
		
		container.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Color blue = e.display.getSystemColor(SWT.COLOR_BLUE);
				e.gc.setBackground(blue);
				Rectangle rect = new Rectangle(20, 30, 50, 100);
				rect.draw(e.gc);		
			}
		});
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
