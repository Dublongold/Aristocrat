package com.game.aristocrat.internet

import retrofit2.Response
import retrofit2.http.GET

interface GetGistDataApi {
    @GET("raw/c9d945e2c930c04a13261fad4a94ffa763129413/gistfile1.txt")
    suspend fun getData(): Response<GetGistDataDto>
}