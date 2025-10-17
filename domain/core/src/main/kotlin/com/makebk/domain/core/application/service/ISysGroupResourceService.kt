package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.SysGroupResource
import com.makebk.domain.core.infrastructure.mapper.SysGroupResourceMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 分组资源表 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface ISysGroupResourceService : IBaseService<SysGroupResource>


@Service
class ISysGroupResourceServiceImpl : IBaseServiceImpl<SysGroupResourceMapper, SysGroupResource>(),
    ISysGroupResourceService
