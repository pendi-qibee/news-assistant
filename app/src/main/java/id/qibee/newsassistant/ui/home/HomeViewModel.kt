package id.qibee.newsassistant.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.qibee.newsassistant.model.TopHeadlines
import id.qibee.newsassistant.repository.NewsRepository
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber.d

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val newsRepository = NewsRepository()
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    private var _newsList = MutableLiveData<TopHeadlines>()

    val newsList: LiveData<TopHeadlines>
        get() = _newsList

    init {
        fetchNewsData()
    }

    private fun fetchNewsData() {
        viewModelScope.launch {
            try {
                newsRepository.fetchNews()
                _newsList.value = newsRepository.topHeadlines
                d(" title --> ${newsRepository.topHeadlines.articles[0].title} ")
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                Log.e("errormessage", networkError.toString())
            }
        }
    }
}
