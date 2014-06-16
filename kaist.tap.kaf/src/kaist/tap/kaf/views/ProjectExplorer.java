package kaist.tap.kaf.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import kaist.tap.kaf.manager.*;

public class ProjectExplorer extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "kaist.tap.kaf.views.ProjectExplorer";

	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	private String[] treeRoot = new String[1];

	class ViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider {

		private View[] children;

		public ViewContentProvider() {
			super();
			treeRoot[0] = new String("Untitled");
		}

		@Override
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object parent) {
			if (parent instanceof View[]) {
				children = (View[]) parent;
			}
			return treeRoot;
		}

		@Override
		public Object getParent(Object child) {
			if (child instanceof View) {
				return treeRoot[0];
			}
			return null;
		}

		@Override
		public Object[] getChildren(Object parent) {
			if (parent instanceof View) {
				return null;
			} else if (parent instanceof String) {
				return children;
			} else
				return null;
		}

		@Override
		public boolean hasChildren(Object parent) {
			if (parent instanceof String)
				return true;
			return false;
		}
	}

	class ViewLabelProvider extends LabelProvider {

		@Override
		public String getText(Object obj) {
			if (obj instanceof View) {
				View v = (View) obj;
				return v.getName();
			} else
				return obj.toString();
		}

		@Override
		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof String)
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(imageKey);
		}
	}

	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public ProjectExplorer() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getElements());
		viewer.expandAll();

		viewer.setCellEditors(new CellEditor[] { new TextCellEditor(viewer
				.getTree()) });
		viewer.setCellModifier(new ICellModifier() {

			@Override
			public void modify(Object element, String property, Object value) {
				TreeItem item = (TreeItem) element;
				Object data = item.getData();

				if (data instanceof String) {
					item.setData(new String((String) value));
					item.setText((String) value);
				}
			}

			@Override
			public Object getValue(Object element, String property) {
				return element.toString();
			}

			@Override
			public boolean canModify(Object element, String property) {
				if (property.equals("Title")) {
					return true;
				} else
					return false;
			}
		});

		viewer.setColumnProperties(new String[] { "Title", "View1", "View2" });

		getSite().setSelectionProvider(viewer);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
			}
		});

		TreeViewerEditor.create(viewer,
				new ColumnViewerEditorActivationStrategy(viewer) {
					@Override
					protected boolean isEditorActivationEvent(
							ColumnViewerEditorActivationEvent event) {
						return event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION;
					}
				}, ColumnViewerEditor.KEEP_EDITOR_ON_DOUBLE_CLICK
						| ColumnViewerEditor.KEYBOARD_ACTIVATION);

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem()
				.setHelp(viewer.getControl(), "kaist.tap.kaf.viewer");
		makeActions();
		hookContextMenu();
		// hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				ProjectExplorer.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		// manager.add(action1);
		// manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		action1 = new Action() {
			@Override
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		action2 = new Action() {
			@Override
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			@Override
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();
				if (obj instanceof String) {
					showMessage("Double-click detected on " + obj.toString());
				}
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(),
				"Project Explorer", message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	private View[] getElements() {
		View[] views = new View[2];

		views[0] = new LogicalView();
		views[1] = new RunTimeView();

		return views;
	}
}