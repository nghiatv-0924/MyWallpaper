package com.sun.mywallpaper.ui.userdetail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.CollectionAdapter
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.LastItemListener
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.data.model.Collection
import com.sun.mywallpaper.data.model.User
import com.sun.mywallpaper.databinding.FragmentCollectionBinding
import com.sun.mywallpaper.di.KoinNames
import com.sun.mywallpaper.ui.collectiondetail.CollectionDetailFragment
import com.sun.mywallpaper.util.Constants
import com.sun.mywallpaper.viewmodel.CollectionViewModel
import kotlinx.android.synthetic.main.fragment_collection.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class UserCollectionFragment : BaseFragment<FragmentCollectionBinding, CollectionViewModel>(),
    OnRecyclerItemClickListener<Collection> {

    override val layoutResource: Int
        get() = R.layout.fragment_collection
    override val viewModel: CollectionViewModel by viewModel()

    private val userCollectionAdapter: CollectionAdapter =
        get(named(KoinNames.USER_DETAIL_COLLECTION_ADAPTER))
    private var page = Constants.DEFAULT_PAGE
    private val user by lazy {
        arguments?.getParcelable<User>(USER_DETAIL)
    }

    override fun initComponents() {
        recyclerViewCollection.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =
                userCollectionAdapter.also { it.setOnRecyclerItemClickListener(this@UserCollectionFragment) }
            hasFixedSize()
            addOnScrollListener(object : LastItemListener() {
                override fun onLastItemVisible() {
                    user?.let {
                        viewModel.getUserCollections(
                            it.username,
                            ++page,
                            Constants.DEFAULT_PER_PAGE
                        )
                    }
                }
            })
        }
        collectionSwipeRefreshLayout.apply {
            user?.let {
                setOnRefreshListener {
                    page = 1
                    viewModel.refreshUserCollections(
                        it.username,
                        page,
                        Constants.DEFAULT_PER_PAGE
                    )
                    isRefreshing = false
                }
            }
        }
    }

    override fun setBindingVariables() {
        super.setBindingVariables()
        viewDataBinding.collectionViewModel = this.viewModel
    }

    override fun initData() {
        super.initData()
        user?.let {
            viewModel.refreshUserCollections(
                it.username,
                page,
                Constants.DEFAULT_PER_PAGE
            )
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.userCollections.observe(viewLifecycleOwner, Observer {
            it?.let {
                userCollectionAdapter.updateData(it)
                progressBar.visibility = View.GONE
                recyclerViewCollection.visibility = View.VISIBLE
            }
        })
    }

    override fun showItemDetail(item: Collection) {
        getNavigationManager().open(CollectionDetailFragment.newInstance(item))
    }

    companion object {
        private const val USER_DETAIL = "user_detail"

        @JvmStatic
        fun newInstance(user: User) = UserCollectionFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER_DETAIL, user)
            }
        }
    }
}
