package com.example.myapplication.db

import androidx.room.*
import com.example.myapplication.pojo.NewsTittles
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "news_feed",indices = [Index(value = ["_id"], unique = true)])
class NewsJsonDataConverter {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0


    @TypeConverter
    fun toNewsModel(json: String) =
        Gson().fromJson<NewsTittles>(json, object : TypeToken<NewsTittles>() {}.type)

    @TypeConverter
    fun getNewsModel(model: NewsTittles?) = Gson().toJson(model)


}