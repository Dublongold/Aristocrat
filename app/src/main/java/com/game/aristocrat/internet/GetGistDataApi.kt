package com.game.aristocrat.internet

import retrofit2.Response
import retrofit2.http.GET

interface GetGistDataApi {
    @GET("raw/200016ed8bc37509ce64024fea80ee5784aafa01/forTest.txt")
    suspend fun getData(): Response<GetGistDataDto>
}