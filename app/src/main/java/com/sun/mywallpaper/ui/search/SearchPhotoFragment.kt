package com.sun.mywallpaper.ui.search

import android.view.View
import androidx.lifecycle.Observer
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.PhotoAdapter
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.data.model.Photo
import com.sun.mywallpaper.databinding.FragmentSearchPhotoBinding
import com.sun.mywallpaper.di.KoinNames
import com.sun.mywallpaper.ui.photodetail.PhotoDetailFragment
import com.sun.mywallpaper.util.Constants
import com.sun.mywallpaper.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search_photo.*
import kotlinx.android.synthetic.main.no_results_layout.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class SearchPhotoFragment : BaseFragment<FragmentSearchPhotoBinding, SearchViewModel>(),
    OnRecyclerItemClickListener<Photo> {

    override val layoutResource: Int
        get() = R.layout.fragment_search_photo
    override val viewModel: SearchViewModel by viewModel()

    private val searchPhotoAdapter: PhotoAdapter = get(named(KoinNames.SEARCH_PHOTO_ADAPTER))
    private var page = Constants.DEFAULT_PAGE
    private var orientation: String? = null

    override fun initComponents() {
        recyclerViewSearchPhoto.apply {
            adapter =
                searchPhotoAdapter.also { it.setOnRecyclerItemClickListener(this@SearchPhotoFragment) }
            hasFixedSize()
        }
        searchPhotoSwipeRefreshLayout.apply {
            setOnRefreshListener {
                isRefreshing = false
            }
        }
        noResultsView.visibility = View.VISIBLE
    }

    override fun setBindingVariables() {
        super.setBindingVariables()
        viewDataBinding.viewModel = this.viewModel
    }

    override fun observeData() {
        super.observeData()
        viewModel.searchPhotoResponses.observe(viewLifecycleOwner, Observer {
            it?.let {
                progressBar.visibility = View.GONE
                if (it.total == Constants.NO_VALUE) {
                    noResultsView.visibility = View.VISIBLE
                } else {
                    searchPhotoAdapter.updateData(it.results)
                    recyclerViewSearchPhoto.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun showItemDetail(item: Photo) {
        getNavigationManager().open(PhotoDetailFragment.newInstance(item))
    }

    fun setOrientation(query: String, spinnerPosition: Int) {
        when (spinnerPosition) {
            0 -> if (isOrientationChanged(null))
                searchPhotos(query)

            1 -> if (isOrientationChanged(LANDSCAPE))
                searchPhotos(query)

            2 -> if (isOrientationChanged(PORTRAIT))
                searchPhotos(query)

            3 -> if (isOrientationChanged(SQUARISH))
                searchPhotos(query)
        }
    }

    fun searchPhotos(query: String) {
        viewModel.getSearchPhotos(query, page, Constants.DEFAULT_PER_PAGE, null, orientation)
        noResultsView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun isOrientationChanged(orientation: String?) = if (this.orientation == orientation)
        false
    else {
        this.orientation = orientation
        true
    }

    companion object {
        private const val LANDSCAPE = "landscape"
        private const val PORTRAIT = "portrait"
        private const val SQUARISH = "squarish"

        @JvmStatic
        fun newInstance() = SearchPhotoFragment()
    }
}
