package org.noos.xing.mydoggy.plaf.ui.resource;


import javax.swing.UIManager;

public class UIPropertyResolver extends IPropertyResolver {

	private IPropertyResolver next ; 
	
	protected UIPropertyResolver(IPropertyResolver resolver) {
		this.next = resolver ; 
	}
	
	@Override
	public Object resolve(String value) {
	 
		if(value.startsWith(UI_PREFIX)) { 
			String propVal = getOrThrow(value) ;
			
			Object obj =  UIManager.get(propVal) ;
		    if(obj == null) { 
		    	throw new IllegalArgumentException("UIManager Property " + propVal + " undefined ") ; 
		    }
		    return obj ; 
		}
		return next.resolve(value) ; 
	}
}
