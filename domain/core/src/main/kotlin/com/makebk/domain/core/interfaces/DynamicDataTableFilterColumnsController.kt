package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.IDynamicDataTableFilterColumnsService
import com.makebk.domain.core.entits.DynamicDataTableFilterColumns

/**
 * <p>
 * 动态数据表格过滤字段设置 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/dynamicDataTableFilterColumns")
class DynamicDataTableFilterColumnsController :
    IBaseController<IDynamicDataTableFilterColumnsService, DynamicDataTableFilterColumns>()
