package com.makebk.sdk.exception


/**
 *  SdkRuntimeException
 *  @description：TODO
 *  @author：gme 2023/3/24｜09:49
 *  @version：0.0.1
 */
class SdkRuntimeException() : RuntimeException() {
    var code: Int? = null
    override var message: String? = null
    var error: SdkError? = null

    constructor(error: SdkError?) : this() {
        this.error = error
    }

    constructor(code: Int, message: String) : this() {
        this.code = code
        this.message = message
    }

    constructor(code: Int, message: String, error: SdkError) : this() {
        this.code = code
        this.message = message
        this.error = error
    }
}