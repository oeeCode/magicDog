package com.makebk.domain.core.entits

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

/**
 * <p>
 * 动态数据表单字段设置
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("dynamic_data_form_column")
@ApiModel(value = "DynamicDataFormColumn对象", description = "动态数据表单字段设置")
class DynamicDataFormColumn : Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    /**
     * 表单ID
     */
    @ApiModelProperty("表单ID")
    var formId: Int? = null

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

    /**
     * 显示统计字数
     */
    @ApiModelProperty("显示统计字数")
    var showCount: Int? = null

    /**
     * 最大值
     */
    @ApiModelProperty("最大值")
    var maxlength: BigDecimal? = null

    /**
     * 最小值
     */
    @ApiModelProperty("最小值")
    var minlength: BigDecimal? = null

    /**
     * 精度
     */
    @ApiModelProperty("精度")
    var precision: Int? = null

    /**
     * 类型
     */
    @ApiModelProperty("类型")
    var type: String? = null

    /**
     * 取值范围 对应数据字典
     */
    @ApiModelProperty("取值范围 对应数据字典")
    var valueRange: Int? = null

    /**
     * 是否必填
     */
    @ApiModelProperty("是否必填")
    var required: Int? = null

    /**
     * 表单验证时提示文字 不填写时取 placeholder
     */
    @ApiModelProperty("表单验证时提示文字 不填写时取 placeholder")
    var message: String? = null

    /**
     * 验证正则表单式
     */
    @ApiModelProperty("验证正则表单式")
    var pattern: String? = null

    /**
     * 宽度 类型为upload时有效
     */
    @ApiModelProperty("宽度 类型为upload时有效")
    var width: String? = null

    /**
     * 高度 类型为upload时有效
     */
    @ApiModelProperty("高度 类型为upload时有效")
    var height: String? = null

    override fun toString(): String {
        return "DynamicDataFormColumn{" +
                "id=" + id +
                ", formId=" + formId +
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
                ", showCount=" + showCount +
                ", maxlength=" + maxlength +
                ", minlength=" + minlength +
                ", precision=" + precision +
                ", type=" + type +
                ", valueRange=" + valueRange +
                ", required=" + required +
                ", message=" + message +
                ", pattern=" + pattern +
                ", width=" + width +
                ", height=" + height +
                "}"
    }
}
