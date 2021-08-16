package com.example.pawsome

import com.example.pawsome.responses.PhotoResult
import com.example.pawsome.responses.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface DogService {
    @GET("?api_key=8bb48647-4666-4932-8cd4-bfdd92b83217&limit=50&order=asc")
    suspend fun searchImages(): List<PhotoResult>

    @Multipart
    @Headers("x-api-key: 8bb48647-4666-4932-8cd4-bfdd92b83217",
                    "Content-Type: multipart/form-data")
    @POST("?")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("sub_id") sub_id: RequestBody
    ): UploadResponse
}