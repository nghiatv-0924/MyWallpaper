package com.sun.mywallpaper.ui.collectiondetail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.PhotoAdapter
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.FragmentInteractionListener
import com.sun.mywallpaper.base.LastItemListener
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.data.model.Collection
import com.sun.mywallpaper.data.model.Photo
import com.sun.mywallpaper.databinding.FragmentCollectionDetailBinding
import com.sun.mywallpaper.di.KoinNames
import kotlinx.android.synthetic.main.fragment_collection_detail.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class CollectionDetailFragment :
    BaseFragment<FragmentCollectionDetailBinding, CollectionDetailViewModel>(),
    OnRecyclerItemClickListener<Photo> {

    override val layoutResource: Int
        get() = R.layout.fragment_collection_detail
    override val viewModel: CollectionDetailViewModel by viewModel()

    private var listener: OnCollectionDetailFragmentInteractionListener? = null
    private val photoAdapter: PhotoAdapter = get(named(KoinNames.COLLECTION_DETAIL_ADAPTER))
    private var page = 1
    private val collection by lazy {
        arguments?.getParcelable<Collection>(COLLECTION_DETAIL)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCollectionDetailFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context $ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER")
        }
    }

    @SuppressLint("ResourceType")
    override fun initComponents() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            supportActionBar?.title = collection?.title
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)

        Glide.with(this)
            .load(collection?.user?.profileImage?.medium)
            .apply(RequestOptions.circleCropTransform())
            .into(imageProfileCollection)
        textCollectionDescription.text = collection?.description
        textUserCollection.text = collection?.user?.name

        recyclerViewCollectionDetail.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =
                photoAdapter.also { it.setOnRecyclerItemClickListener(this@CollectionDetailFragment) }
            hasFixedSize()
            addOnScrollListener(object : LastItemListener() {
                override fun onLastItemVisible() {
                    collection?.let {
                        viewModel.getPhotos(it.id, ++page, DEFAULT_PER_PAGE)
                        progressBar.visibility = View.VISIBLE
                    }
                }
            })
        }
        collectionDetailSwipeRefreshLayout.apply {
            setOnRefreshListener {
                page = 1
                collection?.let { viewModel.refreshPhotos(it.id, ++page, DEFAULT_PER_PAGE) }
                isRefreshing = false
            }
        }
    }

    override fun setBindingVariables() {
        super.setBindingVariables()
        viewDataBinding.collectionDetailViewModel = this.viewModel
    }

    override fun initData() {
        super.initData()
        collection?.let { viewModel.getPhotos(it.id, ++page, DEFAULT_PER_PAGE) }
    }

    override fun observeData() {
        super.observeData()
        viewModel.photos.observe(viewLifecycleOwner, Observer {
            it?.let {
                photoAdapter.updateData(it)
                progressBar.visibility = View.GONE
                recyclerViewCollectionDetail.visibility = View.VISIBLE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed()= getNavigationManager().navigateBack()

    override fun showItemDetail(item: Photo) {
    }

    interface OnCollectionDetailFragmentInteractionListener : FragmentInteractionListener

    companion object {
        private const val ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER =
            "must implement OnHomeFragmentInteractionListener"
        private const val COLLECTION_DETAIL = "collection_detail"
        private const val DEFAULT_PER_PAGE = 30

        @JvmStatic
        fun newInstance(collection: Collection) = CollectionDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(COLLECTION_DETAIL, collection)
            }
        }
    }
}
