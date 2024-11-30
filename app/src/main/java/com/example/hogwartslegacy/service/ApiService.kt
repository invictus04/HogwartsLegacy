/*
This interface is defined to the end points of the api and conversion of json objects into the
kotlin objects.
 */

package com.example.hogwartslegacy.service

import com.example.hogwartslegacy.data.HogwartsData
import com.example.hogwartslegacy.data.HogwartsDataItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ApiService {

    @GET("api/characters")
    suspend fun getAllCharacters(): List<HogwartsDataItem>

}

/*

This initializes the Retrofit builder, which is a class used to configure and create a Retrofit instance.
Retrofit is a type-safe HTTP client for Android that simplifies network requests to APIs.

This tells Retrofit how to convert the JSON data coming from the API into Kotlin/Java objects.
The GsonConverterFactory is used here, meaning Retrofit will use Gson (a library for parsing JSON) to deserialize
the API response into your data classes. If the API returns data in JSON format, Retrofit will automatically parse it
into Kotlin objects (based on predefined data models).

This creates and returns the final Retrofit instance with the specified configuration (base URL and Gson converter factory).

 */

val retrofit = Retrofit.Builder().baseUrl("https://hp-api.onrender.com/")
    .addConverterFactory(GsonConverterFactory.create()).build()

/*
ApiService is an interface that defines the HTTP methods (e.g., GET, POST) and endpoints for interacting with the API. It's essentially a blueprint for making API requests.
By using retrofit.create(ApiService::class.java), Retrofit will generate an implementation of this interface at runtime.

hogwartsService is an instance of an API interface (ApiService) that you can use to interact with the Hogwarts API, making network requests to fetch data.
 */

val hogwartsService = retrofit.create(ApiService::class.java)