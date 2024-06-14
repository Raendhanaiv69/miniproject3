package org.d3if0069.miniproject3.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if0069.miniproject3.model.OpStatus
import org.d3if0069.miniproject3.model.Tkd
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://unspoken.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TkdApiService {
    @GET("api_ilmi.php")
    suspend fun getTkd(
        @Header("Authorization") userId: String
    ):List<Tkd>
    @Multipart
    @POST("api_ilmi.php")
    suspend fun postTkd(
        @Header("Authorization") userId: String,
        @Part("nama_gerakan") nama_gerakan: RequestBody,
        @Part("arti_gerakan") arti_gerakan: RequestBody,
        @Part imageId: MultipartBody.Part
    ): OpStatus
    @DELETE("api_ilmi.php")
    suspend fun deletetkd(
        @Header("Authorization") id: String,
        @Query("id") userId: String
    ) : OpStatus
}


object TkdApi {
    val service: TkdApiService by lazy {
        retrofit.create(TkdApiService::class.java)
    }

    fun getTkdUrl(imageId: String): String {
        return "${BASE_URL}image.php?id=$imageId"
    }
}

enum class ApiStatus {LOADING, SUCCESS, FAILED}