package org.noos.xing.mydoggy.plaf.ui.resource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class ImageResolver extends IPropertyResolver {

	private IPropertyResolver next ; 
	private ClassLoader classLoader ; 
	
	public ImageResolver(IPropertyResolver resolver , ClassLoader uiClassLoader) {
		this.next = resolver ; 
		this.classLoader = uiClassLoader ; 
		
	}
	
	@Override
	public Object resolve(String value) {
		
		if(value.startsWith(ICON_PREFIX)) { 
			String propVal = getOrThrow(value) ; 			
			URL iconUrl = getUrl(propVal) ; 
			return new ImageIcon(iconUrl) ; 
		}else if(value.startsWith(IMAGE_PREFIX)) { 
			String propVal = getOrThrow(value) ; 
			try {
				return ImageIO.read(getUrl(propVal)) ;
			} catch (IOException e) {
				throw new UncheckedIOException("Could not read image from url " + value , e ) ;
			} 
		}
		
		return this.next.resolve(value) ; 
	}
	
	private  URL getUrl(String url ) {
		URL result = null;

		if (this.classLoader != null) {
			result = this.classLoader.getResource(url);
		}

		if (result == null) {
			result = getClass().getClassLoader().getResource(url);
		}

		if (result == null) {
			result = Thread.currentThread().getContextClassLoader().getResource(url);
		}

		if (result == null) {
			result = ClassLoader.getSystemClassLoader().getResource(url);
		}
		
		if(result == null)  
			throw new IllegalArgumentException("Invalid resouce url " + url) ;
		
		return result;
	}
	
	
}
