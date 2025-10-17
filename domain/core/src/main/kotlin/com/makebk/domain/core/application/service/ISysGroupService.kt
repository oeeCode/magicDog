package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.SysGroup
import com.makebk.domain.core.infrastructure.mapper.SysGroupMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 分组表 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface ISysGroupService : IBaseService<SysGroup>


@Service
class ISysGroupServiceImpl : IBaseServiceImpl<SysGroupMapper, SysGroup>(), ISysGroupService
