package ${package.Controller};

import com.makebk.common.annotations.Router;
<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>
import ${package.Entity}.${entity};
<#if generateService>
    import ${package.Service}.${table.serviceName};
</#if>

/**
* <p>
    * ${table.comment!} 前端控制器
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Router("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}<#if generateService><${table.serviceName},${entity}></#if>()</#if>
<#else>
    <#if superControllerClass??>
        public class ${table.controllerName} extends ${superControllerClass}<#if generateService><${table.serviceName},${entity}></#if> {
    <#else>
        public class ${table.controllerName} {
    </#if>

    }
</#if>
