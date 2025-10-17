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
 * 资源
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("sys_resource_next")
@ApiModel(value = "SysResourceNext对象", description = "资源")
class SysResourceNext : Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    var title: String? = null

    /**
     * 属于归属 1 PC web  2 小程序  3 h5
     */
    @ApiModelProperty("属于归属 1 PC web  2 小程序  3 h5")
    var belong: Int? = null

    /**
     * 国际化
     */
    @ApiModelProperty("国际化")
    var i18nKey: String? = null

    /**
     * 类型
     */
    @ApiModelProperty("类型")
    var type: Int? = null

    /**
     * 代码
     */
    @ApiModelProperty("代码")
    var code: String? = null

    /**
     * 组件
     */
    @ApiModelProperty("组件")
    var component: String? = null

    /**
     * 隐藏菜单 0否 1是
     */
    @ApiModelProperty("隐藏菜单 0否 1是")
    var hideMenu: Int? = null

    /**
     * 常量路由 0 否 1是
     */
    @ApiModelProperty("常量路由 0 否 1是")
    var constant: Int? = null

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    var icon: String? = null

    /**
     * 路径
     */
    @ApiModelProperty("路径")
    var path: String? = null

    /**
     * 上级ID
     */
    @ApiModelProperty("上级ID")
    var parentId: Long? = null

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    var sort: Long? = null

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
        return "SysResourceNext{" +
                "id=" + id +
                ", title=" + title +
                ", belong=" + belong +
                ", i18nKey=" + i18nKey +
                ", type=" + type +
                ", code=" + code +
                ", component=" + component +
                ", hideMenu=" + hideMenu +
                ", constant=" + constant +
                ", icon=" + icon +
                ", path=" + path +
                ", parentId=" + parentId +
                ", sort=" + sort +
                ", creator=" + creator +
                ", updater=" + updater +
                ", status=" + status +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", isDel=" + isDel +
                "}"
    }
}
