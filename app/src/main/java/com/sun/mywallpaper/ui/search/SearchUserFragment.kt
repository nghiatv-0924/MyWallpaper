package com.sun.mywallpaper.ui.search

import android.view.View
import androidx.lifecycle.Observer
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.UserAdapter
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.data.model.User
import com.sun.mywallpaper.databinding.FragmentSearchUserBinding
import com.sun.mywallpaper.di.KoinNames
import com.sun.mywallpaper.ui.userdetail.UserDetailFragment
import com.sun.mywallpaper.util.Constants
import com.sun.mywallpaper.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search_user.*
import kotlinx.android.synthetic.main.no_results_layout.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class SearchUserFragment : BaseFragment<FragmentSearchUserBinding, SearchViewModel>(),
    OnRecyclerItemClickListener<User> {

    override val layoutResource: Int
        get() = R.layout.fragment_search_user
    override val viewModel: SearchViewModel by viewModel()

    private val searchUserAdapter: UserAdapter = get(named(KoinNames.SEARCH_USER_ADAPTER))
    private var page = Constants.DEFAULT_PAGE

    override fun initComponents() {
        recyclerViewSearchUser.apply {
            adapter =
                searchUserAdapter.also { it.setOnRecyclerItemClickListener(this@SearchUserFragment) }
            hasFixedSize()
        }
        searchUserSwipeRefreshLayout.apply {
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
        viewModel.searchUserResponses.observe(viewLifecycleOwner, Observer {
            it?.let {
                progressBar.visibility = View.GONE
                if (it.total == Constants.NO_VALUE) {
                    noResultsView.visibility = View.VISIBLE
                } else {
                    searchUserAdapter.updateData(it.results)
                    recyclerViewSearchUser.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun showItemDetail(item: User) {
        getNavigationManager().open(UserDetailFragment.newInstance(item))
    }

    fun searchUsers(query: String) {
        viewModel.getSearchUsers(query, page, Constants.DEFAULT_PER_PAGE)
        noResultsView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchUserFragment()
    }
}
