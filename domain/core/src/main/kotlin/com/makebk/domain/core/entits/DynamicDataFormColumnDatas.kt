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
 * 动态数据表单字段默认值设置
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("dynamic_data_form_column_datas")
@ApiModel(value = "DynamicDataFormColumnDatas对象", description = "动态数据表单字段默认值设置")
class DynamicDataFormColumnDatas : Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    /**
     * 条目ID
     */
    @ApiModelProperty("条目ID")
    var columnId: Int? = null

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
     * 值
     */
    @ApiModelProperty("值")
    var value: String? = null

    /**
     * 显示标签
     */
    @ApiModelProperty("显示标签")
    var label: String? = null

    override fun toString(): String {
        return "DynamicDataFormColumnDatas{" +
                "id=" + id +
                ", columnId=" + columnId +
                ", creator=" + creator +
                ", updater=" + updater +
                ", status=" + status +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", isDel=" + isDel +
                ", seq=" + seq +
                ", value=" + value +
                ", label=" + label +
                "}"
    }
}
