package com.example.machinetest.models


import com.google.gson.annotations.SerializedName

data class DummyUserResult(
    @SerializedName("data")
    val data: List<DataX>?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("support")
    val support: Support?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?
)