package com.sun.mywallpaper.ui.userdetail

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.PagerAdapter
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.FragmentInteractionListener
import com.sun.mywallpaper.data.model.User
import com.sun.mywallpaper.databinding.FragmentUserDetailBinding
import com.sun.mywallpaper.util.Utils
import com.sun.mywallpaper.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailFragment : BaseFragment<FragmentUserDetailBinding, UserViewModel>() {
    override val layoutResource: Int
        get() = R.layout.fragment_user_detail
    override val viewModel: UserViewModel by viewModel()

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
        initToolbar()
        initViewPager()
        initOther()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            R.id.actionViewOnUnsplash -> {
                startActivity(Utils.viewIntent(user?.links?.html))
                true
            }

            R.id.actionShare -> {
                startActivity(Utils.shareIntent(user?.links?.html))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    override fun onBackPressed() = getNavigationManager().navigateBack()

    private fun initToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolBarUserDetail)
            supportActionBar?.title = user?.name
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)
    }

    private fun initViewPager() {
        pagerAdapter = PagerAdapter(childFragmentManager)
        pagerAdapter.apply {
            user?.let {
                addFragment(
                    UserPhotoFragment.newInstance(it),
                    "${it.totalPhotos} ${getString(R.string.drawer_photos)}"
                )
                addFragment(
                    UserLikeFragment.newInstance(it),
                    "${it.totalLikes} ${getString(R.string.drawer_likes)}"
                )
                addFragment(
                    UserCollectionFragment.newInstance(it),
                    "${it.totalCollections} ${getString(R.string.drawer_collections)}"
                )
            }
        }
        viewPager.apply {
            adapter = pagerAdapter
            offscreenPageLimit = 2
        }

        tabLayout.setupWithViewPager(viewPager)
    }

    private fun initOther() {
        user?.let {
            Glide.with(this)
                .load(it.profileImage.large)
                .apply(RequestOptions.circleCropTransform())
                .into(imageUser)
            textUserLocation.text = it.location
            textUserPortfolioUrl.text = it.portfolioUrl
            textUserBio.text = it.bio
        }
    }

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
