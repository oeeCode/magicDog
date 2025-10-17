package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.IAdShowStatisticsService
import com.makebk.domain.core.entits.AdShowStatistics

/**
 * <p>
 * 广告展示统计表 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/adShowStatistics")
class AdShowStatisticsController : IBaseController<IAdShowStatisticsService, AdShowStatistics>()
