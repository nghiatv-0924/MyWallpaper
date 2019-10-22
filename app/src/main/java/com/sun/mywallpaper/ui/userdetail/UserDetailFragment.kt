package com.sun.mywallpaper.ui.userdetail

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.PagerAdapter
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.FragmentInteractionListener
import com.sun.mywallpaper.data.model.User
import com.sun.mywallpaper.databinding.FragmentUserDetailBinding
import kotlinx.android.synthetic.main.fragment_user_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailFragment : BaseFragment<FragmentUserDetailBinding, UserDetailViewModel>() {
    override val layoutResource: Int
        get() = R.layout.fragment_user_detail
    override val viewModel: UserDetailViewModel by viewModel()

    private var listener: OnUserDetailFragmentInteractionListener? = null
    private val user by lazy {
        arguments?.getParcelable<User>(USER_DETAIL)
    }
    private lateinit var pagerAdapter: PagerAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnUserDetailFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context $ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER")
        }
    }

    override fun initComponents() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolBar)
            supportActionBar?.title = user?.name
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)

        user?.let {
            Glide.with(this)
                .load(it.profileImage.large)
                .apply(RequestOptions.circleCropTransform())
                .into(imageUser)
            textUserLocation.text = it.location
            textUserPortfolioUrl.text = it.portfolioUrl
            if (it.bio.isNotEmpty()) {
                textUserBio.text = it.bio
                textUserBio.visibility = View.VISIBLE
            }
        }

        pagerAdapter = PagerAdapter(childFragmentManager)
        pagerAdapter.apply {

        }
        viewPager.apply {
            adapter = pagerAdapter
            offscreenPageLimit = 2
        }

        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() = getNavigationManager().navigateBack()

    interface OnUserDetailFragmentInteractionListener : FragmentInteractionListener

    companion object {
        private const val ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER =
            "must implement OnUserDetailFragmentInteractionListener"
        private const val USER_DETAIL = "user_detail"

        @JvmStatic
        fun newInstance(user: User) = UserDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER_DETAIL, user)
            }
        }
    }
}
