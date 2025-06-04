package com.collect.connect.api.network

import com.collect.connect.api.SetResponse
import com.collect.connect.api.Constants
import com.collect.connect.api.PartResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BricksetApiService {


    @GET("getSets")
    suspend fun getSets(
        @Query("apiKey") apiKey: String = Constants.API_KEY,
        @Query("userHash") userHash: String = "",
        @Query("params") params: String
    ): Response<SetResponse>

    @GET("getPartsInSet")
    suspend fun getPartsInSet(
        @Query("apiKey") apiKey: String = Constants.API_KEY,
        @Query("userHash") userHash: String = "",
        @Query("params") params: String
    ): Response<PartResponse>
}