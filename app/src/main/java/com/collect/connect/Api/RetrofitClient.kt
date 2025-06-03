package com.collect.connect




import com.collect.connect.Api.RebrickableApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://rebrickable.com/api/v3/"

    val api: RebrickableApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RebrickableApi::class.java)
    }
}