package com.collect.connect.api

import com.google.gson.annotations.SerializedName

data class SetItem(
    @SerializedName("setID") val setID: Int,
    @SerializedName("number") val number: String,
    @SerializedName("name") val name: String,
    @SerializedName("year") val year: Int,
    @SerializedName("theme") val theme: String,
    @SerializedName("pieces") val pieces: Int?,
    @SerializedName("minifigs") val minifigs: Int?,
    @SerializedName("imageURL") val imageURL: String?,
    @SerializedName("thumbnailURL") val thumbnailURL: String?
)