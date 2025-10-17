package com.makebk.domain.core.interfaces

import com.makebk.common.annotations.Router
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.domain.core.application.service.ISysGroupMemberService
import com.makebk.domain.core.entits.SysGroupMember

/**
 * <p>
 * 权限组用户 前端控制器
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Router("/sysGroupMember")
class SysGroupMemberController : IBaseController<ISysGroupMemberService, SysGroupMember>()
