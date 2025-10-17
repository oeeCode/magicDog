package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.IDynamicDataTableActionsService
import com.makebk.domain.core.entits.DynamicDataTableActions

/**
 * <p>
 * 动态数据表格设置 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/dynamicDataTableActions")
class DynamicDataTableActionsController : IBaseController<IDynamicDataTableActionsService, DynamicDataTableActions>()
