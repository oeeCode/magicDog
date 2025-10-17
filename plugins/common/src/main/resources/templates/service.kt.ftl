package ${package.Service};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
<#if generateService>
    import ${package.Service}.${table.serviceName};
</#if>
import ${superServiceClassPackage};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

/**
* <p>
    * ${table.comment!} 服务类
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
    interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
    public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    }
</#if>


@Service
<#if kotlin>
    class ${table.serviceName}Impl : ${superServiceImplClass}<${table.mapperName}, ${entity}>(),  ${table.serviceName}
<#else>
    public class ${table.serviceName} extends ${superServiceImplClass}<${table.mapperName},${entity}>, implements ${superServiceClass}
    {

    }
</#if>