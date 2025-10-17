package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.ISysTenantService
import com.makebk.domain.core.entits.SysTenant

/**
 * <p>
 * 租户表 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/sysTenant")
class SysTenantController : IBaseController<ISysTenantService, SysTenant>()
