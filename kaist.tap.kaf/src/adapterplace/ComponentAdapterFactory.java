package adapterplace;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;

import kaist.tap.kaf.component.*;
 
public class ComponentAdapterFactory implements IAdapterFactory {
 
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		
		if (adapterType== IPropertySource.class && adaptableObject instanceof Component){
			Component comp = (Component) adaptableObject;
			System.out.println(comp.getName());
			return new ComponentAdapter((Component) adaptableObject);
		} 
		
		return null;
	}
 
	@Override
	public Class[] getAdapterList() {
		// TODO Auto-generated method stub
		//return null;
		return new Class[] {IPropertySource.class};
	}
}
