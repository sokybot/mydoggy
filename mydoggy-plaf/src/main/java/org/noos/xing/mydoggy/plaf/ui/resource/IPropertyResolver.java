package org.noos.xing.mydoggy.plaf.ui.resource;


public abstract class  IPropertyResolver {

	protected  final String STR_PREFIX = "$STR" ; 
	protected  final String INT_PREFIX = "$INT" ; 
	protected  final String FLOAT_PREFIX = "$FLOAT" ; 
	protected  final String BOOLEAN_PREFIX = "$BOOL" ; 
	protected  final String ICON_PREFIX = "$ICON" ; 
	protected  final String IMAGE_PREFIX = "$IMAGE" ; 
	protected  final String RGB_PREFIX = "$RGB" ; 
	protected  final String UI_PREFIX = "$UI" ; 
	protected  final String INSETS_PREFIX = "$INSETS" ; 
	
	
	public abstract Object resolve(String value) ; 
	
     private String extractValue(String val) {
		int b = val.indexOf("{") ; 
		int e = val.lastIndexOf("}") ; 
		
		if(b == -1 || e == -1) { 
			throw new IllegalArgumentException(val + " is not valid expression"); //use other exception
		}
		
		
		return val.substring(b + 1 , e);
	} 
     
    protected String getOrThrow(String value) { 
    	String propVal = extractValue(value) ; 
    	if(propVal.isBlank()) 
    		throw new IllegalArgumentException("Property value could not be blank");
    	return propVal ; 
    }
}
