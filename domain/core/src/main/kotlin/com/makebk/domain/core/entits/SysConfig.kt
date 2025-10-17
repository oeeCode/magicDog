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
 * 系统配置信息表
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("sys_config")
@ApiModel(value = "SysConfig对象", description = "系统配置信息表")
class SysConfig : Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    /**
     * key
     */
    @ApiModelProperty("key")
    var paramKey: String? = null

    /**
     * value
     */
    @ApiModelProperty("value")
    var paramValue: String? = null

    /**
     * 状态   0：隐藏   1：显示
     */
    @ApiModelProperty("状态   0：隐藏   1：显示")
    var status: Byte? = null

    /**
     * 备注
     */
    @ApiModelProperty("备注")
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
     * 0未删除1已删除
     */
    @ApiModelProperty("0未删除1已删除")
    var isDel: Byte? = null

    override fun toString(): String {
        return "SysConfig{" +
                "id=" + id +
                ", paramKey=" + paramKey +
                ", paramValue=" + paramValue +
                ", status=" + status +
                ", remark=" + remark +
                ", creator=" + creator +
                ", updater=" + updater +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", isDel=" + isDel +
                "}"
    }
}
