package com.makebk.security.shiro

import org.apache.shiro.authc.AuthenticationToken

class JwtToken(var token: String) : AuthenticationToken {

    override fun getPrincipal(): String {
        return token;
    }

    override fun getCredentials(): String {
        return token;
    }
}