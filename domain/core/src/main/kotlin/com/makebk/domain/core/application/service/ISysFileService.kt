package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.SysFile
import com.makebk.domain.core.infrastructure.mapper.SysFileMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 文件上传 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface ISysFileService : IBaseService<SysFile>


@Service
class ISysFileServiceImpl : IBaseServiceImpl<SysFileMapper, SysFile>(), ISysFileService
