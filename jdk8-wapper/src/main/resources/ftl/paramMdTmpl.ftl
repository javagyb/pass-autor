
<#macro recurseFieldMacro field depth>
<#if field.isParentTypeArgument()>
    <#if field.fields??>
        <#list field.fields as child>
            <@recurseFieldMacro field=child depth=depth+1/>
        </#list>
    </#if>
<#else >
|<#if (depth>0) ><#list 1..depth as i>&nbsp;&nbsp;</#list>└</#if><#if field.name??><#if field.isParentTypeArgument()>**${field.name}**<#else>${field.name}</#if></#if>|${field.type?html}|<#if field.parameterOccurs??><#if field.parameterOccurs == "REQUIRED">是<#elseif field.parameterOccurs == "OPTIONAL">否</#if></#if>|<#if field.description??>${field.description}</#if>|<#if field.valueRange??>${field.valueRange}</#if>|<#if field.example??><#if field.example.value??>${field.example.value}</#if></#if>|
    <#if field.fields??>
        <#list field.fields as child>
            <@recurseFieldMacro field=child depth=depth+1/>
        </#list>
    </#if>
</#if>
  </#macro>


<#macro recurseFieldMacro4Res field depth>
<#if field.isParentTypeArgument()>
    <#if field.fields??>
        <#list field.fields as child>
            <@recurseFieldMacro4Res field=child depth=depth+1/>
        </#list>
    </#if>
<#else >
|<#if (depth>0) ><#list 1..depth as i>&nbsp;&nbsp;</#list>└</#if><#if field.name??><#if field.isParentTypeArgument()>**${field.name}**<#else>${field.name}</#if></#if>|<#if field.type??>${field.type?html}</#if>|<#if field.valueRange??>${field.valueRange}</#if>|<#if field.description??  && field.description != "">${field.description}</#if><#if field.example??><#if field.example.value?? && field.example.value != ""><br>${field.example.value}</#if></#if>|
<#if field.fields??>
    <#list field.fields as child>
        <@recurseFieldMacro4Res field=child depth=depth+1/>
    </#list>
</#if>
</#if>
</#macro>



  <#macro reqParamTableMacro params>
 |入参名称|类型|是否必须|描述|值域范围|示例|
 |-----|:---|:--:|--------|-------|-------|
<#list params as param>
  <@recurseFieldMacro field=param depth=0/>
</#list>
</table>
  </#macro>

  <#macro resParamTableMacro param>
|返回参数名称|类型|值域范围|描述/示例|
|-----|:---|:--:|--------|
<@recurseFieldMacro4Res field=param depth=0/>
  </#macro>

