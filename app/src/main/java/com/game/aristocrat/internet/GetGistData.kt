package com.game.aristocrat.internet

import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class GetGistData {
    private val mapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
    private val client = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create(mapper))
        .build()
        .create(GetGistDataApi::class.java)

    suspend fun getGistData(): GetGistDataDto? {
        val response = client.getData()
        return if(response.isSuccessful) {
            response.body()!!
        }
        else {
            Log.w("Response", "Response isn't successful.")
            null
        }
    }

    companion object {
        private val BASE_URL = "https://gist.githubusercontent.com/Dublongold/76523dda8e34eeaa94416b43b95f1fc0/"
    }
}