package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.ISysGroupService
import com.makebk.domain.core.entits.SysGroup

/**
 * <p>
 * 分组表 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/sysGroup")
class SysGroupController : IBaseController<ISysGroupService, SysGroup>()
