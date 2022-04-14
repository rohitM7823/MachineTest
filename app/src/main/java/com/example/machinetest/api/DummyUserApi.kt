package com.example.machinetest.api

import com.example.machinetest.models.DummyUserResult
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyUserApi {

    @GET("api/users")
    suspend fun getDummyUsers(@Query("page") page: String): DummyUserResult
}