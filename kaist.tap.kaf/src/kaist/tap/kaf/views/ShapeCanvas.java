package kaist.tap.kaf.views;

import kaist.tap.kaf.component.Component;
import kaist.tap.kaf.component.Line;
import kaist.tap.kaf.component.Rectangle;
import kaist.tap.kaf.manager.*;
import kaist.tap.kaf.manager.View.viewType;

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
	protected ViewManager vm;
	ListenerList listeners = new ListenerList();
	private Component selected = null;
	private Component psel = null;
	private Component tmpComp = null;
	private Component prevSel = null;
	private Point sp = null;
	
	
	public ShapeCanvas(Composite parent, int style) {
		super(parent, style);
		
		vm = new ViewManager();
		vm.addView(new LogicalView());
		vm.addRepo(new ComponentRepository());
		vm.addView(new RunTimeView());
		vm.addRepo(new ComponentRepository());
		
		repo = vm.getRepo(viewType.LOGICAL_VIEW);
		
		this.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection.isEmpty() == false) {
					Object select = selection.getFirstElement();
					if (select instanceof Component) {
						selected = (Component) select;
					}
					else if (select instanceof View) {
						View v = (View) select;
						repo = vm.getRepo(v.getViewType());
						redraw();
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
						current.select();
						if (prevSel != null) prevSel.unselect();
						prevSel = current;
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
		System.out.println("SelectionChangedListener");
		listeners.add(listener);
	}

	public ISelection getSelection() {
		System.out.println("ShapeCanvas : getSelection");
		
		if (selected != null) {
			return new StructuredSelection(selected);
		}
		
		return new StructuredSelection();
	}

	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.remove(listener);
	}

	public void setSelection(ISelection select) {
		Object[] list = listeners.getListeners();
		for (int i = 0; i < listeners.size(); ++i) {
			((ISelectionChangedListener) list[i]).selectionChanged(new SelectionChangedEvent(this, select));
		}
	}
}