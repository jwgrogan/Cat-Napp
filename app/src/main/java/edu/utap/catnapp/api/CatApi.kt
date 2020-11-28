package edu.utap.catnapp.api

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {
    @GET("/v1/images/search?limit=9")
    suspend fun getNineCats(@Query("category_ids") categories: String) : CatResponse


    // I just looked at the response and "parsed" it by eye
    data class CatResponse(val results: List<CatPost>)

    companion object {
        // Leave this as a simple, base URL.  That way, we can have many different
        // functions (above) that access different "paths" on this server
        // https://square.github.io/okhttp/4.x/okhttp/okhttp3/-http-url/
        var url = HttpUrl.Builder()
                .scheme("https")
                .host("api.thecatapi.com")
                .build()

        // Public create function that ties together building the base
        // URL and the private create function that initializes Retrofit
        fun create(): CatApi = create(url)
        private fun create(httpUrl: HttpUrl): CatApi {
            val client = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
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