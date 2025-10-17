package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.DynamicDataTableFilterColumns
import com.makebk.domain.core.infrastructure.mapper.DynamicDataTableFilterColumnsMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 动态数据表格过滤字段设置 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface IDynamicDataTableFilterColumnsService : IBaseService<DynamicDataTableFilterColumns>


@Service
class IDynamicDataTableFilterColumnsServiceImpl :
    IBaseServiceImpl<DynamicDataTableFilterColumnsMapper, DynamicDataTableFilterColumns>(),
    IDynamicDataTableFilterColumnsService
