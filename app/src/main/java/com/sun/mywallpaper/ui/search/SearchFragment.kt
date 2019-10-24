package com.sun.mywallpaper.ui.search

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.PagerAdapter
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.FragmentInteractionListener
import com.sun.mywallpaper.databinding.FragmentSearchBinding
import com.sun.mywallpaper.util.Constants
import com.sun.mywallpaper.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(),
    ViewPager.OnPageChangeListener,
    AdapterView.OnItemSelectedListener {

    override val layoutResource: Int
        get() = R.layout.fragment_search
    override val viewModel: SearchViewModel by viewModel()

    private var listener: OnSearchFragmentInteractionListener? = null
    private lateinit var pagerAdapter: PagerAdapter
    private val inputMethodManager by lazy {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
    }
    private val searchPhotoFragment = SearchPhotoFragment.newInstance()
    private val searchCollectionFragment = SearchCollectionFragment.newInstance()
    private val searchUserFragment = SearchUserFragment.newInstance()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSearchFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context $ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER")
        }
    }

    override fun initComponents() {
        initToolbar()
        initSearchText()
        initViewPager()
        initSpinner()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            R.id.actionClearText -> {
                editTextSearch.text = null
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    override fun onBackPressed() = getNavigationManager().navigateBack()

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        spinnerSearchOption.visibility = when (position) {
            SEARCH_PHOTO_PAGE -> View.VISIBLE
            SEARCH_COLLECTION_PAGE -> View.GONE
            SEARCH_USER_PAGE -> View.GONE
            else -> View.VISIBLE
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        searchPhotoFragment.setOrientation(editTextSearch.text.toString(), position)
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolBarSearch)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = Constants.EMPTY_STRING
        }
        setHasOptionsMenu(true)
    }

    private fun initSearchText() {
        editTextSearch.apply {
            requestFocus()
            setOnEditorActionListener { _, _, _ ->
                searchPhotoFragment.searchPhotos(text.toString().trim())
                searchCollectionFragment.searchCollections(text.toString().trim())
                searchUserFragment.searchUsers(text.toString().trim())
                editTextSearch.clearFocus()
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                true
            }
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    private fun initViewPager() {
        pagerAdapter = PagerAdapter(childFragmentManager)
        pagerAdapter.apply {
            addFragment(searchPhotoFragment, getString(R.string.drawer_photos))
            addFragment(searchCollectionFragment, getString(R.string.drawer_collections))
            addFragment(searchUserFragment, getString(R.string.drawer_users))
        }
        viewPager.apply {
            adapter = pagerAdapter
            addOnPageChangeListener(this@SearchFragment)
            offscreenPageLimit = 2
        }

        tabLayout.setupWithViewPager(viewPager)
    }

    private fun initSpinner() {
        context?.let {
            val spinnerAdapter =
                ArrayAdapter.createFromResource(it, R.array.search_options, R.layout.item_spinner)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSearchOption.apply {
                adapter = spinnerAdapter
                onItemSelectedListener = this@SearchFragment
            }
        }
    }

    interface OnSearchFragmentInteractionListener : FragmentInteractionListener

    companion object {
        private const val ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER =
            "must implement OnSearchFragmentInteractionListener"
        private const val SEARCH_PHOTO_PAGE = 0
        private const val SEARCH_COLLECTION_PAGE = 1
        private const val SEARCH_USER_PAGE = 2

        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
