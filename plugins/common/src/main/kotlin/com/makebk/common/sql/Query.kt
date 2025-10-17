package com.makebk.common.sql

/**
 *  Query
 *  @description：TODO
 *  @author：gme 2023/5/20｜13:43
 *  @version：0.0.1
 */
open class Query() {

    /**
     * 当前页
     */
    var page: Long? = 1
    /**
     * 每页的数量
     */
    var limit: Long? = 15
    /**
     * 正序字段
     */
    var ascs: String? = null
    /**
     * 倒序字段
     */
    var descs: String? = null
    var columns: String? = null
    var alias: String? = null
    var needTotal: Boolean? = false


    constructor(alias: String?) : this() {
        this.alias = alias
    }

    constructor(page: Long?, limit: Long?) : this() {
        this.page = page
        this.limit = limit
    }
    fun buildAlias(alias: String?): Query {
        this.alias = alias
        return this
    }

    fun removeLimit(): Query {
        this.limit = 300000 // 默认最大导出条数
        this.page = null
        return this
    }

    fun removeAlias(): Query {
        this.alias = null
        return this
    }

    override fun toString(): String {
        return "Query(page=$page, limit=$limit, ascs=$ascs, descs=$descs, columns=$columns, alias=$alias), needTotal=$needTotal)"
    }


}