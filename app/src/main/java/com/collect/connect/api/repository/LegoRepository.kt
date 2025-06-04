package com.collect.connect.api.repository

import com.collect.connect.api.PartResponse
import com.collect.connect.api.SetResponse
import com.collect.connect.api.network.RetrofitClient
import retrofit2.Response

class LegoRepository {

    private val api = RetrofitClient.apiService

    suspend fun fetchSetsByYear(year: Int): Response<SetResponse> {
        // Construimos el JSON para “params”
        val jsonParams = "{\"year\":\"$year\"}"
        return api.getSets(params = jsonParams)
    }

    suspend fun fetchPartsForSet(setNumber: String): Response<PartResponse> {
        val jsonParams = "{\"setNumber\":\"$setNumber\"}"
        return api.getPartsInSet(params = jsonParams)
    }
}