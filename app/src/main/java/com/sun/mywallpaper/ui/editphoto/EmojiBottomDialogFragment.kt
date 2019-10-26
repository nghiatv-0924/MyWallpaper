package com.sun.mywallpaper.ui.editphoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.EmojiAdapter
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.di.KoinNames
import ja.burhanrashid52.photoeditor.PhotoEditor
import kotlinx.android.synthetic.main.fragment_emoji_bottom_dialog.*
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

class EmojiBottomDialogFragment : BottomSheetDialogFragment(),
    OnRecyclerItemClickListener<String> {

    private var listener: OnEmojiFragmentInteractionListener? = null
    private val emojiAdapter: EmojiAdapter = get(named(KoinNames.EMOJI_ADAPTER))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_emoji_bottom_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewEmoji.apply {
            adapter = emojiAdapter.also {
                it.updateData(PhotoEditor.getEmojis(context))
                it.setOnRecyclerItemClickListener(this@EmojiBottomDialogFragment)
            }
            setHasFixedSize(true)
        }
    }

    override fun showItemDetail(item: String) {
        listener?.emojiSelected(item)
        dismiss()
    }

    fun setFragmentInteractionListener(listener: OnEmojiFragmentInteractionListener) {
        this.listener = listener
    }

    interface OnEmojiFragmentInteractionListener {

        fun emojiSelected(emojiUnicode: String)
    }

    companion object {

        fun newInstance() = EmojiBottomDialogFragment()
    }
}
