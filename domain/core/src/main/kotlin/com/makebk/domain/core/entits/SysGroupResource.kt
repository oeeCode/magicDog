package com.makebk.domain.core.entits

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

import java.io.Serializable

/**
 * <p>
 * 分组资源表
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("sys_group_resource")
@ApiModel(value = "SysGroupResource对象", description = "分组资源表")
class SysGroupResource : Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    /**
     * 组主键
     */
    @ApiModelProperty("组主键")
    var groupId: Long? = null

    /**
     * 资源主键
     */
    @ApiModelProperty("资源主键")
    var resourceId: Long? = null

    /**
     * 0未删除1已删除
     */
    @ApiModelProperty("0未删除1已删除")
    var isDel: Byte? = null

    override fun toString(): String {
        return "SysGroupResource{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", resourceId=" + resourceId +
                ", isDel=" + isDel +
                "}"
    }
}
