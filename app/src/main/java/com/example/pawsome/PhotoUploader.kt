package com.example.pawsome

import android.util.Log
import com.example.pawsome.responses.UploadResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class PhotoUploader {
    private val service: DogService

    companion object{
        const val baseURL = "https://api.thedogapi.com/v1/images/upload/"
    }
    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(DogService::class.java)
    }
    suspend fun postImage(file: File): UploadResponse {

        val requestFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
        val image = MultipartBody.Part.createFormData("file",file.name,requestFile)
        val name = RequestBody.create(MediaType.parse("multipart/form-data"),"Dog Image")
        return service.uploadImage(image, name)
    }
}