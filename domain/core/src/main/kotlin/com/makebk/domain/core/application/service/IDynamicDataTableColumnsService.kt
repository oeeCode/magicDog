package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.DynamicDataTableColumns
import com.makebk.domain.core.infrastructure.mapper.DynamicDataTableColumnsMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 动态数据表格设置 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface IDynamicDataTableColumnsService : IBaseService<DynamicDataTableColumns>


@Service
class IDynamicDataTableColumnsServiceImpl : IBaseServiceImpl<DynamicDataTableColumnsMapper, DynamicDataTableColumns>(),
    IDynamicDataTableColumnsService
