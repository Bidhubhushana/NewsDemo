package com.example.myapplication.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class NewsSource:Serializable {
    @SerializedName("id")
    @Expose
    var id: Any? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

    override fun toString(): String {
        return "NewsSource(id=$id, name=$name)"
    }


}