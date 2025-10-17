package com.makebk.common.config

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Lazy
@Component
class DbMetaObjectHandler : MetaObjectHandler {

    override fun insertFill(metaObject: MetaObject?) {
        TODO("Not yet implemented")
    }

    override fun updateFill(metaObject: MetaObject?) {
        TODO("Not yet implemented")
    }
}