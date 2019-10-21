package com.sun.mywallpaper.ui.home.featuredphoto

import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sun.mywallpaper.R
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.LastItemListener
import com.sun.mywallpaper.databinding.FragmentFeaturedBinding
import com.sun.mywallpaper.di.KoinNames
import com.sun.mywallpaper.adapter.PhotoAdapter
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.data.model.Photo
import com.sun.mywallpaper.util.Constants
import kotlinx.android.synthetic.main.fragment_featured.*
import kotlinx.android.synthetic.main.fragment_new.progressBar
import kotlinx.android.synthetic.main.fragment_new.recyclerViewPhoto
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class FeaturedFragment : BaseFragment<FragmentFeaturedBinding, FeaturedViewModel>(),
    OnRecyclerItemClickListener<Photo> {

    override val layoutResource: Int
        get() = R.layout.fragment_featured
    override val viewModel: FeaturedViewModel by viewModel()

    private val featuredAdapter: PhotoAdapter = get(named(KoinNames.FEATURED_ADAPTER))
    private var page = Constants.DEFAULT_PAGE

    override fun initComponents() {
        setHasOptionsMenu(true)

        recyclerViewPhoto.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =
                featuredAdapter.also { it.setOnRecyclerItemClickListener(this@FeaturedFragment) }
            hasFixedSize()
            addOnScrollListener(object : LastItemListener() {
                override fun onLastItemVisible() {
                    viewModel.getFeaturedPhotos(++page, Constants.DEFAULT_PER_PAGE, SORT_BY_LATEST)
                }
            })
        }
        featuredSwipeRefreshLayout.apply {
            setOnRefreshListener {
                page = 1
                viewModel.refreshFeaturedPhotos(page, Constants.DEFAULT_PER_PAGE, SORT_BY_LATEST)
                isRefreshing = false
            }
        }
    }

    override fun setBindingVariables() {
        super.setBindingVariables()
        viewDataBinding.featuredViewModel = this.viewModel
    }

    override fun initData() {
        super.initData()
        viewModel.getFeaturedPhotos(page, Constants.DEFAULT_PER_PAGE, SORT_BY_LATEST)
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
    }

    companion object {
        private const val SORT_BY_LATEST = "latest"
        private const val SORT_BY_OLDEST = "oldest"
        private const val SORT_BY_POPULAR = "popular"

        @JvmStatic
        fun newInstance() = FeaturedFragment()
    }
}
