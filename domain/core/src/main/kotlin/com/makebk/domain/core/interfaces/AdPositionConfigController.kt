package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.IAdPositionConfigService
import com.makebk.domain.core.entits.AdPositionConfig

/**
 * <p>
 * 广告位置配置表 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/adPositionConfig")
class AdPositionConfigController : IBaseController<IAdPositionConfigService, AdPositionConfig>()
