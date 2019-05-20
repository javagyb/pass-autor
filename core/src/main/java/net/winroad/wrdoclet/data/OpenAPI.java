package net.winroad.wrdoclet.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.github.jsonzou.jmockdata.JMockData;
import com.github.jsonzou.jmockdata.MockConfig;
import com.sun.javadoc.Tag;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

@Data
public class OpenAPI {
	/*
	 * the description for this API
	 */
	private String description;
	/*
	 * the brief description for this API
	 */
	private String brief;
	private RequestMapping requestMapping;
	private ModificationHistory modificationHistory;
	private APIParameter outParameter;
	private List<APIParameter> inParameters = new LinkedList<APIParameter>();
	private String qualifiedName;
	private int authNeeded;
	private String remark;
	private Set<String> tags = new HashSet<String>();
	private boolean deprecated = false;
	private String packageName;
	private String inParametersDemos;
	private String outParametersDemos;

	private ObjectMapper mapper = new ObjectMapper();

//	public List<APIParameter> getOutParameter4List(){
//		if (getOutParameter()==null){
//			return new ArrayList<>()
//		}
//	}


	public String getPackage(){
        System.out.println(getQualifiedName());
		int indexOf = getQualifiedName().lastIndexOf(".");
        int indexOf1 = getQualifiedName().substring(0, indexOf).lastIndexOf(".");
        return getQualifiedName().substring(0,indexOf1);
	}

	public String getSimpleName(){
		String[] split = getQualifiedName().split("\\.");
		String interfaceName = split[split.length-2];
		String methodName = split[split.length-1];
		return interfaceName+"."+methodName;
	}

	public String getMethodCode(){
		String responseName = "void";
		if(getOutParameter()!=null){
			String type = getOutParameter().getType();
			if(type.contains("<")){
				String wapper = StringUtils.substring(type, 0, type.indexOf("<"));
				String[] split = StringUtils.substring(type, type.indexOf("<") + 1, type.indexOf(">")).split("\\,");
				String join = String.join(",", Stream.of(split).map(this::getSimpleType).collect(Collectors.toList()));
				join = "<"+join+">";
				responseName = getSimpleType(wapper)+join;
			}else {
				String[] strings = StringUtils.split(type, "\\.");
				responseName = strings[strings.length-1];
			}
		}
		String inParametersString  = "";
		if(!CollectionUtils.isEmpty(getInParameters())){
			List<String> inParametersStringList = new ArrayList<>();
			for (APIParameter apiParameter: getInParameters()){
				String[] strings = StringUtils.split(apiParameter.getType(), "\\.");
				inParametersStringList.add(strings[strings.length-1]+" "+apiParameter.getName());
			}
			inParametersString = StringUtils.join(inParametersStringList," , ");
		}
		String[] split = getQualifiedName().split("\\.");
		String methodName = split[split.length-1];
		return responseName+" "+methodName+"( "+inParametersString+");";
	}

	public String getOutParametersJson(){
		if(outParameter==null){
			return "";
		}
		final  Map<String,Object> values = new TreeMap<>();
		if(CollectionUtils.isEmpty(outParameter.getFields())){
			 values.putAll(getMapValue(outParameter));
		}else {
			outParameter.getFields().forEach(x->{
				values.putAll(getMapValue(x));
			});
		}
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(values);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getInParametersJson(){
		if(inParameters.isEmpty()){
			return "";
		}
		List<Object> values = new ArrayList<>();
//		Map<String,Object> values = new LinkedHashMap<>();
		for (APIParameter x: inParameters){
			Map<String, Object> mapValue = getMapValue(x);
			if(!CollectionUtils.isEmpty(mapValue)){
				if(mapValue.size()==1){
					values.add(mapValue.values().iterator().next());
				}else {
					values.add(mapValue);
				}
			}
		}
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(values);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

	protected Map<String,Object> getMapValue(APIParameter apiParameter){
		if(StringUtils.isEmpty(apiParameter.getName()) && !CollectionUtils.isEmpty(apiParameter.getFields())){
			Map<String,Object> temp = new LinkedHashMap<>();
			for (APIParameter x: apiParameter.getFields()){
				Map<String, Object> mapValue = getMapValue(x);
				temp.putAll(mapValue);
			}
			return temp;
		}
		Map<String,Object> values = new LinkedHashMap<>();
		String type = apiParameter.getType();
		if(CollectionUtils.isEmpty(apiParameter.getFields())){
			Class<?> typeClass =null;
			try {
				typeClass = ClassUtils.forName(type,getClass().getClassLoader());
			} catch (ClassNotFoundException e) {
			}
			Object value  = null;
			MockConfig mockConfig = new MockConfig();
			mockConfig.longRange(10000_0000_0000L,20000_0000_0000L);
			mockConfig.intRange(0,200);
			value = apiParameter.getDescription();
			if(typeClass!=null && !String.class.isAssignableFrom(typeClass)){
				value  = JMockData.mock(typeClass,mockConfig);
			}

			if(!"N/A".equalsIgnoreCase(apiParameter.getValueRange())){
				value = apiParameter.getValueRange();

			}


			if(apiParameter.getExample()!=null && !"".equalsIgnoreCase(apiParameter.getExample().getValue())){
				value = apiParameter.getExample().getValue();
			}

			if(Objects.equals("从0开始",value)){
				value  = JMockData.mock(typeClass,mockConfig);
			}

			if(typeClass!=null && value!=null){
				if(Integer.class.isAssignableFrom(typeClass)){
					values.put(apiParameter.getName(),Integer.valueOf(String.valueOf(value)));
				}
				if(Float.class.isAssignableFrom(typeClass)){
					values.put(apiParameter.getName(),Float.valueOf(String.valueOf(value)));
				}
				if(Double.class.isAssignableFrom(typeClass)){
					values.put(apiParameter.getName(),Double.valueOf(String.valueOf(value)));
				}
				if(Long.class.isAssignableFrom(typeClass)){
					values.put(apiParameter.getName(),Long.valueOf(String.valueOf(value)));
				}
				if(String.class.isAssignableFrom(typeClass)){
					values.put(apiParameter.getName(),String.valueOf(value));
				}
				if(Boolean.class.isAssignableFrom(typeClass)){
					values.put(apiParameter.getName(),value);
				}
			}else {
				values.put(apiParameter.getName(),value);
			}

		}else {
			if(StringUtils.startsWithIgnoreCase(type,"java.util.List")||StringUtils.startsWithIgnoreCase(type,"java.util.Set")){
				List<Object> objects = new ArrayList<>();
				if(!CollectionUtils.isEmpty(apiParameter.getFields())&& apiParameter.getFields().get(0)!=null && !CollectionUtils.isEmpty( apiParameter.getFields().get(0).getFields())){
					Map<String,Object> temp = new LinkedHashMap<>();
					for (APIParameter x: apiParameter.getFields().get(0).getFields()){
						Map<String, Object> mapValue = getMapValue(x);
						temp.putAll(mapValue);
					}
					objects.add(temp);
					values.put(apiParameter.getName(),objects);
				}
			}else {
				Map<String,Object> temp = new LinkedHashMap<>();
				for (APIParameter x: apiParameter.getFields()){
					Map<String, Object> mapValue = getMapValue(x);
					temp.putAll(mapValue);
				}
				values.put(apiParameter.getName(),temp);
			}
		}
		return values;
	}


	private String  getSimpleType(String type){
		String[] strings = StringUtils.split(type, "\\.");
		return strings[strings.length-1];
	}
	/*
	 * Possible return code list.
	 */
	private String returnCode;

	/*
	 * @return Whether this API has modification on specified version. If no
	 * version specified, returns true.
	 */
	public boolean isModifiedOnVersion(String version) {
		if( this.modificationHistory.isModifiedOnVersion(version)
				|| this.outParameter.isModifiedOnVersion(version) ) {
			return true;
		}
		for(APIParameter inParameter : inParameters) {
			if(inParameter.isModifiedOnVersion(version)) {
				return true;
			}
		}
		return false;
	}

	public ModificationHistory getModificationHistory() {
		return modificationHistory;
	}

	public void setModificationHistory(ModificationHistory modificationHistory) {
		this.modificationHistory = modificationHistory;
	}

	public APIParameter getOutParameter() {
		return outParameter;
	}

	public void setOutParameter(APIParameter outParameter) {
		this.outParameter = outParameter;
	}

	public List<APIParameter> getInParameters() {
		return inParameters;
	}

	public boolean addInParameter(APIParameter inParameter) {
		if(inParameter != null) {
			return this.inParameters.add(inParameter);
		}
		return false;
	}

	public boolean addInParameters(List<APIParameter> inParameters) {
		return this.inParameters.addAll(inParameters);
	}

	public RequestMapping getRequestMapping() {
		return requestMapping;
	}

	public void setRequestMapping(RequestMapping requestMapping) {
		this.requestMapping = requestMapping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	public int getAuthNeeded() {
		return authNeeded;
	}

	public void setAuthNeeded(int authNeeded) {
		this.authNeeded = authNeeded;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}
	
	public void addTags(Tag[] tags) {
		for(Tag t : tags) {
			this.tags.add(t.text());
		}
	}
	
	public void addTags(Collection<String> tags) {
		for(String t : tags) {
			this.tags.add(t);
		}
	}
	
	public void addTag(String tag) {
		this.tags.add(tag);
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isDeprecated() {
		return deprecated;
	}

	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}
}
