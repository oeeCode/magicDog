package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.ISysGroupScopeService
import com.makebk.domain.core.entits.SysGroupScope

/**
 * <p>
 * 权限组查询范围 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/sysGroupScope")
class SysGroupScopeController : IBaseController<ISysGroupScopeService, SysGroupScope>()
