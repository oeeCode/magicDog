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
 * 广告信息表
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("ad_info")
@ApiModel(value = "AdInfo对象", description = "广告信息表")
class AdInfo : Serializable {

    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    /**
     * 广告编码，唯一标识
     */
    @ApiModelProperty("广告编码，唯一标识")
    var adCode: String? = null

    /**
     * 广告名称
     */
    @ApiModelProperty("广告名称")
    var adName: String? = null

    /**
     * 广告类型：image(图片),video(视频),text(文字),html(富文本)
     */
    @ApiModelProperty("广告类型：image(图片),video(视频),text(文字),html(富文本)")
    var adType: String? = null

    /**
     * 广告内容，根据类型存储不同格式的内容
     */
    @ApiModelProperty("广告内容，根据类型存储不同格式的内容")
    var adContent: String? = null

    /**
     * 广告跳转链接
     */
    @ApiModelProperty("广告跳转链接")
    var adLink: String? = null

    /**
     * 跳转方式：blank(新窗口),self(当前窗口),miniapp(小程序跳转)
     */
    @ApiModelProperty("跳转方式：blank(新窗口),self(当前窗口),miniapp(小程序跳转)")
    var targetType: String? = null

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
     * 优先级，数值越大优先级越高
     */
    @ApiModelProperty("优先级，数值越大优先级越高")
    var priority: Int? = null

    /**
     * 广告开始展示时间
     */
    @ApiModelProperty("广告开始展示时间")
    var startTime: Date? = null

    /**
     * 广告结束展示时间
     */
    @ApiModelProperty("广告结束展示时间")
    var endTime: Date? = null

    /**
     * 状态：0-禁用，1-启用
     */
    @ApiModelProperty("状态：0-禁用，1-启用")
    var status: Byte? = null

    /**
     * 展示次数统计
     */
    @ApiModelProperty("展示次数统计")
    var showCount: Int? = null

    /**
     * 点击次数统计
     */
    @ApiModelProperty("点击次数统计")
    var clickCount: Int? = null

    /**
     * 转化次数统计
     */
    @ApiModelProperty("转化次数统计")
    var conversionCount: Int? = null

    /**
     * 计费类型：cpc(按点击),cpm(按展示),cpa(按转化),cpcv(按有效点击)
     */
    @ApiModelProperty("计费类型：cpc(按点击),cpm(按展示),cpa(按转化),cpcv(按有效点击)")
    var costType: String? = null

    /**
     * 费用金额
     */
    @ApiModelProperty("费用金额")
    var costAmount: BigDecimal? = null

    /**
     * 日预算
     */
    @ApiModelProperty("日预算")
    var dailyBudget: BigDecimal? = null

    /**
     * 总预算
     */
    @ApiModelProperty("总预算")
    var totalBudget: BigDecimal? = null

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
        return "AdInfo{" +
                "id=" + id +
                ", adCode=" + adCode +
                ", adName=" + adName +
                ", adType=" + adType +
                ", adContent=" + adContent +
                ", adLink=" + adLink +
                ", targetType=" + targetType +
                ", positionCode=" + positionCode +
                ", pageCode=" + pageCode +
                ", priority=" + priority +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", showCount=" + showCount +
                ", clickCount=" + clickCount +
                ", conversionCount=" + conversionCount +
                ", costType=" + costType +
                ", costAmount=" + costAmount +
                ", dailyBudget=" + dailyBudget +
                ", totalBudget=" + totalBudget +
                ", creator=" + creator +
                ", updater=" + updater +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", version=" + version +
                ", isDel=" + isDel +
                "}"
    }
}
