package com.example.marketplanner.api

import com.example.marketplanner.models.DailyOpenCloseModel
import retrofit2.http.GET

interface DailyDataApi {
    companion object{
        const val BASE_URL = "https://api.polygon.io/"
    }

    @GET("v1/open-close/AAPL/2021-04-20?unadjusted=true&apiKey=MUAFBN7aOjZV4HLe9w2X2wACiZCe8Byc")
    suspend fun getDailyOpenCloseData(): List<DailyOpenCloseModel>
}