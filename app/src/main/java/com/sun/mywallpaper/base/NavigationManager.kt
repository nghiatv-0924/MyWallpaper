package com.sun.mywallpaper.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sun.mywallpaper.R

class NavigationManager(
    private val fragmentManager: FragmentManager,
    private val container: Int
) {
    val isRootFragmentVisible = fragmentManager.backStackEntryCount <= STACK_ENTRY_COUNT_LIMIT

    private var navigationListener: (() -> Unit)? = null

    init {
        fragmentManager.addOnBackStackChangedListener {
            navigationListener?.invoke()
        }
    }

    fun open(fragment: Fragment) {
        openFragment(fragment, true, false)
    }

    fun openAsRoot(fragment: Fragment) {
        popEveryFragment()
        openFragment(fragment, false, true)
    }

    fun navigateBack() = if (fragmentManager.backStackEntryCount == 0) {
        false
    } else {
        fragmentManager.popBackStackImmediate()
        true
    }

    private fun openFragment(fragment: Fragment, addToBackStack: Boolean, isRoot: Boolean) {
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (isRoot) {
            fragmentTransaction.add(container, fragment, FRAGMENT_TAG_ROOT)
        } else {
            fragmentTransaction.add(container, fragment)
        }

        fragmentTransaction.setAnimations()

        if (addToBackStack) fragmentTransaction.addToBackStack(fragment.toString())

        fragmentTransaction.commit()
    }

    private fun FragmentTransaction.setAnimations() =
        setCustomAnimations(
            R.anim.slide_in_left,
            R.anim.slide_out_right,
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )

    private fun popEveryFragment() {
        fragmentManager.popBackStackImmediate(
            FRAGMENT_TAG_ROOT,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    companion object {
        private const val STACK_ENTRY_COUNT_LIMIT = 1
        private const val FRAGMENT_TAG_ROOT = "ROOT"
    }
}
