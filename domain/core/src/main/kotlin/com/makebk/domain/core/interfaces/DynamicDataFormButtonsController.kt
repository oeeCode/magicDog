package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.IDynamicDataFormButtonsService
import com.makebk.domain.core.entits.DynamicDataFormButtons

/**
 * <p>
 * 动态数据表单操作按钮设置 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/dynamicDataFormButtons")
class DynamicDataFormButtonsController : IBaseController<IDynamicDataFormButtonsService, DynamicDataFormButtons>()
