package id.qibee.newsassistant.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.qibee.newsassistant.R
import id.qibee.newsassistant.databinding.FragmentHomeBinding
import id.qibee.newsassistant.model.TopHeadlines
import timber.log.Timber.d

class HomeFragment : Fragment() {

    //    private val click: NewsClick = this::onItemClick
    private val viewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, HomeViewModelFactory(activity.application))
            .get(HomeViewModel::class.java)
    }
    private var newsRvAdapter: NewsRvAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.newsList.observe(viewLifecycleOwner, Observer<TopHeadlines> { headlines ->
            headlines?.let {
                newsRvAdapter?.articleList = it.articles
                d(" title --> ${it.articles[0].title} ")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        newsRvAdapter = NewsRvAdapter(NewsClick { })
        binding.root.findViewById<RecyclerView>(R.id.rvNews).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsRvAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        return binding.root
    }
}
