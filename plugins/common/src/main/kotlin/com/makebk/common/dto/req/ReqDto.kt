package com.makebk.common.dto.req

import com.makebk.common.dto.UIDto
import jakarta.validation.Valid

/**
 *
 * 　@类   名：ReqDto
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/21 2:02 PM
 *
 */

data class ReqDto<T>(
    var channel: String?,
    var requestId: String?,
    @field:Valid var body: T,
) : UIDto