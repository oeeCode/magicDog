package com.makebk.domain.core.entits

import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable
import java.util.*

/**
 * <p>
 * 接口日志
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("sys_log_api")
@ApiModel(value = "SysLogApi对象", description = "接口日志")
class SysLogApi : Serializable {

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
     * 服务器环境
     */
    @ApiModelProperty("服务器环境")
    var env: String? = null

    /**
     * 日志类型
     */
    @ApiModelProperty("日志类型")
    var type: String? = null

    /**
     * 日志标题
     */
    @ApiModelProperty("日志标题")
    var title: String? = null

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    var describe: String? = null

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
     * 返回结果
     */
    @ApiModelProperty("返回结果")
    var response: String? = null

    /**
     * 用户代理
     */
    @ApiModelProperty("用户代理")
    var userAgent: String? = null

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
     * 操作提交的数据
     */
    @ApiModelProperty("操作提交的数据")
    var params: String? = null

    /**
     * 执行时间
     */
    @ApiModelProperty("执行时间")
    var time: String? = null

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

    /**
     * 0未删除1已删除
     */
    @ApiModelProperty("0未删除1已删除")
    var isDel: Byte? = null

    var createByName: String? = null

    override fun toString(): String {
        return "SysLogApi{" +
                "id=" + id +
                ", tenantsId=" + tenantsId +
                ", serviceId=" + serviceId +
                ", serverHost=" + serverHost +
                ", serverIp=" + serverIp +
                ", env=" + env +
                ", type=" + type +
                ", title=" + title +
                ", describe=" + describe +
                ", method=" + method +
                ", requestUri=" + requestUri +
                ", response=" + response +
                ", userAgent=" + userAgent +
                ", remoteIp=" + remoteIp +
                ", methodClass=" + methodClass +
                ", methodName=" + methodName +
                ", params=" + params +
                ", time=" + time +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", isDel=" + isDel +
                ", createByName=" + createByName +
                "}"
    }
}
