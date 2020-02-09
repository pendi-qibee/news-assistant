package id.qibee.newsassistant.repository

import id.qibee.newsassistant.BuildConfig
import id.qibee.newsassistant.model.TopHeadlines
import id.qibee.newsassistant.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository {
    val apiKey = BuildConfig.API_DEVELOPER_TOKEN
    val country = "id"
    suspend fun fetchNews(): TopHeadlines? {
        withContext(Dispatchers.IO) {
            return@withContext RetrofitBuilder.service.getTopHeadlines(country, apiKey).await()
        }
        return null
    }
}
