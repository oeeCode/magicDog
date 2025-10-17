package com.makebk.common.exception

/**
 *  BaseException
 *  @description：TODO
 *  @author：gme 2023/2/8｜16:14
 *  @version：0.0.1
 */
open class BaseException() : RuntimeException() {
    var code: Int? = null
    override var message: String? = null
    var ex: Any? = null

    constructor(code: Int?) : this() {
        this.code = code
    }

    constructor(message: String?) : this() {
        this.message = message
    }

    constructor(code: Int?, message: String?) : this() {
        this.code = code
        this.message = message
    }

    constructor(message: String?, ex: Any?) : this() {
        this.message = message
        this.ex = ex
    }

    constructor(code: Int?, message: String?, ex: Any?) : this() {
        this.code = code
        this.message = message
        this.ex = ex
    }
}