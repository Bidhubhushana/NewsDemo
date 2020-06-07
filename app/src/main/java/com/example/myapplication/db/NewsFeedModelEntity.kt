package com.example.myapplication.db

import androidx.room.*


@Entity(tableName = "news_feed",indices = [Index(value = ["_id"], unique = true)])

class NewsFeedModelEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0


    @ColumnInfo(name ="source")
    var source: String ? = null


    @ColumnInfo(name ="author")
    var author: String? = null


    @ColumnInfo(name ="title")
    var title: String? = null


    @ColumnInfo(name ="description")
    var description: String? = null

    @ColumnInfo(name ="url")
    var url: String? = null

    @ColumnInfo(name ="urlToImage")
    var urlToImage: String? = null

    @ColumnInfo(name ="publishedAt")
    var publishedAt: String? = null

    @ColumnInfo(name ="content")
    var content: String? = null

    override fun toString(): String {
        return "NewsTittles(source=$source, author=$author, title=$title, description=$description, url=$url, urlToImage=$urlToImage, publishedAt=$publishedAt, content=$content)"
    }

}