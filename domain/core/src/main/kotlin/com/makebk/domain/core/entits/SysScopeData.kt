package com.makebk.domain.core.entits

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable
import java.util.*

/**
 * <p>
 * 数据权限表
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("sys_scope_data")
@ApiModel(value = "SysScopeData对象", description = "数据权限表")
class SysScopeData : Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    /**
     * 资源主键
     */
    @ApiModelProperty("资源主键")
    var resourceId: Long? = null

    /**
     * 资源编号
     */
    @ApiModelProperty("资源编号")
    var resourceCode: String? = null

    /**
     * 数据权限名称
     */
    @ApiModelProperty("数据权限名称")
    var scopeName: String? = null

    /**
     * 数据权限字段
     */
    @ApiModelProperty("数据权限字段")
    var scopeField: String? = null

    /**
     * 数据权限类名
     */
    @ApiModelProperty("数据权限类名")
    var scopeClass: String? = null

    /**
     * 数据权限字段
     */
    @ApiModelProperty("数据权限字段")
    var scopeColumn: String? = null

    /**
     * 数据权限类型
     */
    @ApiModelProperty("数据权限类型")
    var scopeType: Int? = null

    /**
     * 数据权限值域
     */
    @ApiModelProperty("数据权限值域")
    var scopeValue: String? = null

    /**
     * 数据权限备注
     */
    @ApiModelProperty("数据权限备注")
    var remark: String? = null

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    var creator: Long? = null

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    var updater: Long? = null

    var cTime: Date? = null

    var uTime: Date? = null

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    var status: Int? = null

    /**
     * 0未删除1已删除
     */
    @ApiModelProperty("0未删除1已删除")
    var isDel: Byte? = null

    override fun toString(): String {
        return "SysScopeData{" +
                "id=" + id +
                ", resourceId=" + resourceId +
                ", resourceCode=" + resourceCode +
                ", scopeName=" + scopeName +
                ", scopeField=" + scopeField +
                ", scopeClass=" + scopeClass +
                ", scopeColumn=" + scopeColumn +
                ", scopeType=" + scopeType +
                ", scopeValue=" + scopeValue +
                ", remark=" + remark +
                ", creator=" + creator +
                ", updater=" + updater +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", status=" + status +
                ", isDel=" + isDel +
                "}"
    }
}
