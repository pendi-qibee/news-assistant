package id.qibee.newsassistant.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.qibee.newsassistant.R
import id.qibee.newsassistant.databinding.ItemNewsBinding
import id.qibee.newsassistant.model.ArticlesItem
import timber.log.Timber.d

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class NewsRvAdapter(private val callback: NewsClick) : RecyclerView.Adapter<NewsViewHolder>() {

    var articleList: List<ArticlesItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val withDataBinding: ItemNewsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            NewsViewHolder.LAYOUT,
            parent,
            false)
        return NewsViewHolder(withDataBinding)
    }

    override fun getItemCount() = articleList.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.news = articleList[position]
            it.newsCallback = callback
            d(" title --> ${articleList[position].title} ")
        }
    }
}

class NewsViewHolder(val viewDataBinding: ItemNewsBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.item_news
    }
}

/**
 * Click listener
 *
 */
class NewsClick(val block: (ArticlesItem) -> Unit) {
    fun onClick(articlesItem: ArticlesItem) = block(articlesItem)
}
