package com.game.aristocrat.internet

import retrofit2.Response
import retrofit2.http.GET

interface GetGistDataApi {
    @GET("0209b4eb465c4e1621e357afdb1f58d4/raw/gistfile1.txt")
    suspend fun getData(): Response<GetGistDataDto>
}