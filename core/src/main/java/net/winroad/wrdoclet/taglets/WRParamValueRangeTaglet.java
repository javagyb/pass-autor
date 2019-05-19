package net.winroad.wrdoclet.taglets;

import com.sun.tools.doclets.Taglet;

import java.util.Map;

public class WRParamValueRangeTaglet extends AbstractWRParamTaglet {
	public static final String NAME = "ValueRange";

	@Override
	public String getName() {
		return NAME;
	}

	/**
	 * Register this Taglet.
	 * 
	 * @param tagletMap
	 *            the map to register this tag to.
	 */
	@SuppressWarnings("unchecked")
	public static void register(@SuppressWarnings("rawtypes") Map tagletMap) {
		WRParamValueRangeTaglet tag = new WRParamValueRangeTaglet();
		Taglet t = (Taglet) tagletMap.get(tag.getName());
		if (t != null) {
			tagletMap.remove(tag.getName());
		}
		tagletMap.put(tag.getName(), tag);
	}
}
