package kaist.tap.kaf.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;

import kaist.tap.kaf.component.*;
import kaist.tap.kaf.component.Rectangle;

public class Canvas extends ViewPart {

	public static final String ID = "kaist.tap.kaf.views.Canvas"; //$NON-NLS-1$
	protected ComponentRepository mRep;
	
	public Canvas() {
		mRep = new ComponentRepository();
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
				System.out.println("draw");
				if (mRep.GetNumberOfComponents() > 0) {
					mRep.draw(e.gc);
				}
				e.gc.dispose();
			}
		}); 
		
		container.addMouseListener(new MouseAdapter() {
			Point sp;
			
			public void mouseDown(MouseEvent e) {
				sp = new Point(e.x, e.y);
			}
			
			public void mouseUp(MouseEvent e) {
				int x, y, w, h;
				
				x = sp.x < e.x ? sp.x : e.x;
				y = sp.y < e.y ? sp.y : e.y;
				
				w = Math.abs(e.x-sp.x);
				h = Math.abs(e.y-sp.y);
				w = (w < 5) ? 20 : w;
				h = (h < 5) ? 20 : h;
				
				Rectangle rect = new Rectangle(x, y, w, h);
				rect.setColor(SWTResourceManager.getColor(SWT.COLOR_RED));
				mRep.Register(rect);
				Display.getCurrent().update();
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
