package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.common.constant.DefaultMethodEnums
import com.makebk.domain.core.application.service.IAdInfoService
import com.makebk.domain.core.entits.AdInfo

/**
 * <p>
 * 广告信息表 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router(
    "/adInfo",
    excludePermission = [
        DefaultMethodEnums.ALL,
        DefaultMethodEnums.PAGE,
        DefaultMethodEnums.FINDALL,
        DefaultMethodEnums.ADD,
        DefaultMethodEnums.FIND,
        DefaultMethodEnums.LIST,
        DefaultMethodEnums.FINDID,
    ]
)
class AdInfoController : IBaseController<IAdInfoService, AdInfo>()
