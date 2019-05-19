package net.winroad.wrdoclet.doc;

import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import net.winroad.wrdoclet.data.OpenAPI;

@Data
public class Doc {

	private String tag;

	private String description;

	private List<OpenAPI> detailApis;

	private List<API> APIs = new LinkedList<API>();

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<API> getAPIs() {
		return APIs;
	}

	public void setAPIs(List<API> aPIs) {
		APIs = aPIs;
	}
}
