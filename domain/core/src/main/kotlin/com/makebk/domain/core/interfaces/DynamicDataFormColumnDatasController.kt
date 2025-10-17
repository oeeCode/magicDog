package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.IDynamicDataFormColumnDatasService
import com.makebk.domain.core.entits.DynamicDataFormColumnDatas

/**
 * <p>
 * 动态数据表单字段默认值设置 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/dynamicDataFormColumnDatas")
class DynamicDataFormColumnDatasController :
    IBaseController<IDynamicDataFormColumnDatasService, DynamicDataFormColumnDatas>()
