package edu.utap.catnapp.api

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CatApi {
    @GET("/v1/images/search?limit=10")
    suspend fun getNineCats(@Query("category_ids") categories: String, @Header("x-api-key") key: String) : List<CatPost>

    companion object {
        var httpurl = HttpUrl.Builder()
            .scheme("https")
            .host("api.thecatapi.com")
            .build()
        fun create(): CatApi = create(httpurl)
        private fun create(httpUrl: HttpUrl): CatApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    // Enable basic HTTP logging to help with debugging.
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CatApi::class.java)
        }
    }
}