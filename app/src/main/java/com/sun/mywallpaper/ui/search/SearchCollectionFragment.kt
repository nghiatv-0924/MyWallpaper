package com.sun.mywallpaper.ui.search

import android.view.View
import androidx.lifecycle.Observer
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.CollectionAdapter
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.data.model.Collection
import com.sun.mywallpaper.databinding.FragmentSearchCollectionBinding
import com.sun.mywallpaper.di.KoinNames
import com.sun.mywallpaper.ui.collectiondetail.CollectionDetailFragment
import com.sun.mywallpaper.util.Constants
import com.sun.mywallpaper.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search_collection.*
import kotlinx.android.synthetic.main.no_results_layout.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class SearchCollectionFragment : BaseFragment<FragmentSearchCollectionBinding, SearchViewModel>(),
    OnRecyclerItemClickListener<Collection> {

    override val layoutResource: Int
        get() = R.layout.fragment_search_collection
    override val viewModel: SearchViewModel by viewModel()

    private val searchCollectionAdapter: CollectionAdapter =
        get(named(KoinNames.SEARCH_COLLECTION_ADAPTER))
    private var page = Constants.DEFAULT_PAGE

    override fun initComponents() {
        recyclerViewSearchCollection.apply {
            adapter =
                searchCollectionAdapter.also { it.setOnRecyclerItemClickListener(this@SearchCollectionFragment) }
            hasFixedSize()
        }
        searchCollectionSwipeRefreshLayout.apply {
            setOnRefreshListener {
                isRefreshing = false
            }
        }
    }

    override fun setBindingVariables() {
        super.setBindingVariables()
        viewDataBinding.viewModel = this.viewModel
    }

    override fun observeData() {
        super.observeData()
        viewModel.searchCollectionResponses.observe(viewLifecycleOwner, Observer {
            it?.let {
                searchCollectionAdapter.updateData(it.results)
                progressBar.visibility = View.GONE
                recyclerViewSearchCollection.visibility = View.VISIBLE
            }
        })
    }

    override fun showItemDetail(item: Collection) {
        getNavigationManager().open(CollectionDetailFragment.newInstance(item))
    }

    fun searchCollections(query: String) {
        viewModel.getSearchCollections(query, page, Constants.DEFAULT_PER_PAGE)
        noResultsView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchCollectionFragment()
    }
}
