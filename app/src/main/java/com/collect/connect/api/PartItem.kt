package com.collect.connect.api

import com.google.gson.annotations.SerializedName

data class PartItem(
    @SerializedName("elementID") val elementID: Int,
    @SerializedName("elementName") val elementName: String,
    @SerializedName("quantityInSet") val quantityInSet: Int,
    @SerializedName("elementImageURL") val elementImageURL: String?
)