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
 * 动态数据表格过滤字段设置
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("dynamic_data_table_filter_columns")
@ApiModel(value = "DynamicDataTableFilterColumns对象", description = "动态数据表格过滤字段设置")
class DynamicDataTableFilterColumns : Serializable {

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
     * 标签
     */
    @ApiModelProperty("标签")
    var label: String? = null

    /**
     * name
     */
    @ApiModelProperty("name")
    var name: String? = null

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
     * 排序
     */
    @ApiModelProperty("排序")
    var seq: Int? = null

    /**
     * 占位符
     */
    @ApiModelProperty("占位符")
    var placeholder: String? = null

    /**
     * 提示文字
     */
    @ApiModelProperty("提示文字")
    var tips: String? = null

    /**
     * 是否禁用
     */
    @ApiModelProperty("是否禁用")
    var disabled: Int? = null

    /**
     * 默认值
     */
    @ApiModelProperty("默认值")
    var value: String? = null

    /**
     * 尺寸
     */
    @ApiModelProperty("尺寸")
    var size: String? = null

    override fun toString(): String {
        return "DynamicDataTableFilterColumns{" +
                "id=" + id +
                ", tableId=" + tableId +
                ", label=" + label +
                ", name=" + name +
                ", creator=" + creator +
                ", updater=" + updater +
                ", status=" + status +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", isDel=" + isDel +
                ", seq=" + seq +
                ", placeholder=" + placeholder +
                ", tips=" + tips +
                ", disabled=" + disabled +
                ", value=" + value +
                ", size=" + size +
                "}"
    }
}
