package org.noos.xing.mydoggy.plaf.ui.resource;

import java.awt.Color;
import java.util.MissingFormatArgumentException;
import java.util.UnknownFormatConversionException;

public class RGBResolver extends IPropertyResolver {

	private IPropertyResolver next;

	public RGBResolver(IPropertyResolver resolver) {
		this.next = resolver;
	}

	@Override
	public Object resolve(String value) {

		if (value.startsWith(RGB_PREFIX)) {
			Color color = null;

			String colorDef = getOrThrow(value).trim().toLowerCase();
			if (colorDef.matches("((\\d{1,3}) *, *(\\d{1,3}) *, *(\\d{1,3}))")) {
				String[] elms = colorDef.split(",");
				if (elms.length == 3) {

					color = new Color(Integer.parseInt(elms[0].trim()), Integer.parseInt(elms[1].trim()),
							Integer.parseInt(elms[2].trim()));
				}
			} else if (colorDef.matches("((?:#|0x)([a-f0-9]{6}))")) {
				color = Color.decode(colorDef);
			} else if ("black".equals(colorDef)) {
				color = Color.BLACK;
			} else if ("blue".equals(colorDef)) {
				color = Color.BLUE;
			} else if ("cyan".equals(colorDef)) {
				color = Color.CYAN;
			} else if ("dark_grey".equals(colorDef)) {
				color = Color.DARK_GRAY;
			} else if ("gray".equals(colorDef)) {
				color = Color.GRAY;
			} else if ("green".equals(colorDef)) {
				color = Color.GREEN;
			} else if ("magenta".equals(colorDef)) {
				color = Color.MAGENTA;
			} else if ("orange".equals(colorDef)) {
				color = Color.ORANGE;
			} else if ("pink".equals(colorDef)) {
				color = Color.PINK;
			} else if ("red".equals(colorDef)) {
				color = Color.RED;
			} else if ("white".equals(colorDef)) {
				color = Color.WHITE;
			} else if ("yellow".equals(colorDef)) {
				color = Color.YELLOW;
			} else
				throw new IllegalArgumentException("Could not resolve property value " + value);

			return color;
		}

		return this.next.resolve(value);
	}

}
