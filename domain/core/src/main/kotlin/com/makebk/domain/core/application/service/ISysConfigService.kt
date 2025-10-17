package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.SysConfig
import com.makebk.domain.core.infrastructure.mapper.SysConfigMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 系统配置信息表 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface ISysConfigService : IBaseService<SysConfig>


@Service
class ISysConfigServiceImpl : IBaseServiceImpl<SysConfigMapper, SysConfig>(), ISysConfigService
