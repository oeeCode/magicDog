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
 * 员工表
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("sys_staff")
@ApiModel(value = "SysStaff对象", description = "员工表")
class SysStaff : Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    /**
     * 租户ID
     */
    @ApiModelProperty("租户ID")
    var tenantId: Long? = null

    /**
     * 代码
     */
    @ApiModelProperty("代码")
    var code: String? = null

    /**
     * 账号
     */
    @ApiModelProperty("账号")
    var account: String? = null

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    var password: String? = null

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    var name: String? = null

    /**
     * 真实名称
     */
    @ApiModelProperty("真实名称")
    var realName: String? = null

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    var avatar: String? = null

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    var email: String? = null

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    var phone: String? = null

    /**
     * 盐
     */
    @ApiModelProperty("盐")
    var salt: String? = null

    var openid: String? = null

    /**
     * appid
     */
    @ApiModelProperty("appid")
    var appid: String? = null

    /**
     * unionid
     */
    @ApiModelProperty("unionid")
    var unionid: String? = null

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

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    var status: Int? = null

    var cTime: Date? = null

    var uTime: Date? = null

    /**
     * 0未删除1已删除
     */
    @ApiModelProperty("0未删除1已删除")
    var isDel: Byte? = null

    /**
     * 0否 1 是
     */
    @ApiModelProperty("0否 1 是")
    var isAdmin: Int? = null

    /**
     * 开启微信 推送 0 否 1是
     */
    @ApiModelProperty("开启微信 推送 0 否 1是")
    var wechatPush: Int? = null

    override fun toString(): String {
        return "SysStaff{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", code=" + code +
                ", account=" + account +
                ", password=" + password +
                ", name=" + name +
                ", realName=" + realName +
                ", avatar=" + avatar +
                ", email=" + email +
                ", phone=" + phone +
                ", salt=" + salt +
                ", openid=" + openid +
                ", appid=" + appid +
                ", unionid=" + unionid +
                ", creator=" + creator +
                ", updater=" + updater +
                ", status=" + status +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", isDel=" + isDel +
                ", isAdmin=" + isAdmin +
                ", wechatPush=" + wechatPush +
                "}"
    }
}
