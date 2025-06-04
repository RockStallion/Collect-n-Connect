package com.collect.connect.api

import com.google.gson.annotations.SerializedName

data class SetResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String?,    // Aparece si hay error
    @SerializedName("sets") val sets: List<SetItem> // Lista de sets
)