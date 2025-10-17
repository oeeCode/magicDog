package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.ISysFileService
import com.makebk.domain.core.entits.SysFile

/**
 * <p>
 * 文件上传 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/sysFile")
class SysFileController : IBaseController<ISysFileService, SysFile>()
