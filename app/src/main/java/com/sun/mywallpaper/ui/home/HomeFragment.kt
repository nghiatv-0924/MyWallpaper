package com.sun.mywallpaper.ui.home

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.PagerAdapter
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.FragmentInteractionListener
import com.sun.mywallpaper.databinding.FragmentHomeBinding
import com.sun.mywallpaper.ui.home.collection.CollectionFragment
import com.sun.mywallpaper.ui.home.featuredphoto.FeaturedFragment
import com.sun.mywallpaper.ui.home.newphoto.NewFragment
import com.sun.mywallpaper.util.Utils
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val layoutResource: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()
    private var listener: OnHomeFragmentInteractionListener? = null

    private lateinit var pagerAdapter: PagerAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnHomeFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context $ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER")
        }
    }

    override fun initComponents() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolBar)
            supportActionBar?.setTitle(R.string.app_name)
        }
        setHasOptionsMenu(true)

        activity?.let { activity ->
            (activity as AppCompatActivity).setSupportActionBar(toolBar)
            activity.setTitle(getString(R.string.app_name))
            Utils.isStoragePermissionGranted(activity)
        }
        pagerAdapter = PagerAdapter(childFragmentManager)
        pagerAdapter.apply {
            addFragment(NewFragment.newInstance(), getString(R.string.drawer_new))
            addFragment(FeaturedFragment.newInstance(), getString(R.string.drawer_featured))
            addFragment(CollectionFragment.newInstance(), getString(R.string.drawer_collections))
        }
        viewPager.apply {
            adapter = pagerAdapter
            offscreenPageLimit = 2
        }

        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home, menu)
    }

    interface OnHomeFragmentInteractionListener : FragmentInteractionListener

    companion object {
        private const val ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER =
            "must implement OnHomeFragmentInteractionListener"

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
