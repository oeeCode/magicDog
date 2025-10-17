package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.ISysStaffService
import com.makebk.domain.core.entits.SysStaff

/**
 * <p>
 * 员工表 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/sysStaff")
class SysStaffController : IBaseController<ISysStaffService, SysStaff>()
