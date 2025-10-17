package com.makebk.common.config

import com.baomidou.mybatisplus.annotation.IEnum
import com.baomidou.mybatisplus.core.MybatisParameterHandler
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper
import com.baomidou.mybatisplus.core.handlers.CompositeEnumTypeHandler
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler
import com.baomidou.mybatisplus.core.toolkit.support.SFunction
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler
import com.baomidou.mybatisplus.extension.handlers.GsonTypeHandler
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.makebk.common.config.MyBatisNativeConfiguration.MyBatisRuntimeHintsRegistrar
import org.apache.commons.logging.LogFactory
import org.apache.ibatis.annotations.DeleteProvider
import org.apache.ibatis.annotations.InsertProvider
import org.apache.ibatis.annotations.SelectProvider
import org.apache.ibatis.annotations.UpdateProvider
import org.apache.ibatis.cache.decorators.FifoCache
import org.apache.ibatis.cache.decorators.LruCache
import org.apache.ibatis.cache.decorators.SoftCache
import org.apache.ibatis.cache.decorators.WeakCache
import org.apache.ibatis.cache.impl.PerpetualCache
import org.apache.ibatis.executor.Executor
import org.apache.ibatis.executor.parameter.ParameterHandler
import org.apache.ibatis.executor.resultset.ResultSetHandler
import org.apache.ibatis.executor.statement.BaseStatementHandler
import org.apache.ibatis.executor.statement.RoutingStatementHandler
import org.apache.ibatis.executor.statement.StatementHandler
import org.apache.ibatis.javassist.util.proxy.ProxyFactory
import org.apache.ibatis.javassist.util.proxy.RuntimeSupport
import org.apache.ibatis.logging.Log
import org.apache.ibatis.logging.commons.JakartaCommonsLoggingImpl
import org.apache.ibatis.logging.jdk14.Jdk14LoggingImpl
import org.apache.ibatis.logging.log4j2.Log4j2Impl
import org.apache.ibatis.logging.nologging.NoLoggingImpl
import org.apache.ibatis.logging.slf4j.Slf4jImpl
import org.apache.ibatis.logging.stdout.StdOutImpl
import org.apache.ibatis.mapping.BoundSql
import org.apache.ibatis.reflection.TypeParameterResolver
import org.apache.ibatis.scripting.defaults.RawLanguageDriver
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.mapper.MapperFactoryBean
import org.mybatis.spring.mapper.MapperScannerConfigurer
import org.springframework.aot.hint.MemberCategory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotContribution
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotProcessor
import org.springframework.beans.factory.aot.BeanRegistrationExcludeFilter
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.config.ConstructorArgumentValues
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor
import org.springframework.beans.factory.support.RegisteredBean
import org.springframework.beans.factory.support.RootBeanDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportRuntimeHints
import org.springframework.core.ResolvableType
import org.springframework.util.ClassUtils
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.stream.Collectors
import java.util.stream.Stream


@Configuration(proxyBeanMethods = false)
@ImportRuntimeHints(MyBatisRuntimeHintsRegistrar::class)
class MyBatisNativeConfiguration {

    @Bean
    fun myBatisBeanFactoryInitializationAotProcessor(): MyBatisBeanFactoryInitializationAotProcessor {
        return MyBatisBeanFactoryInitializationAotProcessor()
    }

    @Bean
    fun myBatisMapperFactoryBeanPostProcessor(): MyBatisMapperFactoryBeanPostProcessor {
        return MyBatisMapperFactoryBeanPostProcessor()
    }

    internal class MyBatisRuntimeHintsRegistrar : RuntimeHintsRegistrar {
        override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
            Stream.of(
                RawLanguageDriver::class.java,
                XMLLanguageDriver::class.java,
                RuntimeSupport::class.java,
                ProxyFactory::class.java,
                Slf4jImpl::class.java,
                Log::class.java,
                JakartaCommonsLoggingImpl::class.java,
                Log4j2Impl::class.java,
                Jdk14LoggingImpl::class.java,
                StdOutImpl::class.java,
                NoLoggingImpl::class.java,
                SqlSessionFactory::class.java,
                PerpetualCache::class.java,
                FifoCache::class.java,
                LruCache::class.java,
                SoftCache::class.java,
                WeakCache::class.java,
                SqlSessionFactoryBean::class.java,
                ArrayList::class.java,
                HashMap::class.java,
                TreeSet::class.java,
                HashSet::class.java
            ).forEach { x: Class<*>? ->
                if (x != null) {
                    hints.reflection().registerType(x, *MemberCategory.entries.toTypedArray())
                }
            }
            Stream.of(
                "org/apache/ibatis/builder/xml/.*.dtd",
                "org/apache/ibatis/builder/xml/.*.xsd"
            ).forEach { include: String -> hints.resources().registerPattern(include) }
        }
    }

    class MyBatisBeanFactoryInitializationAotProcessor
        : BeanFactoryInitializationAotProcessor, BeanRegistrationExcludeFilter {

        private val excludeClasses: MutableSet<Class<*>> = HashSet<Class<*>>()

        init {
            excludeClasses.add(MapperScannerConfigurer::class.java)
        }

        override fun isExcludedFromAotProcessing(registeredBean: RegisteredBean): Boolean {
            return excludeClasses.contains(registeredBean.beanClass)
        }

        override fun processAheadOfTime(beanFactory: ConfigurableListableBeanFactory): BeanFactoryInitializationAotContribution? {
            val beanNames = beanFactory.getBeanNamesForType(MapperFactoryBean::class.java)
            if (beanNames.size == 0) {
                return null
            }
            return BeanFactoryInitializationAotContribution { context, code ->
                val hints = context.runtimeHints
                for (beanName in beanNames) {
                    val beanDefinition = beanFactory.getBeanDefinition(beanName.substring(1))
                    val mapperInterface = beanDefinition.propertyValues.getPropertyValue("mapperInterface")
                    if (mapperInterface != null && mapperInterface.value != null) {
                        val mapperInterfaceType = mapperInterface.value as Class<*>?
                        if (mapperInterfaceType != null) {
                            registerReflectionTypeIfNecessary(mapperInterfaceType, hints)
                            hints.proxies().registerJdkProxy(mapperInterfaceType)
                            hints.resources()
                                .registerPattern(mapperInterfaceType.getName().replace('.', '/') + ".xml")
                            registerMapperRelationships(mapperInterfaceType, hints)
                        }
                    }
                }
            }
        }

        private fun registerMapperRelationships(mapperInterfaceType: Class<*>, hints: RuntimeHints) {
            val methods = ReflectionUtils.getAllDeclaredMethods(mapperInterfaceType)
            for (method in methods) {
                if (method.declaringClass != Any::class.java) {
                    ReflectionUtils.makeAccessible(method)
                    registerSqlProviderTypes(
                        method,
                        hints,
                        SelectProvider::class.java,
                        { obj: SelectProvider -> obj.value.java },
                        { obj: SelectProvider -> obj.type.java })
                    registerSqlProviderTypes(
                        method,
                        hints,
                        InsertProvider::class.java,
                        { obj: InsertProvider -> obj.value.java },
                        { obj: InsertProvider -> obj.type.java })
                    registerSqlProviderTypes(
                        method,
                        hints,
                        UpdateProvider::class.java,
                        { obj: UpdateProvider -> obj.value.java },
                        { obj: UpdateProvider -> obj.type.java })
                    registerSqlProviderTypes(
                        method,
                        hints,
                        DeleteProvider::class.java,
                        { obj: DeleteProvider -> obj.value.java },
                        { obj: DeleteProvider -> obj.type.java })
                    val returnType: Class<*> = MyBatisMapperTypeUtils.resolveReturnClass(mapperInterfaceType, method)
                    registerReflectionTypeIfNecessary(returnType, hints)
                    MyBatisMapperTypeUtils.resolveParameterClasses(mapperInterfaceType, method)
                        .forEach(Consumer { x: Class<*> -> registerReflectionTypeIfNecessary(x, hints) })
                }
            }
        }

        @SafeVarargs
        private fun <T : Annotation> registerSqlProviderTypes(
            method: Method,
            hints: RuntimeHints,
            annotationType: Class<T>,
            vararg providerTypeResolvers: Function<T, Class<*>>,
        ) {
            for (annotation in method.getAnnotationsByType(annotationType)) {
                for (providerTypeResolver in providerTypeResolvers) {
                    val resolvedType = providerTypeResolver.apply(annotation)
                    registerReflectionTypeIfNecessary(resolvedType, hints)
                }
            }
        }

        private fun registerReflectionTypeIfNecessary(type: Class<*>, hints: RuntimeHints) {
            if (!type.isPrimitive && !type.getName().startsWith("java")) {
                hints.reflection().registerType(type, *MemberCategory.entries.toTypedArray())
            }
        }
    }

    internal object MyBatisMapperTypeUtils {
        fun resolveReturnClass(mapperInterface: Class<*>?, method: Method): Class<*> {
            val resolvedReturnType = TypeParameterResolver.resolveReturnType(method, mapperInterface)
            return if (resolvedReturnType != null) {
                typeToClass(resolvedReturnType, method.returnType)
            } else {
                method.returnType
            }
        }

        fun resolveParameterClasses(mapperInterface: Class<*>?, method: Method): MutableSet<Class<*>> {
            val paramTypes = TypeParameterResolver.resolveParamTypes(method, mapperInterface)
            return if (paramTypes != null) {
                Stream.of(*paramTypes)
                    .map { x: Type? -> typeToClass(x, x as? Class<*> ?: Any::class.java) }
                    .filter { x: Class<*> -> x != Any::class.java } // 过滤掉默认的 fallback 类型
                    .collect(Collectors.toSet())
            } else {
                mutableSetOf()
            }
        }

        private fun typeToClass(src: Type?, fallback: Class<*>): Class<*> {
            var result: Class<*>? = null
            if (src is Class<*>) {
                result = if (src.isArray) {
                    src.componentType
                } else {
                    src
                }
            } else if (src is ParameterizedType) {
                val parameterizedType = src
                val index = if (parameterizedType.rawType is Class<*>
                    && MutableMap::class.java.isAssignableFrom(parameterizedType.rawType as Class<*>)
                    && parameterizedType.actualTypeArguments.size > 1
                ) 1 else 0
                val actualType = parameterizedType.actualTypeArguments[index]
                result = typeToClass(actualType, fallback)
            }
            if (result == null) {
                result = fallback
            }
            return result
        }
    }

    class MyBatisMapperFactoryBeanPostProcessor : MergedBeanDefinitionPostProcessor, BeanFactoryAware {
        private var beanFactory: ConfigurableBeanFactory? = null

        override fun setBeanFactory(beanFactory: BeanFactory) {
            this.beanFactory = beanFactory as ConfigurableBeanFactory
        }

        override fun postProcessMergedBeanDefinition(
            beanDefinition: RootBeanDefinition,
            beanType: Class<*>,
            beanName: String,
        ) {
            if (this.beanFactory != null && ClassUtils.isPresent(MAPPER_FACTORY_BEAN, beanFactory!!.beanClassLoader)) {
                resolveMapperFactoryBeanTypeIfNecessary(beanDefinition)
            }
        }

        private fun resolveMapperFactoryBeanTypeIfNecessary(beanDefinition: RootBeanDefinition) {
            if (!beanDefinition.hasBeanClass() || !MapperFactoryBean::class.java.isAssignableFrom(beanDefinition.beanClass)) {
                return
            }
            if (beanDefinition.resolvableType.hasUnresolvableGenerics()) {
                val mapperInterface = getMapperInterface(beanDefinition)
                if (mapperInterface != null) {
                    val constructorArgumentValues = ConstructorArgumentValues()
                    constructorArgumentValues.addGenericArgumentValue(mapperInterface)
                    beanDefinition.constructorArgumentValues = constructorArgumentValues
                    beanDefinition.constructorArgumentValues = constructorArgumentValues
                    beanDefinition.setTargetType(
                        ResolvableType.forClassWithGenerics(
                            beanDefinition.beanClass,
                            mapperInterface
                        )
                    )
                }
            }
        }

        private fun getMapperInterface(beanDefinition: RootBeanDefinition): Class<*>? {
            try {
                return beanDefinition.propertyValues.get("mapperInterface") as Class<*>?
            } catch (e: Exception) {
                LOG.debug("Fail getting mapper interface type.", e)
                return null
            }
        }

        companion object {
            private val LOG: org.apache.commons.logging.Log = LogFactory.getLog(
                MyBatisMapperFactoryBeanPostProcessor::class.java
            )
            private const val MAPPER_FACTORY_BEAN = "org.mybatis.spring.mapper.MapperFactoryBean"
        }
    }
}