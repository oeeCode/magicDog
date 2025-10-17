package com.makebk.domain.core.infrastructure.mapper

import com.makebk.common.base.mapper.IBaseMapper
import com.makebk.domain.core.entits.SysGroupMember
import org.apache.ibatis.annotations.Mapper

/**
 * <p>
 * 权限组用户 Mapper 接口
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Mapper

interface SysGroupMemberMapper : IBaseMapper<SysGroupMember>
