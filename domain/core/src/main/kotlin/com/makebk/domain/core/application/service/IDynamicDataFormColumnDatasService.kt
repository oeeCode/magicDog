package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.DynamicDataFormColumnDatas
import com.makebk.domain.core.infrastructure.mapper.DynamicDataFormColumnDatasMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 动态数据表单字段默认值设置 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface IDynamicDataFormColumnDatasService : IBaseService<DynamicDataFormColumnDatas>


@Service
class IDynamicDataFormColumnDatasServiceImpl :
    IBaseServiceImpl<DynamicDataFormColumnDatasMapper, DynamicDataFormColumnDatas>(), IDynamicDataFormColumnDatasService
