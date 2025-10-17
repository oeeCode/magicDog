package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.SysResourceNext
import com.makebk.domain.core.infrastructure.mapper.SysResourceNextMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 资源 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface ISysResourceNextService : IBaseService<SysResourceNext>


@Service
class ISysResourceNextServiceImpl : IBaseServiceImpl<SysResourceNextMapper, SysResourceNext>(), ISysResourceNextService
