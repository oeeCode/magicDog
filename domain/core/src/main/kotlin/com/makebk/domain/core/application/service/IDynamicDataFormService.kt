package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.DynamicDataForm
import com.makebk.domain.core.infrastructure.mapper.DynamicDataFormMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 动态数据表单设置 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface IDynamicDataFormService : IBaseService<DynamicDataForm>


@Service
class IDynamicDataFormServiceImpl : IBaseServiceImpl<DynamicDataFormMapper, DynamicDataForm>(), IDynamicDataFormService
