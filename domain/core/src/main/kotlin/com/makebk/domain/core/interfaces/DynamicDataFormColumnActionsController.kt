package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.IDynamicDataFormColumnActionsService
import com.makebk.domain.core.entits.DynamicDataFormColumnActions

/**
 * <p>
 * 动态数据表单字段更多操作设置 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/dynamicDataFormColumnActions")
class DynamicDataFormColumnActionsController :
    IBaseController<IDynamicDataFormColumnActionsService, DynamicDataFormColumnActions>()
