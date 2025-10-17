package com.makebk.domain.core.infrastructure.mapper

import com.makebk.common.base.mapper.IBaseMapper
import com.makebk.domain.core.entits.SysLogError
import org.apache.ibatis.annotations.Mapper

/**
 * <p>
 * 异常日志 Mapper 接口
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Mapper

interface SysLogErrorMapper : IBaseMapper<SysLogError>
