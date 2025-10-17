package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.IDynamicDataFormService
import com.makebk.domain.core.entits.DynamicDataForm

/**
 * <p>
 * 动态数据表单设置 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/dynamicDataForm")
class DynamicDataFormController : IBaseController<IDynamicDataFormService, DynamicDataForm>()
