package com.codingwithmitch.mviexample.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email")
    @Expose
    val email: String? = null,

    @SerializedName("username")
    @Expose
    val username: String? = null,

    @SerializedName("image")
    @Expose
    val image: String? = null
) {
    override fun toString(): String {
        return "User(email=$email, username=$username, image=$image)"
    }
}