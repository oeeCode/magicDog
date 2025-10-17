package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.SysGroupScope
import com.makebk.domain.core.infrastructure.mapper.SysGroupScopeMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 权限组查询范围 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface ISysGroupScopeService : IBaseService<SysGroupScope>


@Service
class ISysGroupScopeServiceImpl : IBaseServiceImpl<SysGroupScopeMapper, SysGroupScope>(), ISysGroupScopeService
