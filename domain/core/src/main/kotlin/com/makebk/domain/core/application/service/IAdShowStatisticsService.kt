package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.AdShowStatistics
import com.makebk.domain.core.infrastructure.mapper.AdShowStatisticsMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 广告展示统计表 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface IAdShowStatisticsService : IBaseService<AdShowStatistics>


@Service
class IAdShowStatisticsServiceImpl : IBaseServiceImpl<AdShowStatisticsMapper, AdShowStatistics>(),
    IAdShowStatisticsService
