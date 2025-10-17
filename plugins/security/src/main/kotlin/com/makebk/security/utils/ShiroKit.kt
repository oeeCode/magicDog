package com.makebk.security.utils

import com.baomidou.mybatisplus.core.toolkit.StringPool
import org.apache.shiro.SecurityUtils
import org.apache.shiro.crypto.hash.Md5Hash
import org.apache.shiro.crypto.hash.SimpleHash
import org.apache.shiro.subject.Subject
import java.util.Random

/**
 * 权限工具类
 *
 * @Package com.lingyun.security.utils
 * @author GME
 * @date 2024/11/18 09:49
 * @version V1.0
 * @Copyright © 2014-2024 码克布克网络工作室
 */
object ShiroKit {
    /**
     * 加盐参数
     */
    const val hashAlgorithmName: String = "MD5"
    /**
     * 循环次数
     */
    const val hashIterations: Int = 1024


    /**
     * shiro密码加密工具类
     *
     * @param credentials 密码
     * @param saltSource  密码盐
     * @return
     */
    fun md5(credentials: String?, saltSource: String?): String {
        val salt = Md5Hash(saltSource)
        return SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString()
    }

    /**
     * 获取随机盐值
     *
     * @param length
     * @return
     */
    fun getRandomSalt(length: Int): String {
        val base = "abcdefghijklmnopqrstuvwxyz0123456789"
        val random = Random()
        val sb = StringBuffer()
        for (i in 0..<length) {
            val number: Int = random.nextInt(base.length)
            sb.append(base[number])
        }
        return sb.toString()
    }

    /**
     * 验证密码是否一致
     *
     * @param password
     * @param salt
     * @param md5cipherText
     * @return
     */
    fun checkMd5Password( password: String?, salt: String?, md5cipherText: String?): Boolean {
        val credentialsSalt = Md5Hash(salt)
        val hash = SimpleHash(hashAlgorithmName, password, credentialsSalt, hashIterations)
        return md5cipherText == hash.toHex()
    }

    /**
     * 获取当前 Subject
     *
     * @return Subject
     */
    private fun getSubject(): Subject? {
        return SecurityUtils.getSubject()
    }


    /**
     * 验证当前用户是否属于该角色？,使用时与lacksRole 搭配使用
     *
     * @param roleName 角色名
     * @return 属于该角色：true，否则false
     */
    fun hasRole(roleName: String?): Boolean {
        return getSubject() != null && roleName != null && roleName.length > 0 && getSubject()?.hasRole(roleName)?:false
    }

    /**
     * 与hasRole标签逻辑相反，当用户不属于该角色时验证通过。
     *
     * @param roleName 角色名
     * @return 不属于该角色：true，否则false
     */
    fun lacksRole(roleName: String?): Boolean {
        return !hasRole(roleName)
    }

    /**
     * 验证当前用户是否属于以下任意一个角色。
     *
     * @param roleNames 角色列表
     * @return 属于:true,否则false
     */
    fun hasAnyRoles(roleNames: String?): Boolean {
        var hasAnyRole = false
        val subject = getSubject()
        if (subject != null && roleNames != null && roleNames.length > 0) {
            for (role in roleNames.split(StringPool.COMMA.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                if (subject.hasRole(role.trim { it <= ' ' })) {
                    hasAnyRole = true
                    break
                }
            }
        }
        return hasAnyRole
    }

    /**
     * 验证当前用户是否属于以下所有角色。
     *
     * @param roleNames 角色列表
     * @return 属于:true,否则false
     */
    fun hasAllRoles(roleNames: String?): Boolean {
        var hasAllRole = true
        val subject = getSubject()
        if (subject != null && roleNames != null && roleNames.length > 0) {
            for (role in roleNames.split(StringPool.COMMA.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                if (!subject.hasRole(role.trim { it <= ' ' })) {
                    hasAllRole = false
                    break
                }
            }
        }
        return hasAllRole
    }

    /**
     * 验证当前用户是否拥有指定权限,使用时与lacksPermission 搭配使用
     *
     * @param permission 权限名
     * @return 拥有权限：true，否则false
     */
    fun hasPermission(permission: String?): Boolean {
        return getSubject() != null && permission != null && permission.length > 0 && getSubject()?.isPermitted(
            permission
        )?:false
    }

    /**
     * 与hasPermission标签逻辑相反，当前用户没有制定权限时，验证通过。
     *
     * @param permission 权限名
     * @return 拥有权限：true，否则false
     */
    fun lacksPermission(permission: String?): Boolean {
        return !hasPermission(permission)
    }

    /**
     * 已认证通过的用户。不包含已记住的用户，这是与user标签的区别所在。与notAuthenticated搭配使用
     *
     * @return 通过身份验证：true，否则false
     */
    fun isAuthenticated(): Boolean {
        return getSubject() != null && getSubject()?.isAuthenticated()?:false
    }

    /**
     * 未认证通过用户，与authenticated标签相对应。与guest标签的区别是，该标签包含已记住用户。。
     *
     * @return 没有通过身份验证：true，否则false
     */
    fun notAuthenticated(): Boolean {
        return !isAuthenticated()
    }

    /**
     * 认证通过或已记住的用户。与guset搭配使用。
     *
     * @return 用户：true，否则 false
     */
    fun isUser(): Boolean {
        return getSubject() != null && getSubject()?.principal != null
    }

    /**
     * 验证当前用户是否为“访客”，即未认证（包含未记住）的用户。用user搭配使用
     *
     * @return 访客：true，否则false
     */
    fun isGuest(): Boolean {
        return !isUser()
    }

    /**
     * 输出当前用户信息，通常为登录帐号信息。
     *
     * @return 当前用户信息
     */
    fun principal(): String {
        if (getSubject() != null) {
            val principal = getSubject()?.principal
            return principal.toString()
        }
        return ""
    }
}