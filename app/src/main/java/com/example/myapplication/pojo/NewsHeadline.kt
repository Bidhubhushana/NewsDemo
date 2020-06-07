package com.example.myapplication.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class NewsHeadline {

    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null

    @SerializedName("articles")
    @Expose
    var articles: List<NewsTittles>? = null

    override fun toString(): String {
        return "NewsHeadline(status=$status, totalResults=$totalResults, articles=$articles)"
    }


}