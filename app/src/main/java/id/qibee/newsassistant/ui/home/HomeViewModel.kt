package id.qibee.newsassistant.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import id.qibee.newsassistant.model.TopHeadlines
import id.qibee.newsassistant.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val newsRepository = NewsRepository()
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val newsList = MutableLiveData<TopHeadlines>()

    init {
        fetchNewsData()
    }

    private fun fetchNewsData() {
        viewModelScope.launch {
            try {
                newsList.value = newsRepository.fetchNews()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                Log.e("errormessage", networkError.toString())
            }
        }
    }
}
