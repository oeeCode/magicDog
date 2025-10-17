package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.AdInfo
import com.makebk.domain.core.infrastructure.mapper.AdInfoMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 广告信息表 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface IAdInfoService : IBaseService<AdInfo>


@Service
class IAdInfoServiceImpl : IBaseServiceImpl<AdInfoMapper, AdInfo>(), IAdInfoService
