package org.noos.xing.mydoggy.plaf.ui.look;

import org.noos.common.context.Context;
import org.noos.common.object.ObjectCreator;
import org.noos.common.object.ObjectCustomizer;
import org.noos.xing.mydoggy.plaf.PropertyChangeEventSource;
import org.noos.xing.mydoggy.plaf.ui.ResourceManager;
import org.noos.xing.mydoggy.plaf.ui.resource.IPropertyResolver;
import org.noos.xing.mydoggy.plaf.ui.resource.ImageResolver;
import org.noos.xing.mydoggy.plaf.ui.resource.InsetsResolver;
import org.noos.xing.mydoggy.plaf.ui.resource.PrimativeResolver;
import org.noos.xing.mydoggy.plaf.ui.resource.RGBResolver;
import org.noos.xing.mydoggy.plaf.ui.resource.UIPropertyResolver;
import org.noos.xing.mydoggy.plaf.ui.transparency.TransparencyManager;
import org.noos.xing.mydoggy.plaf.ui.transparency.WindowTransparencyManager;
import org.noos.xing.mydoggy.plaf.ui.util.DummyResourceBundle;
import org.noos.xing.mydoggy.plaf.ui.util.FindFocusableQuestion;
import org.noos.xing.mydoggy.plaf.ui.util.MyDoggyUtil;
import org.noos.xing.mydoggy.plaf.ui.util.ParentOfQuestion;
import org.noos.xing.mydoggy.plaf.ui.util.SwingUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.function.Function;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class MyDoggyResourceManager extends PropertyChangeEventSource implements ResourceManager {

	private static final String resourceName = "mydoggy_defaults.properties";

	protected ClassLoader classLoader;
	protected Properties resources;

	protected Map<String, ObjectCreator<Component>> cmpCreators;
	protected Map<String, ObjectCreator<ComponentUI>> cmpUiCreators;
	protected Map<String, ObjectCustomizer<Component>> cmpCustomizers;
	protected Map<Class, ObjectCreator> instanceCreators;

	protected String bundlePath;
	protected ResourceBundle resourceBundle;
	protected ResourceBundle userResourceBundle;
	private IPropertyResolver propertyResolver;

	protected TransparencyManager<Window> transparencyManager;

	public MyDoggyResourceManager() {
		// To initialize the resource manager use @link{setClassLoader}
		this.propertyResolver = new InsetsResolver();
		this.propertyResolver = new UIPropertyResolver(propertyResolver);
		this.propertyResolver = new PrimativeResolver(propertyResolver);
		this.propertyResolver = new RGBResolver(propertyResolver);
		this.propertyResolver = new ImageResolver(propertyResolver, classLoader);
	}

	public void setClassloader(ClassLoader classLoader) {
		this.classLoader = classLoader;

		// Now load all the resources...
		loadResources();
		initComponentCreators();
		initTransparencyManager();

		// Put a flag
		UIManager.put(ResourceManager.class, this);
	}

	private Optional<Properties> findPropForlaf(String name) {
		System.out.println("Try loading properties for " + name);
		String target = name;
		target = target.toLowerCase().replaceAll("[ \\t]+", "_");
		target = "mydoggy_" + target + ".properties";
		Properties prop = null;
		try {
			prop = SwingUtil.loadPropertiesFile(target, classLoader);
			return Optional.of(prop);
		} catch (IllegalArgumentException ex) {
			return Optional.empty();
		}

	}

	private void load(Properties src, boolean override) {
		src.keySet().stream().filter((k) -> ((override) || (!override && !UIManager.getDefaults().contains(k))))
				.map((k) -> k.toString()).forEach((k) -> loadResource(k.toString(), src.getProperty(k)));
	}

	/**
	 * vpc
	 */
	protected void onLookAndFeelChanged() {
		String n = UIManager.getLookAndFeel().getName();

		Properties resources = SwingUtil.loadPropertiesFile("mydoggy_defaults.properties", classLoader);
		findPropForlaf(n).ifPresentOrElse((lafProps) -> resources.putAll(lafProps),
				() -> System.out.println("No Laf Properties for " + n));

		load(resources, true);
		

//		
//		for (Object key : resources.keySet()) {
//			String strKey = key.toString();
//			//int pointIndex = strKey.indexOf('.');
//			//if (pointIndex != -1) {
//			//	String prefix = strKey.substring(0, pointIndex);
//			//	String propertyName = strKey.substring(prefix.length() + 1);
//
//				// if (UIManager.getDefaults().containsKey(propertyName)) {
//				loadResource(strKey, resources.getProperty(strKey));
//				// }
//			//}
//		}
//
//		Color defColor = (Color) UIManager.get("Button.background");
//		if (defColor == null) {
//			defColor = new JButton().getBackground();
//		}
//		Color selColor = (Color) UIManager.get("CheckBoxMenuItem.selectionBackground");
//		if (selColor == null) {
//			selColor = new JButton().getBackground();
//		}
//       // bb=Color.RED;
//		UIManager.put("ToolWindowTitleBarUI.background.active.start", new Color(145, 181, 255));
//		UIManager.put("ToolWindowTitleBarUI.background.active.end", new Color(96, 123, 183));
//		UIManager.put("ToolWindowTitleBarUI.background.inactive.start", new Color(193, 189, 182));
//		UIManager.put("ToolWindowTitleBarUI.background.inactive.end", new Color(167, 164, 157));
//		UIManager.put("ToolWindowTitleBarUI.id.background.flashing.on", new Color(183, 200, 232));
//		UIManager.put("ToolWindowTitleBarUI.id.background.flashing.off", new Color(192, 192, 192));
//		UIManager.put("ToolWindowTitleBarUI.id.background.animating", new Color(192, 192, 192));
//		UIManager.put("ToolWindowTitleBarUI.id.background.active", new Color(183, 200, 232));
//		UIManager.put("ToolWindowTitleBarUI.id.background.inactive", new Color(192, 192, 192));
//		UIManager.put("ToolWindowTitleBarUI.id.foreground.active", new Color(0, 0, 0));
//		UIManager.put("ToolWindowTitleBarUI.id.foreground.inactive", new Color(128, 128, 128));
//		UIManager.put("ToolWindowTitleBarUI.tab.foreground.selected", new Color(255, 255, 255));
//		UIManager.put("Color.ToolWindowTitleBarUI.tab.foreground.unselected", new Color(212, 212, 212));
//		UIManager.put("ToolWindowRepresentativeAnchorUI.border.mouseIn", new Color(0, 0, 0));
//		UIManager.put("ToolWindowRepresentativeAnchorUI.border.mouseOut", new Color(100, 100, 100));
//		UIManager.put("ToolWindowRepresentativeAnchorUI.background.active.start", selColor);
//		UIManager.put("ToolWindowRepresentativeAnchorUI.background.active.end", selColor.darker());
//		UIManager.put("ToolWindowRepresentativeAnchorUI.background.inactive", defColor); // new Color(247, 243, 239)
//		UIManager.put("ToolWindowRepresentativeAnchorUI.foreground", new Color(0, 0, 0));
//		UIManager.put("ToolWindowRepresentativeAnchorUI.foreground.unavailable", new Color(128, 128, 128));
	}

	protected void loadResources() {
		// Check for the flag
		if (isAlreadyLoaded()) {
			return;
		}

		// Load defaults from file
		resources = SwingUtil.loadPropertiesFile(resourceName, classLoader);
		String currentLaf = UIManager.getLookAndFeel().getName();

		findPropForlaf(currentLaf).ifPresentOrElse((lafProps) -> resources.putAll(lafProps),
				() -> System.out.println("No Laf Properties for " + currentLaf));

		load(resources, false);

//		for (Object key : resources.keySet()) {
//			String strKey = key.toString();
//			//int pointIndex = strKey.indexOf('.');
//			//if (pointIndex != -1) {
//				//String prefix = strKey.substring(0, pointIndex);
//				//String propertyName = strKey.substring(prefix.length() + 1);
//
//				if (!UIManager.getDefaults().containsKey(strKey)) {
//					loadResource(strKey, resources.getProperty(strKey));
//				}
//			//}
//		}
		/**
		 * vpc
		 */
		// onLookAndFeelChanged();

		/**
		 * vpc
		 */
		UIManager.addPropertyChangeListener(new PropertyChangeListener() {
			// @Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("lookAndFeel".equals(evt.getPropertyName())) {
					System.out.println("LookAndFeel changed");
					onLookAndFeelChanged();
				}
			}
		});

		// Load static
		loadObjects();
	}

	protected void loadResource(String name, String value) {

		if (name.startsWith("ResourceBundle")) {
			this.bundlePath = value;
			// JOptionPane.showConfirmDialog(null, "ResouceBundle : " + value) ;
			return;
		}

		Object objVal = this.propertyResolver.resolve(value);
		if (objVal instanceof Icon) {
			putIcon(name, (Icon) objVal);
		} else if (objVal instanceof BufferedImage) {
			putImage(name, (BufferedImage) objVal);
		} else if (objVal instanceof Color) {
			putColor(name, (Color) objVal);
		} else if (objVal instanceof String) {
			putProperty(name, (String) objVal);
		} else if (objVal instanceof Integer) {
			putInt(name, (Integer) objVal);
		} else if (objVal instanceof Float) {
			putFloat(name, (Float) objVal);
		} else if (objVal instanceof Boolean) {
			putBoolean(name, (Boolean) objVal);
		} else {
			putObject(name, objVal); // Insets Objects included here

		}

	}

	public <T> T createInstance(Class<T> clazz, Context context) {
		return (T) instanceCreators.get(clazz).create(context);
	}

	public <T extends Component> T createComponent(String key, Context context) {
		return (T) applyCustomization(key, cmpCreators.get(key).create(context), context);
	}

	public ComponentUI createComponentUI(String key, Context context) {
		return cmpUiCreators.get(key).create(context);
	}

	public <T extends Component> T applyCustomization(String key, T component, Context context) {
		if (cmpCustomizers.containsKey(key)) {
			cmpCustomizers.get(key).customize(component, context);
		}
		return component;
	}

	public Icon getIcon(String id) {
		return UIManager.getIcon(id);
	}

	public Icon putIcon(String id, Icon icon) {
		Icon oldIcon = (Icon) UIManager.put(id, icon);

		List<String> icons = (List<String>) UIManager.get("mydoggy.icons");
		if (icons == null) {
			icons = new ArrayList<String>();
			UIManager.put("mydoggy.icons", icons);
		} else {

			if (!icons.contains(id)) {
				icons.add(id);
			}
		}

		return oldIcon;
	}

	public Color getColor(String id) {
		return UIManager.getColor(id);
	}

	public Color putColor(String id, Color color) {
		Color oldColor = (Color) UIManager.put(id, color);

		List<String> colors = (List<String>) UIManager.get("mydoggy.colors");
		if (colors == null) {
			colors = new ArrayList<String>();
			UIManager.put("mydoggy.colors", colors);
		} else {
			if (!colors.contains(id)) {
				colors.add(id);

			}
		}

		return oldColor;
	}

	public void putImage(String name, BufferedImage bufferedImage) {
		UIManager.put(name, bufferedImage);
	}

	public BufferedImage getImage(String id) {
		return (BufferedImage) UIManager.get(id);
	}

	public TransparencyManager<Window> getTransparencyManager() {
		return transparencyManager;
	}

	public void setTransparencyManager(TransparencyManager<Window> transparencyManager) {
		this.transparencyManager = transparencyManager;
		UIManager.put(TransparencyManager.class, transparencyManager);
	}

	public void setLocale(Locale locale) {
		if (UIManager.get("mydoggy.resourceBundle") != null) {
			this.resourceBundle = (ResourceBundle) UIManager.get("mydoggy.resourceBundle");
			this.bundlePath = (String) UIManager.get("mydoggy.bundlePath");
		} else {
			this.resourceBundle = loadResourceBundle(locale, bundlePath);
			UIManager.put("mydoggy.resourceBundle", resourceBundle);
			UIManager.put("mydoggy.bundlePath", bundlePath);
		}
	}

	public void setUserBundle(Locale locale, String bundle, ClassLoader classLoader) {
		this.userResourceBundle = loadResourceBundle(locale, bundle, classLoader);
		UIManager.put("mydoggy.resourceBundle.user", resourceBundle);
	}

	public void setUserBundle(ResourceBundle userBundle) {
		if (userBundle == null) {
			this.userResourceBundle = new DummyResourceBundle();
		} else {
			this.userResourceBundle = userBundle;
		}
		UIManager.put("mydoggy.resourceBundle.user", userResourceBundle);
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public ResourceBundle getUserResourceBundle() {
		return userResourceBundle;
	}

	public String getString(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (Exception e) {
			return key;
		}
	}

	public String getUserString(String key) {
		try {
			return (userResourceBundle != null) ? userResourceBundle.getString(key) : key;
		} catch (Exception e) {
			return key;
		}
	}

	public java.util.List<String> getColors() {
		return (List<String>) UIManager.get("mydoggy.colors");
	}

	public java.util.List<String> getIcons() {
		return (List<String>) UIManager.get("mydoggy.icons");
	}

	public String getProperty(String name) {
		return UIManager.getString(name);
	}

	public void putProperty(String name, String value) {
		UIManager.put(name, value);
	}

	public boolean getBoolean(String name, boolean defaultValue) {
		return SwingUtil.getBoolean(name, defaultValue);
	}

	public void putBoolean(String name, boolean value) {
		UIManager.put(name, value);
	}

	public float getFloat(String name, float defaultValue) {
		return SwingUtil.getFloat(name, defaultValue);
	}

	public void putFloat(String name, float value) {
		UIManager.put(name, value);
	}

	public int getInt(String name, int defaultValue) {
		return SwingUtil.getInt(name, defaultValue);
	}

	public void putInt(String name, int value) {
		UIManager.put(name, value);
	}

	public void putObject(Object key, Object value) {
		UIManager.put(key, value);
	}

	public <T> T getObject(Class<T> clazz, T defaultValue) {
		Object value = UIManager.get(clazz);
		if (clazz.isInstance(value)) {
			return (T) value;
		}
		return defaultValue;
	}

	public void putInstanceCreator(Class aClass, ObjectCreator instanceCreator) {
		instanceCreators.put(aClass, instanceCreator);
	}

	public void putComponentCreator(String key, ObjectCreator<Component> componentCreator) {
		cmpCreators.put(key, componentCreator);
	}

	public void putComponentCustomizer(String key, ObjectCustomizer<Component> componentCustomizer) {
		cmpCustomizers.put(key, componentCustomizer);
	}

	public void putComponentUICreator(String key, ObjectCreator<ComponentUI> componentUICreator) {
		cmpUiCreators.put(key, componentUICreator);
	}

	protected void loadObjects() {
		putObject(FindFocusableQuestion.class, new FindFocusableQuestion());
	}

	protected boolean isAlreadyLoaded() {
		return UIManager.get(ResourceManager.class) != null;
	}

	protected void initComponentCreators() {
		cmpCreators = new Hashtable<String, ObjectCreator<Component>>();
		cmpUiCreators = new Hashtable<String, ObjectCreator<ComponentUI>>();

		cmpCustomizers = new Hashtable<String, ObjectCustomizer<Component>>();

		instanceCreators = new Hashtable<Class, ObjectCreator>();
		instanceCreators.put(ParentOfQuestion.class, new ParentOfQuestionInstanceCreator());
	}

	protected ResourceBundle loadResourceBundle(Locale locale, String bundle) {
		ResourceBundle result = loadResourceBundle(locale, bundle, classLoader);
		if (result instanceof DummyResourceBundle) {
			result = loadResourceBundle(locale, bundle, this.getClass().getClassLoader());
		}

		if (result instanceof DummyResourceBundle) {
			result = loadResourceBundle(locale, bundle, Thread.currentThread().getContextClassLoader());
		}

		if (result instanceof DummyResourceBundle) {
			result = loadResourceBundle(locale, bundle, ClassLoader.getSystemClassLoader());
		}

		return result;
	}

	protected ResourceBundle loadResourceBundle(Locale locale, String bundle, ClassLoader classLoader) {
		ResourceBundle result;
		if (locale == null) {
			locale = Locale.getDefault();
		}

		try {
			if (classLoader == null) {
				result = ResourceBundle.getBundle(bundle, locale);
			} else {
				result = ResourceBundle.getBundle(bundle, locale, classLoader);
			}
		} catch (Throwable e) {
			e.printStackTrace();
			result = new DummyResourceBundle();
		}
		return result;
	}

	protected void initTransparencyManager() {
		if (isAlreadyLoaded()) {
			return;
		}

		setTransparencyManager(new WindowTransparencyManager());
	}

	public static class ParentOfQuestionInstanceCreator implements ObjectCreator {

		public Object create(Context context) {
			return new ParentOfQuestion(context.get(Component.class));
		}

	}

}
