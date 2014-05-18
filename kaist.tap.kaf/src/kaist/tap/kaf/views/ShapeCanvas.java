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
	private Component tmpComp = null;
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
				if (tmpComp != null) {
					tmpComp.draw(e.gc);
				}
			}
		}); 
				
		addMouseListener(new MouseAdapter() {	
			public void mouseDown(MouseEvent e) {
				Component current = null;	
						
				// pick test
				for (int i = 0; i < repo.GetNumberOfComponents(); ++i) {
					current = repo.Get(i); 
					if (current.contains(e.x, e.y)) { 					
						setSelection(new StructuredSelection(current));	
						break;
					}
				}
				
				sp = new Point(e.x, e.y);
			}
			
			public void mouseUp(MouseEvent e) {
				int x, y, w, h;	
				
				tmpComp = null;
				if (selected == null) {
					sp = null;
					return;
				}
				
				if (selected.getDrawn() == false) {
					if (selected instanceof Rectangle) {
						x = sp.x < e.x ? sp.x : e.x;
						y = sp.y < e.y ? sp.y : e.y;
				
						w = Math.abs(e.x-sp.x);
						h = Math.abs(e.y-sp.y);
						w = (w < 5) ? 20 : w;
						h = (h < 5) ? 20 : h;
				
						Rectangle src = (Rectangle) selected;
						Rectangle rect = src.clone();
						rect.setDrawn(true);
						repo.Register(rect);
					}
					else if (selected instanceof Line) {
						Line src = (Line) selected;
						Line line = src.clone();
						line.setDrawn(true);
						repo.Register(line);
					}
				}
				else {
					selected.move(e.x-sp.x, e.y-sp.y);
				}
				
				redraw();
				sp = null;
				selected = null;
			}		
		});
		
		addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				// TODO Auto-generated method stub
				if (sp == null) return;
				
				if (selected != null) {
					//System.out.println("get drawn of selected is " + selected.getDrawn());
					if (selected.getDrawn()==true) {
						selected.move(e.x-sp.x, e.y-sp.y);
						sp.x = e.x; sp.y = e.y;
					}
					else {
						// rubber band effect
						if (selected instanceof Rectangle) {
							int x, y, w, h;
							Rectangle rect = (Rectangle) selected;
							x = sp.x < e.x ? sp.x : e.x;
							y = sp.y < e.y ? sp.y : e.y;
					
							w = Math.abs(e.x-sp.x);
							h = Math.abs(e.y-sp.y);
							
							rect.setPosition(new Point(x, y));
							rect.setWidth(w);
							rect.setHeight(h);
						}
						else if (selected instanceof Line) {
							selected.setPosition(sp);
							selected.setEndPosition(new Point(e.x, e.y));
						}
						
						tmpComp = selected;
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