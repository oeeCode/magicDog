package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.ISysDictionaryService
import com.makebk.domain.core.entits.SysDictionary

/**
 * <p>
 * 字典 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/sysDictionary")
class SysDictionaryController : IBaseController<ISysDictionaryService, SysDictionary>()
