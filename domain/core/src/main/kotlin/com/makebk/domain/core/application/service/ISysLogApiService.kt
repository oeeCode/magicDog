package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.SysLogApi
import com.makebk.domain.core.infrastructure.mapper.SysLogApiMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 接口日志 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface ISysLogApiService : IBaseService<SysLogApi>


@Service
class ISysLogApiServiceImpl : IBaseServiceImpl<SysLogApiMapper, SysLogApi>(), ISysLogApiService
