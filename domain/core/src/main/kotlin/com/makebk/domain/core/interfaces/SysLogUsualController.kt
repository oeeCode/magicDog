package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.ISysLogUsualService
import com.makebk.domain.core.entits.SysLogUsual

/**
 * <p>
 * 通用日志 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/sysLogUsual")
class SysLogUsualController : IBaseController<ISysLogUsualService, SysLogUsual>()
