
<#macro recurseFieldMacro field depth>
  <#if field.isParentTypeArgument()>

  </#if>
  <tr>
    <td align="left" style="white-space: nowrap;">
      <#if (depth>
        0) >
        <#list 1..depth as i>
          &nbsp;&nbsp;
        </#list>
      </#if>
      <#if field.name??>
        <#if field.isParentTypeArgument()>
          <b>
            ${field.name}
          </b>
          <#else>
            ${field.name}
          </#if>
        </#if>
      </td>
      <td>
        <#if field.type??>
          ${field.type?html}
        </#if>
      </td>
      <td style="text-align: center;">
        <#if field.parameterOccurs??>
          <#if field.parameterOccurs == "REQUIRED">
          	是
          <#elseif field.parameterOccurs == "OPTIONAL">
          	否
          </#if>
        </#if>
      </td>
      <td>
          <#if field.description??>
            ${field.description}
          </#if>
      </td>
      <td>
          <#if field.valueRange??>
              ${field.valueRange}
          </#if>
      </td>
      <td>
        <#if field.example??>
<pre>
        	<#if field.example.summary??>
${field.example.summary}
        	</#if>
        	<#if field.example.description??>
${field.example.description}
        	</#if>
        	<#if field.example.value??>
示例：
${field.example.value}
        	</#if>
        	<#if field.example.externalValue??>
示例：
${field.example.externalValue}
        	</#if>
</pre>        	
        </#if>
        <#if field.example?? && field.history?? && field.history.modificationRecordList?size != 0 >
        	<hr>
        </#if>
        <#if field.history?? && field.history.modificationRecordList?size != 0 >
          <table class="innerTable">
            <@modifyHistoryMacro modificationRecordList=field.history.modificationRecordList />
          </table>
        </#if>
      </td>
    </tr>
    <#if field.fields??>
      <#list field.fields as child>
        <@recurseFieldMacro field=child depth=depth+1/>
      </#list>
    </#if>
  </#macro>


<#macro recurseFieldMacro4Res field depth>
  <tr>
      <td align="left" style="white-space: nowrap;">
      <#if (depth>
      0) >
          <#list 1..depth as i>
          &nbsp;&nbsp;
          </#list>
      </#if>
      <#if field.name??>
          <#if field.isParentTypeArgument()>
          <b>
              ${field.name}
          </b>
          <#else>
              ${field.name}
          </#if>
      </#if>
      </td>
      <td>
        <#if field.type??>
            ${field.type?html}
        </#if>
      </td>
      <td>
          <#if field.valueRange??>
              ${field.valueRange}
          </#if>
      </td>
      <td>
       <#if field.description??  && field.description != "">
           ${field.description}
       </#if>
        <#if field.example??>
            <pre>
        	<#if field.example.summary?? && field.example.summary != "">
                ${field.example.summary}
            </#if>
        	<#if field.example.description?? && field.example.description != "">
                ${field.example.description}
            </#if>
        	<#if field.example.value?? && field.example.value != "">
示例：
                ${field.example.value}
            </#if>
            </pre>
        </#if>
      </td>
  </tr>
    <#if field.fields??>
        <#list field.fields as child>
            <@recurseFieldMacro4Res field=child depth=depth+1/>
        </#list>
    </#if>
</#macro>



  <#macro reqParamTableMacro params>
<table align="left">
    <tr>
      <th></span>
        入参名称
      </th>
      <th ></span>
        类型
      </th>
      <th></span>
        是否必须
      </th>
      <th></span>
        描述
      </th>
        <th></span>
            值域范围
        </th>
      <th style="width: 30%;">
        示例及修订记录
      </th>
    </tr>
    <#list params as param>
      <@recurseFieldMacro field=param depth=0/>
    </#list>
</table>
  </#macro>

  <#macro resParamTableMacro param>
<table align="left">
  <tr>
    <th></span>
        返回参数名称
    </th>
    <th></span>
      类型
    </th>
    <th></span>
        值域范围
    </th>
    <th ></span>
        描述/示例
    </th>
  </tr>
  <tbody>
    <@recurseFieldMacro4Res field=param depth=0/>
  </tbody>
</table>
  </#macro>

