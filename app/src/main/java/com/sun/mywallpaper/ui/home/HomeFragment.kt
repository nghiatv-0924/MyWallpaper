package com.sun.mywallpaper.ui.home

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.PagerAdapter
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.FragmentInteractionListener
import com.sun.mywallpaper.databinding.FragmentHomeBinding
import com.sun.mywallpaper.ui.search.SearchFragment
import com.sun.mywallpaper.util.Utils
import com.sun.mywallpaper.viewmodel.HomeViewModel
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
        initToolbar()
        initViewPager()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.actionSearch -> {
                getNavigationManager().open(SearchFragment.newInstance())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    private fun initToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolBarHome)
            supportActionBar?.setTitle(R.string.app_name)
            Utils.isStoragePermissionGranted(this)
        }
        setHasOptionsMenu(true)
    }

    private fun initViewPager() {
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

    interface OnHomeFragmentInteractionListener : FragmentInteractionListener

    companion object {
        private const val ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER =
            "must implement OnHomeFragmentInteractionListener"

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
