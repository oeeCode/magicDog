package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.AdPositionConfig
import com.makebk.domain.core.infrastructure.mapper.AdPositionConfigMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 广告位置配置表 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface IAdPositionConfigService : IBaseService<AdPositionConfig>


@Service
class IAdPositionConfigServiceImpl : IBaseServiceImpl<AdPositionConfigMapper, AdPositionConfig>(),
    IAdPositionConfigService
