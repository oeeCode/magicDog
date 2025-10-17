package com.makebk.common.util

import cn.hutool.log.LogFactory
import kotlinx.coroutines.*
import java.util.concurrent.Executors


/**
 *
 * 协程工具类
 *
 * 启动模式
 *  DEFAULT:协程创建后，立即开始调度，在调度前如果协程被取消，其将直接进入取消 响应的状态。
 *  ATOMIC:协程创建后，立即开始调度，协程执行到第一个挂起点之前不响应取消。
 *  LAZY:只有协程被需要时，包括主动调用协程的start、join或者await等函数时才会开始调度，如果调度前就被取消，那么该协程将直接进入异常结束状态。
 *  UNDISPATCHED:协程创建后立即在当前函数调用栈中执行，直到遇到第一个真正挂起 的点
 *
 *
 * @Package com.makebk.common.utils
 * @author GME
 * @date 2024/3/29 09:23
 * @version V1.0
 * @Copyright © 2014-2023 码克布克网络工作室
 */
object CoroutineUtil {

    /** logger */
    private val logger = LogFactory.get(javaClass)

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logger.info("-----------------------------------------------------------------")
        logger.error("Uncaught exception in coroutine", throwable)
        logger.info("-----------------------------------------------------------------")
    }

    val Unconfined = CoroutineScope(SupervisorJob() + Dispatchers.Unconfined + exceptionHandler)

    /**
     *  虚拟线程
     */
    val VirtualThread = CoroutineScope(
        SupervisorJob() + Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher() + exceptionHandler
    )


    /**
     *  非主线程
     *
     *  专为CPU密集型任务进行了优化
     *  数组排序，JSON数据解析，处理差异判断
     */
    val Default = CoroutineScope(
        SupervisorJob() + Dispatchers.Default + exceptionHandler
    )

    /**
     * 非主线程
     *
     * 专为磁盘和网络IO进行了优化
     * 效据库
     * 文件读写
     * 网络处理
     */
    val IO = CoroutineScope(
        SupervisorJob() + Dispatchers.IO + exceptionHandler
    )

    /**
     *  Android上的主线程用来处理UI交互和一些轻量级任务
     *  调用suspend函数
     *  调用UI函数
     *  更新LiveData
     */
    val Main = CoroutineScope(
        SupervisorJob() + Dispatchers.Main+ exceptionHandler
    )


}