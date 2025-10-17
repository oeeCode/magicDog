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
 * 广告位置配置表
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("ad_position_config")
@ApiModel(value = "AdPositionConfig对象", description = "广告位置配置表")
class AdPositionConfig : Serializable {

    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    /**
     * 广告位编码
     */
    @ApiModelProperty("广告位编码")
    var positionCode: String? = null

    /**
     * 广告位名称
     */
    @ApiModelProperty("广告位名称")
    var positionName: String? = null

    /**
     * 页面编码
     */
    @ApiModelProperty("页面编码")
    var pageCode: String? = null

    /**
     * 页面名称
     */
    @ApiModelProperty("页面名称")
    var pageName: String? = null

    /**
     * 广告位类型：banner(横幅),popup(弹窗),sidebar(侧边栏),content(内容中)
     */
    @ApiModelProperty("广告位类型：banner(横幅),popup(弹窗),sidebar(侧边栏),content(内容中)")
    var positionType: String? = null

    /**
     * 广告位尺寸，格式：宽x高，如：300x250
     */
    @ApiModelProperty("广告位尺寸，格式：宽x高，如：300x250")
    var positionSize: String? = null

    /**
     * 广告位样式配置，JSON格式存储
     */
    @ApiModelProperty("广告位样式配置，JSON格式存储")
    var positionStyle: String? = null

    /**
     * 最大广告数量
     */
    @ApiModelProperty("最大广告数量")
    var maxAds: Int? = null

    /**
     * 展示模式：sequence(顺序),random(随机),rotation(轮播)
     */
    @ApiModelProperty("展示模式：sequence(顺序),random(随机),rotation(轮播)")
    var displayMode: String? = null

    /**
     * 刷新间隔（秒），0表示不自动刷新
     */
    @ApiModelProperty("刷新间隔（秒），0表示不自动刷新")
    var refreshInterval: Int? = null

    /**
     * 是否启用开关：0-禁用，1-启用
     */
    @ApiModelProperty("是否启用开关：0-禁用，1-启用")
    var enableSwitch: Byte? = null

    /**
     * 目标设备：all(全部),mobile(移动端),pc(桌面端)
     */
    @ApiModelProperty("目标设备：all(全部),mobile(移动端),pc(桌面端)")
    var targetDevices: String? = null

    /**
     * 目标平台：all(全部),ios,android,miniapp,h5,pc
     */
    @ApiModelProperty("目标平台：all(全部),ios,android,miniapp,h5,pc")
    var targetPlatforms: String? = null

    /**
     * 目标地区，JSON格式存储地区编码
     */
    @ApiModelProperty("目标地区，JSON格式存储地区编码")
    var targetRegions: String? = null

    /**
     * 广告位描述
     */
    @ApiModelProperty("广告位描述")
    var description: String? = null

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    var creator: String? = null

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    var updater: String? = null

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    var cTime: Date? = null

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    var uTime: Date? = null

    /**
     * 版本号，用于乐观锁
     */
    @ApiModelProperty("版本号，用于乐观锁")
    var version: Int? = null

    /**
     * 是否删除：0-未删除，1-已删除
     */
    @ApiModelProperty("是否删除：0-未删除，1-已删除")
    var isDel: Byte? = null

    override fun toString(): String {
        return "AdPositionConfig{" +
                "id=" + id +
                ", positionCode=" + positionCode +
                ", positionName=" + positionName +
                ", pageCode=" + pageCode +
                ", pageName=" + pageName +
                ", positionType=" + positionType +
                ", positionSize=" + positionSize +
                ", positionStyle=" + positionStyle +
                ", maxAds=" + maxAds +
                ", displayMode=" + displayMode +
                ", refreshInterval=" + refreshInterval +
                ", enableSwitch=" + enableSwitch +
                ", targetDevices=" + targetDevices +
                ", targetPlatforms=" + targetPlatforms +
                ", targetRegions=" + targetRegions +
                ", description=" + description +
                ", creator=" + creator +
                ", updater=" + updater +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", version=" + version +
                ", isDel=" + isDel +
                "}"
    }
}
