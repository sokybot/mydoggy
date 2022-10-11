package org.noos.xing.mydoggy.plaf.ui.resource;

public class PrimativeResolver extends  IPropertyResolver{

	private IPropertyResolver next ; 
	
	
	public PrimativeResolver(IPropertyResolver resolver) { 
		this.next = resolver ; 
	}
	
	@Override
	public Object resolve(String value) {
	  
		
		if(value.startsWith(STR_PREFIX)) { 
			return  getOrThrow(value).trim(); 
		}else if(value.startsWith(INT_PREFIX)) { 
			return Integer.parseInt(getOrThrow(value).trim()) ; 
		}else if(value.startsWith(FLOAT_PREFIX)) { 
			return Float.parseFloat(getOrThrow(value).trim()) ; 
		}else if(value.startsWith(BOOLEAN_PREFIX)) { 
			return Boolean.parseBoolean(getOrThrow(value).trim()) ; 
		}
		
		return this.next.resolve(value) ; 
	}
}
