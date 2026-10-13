package com.project.konkor.model

object TokenContainer {
    var accessToken: String? = null
        private set
    var refreshToken: String? = null
        private set

    fun update(accessToken: String?, refreshToken: String?) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }
}