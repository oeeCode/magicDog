package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.ISysScopeDataService
import com.makebk.domain.core.entits.SysScopeData

/**
 * <p>
 * 数据权限表 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/sysScopeData")
class SysScopeDataController : IBaseController<ISysScopeDataService, SysScopeData>()
