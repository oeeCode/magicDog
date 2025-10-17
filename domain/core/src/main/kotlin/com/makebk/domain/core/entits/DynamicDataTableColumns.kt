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
@TableName("dynamic_data_table_columns")
@ApiModel(value = "DynamicDataTableColumns对象", description = "动态数据表格设置")
class DynamicDataTableColumns : Serializable {

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
     * key
     */
    @ApiModelProperty("key")
    var key: String? = null

    /**
     * type
     */
    @ApiModelProperty("type")
    var type: String? = null

    /**
     * 固定位置
     */
    @ApiModelProperty("固定位置")
    var fixed: String? = null

    /**
     * 宽度
     */
    @ApiModelProperty("宽度")
    var width: String? = null

    /**
     * 最小宽度
     */
    @ApiModelProperty("最小宽度")
    var minWidth: String? = null

    /**
     * 最大宽度
     */
    @ApiModelProperty("最大宽度")
    var maxWidth: String? = null

    /**
     * 样式名称
     */
    @ApiModelProperty("样式名称")
    var className: String? = null

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    var tilte: String? = null

    /**
     * 内容对其
     */
    @ApiModelProperty("内容对其")
    var align: String? = null

    /**
     * 标题对其
     */
    @ApiModelProperty("标题对其")
    var titleAlign: String? = null

    /**
     * 省略
     */
    @ApiModelProperty("省略")
    var ellipsis: String? = null

    /**
     * 是否可以改变大小 0否 1是
     */
    @ApiModelProperty("是否可以改变大小 0否 1是")
    var resizable: Int? = null

    /**
     * 排序字段
     */
    @ApiModelProperty("排序字段")
    var sortOrder: String? = null

    /**
     * 自定义渲染器
     */
    @ApiModelProperty("自定义渲染器")
    var render: String? = null

    /**
     * 显示顺序
     */
    @ApiModelProperty("显示顺序")
    var seq: Int? = null

    override fun toString(): String {
        return "DynamicDataTableColumns{" +
                "id=" + id +
                ", tableId=" + tableId +
                ", creator=" + creator +
                ", updater=" + updater +
                ", status=" + status +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", isDel=" + isDel +
                ", key=" + key +
                ", type=" + type +
                ", fixed=" + fixed +
                ", width=" + width +
                ", minWidth=" + minWidth +
                ", maxWidth=" + maxWidth +
                ", className=" + className +
                ", tilte=" + tilte +
                ", align=" + align +
                ", titleAlign=" + titleAlign +
                ", ellipsis=" + ellipsis +
                ", resizable=" + resizable +
                ", sortOrder=" + sortOrder +
                ", render=" + render +
                ", seq=" + seq +
                "}"
    }
}
