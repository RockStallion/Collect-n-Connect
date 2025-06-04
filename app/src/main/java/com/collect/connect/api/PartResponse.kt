package com.collect.connect.api

import com.google.gson.annotations.SerializedName

data class PartResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String?,
    @SerializedName("parts") val parts: List<PartItem>
)