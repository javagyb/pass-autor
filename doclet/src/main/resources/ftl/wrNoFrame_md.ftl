<#include "modifyHisTmpl.ftl">
<#include "paramMdTmpl.ftl">
## Dubbo接口名称
${api.simpleName!}
## 功能介绍

> 对接口提供的功能进行介绍
${api.description!}

## (可选)名词解释

> 按需对专业名词进行解释

## Maven 依赖

```xml
<dependency>
  <groupId>cn.gov.zcy.paas</groupId>
  <artifactId>trade-quote-facade-api</artifactId>
  <version>1.0.0-RELEASE</version>
</dependency>
```

- 注：如果有二方包、三方包依赖，请一一列出

## Dubbo接口声明

```java
package   ${api.package!};

public interface ${serviceName!} {
    ${api.methodCode!}
  }

  ```


  ## 请求参数
  ### 请求参数示例

  ```json
 <#if api.inParametersJson??>
   ${api.inParametersJson}
 <#else>
      <p>N/A</p>
     </#if>
  ```

  ### 请求参数说明

     <#if api.inParameters?? && (api.inParameters?size != 0)>
       <@reqParamTableMacro params=api.inParameters />
     <#else>
      <p>N/A</p>
     </#if>


  ## 返回结果
  ### 返回结果示例

  ```json
    <#if api.outParametersJson??>
      ${api.outParametersJson}
    <#else>
      <p>N/A</p>
    </#if>
  ```

  ### 返回结果说明

    <#if api.outParameter??>
      <@resParamTableMacro param=api.outParameter />
    <#else>
      <p>N/A</p>
    </#if>

## 错误与异常

> 只展现业务特殊异常，系统级异常参见：[系统级异常规范]()

## 性能说明


## 代码示例

> 建议把此API的各种玩法示例都写全，防止业务方不会用、用错。




## 相关链接

- 同一流程上下游的其他API
- API用到的相关入参，且此入参是通过其他API获得的










