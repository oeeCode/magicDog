package com.makebk.domain.core.application.service

import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import com.makebk.domain.core.entits.SysDictionary
import com.makebk.domain.core.infrastructure.mapper.SysDictionaryMapper
import org.springframework.stereotype.Service

/**
 * <p>
 * 字典 服务类
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
interface ISysDictionaryService : IBaseService<SysDictionary>


@Service
class ISysDictionaryServiceImpl : IBaseServiceImpl<SysDictionaryMapper, SysDictionary>(), ISysDictionaryService
