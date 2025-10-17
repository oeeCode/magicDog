package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.DynamicDataTableActions
import com.makebk.domain.core.infrastructure.mapper.DynamicDataTableActionsMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 动态数据表格设置 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface IDynamicDataTableActionsService : IBaseService<DynamicDataTableActions>


@Service
class IDynamicDataTableActionsServiceImpl : IBaseServiceImpl<DynamicDataTableActionsMapper, DynamicDataTableActions>(),
    IDynamicDataTableActionsService
