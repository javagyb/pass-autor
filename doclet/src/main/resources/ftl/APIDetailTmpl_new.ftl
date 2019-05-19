<#include "modifyHisTmpl.ftl">
<#include "paramMdTmpl.ftl">

  <#if openAPI.inParameters?? && (openAPI.inParameters?size != 0)>

      ${openAPI.inParametersDemos!}

      <@reqParamTableMacro params=openAPI.inParameters />
  <#else>
      <p>N/A</p>
  </#if>
