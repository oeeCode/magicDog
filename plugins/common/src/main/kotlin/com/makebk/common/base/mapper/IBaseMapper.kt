package com.makebk.common.base.mapper

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.toolkit.Constants
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.mapping.ResultSetType
import org.apache.ibatis.session.ResultHandler

interface IBaseMapper<T> : BaseMapper<T> {

    /**
     *  根据条件查询返回数据条目条数
     */
    fun queryListQueryWrapper_mpCount(
        @Param(Constants.WRAPPER) queryWrapper: QueryWrapper<T>,
    ): Long


    /**
     * 根据条件查询返回数据列表
     */
    fun queryListQueryWrapper(
        @Param(Constants.WRAPPER) queryWrapper: QueryWrapper<T>,
    ): List<T>


    /**
     * 根据条件查询返回数据列表
     */
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = Integer.MIN_VALUE)
    fun queryListQueryWrapper(
        @Param(Constants.WRAPPER) queryWrapper: QueryWrapper<T>,
        handler: ResultHandler<T>,
    )

}