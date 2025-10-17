package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.ISysResourceNextService
import com.makebk.domain.core.entits.SysResourceNext

/**
 * <p>
 * 资源 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/sysResourceNext")
class SysResourceNextController : IBaseController<ISysResourceNextService, SysResourceNext>()
