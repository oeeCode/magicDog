package com.makebk.domain.core.entits

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

import java.io.Serializable

/**
 * <p>
 * 权限组用户
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("sys_group_member")
@ApiModel(value = "SysGroupMember对象", description = "权限组用户")
class SysGroupMember : Serializable {

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
     * 组内用户主键
     */
    @ApiModelProperty("组内用户主键")
    var memberId: Long? = null

    /**
     * 0未删除1已删除
     */
    @ApiModelProperty("0未删除1已删除")
    var isDel: Byte? = null

    override fun toString(): String {
        return "SysGroupMember{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", memberId=" + memberId +
                ", isDel=" + isDel +
                "}"
    }
}
