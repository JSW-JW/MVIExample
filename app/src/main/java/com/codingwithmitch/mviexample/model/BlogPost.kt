package com.codingwithmitch.mviexample.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BlogPost(
    @SerializedName("")
    @Expose
    val pk : Int? = null,

    @SerializedName("title")
    @Expose
    val title : String? = null,

    @SerializedName("body")
    @Expose
    val body : String? = null,

    @SerializedName("image")
    @Expose
    val image : String? = null
) {
    override fun toString(): String {
        return "BlogPost(pk=$pk, title=$title, body=$body, image=$image)"
    }
}