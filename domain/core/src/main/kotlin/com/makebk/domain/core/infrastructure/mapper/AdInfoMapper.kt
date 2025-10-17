package com.makebk.domain.core.infrastructure.mapper

import com.makebk.common.base.mapper.IBaseMapper
import com.makebk.domain.core.entits.AdInfo
import org.apache.ibatis.annotations.Mapper

/**
 * <p>
 * 广告信息表 Mapper 接口
 * </p>
 *
 * @author gme
 * @since 2025-10-02
 */
@Mapper

interface AdInfoMapper : IBaseMapper<AdInfo>
