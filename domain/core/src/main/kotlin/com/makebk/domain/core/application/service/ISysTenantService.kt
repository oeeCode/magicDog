package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.SysTenant
import com.makebk.domain.core.infrastructure.mapper.SysTenantMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 租户表 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface ISysTenantService : IBaseService<SysTenant>


@Service
class ISysTenantServiceImpl : IBaseServiceImpl<SysTenantMapper, SysTenant>(), ISysTenantService
