package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.SysStaff
import com.makebk.domain.core.infrastructure.mapper.SysStaffMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 员工表 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface ISysStaffService : IBaseService<SysStaff>


@Service
class ISysStaffServiceImpl : IBaseServiceImpl<SysStaffMapper, SysStaff>(), ISysStaffService
