package com.sun.mywallpaper.ui.home

import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.PhotoAdapter
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.LastItemListener
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.data.model.Photo
import com.sun.mywallpaper.databinding.FragmentPhotoBinding
import com.sun.mywallpaper.di.KoinNames
import com.sun.mywallpaper.ui.photodetail.PhotoDetailFragment
import com.sun.mywallpaper.util.Constants
import com.sun.mywallpaper.viewmodel.PhotoViewModel
import kotlinx.android.synthetic.main.fragment_photo.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class FeaturedFragment : BaseFragment<FragmentPhotoBinding, PhotoViewModel>(),
    OnRecyclerItemClickListener<Photo> {

    override val layoutResource: Int
        get() = R.layout.fragment_photo
    override val viewModel: PhotoViewModel by viewModel()

    private val featuredAdapter: PhotoAdapter = get(named(KoinNames.FEATURED_ADAPTER))
    private var page = Constants.DEFAULT_PAGE

    override fun initComponents() {
        setHasOptionsMenu(true)
        initRecyclerView()
        initSwipeRefreshLayout()
    }

    override fun setBindingVariables() {
        super.setBindingVariables()
        viewDataBinding.viewModel = this.viewModel
    }

    override fun initData() {
        super.initData()
        refreshFeaturedPhotos()
    }

    override fun observeData() {
        super.observeData()
        viewModel.featuredPhotos.observe(viewLifecycleOwner, Observer {
            it?.let {
                featuredAdapter.updateData(it)
                progressBar.visibility = View.GONE
                recyclerViewPhoto.visibility = View.VISIBLE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.featured_photo, menu)
    }

    override fun showItemDetail(item: Photo) {
        getNavigationManager().open(PhotoDetailFragment.newInstance(item))
    }

    private fun initRecyclerView() {
        recyclerViewPhoto.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =
                featuredAdapter.also { it.setOnRecyclerItemClickListener(this@FeaturedFragment) }
            hasFixedSize()
            addOnScrollListener(object : LastItemListener() {
                override fun onLastItemVisible() {
                    if (loadMore)
                        getFeaturedPhotos()
                }
            })
        }
    }

    private fun initSwipeRefreshLayout() {
        photoSwipeRefreshLayout.apply {
            setOnRefreshListener {
                refreshFeaturedPhotos()
                isRefreshing = false
            }
        }
    }

    private fun refreshFeaturedPhotos() {
        page = Constants.DEFAULT_PAGE
        loadMore = true
        viewModel.refreshFeaturedPhotos(page, Constants.DEFAULT_PER_PAGE, SORT_BY_LATEST)
    }

    private fun getFeaturedPhotos() {
        viewModel.getFeaturedPhotos(++page, Constants.DEFAULT_PER_PAGE, SORT_BY_LATEST)
    }

    companion object {
        private const val SORT_BY_LATEST = "latest"
        private const val SORT_BY_OLDEST = "oldest"
        private const val SORT_BY_POPULAR = "popular"

        @JvmStatic
        fun newInstance() = FeaturedFragment()
    }
}
