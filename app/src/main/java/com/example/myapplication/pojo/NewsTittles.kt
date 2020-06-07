package com.example.myapplication.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class NewsTittles : Serializable {

    @SerializedName("source")
    @Expose
    var source: NewsSource? = null
    @SerializedName("author")
    @Expose
    var author: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("urlToImage")
    @Expose
    var urlToImage: String? = null
    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String? = null
    @SerializedName("content")
    @Expose
    var content: String? = null

    override fun toString(): String {
        return "NewsTittles(source=$source, author=$author, title=$title, description=$description, url=$url, urlToImage=$urlToImage, publishedAt=$publishedAt, content=$content)"
    }


}