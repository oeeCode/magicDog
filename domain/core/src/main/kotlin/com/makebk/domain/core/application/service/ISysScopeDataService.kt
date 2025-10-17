package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.SysScopeData
import com.makebk.domain.core.infrastructure.mapper.SysScopeDataMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 数据权限表 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface ISysScopeDataService : IBaseService<SysScopeData>


@Service
class ISysScopeDataServiceImpl : IBaseServiceImpl<SysScopeDataMapper, SysScopeData>(), ISysScopeDataService
