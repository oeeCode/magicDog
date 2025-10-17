package com.makebk.domain.core.infrastructure.mapper

import com.makebk.common.base.mapper.IBaseMapper
import com.makebk.domain.core.entits.AdShowStatistics
import org.apache.ibatis.annotations.Mapper

/**
 * <p>
 * 广告展示统计表 Mapper 接口
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Mapper

interface AdShowStatisticsMapper : IBaseMapper<AdShowStatistics>
