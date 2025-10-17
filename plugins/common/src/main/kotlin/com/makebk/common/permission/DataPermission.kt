package com.makebk.common.permission

import com.makebk.common.constant.Constant
import java.lang.annotation.Inherited

/**
 *
 * 　@类   名：DataPermission
 * 　@描   述：权限范围拦截注解
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/20 4:32 PM
 *
 */
@Inherited
@MustBeDocumented
@Target(AnnotationTarget.TYPE, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DataPermission(
    val scopeColumn: String = Constant.FIELD_TENANT_ID,
    val scopeType: DataPermissionType = DataPermissionType.OWN_TENANT_CHILD,
    val scopeField: String = Constant.BASE_SCOPE_FIELD,
    val scopeValue: String = "",
    val resourceCode: String = "",
) {
}