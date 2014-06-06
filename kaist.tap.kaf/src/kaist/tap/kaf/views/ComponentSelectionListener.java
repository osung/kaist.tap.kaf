package kaist.tap.kaf.views;

import kaist.tap.kaf.component.Component;
import kaist.tap.kaf.manager.View;

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
			Object obj = ((IStructuredSelection)sel).getFirstElement();
			if (obj instanceof String) return;
			
			ISelection selected = (ISelection) obj;
			Object current = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
			if (selected != current && (selected instanceof Component || selected instanceof View)) {
				viewer.setSelection(sel);
				if (viewer instanceof StructuredViewer) {
					((StructuredViewer) viewer).reveal(selected);
				}
			}
		}
	}
}
