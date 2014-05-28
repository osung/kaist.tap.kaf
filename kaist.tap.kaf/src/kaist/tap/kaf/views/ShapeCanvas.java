package kaist.tap.kaf.views;

import java.util.Vector;

import kaist.tap.kaf.component.Component;
import kaist.tap.kaf.component.Line;
import kaist.tap.kaf.component.Parallelogram;
import kaist.tap.kaf.component.Rectangle;
import kaist.tap.kaf.component.Group;
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

public class ShapeCanvas extends Canvas implements ISelectionProvider {
	protected ComponentRepository repo;
	protected ViewManager vm;
	ListenerList listeners = new ListenerList();
	private Component selected = null;
	private Vector<Component> psel, copy;
	private Component tmpComp = null;
	private Point sp = null;
	
	public ShapeCanvas(Composite parent, int style) {
		super(parent, style);
		
		vm = new ViewManager();
		vm.addView(new LogicalView());
		vm.addRepo(new ComponentRepository());
		vm.addView(new RunTimeView());
		vm.addRepo(new ComponentRepository());
		
		repo = vm.getRepo(viewType.LOGICAL_VIEW);
		
		psel = new Vector<Component>();
		copy = new Vector<Component>();
		
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
						psel.clear();
						copy.clear();
						redraw();
					}
				}
			}
		});
		
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				if (repo.getNumberOfComponents() > 0) {
					repo.draw(e.gc);
				}
				if (tmpComp != null) {
					tmpComp.draw(e.gc);
				}
				e.gc.dispose();
			}
		}); 
				
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				
				if (e.keyCode == SWT.DEL) {
					// delete selected component
					if (psel.size() == 0) return;
					
					repo.remove(psel);
					psel.clear();
				}
				else if ((e.stateMask & SWT.CTRL) != 0) {
					if (e.keyCode == 'c' || e.keyCode == 'C') {
						// copy component
						if (psel.size() == 0) return;
						
						if (psel.get(0).isSelected() == true) {
							for (int i = 0; i < psel.size(); ++i) {
								copy.add(psel.get(i).clone());
							}
						}
						else {
							copy.clear();
						}
					}
					else if (e.keyCode == 'x' || e.keyCode == 'X') {
						if (psel.size() == 0) return;
						
						if (psel.get(0).isSelected() == true) {
							for (int i = 0; i < psel.size(); ++i) {
								copy.add(psel.get(i).clone());
							}
						}
						repo.remove(psel);
						psel.clear();
					}
					else if (e.keyCode == 'v' || e.keyCode == 'V') {
						// paste component
						if (copy.size() == 0) return;
						
						for (int i = 0; i < copy.size(); ++i) {
							Component comp = copy.get(i);
							comp.move(10, 10);
							repo.register(comp);
						}
						copy.clear();
					}
					else if (e.keyCode == 'G' || e.keyCode == 'g') {
						if (psel.size() <= 1) return;
						System.out.println("G is pressed");
						
						Group group = new Group();
						for (int i = 0; i < psel.size(); ++i) {
							Component c = psel.get(i);
							group.addComponent(c);
							c.setGrouped();
						}
						repo.register(group);
					}
					else if (e.keyCode == 'U' || e.keyCode == 'u') {
						for (int i = 0; i < psel.size(); ++i) {
							Component c = psel.get(i);
							
							if (c instanceof Group) {
								((Group) c).clear();
								psel.remove(c);
								repo.remove(c);
							}
						}
					}
				}
				
				redraw();
			}
		});
		
		addMouseListener(new MouseAdapter() {	
			public void mouseDown(MouseEvent e) {
				Component current = null;	
				boolean shift = false;
				
				if ((e.stateMask & SWT.SHIFT) != 0) {
					shift = true;
				}
				
				// pick test
				for (int i = repo.getNumberOfComponents()-1; i >=0; --i) {
					current = repo.get(i); 
					if (current.contains(e.x, e.y)) { 					
						setSelection(new StructuredSelection(current));	
		
						if (psel.size() > 0 && shift == false) {
							for (int j = 0; j < psel.size(); ++j) {
								psel.get(j).unselect();
							}
							psel.clear();
						}
						
						current.select();
						psel.add(current);
						break;
					}
					else current = null;
				}
				
				// if no one selected, but previous selection is existed
				if (current == null && psel.size() > 0) {
					for (int i = 0; i < psel.size(); ++i) {
						psel.get(i).unselect();
						psel.clear();
					}
				}
				
				sp = new Point(e.x, e.y);
				redraw();
			}
			
			public void mouseUp(MouseEvent e) {
				int x, y, w, h;				
				
				tmpComp = null;
				if (selected == null) {
					sp = null;
					return;
				}
				
				if (selected.getDrawn() == false) {
					if (selected instanceof Parallelogram) {
						x = sp.x < e.x ? sp.x : e.x;
						y = sp.y < e.y ? sp.y : e.y;
				
						w = Math.abs(e.x-sp.x);
						h = Math.abs(e.y-sp.y);
						w = (w < 5) ? 20 : w;
						h = (h < 5) ? 20 : h;
				
						Parallelogram src = (Parallelogram) selected;
						Parallelogram para = src.clone();
						para.setControlPoint(w/4);
						para.setDrawn(true);
						repo.register(para);
					}
					else if (selected instanceof Rectangle) {
						x = sp.x < e.x ? sp.x : e.x;
						y = sp.y < e.y ? sp.y : e.y;
				
						w = Math.abs(e.x-sp.x);
						h = Math.abs(e.y-sp.y);
						w = (w < 5) ? 20 : w;
						h = (h < 5) ? 20 : h;
				
						Rectangle src = (Rectangle) selected;
						Rectangle rect = src.clone();
						rect.setDrawn(true);
						repo.register(rect);
					}
					else if (selected instanceof Line) {
						Line src = (Line) selected;
						Line line = src.clone();
						line.setDrawn(true);
						repo.register(line);
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
						//selected.move(e.x-sp.x, e.y-sp.y);
						for (int i = 0; i < psel.size(); ++i) {
							psel.get(i).move(e.x-sp.x, e.y-sp.y);
						}
						
						sp.x = e.x; sp.y = e.y;
					}
					else {
						int x, y, w, h;
						// rubber band effect 
						/* if (selected instanceof Parallelogram) {
							Parallelogram para = (Parallelogram) selected;
							x = sp.x < e.x ? sp.x : e.x;
							y = sp.y < e.y ? sp.y : e.y;
					
							w = Math.abs(e.x-sp.x);
							h = Math.abs(e.y-sp.y);
							w = (w < 5) ? 20 : w;
							h = (h < 5) ? 20 : h;
					
							para.setPosition(new Point(x, y));
							para.setWidth(w);
							para.setHeight(h);
						}
						else */ if (selected instanceof Rectangle) {
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
						
						//if (selected instanceof Parallelogram) 
						
						tmpComp = selected;
					}
						
					redraw();
				}
			}
			
		});
	}
	
	public void deleteSelections() {
		while (psel.size() > 0) {
	//		psel.
		}
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