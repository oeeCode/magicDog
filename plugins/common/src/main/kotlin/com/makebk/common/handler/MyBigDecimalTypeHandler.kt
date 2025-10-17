package com.makebk.common.handler

import org.apache.ibatis.type.BigDecimalTypeHandler
import org.apache.ibatis.type.JdbcType
import org.apache.ibatis.type.MappedJdbcTypes
import java.math.BigDecimal
import java.math.RoundingMode
import java.sql.CallableStatement
import java.sql.ResultSet


/**
 * @Package com.makebk.common.handler
 * @author GME
 * @date 2023/9/19 2:03 PM
 * @version V1.0
 * @Copyright © 2014-2023 码克布克网络工作室
 */
@MappedJdbcTypes(JdbcType.DECIMAL)
class MyBigDecimalTypeHandler : BigDecimalTypeHandler() {
    override fun getNullableResult(rs: ResultSet?, columnName: String?): BigDecimal =
        if (super.getNullableResult(rs, columnName) == null) {
            BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
        } else {
            super.getNullableResult(rs, columnName).setScale(2, RoundingMode.HALF_UP)
        }

    override fun getNullableResult(rs: ResultSet?, columnIndex: Int): BigDecimal =
        if (super.getNullableResult(rs, columnIndex) == null) {
            BigDecimal.ZERO.setScale(2, RoundingMode.UP)
        } else {
            super.getNullableResult(rs, columnIndex).setScale(2, RoundingMode.HALF_UP)
        }

    override fun getNullableResult(cs: CallableStatement?, columnIndex: Int): BigDecimal =
        if (super.getNullableResult(cs, columnIndex) == null) {
            BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
        } else {
            super.getNullableResult(cs, columnIndex).setScale(2, RoundingMode.HALF_UP)
        }

}