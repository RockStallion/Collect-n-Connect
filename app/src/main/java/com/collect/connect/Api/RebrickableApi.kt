package com.collect.connect.Api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RebrickableApi {
    @GET("lego/sets/")
    fun getSets(
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null
    ): Call<SetsResponse>
}