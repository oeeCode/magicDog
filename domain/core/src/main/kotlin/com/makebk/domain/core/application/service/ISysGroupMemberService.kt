package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.SysGroupMember
import com.makebk.domain.core.infrastructure.mapper.SysGroupMemberMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 权限组用户 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface ISysGroupMemberService : IBaseService<SysGroupMember>


@Service
class ISysGroupMemberServiceImpl : IBaseServiceImpl<SysGroupMemberMapper, SysGroupMember>(), ISysGroupMemberService
