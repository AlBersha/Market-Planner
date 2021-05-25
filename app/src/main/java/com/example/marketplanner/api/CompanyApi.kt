package com.example.marketplanner.api

import com.example.marketplanner.models.CompanyModel
import retrofit2.http.GET

interface CompanyApi {
    companion object{
        const val BASE_URL = "https://api.polygon.io/"
    }

    @GET("v3/reference/tickers?active=true&sort=ticker&order=asc&limit=10&apiKey=MUAFBN7aOjZV4HLe9w2X2wACiZCe8Byc")
    suspend fun getTickerDetails(): List<CompanyModel>
}