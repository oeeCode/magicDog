package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.DynamicDataFormColumnActions
import com.makebk.domain.core.infrastructure.mapper.DynamicDataFormColumnActionsMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 动态数据表单字段更多操作设置 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface IDynamicDataFormColumnActionsService : IBaseService<DynamicDataFormColumnActions>


@Service
class IDynamicDataFormColumnActionsServiceImpl :
    IBaseServiceImpl<DynamicDataFormColumnActionsMapper, DynamicDataFormColumnActions>(),
    IDynamicDataFormColumnActionsService
