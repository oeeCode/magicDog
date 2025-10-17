package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.DynamicDataFormColumn
import com.makebk.domain.core.infrastructure.mapper.DynamicDataFormColumnMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 动态数据表单字段设置 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface IDynamicDataFormColumnService : IBaseService<DynamicDataFormColumn>


@Service
class IDynamicDataFormColumnServiceImpl : IBaseServiceImpl<DynamicDataFormColumnMapper, DynamicDataFormColumn>(),
    IDynamicDataFormColumnService
