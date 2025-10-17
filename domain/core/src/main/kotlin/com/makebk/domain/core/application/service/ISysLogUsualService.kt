package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.SysLogUsual
import com.makebk.domain.core.infrastructure.mapper.SysLogUsualMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 通用日志 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface ISysLogUsualService : IBaseService<SysLogUsual>


@Service
class ISysLogUsualServiceImpl : IBaseServiceImpl<SysLogUsualMapper, SysLogUsual>(), ISysLogUsualService
