package com.makebk.security.config

import cn.hutool.extra.spring.SpringUtil
import com.makebk.common.permission.JwtProperties
import com.makebk.security.cache.ShiroCacheManager
import com.makebk.security.properties.ShiroFilterProperties
import com.makebk.security.realm.ShiroRealm
import com.makebk.security.shiro.DefaultAuthorizationAttributeSourceAdvisor
import com.makebk.security.shiro.HttpAuthenticationFilter
import com.makebk.security.shiro.JwtToken
import com.makebk.security.shiro.SystemLogoutFilter
import com.makebk.security.utils.JwtUtil
import jakarta.servlet.Filter
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator
import org.apache.shiro.mgt.DefaultSubjectDAO
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.util.ThreadContext
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.redisson.api.RedissonClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.core.annotation.Order
import java.util.function.Consumer


@Order(9)
@Configuration
class ShiroConfig() {

    @Bean
    fun authorizationAttributeSourceAdvisor(securityManager: DefaultWebSecurityManager):
            DefaultAuthorizationAttributeSourceAdvisor {
        val advisor = DefaultAuthorizationAttributeSourceAdvisor()
        advisor.securityManager = securityManager
        return advisor
    }

    /**
     * 配置自定义缓存信息
     * redis 缓存方式
     *
     * @param shiroRealm        权限认证实现类. 因采用多模块开发权限认证时需要进行数据库操作当前模块未集成数据库操作
     * 所有shiroRealm类为抽象类 在实际应用时继承该类实现认证方法即可
     * @param shiroCacheManager 登录信息缓存 token 缓存
     * @return
     */
    @Bean
    fun securityManager(shiroRealm: ShiroRealm, shiroCacheManager: ShiroCacheManager): DefaultWebSecurityManager {
        val securityManager = DefaultWebSecurityManager()
        shiroRealm.setAuthenticationTokenClass(JwtToken::class.java)
        securityManager.setRealm(shiroRealm)
        val subjectDAO = DefaultSubjectDAO()
        val defaultSessionStorageEvaluator = DefaultSessionStorageEvaluator()
        defaultSessionStorageEvaluator.isSessionStorageEnabled = false
        subjectDAO.sessionStorageEvaluator = defaultSessionStorageEvaluator
        securityManager.subjectDAO = subjectDAO
        securityManager.cacheManager = shiroCacheManager
        ThreadContext.bind(securityManager)
        return securityManager
    }

    /**
     * shiro
     * 拦截器配置
     * 设置拦截器需要拦截的路径以及操作.
     *
     * @param securityManager
     * @param jedisUtils
     * @param jwtProperties
     * @param syncCacheService
     * @return
     */
    @Bean
    @Lazy
    fun shiroFilter(
        securityManager: DefaultWebSecurityManager,
        jwtUtil: JwtUtil,
        jwtProperties: JwtProperties,
    ): ShiroFilterFactoryBean? {
        val shiroFilter = ShiroFilterFactoryBean()
        shiroFilter.securityManager = securityManager
        val redissonClient = SpringUtil.getBean(RedissonClient::class.java)
        val filterMap: MutableMap<String, Filter> = HashMap()
        filterMap["auth"] = HttpAuthenticationFilter(jwtUtil, jwtProperties, redissonClient)
        filterMap["logout"] = SystemLogoutFilter(jwtProperties, redissonClient, jwtUtil)
        shiroFilter.filters = filterMap
        val filterRuleMap: MutableMap<String?, String?> = HashMap(16)
        val perms: List<Map<String, String>> = getShiroFilterProperties().perms
        perms.forEach(Consumer { perm: Map<String, String> ->
            filterRuleMap[perm["key"]] = perm["value"]
        })
        shiroFilter.filterChainDefinitionMap = filterRuleMap
        return shiroFilter
    }

    /**
     * 获取Shiro配置文件信息
     * 对应 application.yml 文件中 permission-config 配置
     * permission-config:
     * perms:
     * - key: /user/login 拦截路径
     * value: anon 无需认证
     * - key: /logout 拦截路径
     * value: logout 退出方法
     * - key: / **   拦截路径
     * value: auth 需要拦截认证
     * 以上为自定义配置可根据实际开发中进行设置
     *
     * @return
     */
    @Bean
    fun getShiroFilterProperties(): ShiroFilterProperties {
        return ShiroFilterProperties();
    }
}