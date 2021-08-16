package com.example.pawsome

import com.example.pawsome.responses.PhotoResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhotoRetriever {
    private val service: DogService
    companion object{
        const val baseURL = "https://api.thedogapi.com/v1/images/search/"
    }
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(DogService::class.java)
    }
    suspend fun getImages(): List<PhotoResult>{
        return service.searchImages()
    }
}