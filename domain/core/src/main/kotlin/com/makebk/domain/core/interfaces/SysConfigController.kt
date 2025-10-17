package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.ISysConfigService
import com.makebk.domain.core.entits.SysConfig

/**
 * <p>
 * 系统配置信息表 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/sysConfig")
class SysConfigController : IBaseController<ISysConfigService, SysConfig>()
