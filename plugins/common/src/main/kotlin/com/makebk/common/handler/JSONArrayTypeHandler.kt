package com.makebk.common.handler

import cn.hutool.json.JSONArray
import cn.hutool.json.JSONUtil
import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import org.apache.ibatis.type.MappedJdbcTypes
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

/**
 *  JSONArrayTypeHandler
 *  @description：TODO
 *  @author：gme 2023/2/10｜11:11
 *  @version：0.0.1
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
class JSONArrayTypeHandler : BaseTypeHandler<JSONArray?>() {

    override fun setNonNullParameter(ps: PreparedStatement?, i: Int, parameter: JSONArray?, jdbcType: JdbcType?) {
        ps?.setString(i, parameter.toString())
    }

    @Throws(SQLException::class)
    override fun getNullableResult(resultSet: ResultSet, columnName: String?): JSONArray {
        return JSONUtil.parseArray(resultSet.getString(columnName))
    }

    @Throws(SQLException::class)
    override fun getNullableResult(resultSet: ResultSet, columnIndex: Int): JSONArray {
        return JSONUtil.parseArray(resultSet.getString(columnIndex))
    }

    @Throws(SQLException::class)
    override fun getNullableResult(callableStatement: CallableStatement, columnIndex: Int): JSONArray {
        return JSONUtil.parseArray(callableStatement.getString(columnIndex))
    }
}