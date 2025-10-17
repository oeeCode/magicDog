package com.makebk.security.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(
    prefix = "permission"
)
class ShiroFilterProperties {
    var perms: List<Map<String, String>> = ArrayList<Map<String, String>>();
}