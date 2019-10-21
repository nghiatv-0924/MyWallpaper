package com.sun.mywallpaper.ui.home.newphoto

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
import com.sun.mywallpaper.databinding.FragmentNewBinding
import com.sun.mywallpaper.di.KoinNames
import com.sun.mywallpaper.util.Constants
import kotlinx.android.synthetic.main.fragment_new.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class NewFragment : BaseFragment<FragmentNewBinding, NewViewModel>(),
    OnRecyclerItemClickListener<Photo> {

    override val layoutResource: Int
        get() = R.layout.fragment_new
    override val viewModel: NewViewModel by viewModel()

    private val newAdapter: PhotoAdapter = get(named(KoinNames.NEW_ADAPTER))
    private var page = Constants.DEFAULT_PAGE

    override fun initComponents() {
        setHasOptionsMenu(true)

        recyclerViewPhoto.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newAdapter.also { it.setOnRecyclerItemClickListener(this@NewFragment) }
            hasFixedSize()
            addOnScrollListener(object : LastItemListener() {
                override fun onLastItemVisible() {
                    viewModel.getNewPhotos(++page, Constants.DEFAULT_PER_PAGE, SORT_BY_LATEST)
                }
            })
        }
        newSwipeRefreshLayout.apply {
            setOnRefreshListener {
                page = 1
                viewModel.refreshNewPhotos(page, Constants.DEFAULT_PER_PAGE, SORT_BY_LATEST)
                isRefreshing = false
            }
        }
    }

    override fun setBindingVariables() {
        super.setBindingVariables()
        viewDataBinding.newViewModel = this.viewModel
    }

    override fun initData() {
        super.initData()
        viewModel.getNewPhotos(page, Constants.DEFAULT_PER_PAGE, SORT_BY_LATEST)
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
    }

    companion object {
        private const val SORT_BY_LATEST = "latest"
        private const val SORT_BY_OLDEST = "oldest"
        private const val SORT_BY_POPULAR = "popular"

        @JvmStatic
        fun newInstance() = NewFragment()
    }
}
