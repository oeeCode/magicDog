package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.ISysLogApiService
import com.makebk.domain.core.entits.SysLogApi

/**
 * <p>
 * 接口日志 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/sysLogApi")
class SysLogApiController : IBaseController<ISysLogApiService, SysLogApi>()
