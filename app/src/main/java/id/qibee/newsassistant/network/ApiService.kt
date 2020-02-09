package id.qibee.newsassistant.network

import id.qibee.newsassistant.model.TopHeadlines
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Deferred<TopHeadlines>
}
