package com.makebk.common.handler

import com.makebk.common.vo.IUser
import kotlinx.coroutines.asContextElement

class UserContextHandler : AutoCloseable {

    constructor()
    constructor(user: IUser) {
        current.set(user)
    }

    @Throws(Exception::class)
    override fun close() {
        current.remove()
    }

    companion object {
        val current = ThreadLocal<IUser>()
        var currentUser: IUser?
            get() = current.get()
            set(user) {
                current.set(user)
            }

        fun remove() {
            try {
                UserContextHandler().close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun context() =
            current.asContextElement(currentUser ?: IUser())
    }
}