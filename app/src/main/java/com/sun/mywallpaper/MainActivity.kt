package com.sun.mywallpaper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.HasNavigationManager
import com.sun.mywallpaper.base.NavigationManager
import com.sun.mywallpaper.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity(),
    HomeFragment.OnHomeFragmentInteractionListener,
    HasNavigationManager {

    private lateinit var navigationManager: NavigationManager
    private lateinit var currentFragment: BaseFragment<*, *>

    override fun onCreate(savedInstanceState: Bundle?) {
        navigationManager = NavigationManager(supportFragmentManager, R.id.mainContainer)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?: navigationManager.openAsRoot(HomeFragment.newInstance())
        setSupportActionBar(toolBar)
    }

    override fun setCurrentFragment(fragment: BaseFragment<*, *>) {
        currentFragment = fragment
    }

    override fun provideNavigationManager() = navigationManager
}
