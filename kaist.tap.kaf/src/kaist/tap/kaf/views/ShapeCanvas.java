package kaist.tap.kaf.views;

import kaist.tap.kaf.component.Component;
import kaist.tap.kaf.component.Line;
import kaist.tap.kaf.component.Rectangle;

import org.eclipse.jface.util.ListenerList;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

public class ShapeCanvas extends Canvas implements ISelectionProvider {
	protected ComponentRepository repo;
	ListenerList listeners = new ListenerList();
	private Component selected = null;
	private Component psel = null;
	private Point sp = null;
	
	public ShapeCanvas(Composite parent, int style) {
		super(parent, style);
		
		repo = new ComponentRepository();
		
		this.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				System.out.println("4444 : Selection changed");
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection.isEmpty() == false) {
					Object select = selection.getFirstElement();
					if (select instanceof Component) {
						selected = (Component) select;
					}
				}
			}
		});
		
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				if (repo.GetNumberOfComponents() > 0) {
					repo.draw(e.gc);
				}
			}
		}); 
				
		addMouseListener(new MouseAdapter() {	
			public void mouseDown(MouseEvent e) {
				for (int i = 0; i < repo.GetNumberOfComponents(); ++i) {
					Component current = repo.Get(i); 
					if (current.contains(e.x, e.y)) { 
						if (selected != null && selected.getDrawn() == false) {
							psel = selected;
						}					
						setSelection(new StructuredSelection(current));
					}
				}
				
				sp = new Point(e.x, e.y);
			}
			
			public void mouseUp(MouseEvent e) {
				int x, y, w, h;	
				
				if (selected == null) {
					if (psel == null) return;
					
					selected = psel;
				}
				
				if (selected.getDrawn() == false) {
					if (selected instanceof Rectangle) {
						x = sp.x < e.x ? sp.x : e.x;
						y = sp.y < e.y ? sp.y : e.y;
				
						w = Math.abs(e.x-sp.x);
						h = Math.abs(e.y-sp.y);
						w = (w < 5) ? 20 : w;
						h = (h < 5) ? 20 : h;
				
						Rectangle rect = new Rectangle(x, y, w, h);
						rect.setColor(SWTResourceManager.getColor(SWT.COLOR_RED));
						rect.setDrawn(true);
						repo.Register(rect);
					}
					else if (selected instanceof Line) {
						Line line = new Line(sp.x, sp.y, e.x, e.y);
						line.setColor(SWTResourceManager.getColor(SWT.COLOR_BLUE));
						line.setDrawn(true);
						repo.Register(line);
					}
				}
				else {
					selected.move(e.x-sp.x, e.y-sp.y);
					selected = null;
				}
				
				redraw();
				sp = null;
			}		
		});
		
		addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				// TODO Auto-generated method stub
				if (sp == null) return;
				
				if (selected != null) {
					if (selected.getDrawn()==true) {
						selected.move(e.x-sp.x, e.y-sp.y);
						sp.x = e.x; sp.y = e.y;
					}
					else {
						// rubber band effect
					}
						
					redraw();
				}
			}
			
		});
	}
	
	public void addSelectionListener(SelectionListener listener) {
		this.addListener(SWT.Selection, new TypedListener(listener));
	}
	
	public void removeSelectionListener(SelectionListener listener) {
		this.removeListener(SWT.Selection, listener);
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		// TODO Auto-generated method stub
		System.out.println("SelectionChangedListener");
		listeners.add(listener);
	}

	@Override
	public ISelection getSelection() {
		// TODO Auto-generated method stub
		System.out.println("ShapeCanvas : getSelection");
		
		if (selected != null) {
			return new StructuredSelection(selected);
		}
		
		//return null; 
		
		return new StructuredSelection();
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		// TODO Auto-generated method stub
		listeners.remove(listener);
	}

	@Override
	public void setSelection(ISelection select) {
		// TODO Auto-generated method stub
		System.out.println("22222222");
		Object[] list = listeners.getListeners();
		for (int i = 0; i < listeners.size(); ++i) {
			((ISelectionChangedListener) list[i]).selectionChanged(new SelectionChangedEvent(this, select));
		}
	}
}