package com.makebk.domain.core.infrastructure.mapper

import com.makebk.common.base.mapper.IBaseMapper
import com.makebk.domain.core.entits.SysStaff
import org.apache.ibatis.annotations.Mapper

/**
 * <p>
 * 员工表 Mapper 接口
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Mapper

interface SysStaffMapper : IBaseMapper<SysStaff>
