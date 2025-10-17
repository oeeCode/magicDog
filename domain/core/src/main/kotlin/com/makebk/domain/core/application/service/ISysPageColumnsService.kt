package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.SysPageColumns
import com.makebk.domain.core.infrastructure.mapper.SysPageColumnsMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 页面表格设置 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface ISysPageColumnsService : IBaseService<SysPageColumns>


@Service
class ISysPageColumnsServiceImpl : IBaseServiceImpl<SysPageColumnsMapper, SysPageColumns>(), ISysPageColumnsService
