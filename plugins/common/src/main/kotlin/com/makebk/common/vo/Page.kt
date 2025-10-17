package com.makebk.common.vo

import com.baomidou.mybatisplus.core.metadata.IPage
import java.io.Serializable
import kotlin.math.ceil

open class Page<T>() : Serializable {
    /**
     * 总记录数
     */
    var totalCount = 0

    /**
     * 每页记录数
     */
    var pageSize = 0

    /**
     * 总页数
     */
    var totalPage = 0

    /**
     * 当前页数
     */
    var currPage = 0

    /**
     * 列表数据
     */
    var list: List<T>? = null

    /**
     * 分页
     *
     * @param list       列表数据
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param currPage   当前页数
     */
    constructor(list: List<T>?, totalCount: Int, pageSize: Number?, currPage: Number?) : this() {
        this.list = list
        this.totalCount = totalCount
        this.pageSize = (pageSize ?: 1).toInt()
        this.currPage = (currPage ?: 1).toInt()
        this.totalPage = ceil(totalCount.toDouble() / (pageSize ?: 1).toInt()).toInt()
    }

    /**
     * 分页
     */
    constructor(page: IPage<T>?) : this() {
        this.list = page?.records
        if (page != null) {
            this.totalCount = page.total.toInt()
            this.pageSize = page.size.toInt()
            this.currPage = page.current.toInt()
            this.totalPage = page.pages.toInt()
        }
    }


    /**
     * 分页
     */
    constructor(list: List<T>?) : this() {
        if (list != null) {
            this.totalCount = list.size
            this.list = list
        }
    }

}