package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.DynamicDataFormButtons
import com.makebk.domain.core.infrastructure.mapper.DynamicDataFormButtonsMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 动态数据表单操作按钮设置 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface IDynamicDataFormButtonsService : IBaseService<DynamicDataFormButtons>


@Service
class IDynamicDataFormButtonsServiceImpl : IBaseServiceImpl<DynamicDataFormButtonsMapper, DynamicDataFormButtons>(),
    IDynamicDataFormButtonsService
