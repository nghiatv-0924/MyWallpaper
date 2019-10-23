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

class NewFragment : BaseFragment<FragmentPhotoBinding, PhotoViewModel>(),
    OnRecyclerItemClickListener<Photo> {

    override val layoutResource: Int
        get() = R.layout.fragment_photo
    override val viewModel: PhotoViewModel by viewModel()

    private val newAdapter: PhotoAdapter = get(named(KoinNames.NEW_ADAPTER))
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
        refreshNewPhotos()
    }

    override fun observeData() {
        super.observeData()
        viewModel.newPhotos.observe(viewLifecycleOwner, Observer {
            it?.let {
                newAdapter.updateData(it)
                progressBar.visibility = View.GONE
                recyclerViewPhoto.visibility = View.VISIBLE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.new_photo, menu)
    }

    override fun showItemDetail(item: Photo) {
        getNavigationManager().open(PhotoDetailFragment.newInstance(item))
    }

    private fun initRecyclerView() {
        recyclerViewPhoto.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newAdapter.also { it.setOnRecyclerItemClickListener(this@NewFragment) }
            hasFixedSize()
            addOnScrollListener(object : LastItemListener() {
                override fun onLastItemVisible() {
                    if (loadMore)
                        getNewPhotos()
                }
            })
        }
    }

    private fun initSwipeRefreshLayout() {
        photoSwipeRefreshLayout.apply {
            setOnRefreshListener {
                refreshNewPhotos()
                isRefreshing = false
            }
        }
    }

    private fun refreshNewPhotos() {
        page = Constants.DEFAULT_PAGE
        loadMore = true
        viewModel.refreshNewPhotos(page, Constants.DEFAULT_PER_PAGE, SORT_BY_LATEST)
    }

    private fun getNewPhotos() {
        viewModel.getNewPhotos(++page, Constants.DEFAULT_PER_PAGE, SORT_BY_LATEST)
    }

    companion object {
        private const val SORT_BY_LATEST = "latest"
        private const val SORT_BY_OLDEST = "oldest"
        private const val SORT_BY_POPULAR = "popular"

        @JvmStatic
        fun newInstance() = NewFragment()
    }
}
