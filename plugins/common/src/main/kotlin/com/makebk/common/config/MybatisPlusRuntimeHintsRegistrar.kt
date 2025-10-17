package com.makebk.common.config

import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper
import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.conditions.interfaces.Compare
import com.baomidou.mybatisplus.core.conditions.interfaces.Func
import com.baomidou.mybatisplus.core.conditions.interfaces.Join
import com.baomidou.mybatisplus.core.conditions.interfaces.Nested
import com.baomidou.mybatisplus.core.conditions.query.Query
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import org.apache.ibatis.executor.Executor
import org.apache.ibatis.executor.statement.StatementHandler
import org.apache.ibatis.mapping.BoundSql
import org.apache.ibatis.session.SqlSession
import org.springframework.aot.hint.ExecutableMode
import org.springframework.aot.hint.MemberCategory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportRuntimeHints


@Configuration(proxyBeanMethods = false)
@ImportRuntimeHints(MybatisPlusRuntimeHintsRegistrar::class)
class MybatisPlusRuntimeHintsRegistrar : RuntimeHintsRegistrar {

    override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
        hints.proxies()
            .registerJdkProxy(Func::class.java)
            .registerJdkProxy(Join::class.java)
            .registerJdkProxy(Query::class.java)
            .registerJdkProxy(IPage::class.java)
            .registerJdkProxy(Nested::class.java)
            .registerJdkProxy(Compare::class.java)
            .registerJdkProxy(Executor::class.java)
            .registerJdkProxy(IService::class.java)
            .registerJdkProxy(SqlSession::class.java)
            .registerJdkProxy(StatementHandler::class.java)
        hints.reflection()
            .registerType(
                Wrapper::class.java,
                { builder ->
                    builder.withMembers(
                        MemberCategory.INVOKE_DECLARED_METHODS,
                        MemberCategory.INVOKE_PUBLIC_METHODS
                    )
                })
            .registerType(
                Wrappers::class.java,
                { builder ->
                    builder.withMembers(
                        MemberCategory.INVOKE_DECLARED_METHODS,
                        MemberCategory.INVOKE_PUBLIC_METHODS
                    )
                })
            .registerType(
                MybatisXMLLanguageDriver::class.java,
                { builder -> builder.withMethod("<init>", mutableListOf(), ExecutableMode.INVOKE) })
            .registerType(
                QueryWrapper::class.java,
                { builder ->
                    builder.withMembers(
                        MemberCategory.INVOKE_DECLARED_METHODS,
                        MemberCategory.INVOKE_PUBLIC_METHODS
                    )
                })
            .registerType(
                AbstractWrapper::class.java,
                { builder ->
                    builder.withMembers(
                        MemberCategory.INVOKE_DECLARED_METHODS,
                        MemberCategory.INVOKE_PUBLIC_METHODS
                    )
                })
            .registerType(
                AbstractChainWrapper::class.java,
                { builder ->
                    builder.withMembers(
                        MemberCategory.INVOKE_DECLARED_METHODS,
                        MemberCategory.INVOKE_PUBLIC_METHODS
                    )
                })
            .registerType(
                Page::class.java,
                { builder ->
                    builder.withMembers(
                        MemberCategory.INVOKE_DECLARED_METHODS,
                        MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INTROSPECT_PUBLIC_CONSTRUCTORS
                    )
                })
            .registerType(
                BoundSql::class.java,
                { builder ->
                    builder.withMembers(
                        MemberCategory.INVOKE_DECLARED_METHODS,
                        MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INTROSPECT_PUBLIC_CONSTRUCTORS,
                        MemberCategory.DECLARED_FIELDS
                    )
                })
    }
}