package com.collect.connect.Api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Headers

interface RebrickableApi {
    @Headers("Authorization: key a9e2b8a5c456ed94eba523db763a3e29")
    @GET("lego/sets/")
    fun getLegoSets(
        @Query("page") page: Int = 1
    ): Call<SetsResponse>
}