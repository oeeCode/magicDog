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
 * 广告展示统计表
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("ad_show_statistics")
@ApiModel(value = "AdShowStatistics对象", description = "广告展示统计表")
class AdShowStatistics : Serializable {

    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    /**
     * 广告ID
     */
    @ApiModelProperty("广告ID")
    var adId: Long? = null

    /**
     * 广告编码
     */
    @ApiModelProperty("广告编码")
    var adCode: String? = null

    /**
     * 广告位编码
     */
    @ApiModelProperty("广告位编码")
    var positionCode: String? = null

    /**
     * 页面编码
     */
    @ApiModelProperty("页面编码")
    var pageCode: String? = null

    /**
     * 展示日期
     */
    @ApiModelProperty("展示日期")
    var showDate: Date? = null

    /**
     * 展示小时（0-23）
     */
    @ApiModelProperty("展示小时（0-23）")
    var showHour: Int? = null

    /**
     * 展示次数
     */
    @ApiModelProperty("展示次数")
    var showCount: Int? = null

    /**
     * 点击次数
     */
    @ApiModelProperty("点击次数")
    var clickCount: Int? = null

    /**
     * 转化次数
     */
    @ApiModelProperty("转化次数")
    var conversionCount: Int? = null

    /**
     * 独立IP数
     */
    @ApiModelProperty("独立IP数")
    var ipCount: Int? = null

    /**
     * 独立访客数
     */
    @ApiModelProperty("独立访客数")
    var uvCount: Int? = null

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    var cTime: Date? = null

    override fun toString(): String {
        return "AdShowStatistics{" +
                "id=" + id +
                ", adId=" + adId +
                ", adCode=" + adCode +
                ", positionCode=" + positionCode +
                ", pageCode=" + pageCode +
                ", showDate=" + showDate +
                ", showHour=" + showHour +
                ", showCount=" + showCount +
                ", clickCount=" + clickCount +
                ", conversionCount=" + conversionCount +
                ", ipCount=" + ipCount +
                ", uvCount=" + uvCount +
                ", cTime=" + cTime +
                "}"
    }
}
