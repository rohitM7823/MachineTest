package com.example.machinetest.repository

import android.app.DownloadManager
import android.util.Log
import com.example.machinetest.api.DummyUserApi
import com.example.machinetest.models.Country
import com.example.machinetest.models.Data
import com.example.machinetest.models.DataX
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MainRepository @Inject constructor(

) {

    private val listOfData = mutableListOf<Data>()

    fun getCountries(): Flow<List<Country>> = flow {

        delay(2000L)

        emit(
            listOf(
                Country("1", "India"),
                Country("2", "America"),
                Country("3", "Germany"),
                Country("4", "Italy"),
                Country("5", "France"),
            )
        )
    }

    fun getAdultsCount(): kotlinx.coroutines.flow.Flow<List<String>> = flow {
        delay(2000L)
        emit(
            listOf(
                "1",
                "2",
                "3",
                "4",
                "5"
            )
        )
    }


    fun getKidsCount(): kotlinx.coroutines.flow.Flow<List<String>> = flow {
        delay(2000L)
        emit(
            listOf(
                "1",
                "2",
                "3"
            )
        )
    }


    fun getRoomReference(): kotlinx.coroutines.flow.Flow<List<String>> = flow {
        delay(2000L)
        emit(
            listOf(
                "AC",
                "NON-AC"
            )
        )
    }

    fun submitAllData(
        fname: String,
        lname: String,
        email: String,
        phone: String,
        pincode: String,
        address: String,
        selectedCountry: String,
        checkInDate: String,
        checkOutDate: String,
        selectedRooPref: String,
        adultCount: String,
        kidsCount: String
    ): Flow<String> = flow {

        delay(1000L)
        emit("Uploading....")
        delay(2000L)
        listOfData.add(
            Data(
                fname,
                lname,
                email,
                phone,
                pincode,
                address,
                selectedCountry,
                checkInDate,
                checkOutDate,
                selectedRooPref,
                adultCount,
                kidsCount
            )
        )
        emit("Submitted Successfully")
    }

    fun showAllData(): Flow<List<Data>> = flow {
        delay(1000L)
        emit(listOfData)
    }

    fun getSearchableData(query: String): Flow<List<DataX>?> = flow {

        try {
            val netCall = Retrofit.Builder()
                .baseUrl("https://reqres.in")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DummyUserApi::class.java)

            val result = netCall.getDummyUsers(query)

            emit(result.data)
        } catch (ex: Exception) {
            emit(null)
            Log.d("testing", ex.toString())
        }

    }

}