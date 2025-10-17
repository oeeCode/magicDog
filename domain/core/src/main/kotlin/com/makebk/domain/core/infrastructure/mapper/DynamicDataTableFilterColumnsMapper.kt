package com.makebk.domain.core.infrastructure.mapper

import com.makebk.common.base.mapper.IBaseMapper
import com.makebk.domain.core.entits.DynamicDataTableFilterColumns
import org.apache.ibatis.annotations.Mapper

/**
 * <p>
 * 动态数据表格过滤字段设置 Mapper 接口
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Mapper

interface DynamicDataTableFilterColumnsMapper : IBaseMapper<DynamicDataTableFilterColumns>
