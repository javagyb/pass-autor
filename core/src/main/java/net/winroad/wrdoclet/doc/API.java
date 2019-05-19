package net.winroad.wrdoclet.doc;

import java.util.List;
import lombok.Data;
import net.winroad.wrdoclet.data.OpenAPI;

@Data
public class API {
	private String url;
	private String filepath;
	private String brief;
	private String methodType;
	private String tooltip;
  private String description;
  private String tag;
	private List<OpenAPI> apis;
}
