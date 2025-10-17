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
 * 字典
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("sys_dictionary")
@ApiModel(value = "SysDictionary对象", description = "字典")
class SysDictionary : Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    /**
     * 上级主键
     */
    @ApiModelProperty("上级主键")
    var parentId: Long? = null

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    var sort: Int? = null

    /**
     * 字典值
     */
    @ApiModelProperty("字典值")
    var dictKey: String? = null

    /**
     * 字典名称
     */
    @ApiModelProperty("字典名称")
    var dictValue: String? = null

    /**
     * 说明
     */
    @ApiModelProperty("说明")
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
        return "SysDictionary{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", sort=" + sort +
                ", dictKey=" + dictKey +
                ", dictValue=" + dictValue +
                ", remark=" + remark +
                ", creator=" + creator +
                ", updater=" + updater +
                ", status=" + status +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", isDel=" + isDel +
                "}"
    }
}
