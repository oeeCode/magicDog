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
 * 分组表
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("sys_group")
@ApiModel(value = "SysGroup对象", description = "分组表")
class SysGroup : Serializable {

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
     * 名称
     */
    @ApiModelProperty("名称")
    var name: String? = null

    /**
     * 类型 1自定义 2用户
     */
    @ApiModelProperty("类型 1自定义 2用户")
    var type: Int? = null

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

    override fun toString(): String {
        return "SysGroup{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", name=" + name +
                ", type=" + type +
                ", creator=" + creator +
                ", updater=" + updater +
                ", status=" + status +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", isDel=" + isDel +
                "}"
    }
}
