package com.makebk.common.executor

import org.apache.commons.logging.LogFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component


/**
 * @Package com.makebk.common.executor
 * @author GME
 * @date 2023/12/23 10:29
 * @version V1.0
 * @Copyright © 2014-2023 码克布克网络工作室
 */
@Component
class AsyncTaskExecutorService {

    /** logger */
    private val logger = LogFactory.getLog(javaClass)

    @Async
    fun run() {
        logger.debug("Async task method has been called ${Thread.currentThread()}")
    }
}