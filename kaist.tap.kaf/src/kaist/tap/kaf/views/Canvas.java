package kaist.tap.kaf.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;


public class Canvas extends ViewPart {

	public static final String ID = "kaist.tap.kaf.views.Canvas"; //$NON-NLS-1$
	protected ShapeCanvas canvas;
	private ISelectionListener mylistener = new ISelectionListener() {

		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			// TODO Auto-generated method stub
			//if (part !=  && selection instanceof IStructuredSelection) {
				System.out.println("1111111");
			//}
		}
		
	};
	
	public Canvas() {
		
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		//Canvas c2 = new Canvas(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		
		final ShapeCanvas canvas = new ShapeCanvas(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		
		//final org.eclipse.swt.widgets.Canvas canvas = new org.eclipse.swt.widgets.Canvas(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		//Composite container = new Composite(parent, SWT.NONE);
		canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		canvas.setFocus();
		getSite().setSelectionProvider(canvas);
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(mylistener);

		createActions();
		initializeToolBar();
		initializeMenu();
		
		/*
		canvas.addListener(SWT.Selection, new Listener() { 
			public void handleEvent(Event e) {
				canvas.notifyListeners(SWT.Selection, new Event());
			}
		});  */
		
		canvas.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println("widgetSelected");
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				System.out.println("widgetDefaultSelected");
			}
		});
		
		//ComponentSelectionListener selectionListener = new ComponentSelectionListener(canvas, getSite().getPart());
		//getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(selectionListener);
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
