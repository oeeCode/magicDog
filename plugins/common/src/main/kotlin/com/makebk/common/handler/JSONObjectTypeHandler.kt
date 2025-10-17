package com.makebk.common.handler

import cn.hutool.json.JSONObject
import cn.hutool.json.JSONUtil
import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import org.apache.ibatis.type.MappedJdbcTypes
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

/**
 *  JSONObjectTypeHandler
 *  @description：TODO
 *  @author：gme 2023/2/10｜11:11
 *  @version：0.0.1
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
class JSONObjectTypeHandler : BaseTypeHandler<JSONObject?>() {
    @Throws(SQLException::class)
    override fun setNonNullParameter(ps: PreparedStatement?, i: Int, array: JSONObject?, jdbcType: JdbcType?) {
        ps?.setString(i, array.toString())
    }

    @Throws(SQLException::class)
    override fun getNullableResult(resultSet: ResultSet, columnName: String?): JSONObject {
        return JSONUtil.parseObj(resultSet.getString(columnName))
    }

    @Throws(SQLException::class)
    override fun getNullableResult(resultSet: ResultSet, columnIndex: Int): JSONObject {
        return JSONUtil.parseObj(resultSet.getString(columnIndex))
    }

    @Throws(SQLException::class)
    override fun getNullableResult(callableStatement: CallableStatement, columnIndex: Int): JSONObject {
        return JSONUtil.parseObj(callableStatement.getString(columnIndex))
    }
}
