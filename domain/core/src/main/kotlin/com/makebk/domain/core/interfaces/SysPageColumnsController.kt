package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.ISysPageColumnsService
import com.makebk.domain.core.entits.SysPageColumns

/**
 * <p>
 * 页面表格设置 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/sysPageColumns")
class SysPageColumnsController : IBaseController<ISysPageColumnsService, SysPageColumns>()
