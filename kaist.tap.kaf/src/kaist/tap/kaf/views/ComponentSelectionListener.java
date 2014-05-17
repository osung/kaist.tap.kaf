package kaist.tap.kaf.views;

import kaist.tap.kaf.component.Component;

import org.eclipse.jface.viewers.*;
import org.eclipse.ui.*;

public class ComponentSelectionListener implements ISelectionListener {
	private ISelectionProvider viewer;
	private IWorkbenchPart part;
	
	public ComponentSelectionListener(ISelectionProvider v, IWorkbenchPart p) {
		this.viewer = v;
		this.part = p;
	}
	
	public void selectionChanged(IWorkbenchPart p, ISelection sel) {
		if (p != this.part) {
			ISelection selected = (ISelection) ((IStructuredSelection)sel).getFirstElement();
			Object current = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
			if (selected != current && selected instanceof Component) {
				viewer.setSelection(sel);
				if (viewer instanceof StructuredViewer) {
					((StructuredViewer) viewer).reveal(selected);
				}
			}
		}
	}
}
