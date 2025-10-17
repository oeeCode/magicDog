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
 * 动态数据表格设置
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("dynamic_data_table_actions")
@ApiModel(value = "DynamicDataTableActions对象", description = "动态数据表格设置")
class DynamicDataTableActions : Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    /**
     * 资源ID
     */
    @ApiModelProperty("资源ID")
    var tableId: Int? = null

    /**
     * 按钮类型
     */
    @ApiModelProperty("按钮类型 ")
    var type: String? = null

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    var creator: Long? = null

    /**
     * 尺寸
     */
    @ApiModelProperty("尺寸")
    var size: String? = null

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

    /**
     * 权限
     */
    @ApiModelProperty("权限")
    var permissions: String? = null

    var cTime: Date? = null

    var uTime: Date? = null

    /**
     * 0未删除1已删除
     */
    @ApiModelProperty("0未删除1已删除")
    var isDel: Byte? = null

    /**
     * 按钮显示文字
     */
    @ApiModelProperty("按钮显示文字")
    var text: String? = null

    /**
     * 显示类型
     */
    @ApiModelProperty("显示类型")
    var displayType: String? = null

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    var icon: String? = null

    /**
     * 事件
     */
    @ApiModelProperty("事件")
    var event: String? = null

    override fun toString(): String {
        return "DynamicDataTableActions{" +
                "id=" + id +
                ", tableId=" + tableId +
                ", type=" + type +
                ", creator=" + creator +
                ", size=" + size +
                ", updater=" + updater +
                ", status=" + status +
                ", permissions=" + permissions +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", isDel=" + isDel +
                ", text=" + text +
                ", displayType=" + displayType +
                ", icon=" + icon +
                ", event=" + event +
                "}"
    }
}
