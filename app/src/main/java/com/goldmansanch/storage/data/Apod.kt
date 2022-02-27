package com.goldmansanch.storage.data

import androidx.room.*
import com.google.gson.annotations.SerializedName


@Entity(tableName = "apod_data", indices = [Index(value = ["date"], unique = true)])
data class Apod(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int,
    @ColumnInfo(name = "copyright")
    @SerializedName("copyright")
    val copyright: String?,
    @ColumnInfo(name = "date")
    @SerializedName("date")
    val date: String,
    @ColumnInfo(name = "explanation")
    @SerializedName("explanation")
    val explanation: String?,
    @ColumnInfo(name = "hdurl")
    @SerializedName("hdurl")
    val hdurl: String?,
    @ColumnInfo(name = "media_type")
    @SerializedName("media_type")
    val mediaType: String?,
    @ColumnInfo(name = "service_version")
    @SerializedName("service_version")
    val serviceVersion: String?,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String?,
    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String?
)