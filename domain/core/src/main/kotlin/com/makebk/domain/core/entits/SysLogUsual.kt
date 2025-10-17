package com.makebk.domain.core.entits

import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable
import java.util.*

/**
 * <p>
 * 通用日志
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("sys_log_usual")
@ApiModel(value = "SysLogUsual对象", description = "通用日志")
class SysLogUsual : Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    var id: String? = null

    /**
     * 租户ID
     */
    @ApiModelProperty("租户ID")
    var tenantsId: Long? = null

    /**
     * 服务ID
     */
    @ApiModelProperty("服务ID")
    var serviceId: String? = null

    /**
     * 服务器名
     */
    @ApiModelProperty("服务器名")
    var serverHost: String? = null

    /**
     * 服务器IP地址
     */
    @ApiModelProperty("服务器IP地址")
    var serverIp: String? = null

    /**
     * 系统环境
     */
    @ApiModelProperty("系统环境")
    var env: String? = null

    /**
     * 日志级别
     */
    @ApiModelProperty("日志级别")
    var logLevel: String? = null

    /**
     * 日志业务id
     */
    @ApiModelProperty("日志业务id")
    var logId: String? = null

    /**
     * 日志数据
     */
    @ApiModelProperty("日志数据")
    var logData: String? = null

    /**
     * 操作方式
     */
    @ApiModelProperty("操作方式")
    var method: String? = null

    /**
     * 请求URI
     */
    @ApiModelProperty("请求URI")
    var requestUri: String? = null

    /**
     * 操作IP地址
     */
    @ApiModelProperty("操作IP地址")
    var remoteIp: String? = null

    /**
     * 方法类
     */
    @ApiModelProperty("方法类")
    var methodClass: String? = null

    /**
     * 方法名
     */
    @ApiModelProperty("方法名")
    var methodName: String? = null

    /**
     * 用户代理
     */
    @ApiModelProperty("用户代理")
    var userAgent: String? = null

    /**
     * 操作提交的数据
     */
    @ApiModelProperty("操作提交的数据")
    var params: String? = null

    /**
     * 执行时间
     */
    @ApiModelProperty("执行时间")
    var time: Date? = null

    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    var createBy: String? = null

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    var createTime: Date? = null

    var createByName: String? = null

    /**
     * 0未删除1已删除
     */
    @ApiModelProperty("0未删除1已删除")
    var isDel: Byte? = null

    override fun toString(): String {
        return "SysLogUsual{" +
                "id=" + id +
                ", tenantsId=" + tenantsId +
                ", serviceId=" + serviceId +
                ", serverHost=" + serverHost +
                ", serverIp=" + serverIp +
                ", env=" + env +
                ", logLevel=" + logLevel +
                ", logId=" + logId +
                ", logData=" + logData +
                ", method=" + method +
                ", requestUri=" + requestUri +
                ", remoteIp=" + remoteIp +
                ", methodClass=" + methodClass +
                ", methodName=" + methodName +
                ", userAgent=" + userAgent +
                ", params=" + params +
                ", time=" + time +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", createByName=" + createByName +
                ", isDel=" + isDel +
                "}"
    }
}
