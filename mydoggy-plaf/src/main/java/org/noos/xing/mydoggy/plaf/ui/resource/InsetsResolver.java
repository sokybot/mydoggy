package org.noos.xing.mydoggy.plaf.ui.resource;

import java.awt.Insets;

public class InsetsResolver extends IPropertyResolver {

	
	
	@Override
	public Object resolve(String value) {
	

		if(value.startsWith(INSETS_PREFIX)) { 
			String propVal = getOrThrow(value).trim();
			
			if(propVal.matches("((\\d+) *, *(\\d+) *, *(\\d+) *, *(\\d+))")) { 
				String[] insets = propVal.split(",");
				
					return new Insets(Integer.parseInt(insets[0].trim()), Integer.parseInt(insets[1].trim()),
							Integer.parseInt(insets[2].trim()), Integer.parseInt(insets[3].trim()));
				

			}else throw new IllegalArgumentException("Expected insets 'top , left , bottom , right' but it was " +
													propVal); 
			
		}else { 
			throw new IllegalArgumentException("Could not resolve expression " + value) ; 
		}
		
	
	}
	
	
}
