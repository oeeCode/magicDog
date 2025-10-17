package com.makebk.domain.core.entits

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

import java.io.Serializable

/**
 * <p>
 * 权限组查询范围
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("sys_group_scope")
@ApiModel(value = "SysGroupScope对象", description = "权限组查询范围")
class SysGroupScope : Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    /**
     * 数据权限id
     */
    @ApiModelProperty("数据权限id")
    var scopeId: Long? = null

    /**
     * 组id
     */
    @ApiModelProperty("组id")
    var groupId: Long? = null

    /**
     * 0未删除1已删除
     */
    @ApiModelProperty("0未删除1已删除")
    var isDel: Byte? = null

    override fun toString(): String {
        return "SysGroupScope{" +
                "id=" + id +
                ", scopeId=" + scopeId +
                ", groupId=" + groupId +
                ", isDel=" + isDel +
                "}"
    }
}
