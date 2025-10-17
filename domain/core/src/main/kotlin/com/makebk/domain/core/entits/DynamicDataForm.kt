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
 * 动态数据表单设置
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("dynamic_data_form")
@ApiModel(value = "DynamicDataForm对象", description = "动态数据表单设置")
class DynamicDataForm : Serializable {

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
    var resourceId: Int? = null

    /**
     * 按钮显示位置
     */
    @ApiModelProperty("按钮显示位置")
    var buttonPos: String? = null

    /**
     * 命名空间
     */
    @ApiModelProperty("命名空间")
    var nameSpace: String? = null

    /**
     * 描述说明
     */
    @ApiModelProperty("描述说明")
    var remark: String? = null

    /**
     * 自定义请求函数对应命名空间中函数
     */
    @ApiModelProperty("自定义请求函数对应命名空间中函数")
    var method: String? = null

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
        return "DynamicDataForm{" +
                "id=" + id +
                ", resourceId=" + resourceId +
                ", buttonPos=" + buttonPos +
                ", nameSpace=" + nameSpace +
                ", remark=" + remark +
                ", method=" + method +
                ", creator=" + creator +
                ", updater=" + updater +
                ", status=" + status +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", isDel=" + isDel +
                "}"
    }
}
