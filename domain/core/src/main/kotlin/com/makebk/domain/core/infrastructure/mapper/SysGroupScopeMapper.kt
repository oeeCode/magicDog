package com.makebk.domain.core.infrastructure.mapper

import com.makebk.common.base.mapper.IBaseMapper
import com.makebk.domain.core.entits.SysGroupScope
import org.apache.ibatis.annotations.Mapper

/**
 * <p>
 * 权限组查询范围 Mapper 接口
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Mapper

interface SysGroupScopeMapper : IBaseMapper<SysGroupScope>
