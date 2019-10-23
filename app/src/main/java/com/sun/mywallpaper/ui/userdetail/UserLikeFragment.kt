package com.sun.mywallpaper.ui.userdetail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.PhotoAdapter
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.LastItemListener
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.data.model.Photo
import com.sun.mywallpaper.data.model.User
import com.sun.mywallpaper.databinding.FragmentPhotoBinding
import com.sun.mywallpaper.di.KoinNames
import com.sun.mywallpaper.util.Constants
import com.sun.mywallpaper.viewmodel.PhotoViewModel
import kotlinx.android.synthetic.main.fragment_photo.*
import kotlinx.android.synthetic.main.no_results_layout.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class UserLikeFragment : BaseFragment<FragmentPhotoBinding, PhotoViewModel>(),
    OnRecyclerItemClickListener<Photo> {

    override val layoutResource: Int
        get() = R.layout.fragment_photo
    override val viewModel: PhotoViewModel by viewModel()

    private val userLikeAdapter: PhotoAdapter = get(named(KoinNames.USER_DETAIL_LIKE_ADAPTER))
    private var page = Constants.DEFAULT_PAGE
    private val user by lazy {
        arguments?.getParcelable<User>(USER_DETAIL)
    }

    override fun initComponents() {
        initRecyclerView()
        initSwipeRefreshLayout()
    }

    override fun setBindingVariables() {
        super.setBindingVariables()
        viewDataBinding.viewModel = this.viewModel
    }

    override fun initData() {
        super.initData()
        refreshUserLikes()
    }

    override fun observeData() {
        super.observeData()
        viewModel.userLikes.observe(viewLifecycleOwner, Observer {
            it?.let {
                userLikeAdapter.updateData(it)
                progressBar.visibility = View.GONE
                recyclerViewPhoto.visibility = View.VISIBLE
            }
        })
    }

    override fun showItemDetail(item: Photo) {
    }

    private fun initRecyclerView() {
        recyclerViewPhoto.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =
                userLikeAdapter.also { it.setOnRecyclerItemClickListener(this@UserLikeFragment) }
            hasFixedSize()
            addOnScrollListener(object : LastItemListener() {
                override fun onLastItemVisible() {
                    getUserLikes()
                }
            })
        }
    }

    private fun initSwipeRefreshLayout() {
        photoSwipeRefreshLayout.apply {
            setOnRefreshListener {
                refreshUserLikes()
                isRefreshing = false
            }
        }
    }

    private fun refreshUserLikes() {
        page = Constants.DEFAULT_PAGE
        progressBar.visibility = View.VISIBLE
        user?.let {
            if (it.totalLikes == Constants.NO_VALUE) {
                progressBar.visibility = View.GONE
                noResultsView.visibility = View.VISIBLE
            } else {
                viewModel.refreshUserLikes(
                    it.username,
                    page,
                    Constants.DEFAULT_PER_PAGE,
                    SORT_BY_LATEST
                )
            }
        }
    }

    private fun getUserLikes() {
        user?.let {
            viewModel.getUserLikes(it.username, ++page, Constants.DEFAULT_PER_PAGE, SORT_BY_LATEST)
        }
    }

    companion object {
        private const val USER_DETAIL = "user_detail"
        private const val SORT_BY_LATEST = "latest"
        private const val SORT_BY_OLDEST = "oldest"
        private const val SORT_BY_POPULAR = "popular"

        @JvmStatic
        fun newInstance(user: User) = UserLikeFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER_DETAIL, user)
            }
        }
    }
}
