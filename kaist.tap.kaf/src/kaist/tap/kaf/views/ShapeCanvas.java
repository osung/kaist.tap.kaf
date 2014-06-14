package kaist.tap.kaf.views;

import java.beans.PropertyChangeListener;
import java.util.Vector;

import kaist.tap.kaf.component.Arrow;
import kaist.tap.kaf.component.Component;
import kaist.tap.kaf.component.Line;
import kaist.tap.kaf.component.Parallelogram;
import kaist.tap.kaf.component.Rectangle;
import kaist.tap.kaf.component.Group;
import kaist.tap.kaf.component.Component.Selection;
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
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.*;

public class ShapeCanvas extends Canvas implements ISelectionProvider {
	private Canvas canvas = this;
	ListenerList listeners = new ListenerList();
	private Vector<Component> psel, copy;
	protected ComponentRepository repo;
	private Component selected = null;
	private Point sp = null;
	private Component tmpComp = null;
	protected ViewManager vm;

	public ShapeCanvas(Composite parent, int style) {
		super(parent, style);

		vm = new ViewManager();
		vm.addView(new LogicalView());
		vm.addRepo(new ComponentRepository(new String("Logical View")));
		vm.addView(new RunTimeView());
		vm.addRepo(new ComponentRepository(new String("Runtime View")));

		repo = vm.getRepo(viewType.LOGICAL_VIEW);

		psel = new Vector<Component>();
		copy = new Vector<Component>();

		this.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				if (selection.isEmpty() == false) {
					Object select = selection.getFirstElement();
					if (select instanceof Component) {
						selected = (Component) select;
					} else if (select instanceof View) {
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

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {

				if (e.keyCode == SWT.DEL) {
					// delete selected component
					if (psel.size() == 0)
						return;

					repo.remove(psel);
					psel.clear();
				} else if ((e.stateMask & SWT.CTRL) != 0) {
					if (e.keyCode == 'c' || e.keyCode == 'C') {
						// copy component
						if (psel.size() == 0)
							return;

						if (psel.get(0).isSelected() == true) {
							for (int i = 0; i < psel.size(); ++i) {
								copy.add(psel.get(i).clone());
							}
						} else {
							copy.clear();
						}
					} else if (e.keyCode == 'x' || e.keyCode == 'X') {
						if (psel.size() == 0)
							return;

						if (psel.get(0).isSelected() == true) {
							for (int i = 0; i < psel.size(); ++i) {
								copy.add(psel.get(i).clone());
							}
						}
						repo.remove(psel);
						psel.clear();
					} else if (e.keyCode == 'v' || e.keyCode == 'V') {
						// paste component
						if (copy.size() == 0)
							return;

						for (int i = 0; i < copy.size(); ++i) {
							Component comp = copy.get(i);
							comp.move(10, 10);
							repo.register(comp);
						}
						copy.clear();
					} else if (e.keyCode == 'G' || e.keyCode == 'g') {
						if (psel.size() <= 1)
							return;
						System.out.println("G is pressed");

						Group group = new Group();
						for (int i = 0; i < psel.size(); ++i) {
							Component c = psel.get(i);
							group.addComponent(c);
							c.unselect();
							c.setGrouped();
						}
						group.setDrawn(true);
						repo.register(group);
						psel.clear();
					} else if (e.keyCode == 'U' || e.keyCode == 'u') {
						for (int i = 0; i < psel.size(); ++i) {
							Component c = psel.get(i);

							if (c instanceof Group) {
								((Group) c).clear();
								psel.remove(c);
								repo.remove(c);
							}
						}
					} else if (e.keyCode == '+' || e.keyCode == '=') {
						if (psel.size() != 1)
							return;

						Component c = psel.get(0);
						repo.raise(c);
					} else if (e.keyCode == '-' || e.keyCode == '_') {
						if (psel.size() != 1)
							return;

						Component c = psel.get(0);
						repo.lower(c);
					} else if (e.keyCode == 'i' || e.keyCode == 'I') {
						saveImage(e.display);
					} else if (e.keyCode == 's' || e.keyCode == 'S') {
						save(e.display);
					} else if (e.keyCode == 'p' || e.keyCode == 'P') {
						for (int i = 0; i < psel.size(); ++i) {
							Component c = psel.get(i);

							if (c instanceof Group) {
								continue;
							}
							else {
								c.addPort();
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
				for (int i = repo.getNumberOfComponents() - 1; i >= 0; --i) {
					current = repo.get(i);
					if (current.contains(e.x, e.y)) {
						setSelection(new StructuredSelection(current));

						if (psel.size() > 0 && shift == false) {
							for (int j = 0; j < psel.size(); ++j) {
								Component comp = psel.get(j);
								if (comp != current) {
									comp.unselect();
								}
							}
							psel.clear();
						}
						current.select();
						psel.add(current);
						break;
					} else
						current = null;
				}

				// if no one selected, but previous selection is existed
				if (current == null && psel.size() > 0) {
					for (int i = 0; i < psel.size(); ++i) {
						psel.get(i).unselect();
					}
					psel.clear();
				}

				sp = new Point(e.x, e.y);
				redraw();
			}

			public void mouseUp(MouseEvent e) {
				int w, h;

				tmpComp = null;
				if (selected == null) {
					sp = null;
					return;
				}

				if (selected.getDrawn() == false) {
					if (selected instanceof Group) {
						selected = null;
						sp = null;
						return;
					} else if (selected instanceof Parallelogram) {
						w = Math.abs(e.x - sp.x);
						h = Math.abs(e.y - sp.y);
						w = (w < 5) ? 20 : w;
						h = (h < 5) ? 20 : h;

						Parallelogram src = (Parallelogram) selected;
						Parallelogram para = src.clone();
						para.setControlPoint(w / 4);
						para.setDrawn(true);
						repo.register(para);
					} else if (selected instanceof Rectangle) {
						w = Math.abs(e.x - sp.x);
						h = Math.abs(e.y - sp.y);
						w = (w < 5) ? 20 : w;
						h = (h < 5) ? 20 : h;

						Rectangle src = (Rectangle) selected;
						Rectangle rect = src.clone();
						rect.setDrawn(true);
						repo.register(rect);
					} else if (selected instanceof Arrow) {
						Arrow src = (Arrow) selected;
						Arrow arrow = src.clone();
						arrow.setDrawn(true);
						repo.register(arrow);
					} else if (selected instanceof Line) {
						Line src = (Line) selected;
						Line line = src.clone();
						line.setDrawn(true);
						repo.register(line);
						checkConnection(line, e.x, e.y);
					}
				} else {
					Selection sel = selected.getSelection();
					if (sel == Selection.FALSE)
						selected.move(e.x - sp.x, e.y - sp.y);
					else {
						selected.resize(e.x, e.y);

						if (sel == Selection.START || sel == Selection.END) {
							checkConnection((Line) selected, e.x, e.y);
						}
					}
				}

				redraw();
				sp = null;
				selected = null;
			}
		});

		addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if (sp == null)
					return;

				if (selected != null) {
					if (selected.getDrawn() == true) {
						for (int i = 0; i < psel.size(); ++i) {
							Component sel = psel.get(i);
							if (sel.getSelection() == Selection.FALSE)
								sel.move(e.x - sp.x, e.y - sp.y);
							else
								sel.resize(e.x, e.y);
						}

						sp.x = e.x;
						sp.y = e.y;
					} else {
						int x, y, w, h;
						if (selected instanceof Rectangle) {
							Rectangle rect = (Rectangle) selected;
							x = sp.x < e.x ? sp.x : e.x;
							y = sp.y < e.y ? sp.y : e.y;

							w = Math.abs(e.x - sp.x);
							h = Math.abs(e.y - sp.y);

							rect.setPosition(new Point(x, y));
							rect.setWidth(w);
							rect.setHeight(h);
						} else if (selected instanceof Line) {
							selected.setPosition(sp);
							selected.setEndPosition(new Point(e.x, e.y));
						}

						tmpComp = selected;
					}

					redraw();
				}
			}
		});

		addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				System.out.println(e.getSource());
				System.out.println(e.data);
			}
		});
	}

	public void checkConnection(Line line, int x, int y) {
		boolean result = false;
		
		for (int i = repo.getNumberOfComponents() - 1; i >= 0; i--) {
			Component comp = repo.get(i);
			if (comp == line)
				continue;
			// prevent connecting to the Group
			if ((comp instanceof Rectangle) == false || (comp instanceof Group))
				continue;
			if (comp.contains(x, y) == true) {
				// connection established
				Selection sel = line.containSelection(x, y);

				if (sel == Selection.FALSE)
					continue;

				if (sel == Selection.START)
					result = line.setStartComponent(comp);
				else
					result = line.setEndComponent(comp);

				if (result == true) {
					comp.addConnection(line);
				
					Rectangle rect = (Rectangle) comp;
					Point cp = rect.getConnectedPoint(line);
					if (sel == Selection.START)
						line.setStartPosition(cp);
					else
						line.setEndPosition(cp);
				}
				break;
			}
		}
	}

	public void addModifyListener(ModifyListener listener) {
		this.addListener(SWT.Modify, new TypedListener(listener));
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.add(listener);
	}

	public void addSelectionListener(SelectionListener listener) {
		this.addListener(SWT.Selection, new TypedListener(listener));
	}

	public void deleteSelections() {
		while (psel.size() > 0) {
			// psel.
		}
	}

	public ISelection getSelection() {
		if (selected != null) {
			return new StructuredSelection(selected);
		}

		return new StructuredSelection();
	}

	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		listeners.remove(listener);
	}

	public void removeSelectionListener(SelectionListener listener) {
		this.removeListener(SWT.Selection, listener);
	}

	public void saveImage(Display display) {
		Image image = new Image(display, canvas.getBounds());
		GC gc = new GC(image);
		canvas.print(gc);
		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[] { image.getImageData() };

		FileDialog fd = new FileDialog(new Shell(display), SWT.SAVE);
		fd.setText("Save");
		fd.setFilterPath("C:/");
		String[] filterExt = { "*.jpg", "*.jpeg", "*.*" };
		fd.setFilterExtensions(filterExt);
		String filename = fd.open();
		//System.out.println(selected);

		loader.save(filename, SWT.IMAGE_JPEG);
		image.dispose();
		gc.dispose();
	}

	public void save(Display display) {
		FileDialog fd = new FileDialog(new Shell(display), SWT.SAVE);
		fd.setText("Save");
		fd.setFilterPath("C:/");
		String[] filterExt = { "*.xml", "*.XML", "*.*" };
		fd.setFilterExtensions(filterExt);
		String filename = fd.open();
		
		vm.WriteXML(filename);
	}
	
	public void setSelection(ISelection select) {
		Object[] list = listeners.getListeners();
		for (int i = 0; i < listeners.size(); ++i) {
			((ISelectionChangedListener) list[i])
					.selectionChanged(new SelectionChangedEvent(this, select));
		}
	}
}