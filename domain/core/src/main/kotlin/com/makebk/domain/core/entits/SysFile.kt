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
 * 文件上传
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@TableName("sys_file")
@ApiModel(value = "SysFile对象", description = "文件上传")
class SysFile : Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    /**
     * URL地址
     */
    @ApiModelProperty("URL地址")
    var url: String? = null

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    var createDate: Date? = null

    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    var filename: String? = null

    /**
     * 文件路径
     */
    @ApiModelProperty("文件路径")
    var path: String? = null

    var type: String? = null

    var size: Long? = null

    /**
     * 0未删除1已删除
     */
    @ApiModelProperty("0未删除1已删除")
    var isDel: Int? = null

    override fun toString(): String {
        return "SysFile{" +
                "id=" + id +
                ", url=" + url +
                ", createDate=" + createDate +
                ", filename=" + filename +
                ", path=" + path +
                ", type=" + type +
                ", size=" + size +
                ", isDel=" + isDel +
                "}"
    }
}
