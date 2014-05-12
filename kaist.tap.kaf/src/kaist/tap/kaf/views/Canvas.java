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
	private ComponentSelectionListener selectionListener;
	
	public Canvas() {
		mRep = new ComponentRepository();
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		//Canvas c2 = new Canvas(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		
		final org.eclipse.swt.widgets.Canvas canvas = new org.eclipse.swt.widgets.Canvas(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		//Composite container = new Composite(parent, SWT.NONE);
		canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		createActions();
		initializeToolBar();
		initializeMenu();
			
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				if (mRep.GetNumberOfComponents() > 0) {
					mRep.draw(e.gc);
				}
			}
		}); 
		
		canvas.addMouseListener(new MouseAdapter() {
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
			
				canvas.redraw();
			}			
		});
		
	//	canvas.add
	//	selectionListener = new ComponentSelectionListener(canvas, getSite().getPart());
	//	getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(selectionListener);
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
