package com.sun.mywallpaper.ui.photodetail

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.sun.mywallpaper.R
import com.sun.mywallpaper.base.BaseFragment
import com.sun.mywallpaper.base.FragmentInteractionListener
import com.sun.mywallpaper.data.model.Photo
import com.sun.mywallpaper.databinding.FragmentPhotoDetailBinding
import com.sun.mywallpaper.ui.userdetail.UserDetailFragment
import com.sun.mywallpaper.util.Constants
import com.sun.mywallpaper.viewmodel.PhotoViewModel
import kotlinx.android.synthetic.main.fragment_photo_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoDetailFragment : BaseFragment<FragmentPhotoDetailBinding, PhotoViewModel>(),
    View.OnClickListener {

    override val layoutResource: Int
        get() = R.layout.fragment_photo_detail
    override val viewModel: PhotoViewModel by viewModel()

    private var listener: OnPhotoDetailFragmentInteractionListener? = null

    private val photo by lazy {
        arguments?.getParcelable<Photo>(PHOTO_DETAIL)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPhotoDetailFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context $ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER")
        }
    }

    override fun initComponents() {
        initToolbar()
        initFab()
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
            else -> super.onOptionsItemSelected(item)
        }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imagePhoto -> {
            }

            R.id.imageUser -> photo?.let {
                UserDetailFragment.newInstance(it.user)
            }

            R.id.textUserName -> photo?.let {
                UserDetailFragment.newInstance(it.user)
            }

            R.id.fabDownload -> {
                fabMenu.close(true)
            }

            R.id.fabWallpaper -> {
                fabMenu.close(true)
            }

            R.id.fabInfo -> {
                fabMenu.close(true)
            }

            R.id.fabStats -> {
                fabMenu.close(true)
            }
        }
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolBar)
            supportActionBar?.title = Constants.EMPTY_STRING
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)
    }

    private fun initFab() {
        fabMenu.setClosedOnTouchOutside(true)
        fabDownload.setOnClickListener(this)
        fabWallpaper.setOnClickListener(this)
        fabInfo.setOnClickListener(this)
        fabStats.setOnClickListener(this)
    }

    private fun initOther() {
        Glide.with(this)
            .load(photo?.urls?.regular)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imagePhoto)

        Glide.with(this)
            .load(photo?.user?.profileImage?.large)
            .apply(RequestOptions.circleCropTransform())
            .into(imageUser)

        textUserName.text = photo?.user?.name
        textPhotoStoryTitle.text =
            photo?.story?.title?.also { textPhotoStoryTitle.visibility = View.VISIBLE }
        textPhotoDescription.text =
            photo?.description?.also { textPhotoDescription.visibility = View.VISIBLE }
        textPhotoLocation.text = photo?.location?.title
        textPhotoCreatedAt.text = photo?.createdAt
        textPhotoLikes.text = getString(R.string.likes, photo?.likes.toString())
        textPhotoDownloads.text = getString(R.string.downloads, photo?.downloads.toString())
        textPhotoColor.text = photo?.color
        imagePhotoColor.setBackgroundColor(Color.parseColor(photo?.color))

        imageUser.setOnClickListener(this)
        textUserName.setOnClickListener(this)
    }

    interface OnPhotoDetailFragmentInteractionListener : FragmentInteractionListener

    companion object {
        private const val ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER =
            "must implement OnPhotoDetailFragmentInteractionListener"
        private const val PHOTO_DETAIL = "photo_detail"

        @JvmStatic
        fun newInstance(photo: Photo) = PhotoDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(PHOTO_DETAIL, photo)
            }
        }
    }
}
