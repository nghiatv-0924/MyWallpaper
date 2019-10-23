package com.sun.mywallpaper.ui.home

import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.CollectionAdapter
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.LastItemListener
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.data.model.Collection
import com.sun.mywallpaper.databinding.FragmentCollectionBinding
import com.sun.mywallpaper.di.KoinNames
import com.sun.mywallpaper.ui.collectiondetail.CollectionDetailFragment
import com.sun.mywallpaper.util.Constants
import com.sun.mywallpaper.viewmodel.CollectionViewModel
import kotlinx.android.synthetic.main.fragment_collection.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class CollectionFragment : BaseFragment<FragmentCollectionBinding, CollectionViewModel>(),
    OnRecyclerItemClickListener<Collection> {

    override val layoutResource: Int
        get() = R.layout.fragment_collection
    override val viewModel: CollectionViewModel by viewModel()

    private val collectionAdapter: CollectionAdapter = get(named(KoinNames.COLLECTION_ADAPTER))
    private var page = Constants.DEFAULT_PAGE

    override fun initComponents() {
        setHasOptionsMenu(true)

        recyclerViewCollection.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =
                collectionAdapter.also { it.setOnRecyclerItemClickListener(this@CollectionFragment) }
            hasFixedSize()
            addOnScrollListener(object : LastItemListener() {
                override fun onLastItemVisible() {
                    viewModel.getCollections(++page, Constants.DEFAULT_PER_PAGE)
                }
            })
        }
        collectionSwipeRefreshLayout.apply {
            setOnRefreshListener {
                page = 1
                viewModel.refreshCollections(page, Constants.DEFAULT_PER_PAGE)
                isRefreshing = false
            }
        }
    }

    override fun setBindingVariables() {
        super.setBindingVariables()
        viewDataBinding.collectionViewModel = this.viewModel
    }

    override fun initData() {
        super.initData()
        viewModel.refreshCollections(page, Constants.DEFAULT_PER_PAGE)
    }

    override fun observeData() {
        super.observeData()
        viewModel.collections.observe(viewLifecycleOwner, Observer {
            it?.let {
                collectionAdapter.updateData(it)
                progressBar.visibility = View.GONE
                recyclerViewCollection.visibility = View.VISIBLE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.collection, menu)
    }

    override fun showItemDetail(item: Collection) {
        getNavigationManager().open(CollectionDetailFragment.newInstance(item))
    }

    companion object {
        @JvmStatic
        fun newInstance() = CollectionFragment()
    }
}
