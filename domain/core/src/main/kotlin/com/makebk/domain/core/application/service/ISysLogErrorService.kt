package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.SysLogError
import com.makebk.domain.core.infrastructure.mapper.SysLogErrorMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 异常日志 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface ISysLogErrorService : IBaseService<SysLogError>


@Service
class ISysLogErrorServiceImpl : IBaseServiceImpl<SysLogErrorMapper, SysLogError>(), ISysLogErrorService
