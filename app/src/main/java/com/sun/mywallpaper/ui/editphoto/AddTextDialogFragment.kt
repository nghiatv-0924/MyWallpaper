package com.sun.mywallpaper.ui.editphoto

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.ColorPickerAdapter
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.di.KoinNames
import com.sun.mywallpaper.util.Utils
import kotlinx.android.synthetic.main.fragment_add_text_dialog.*
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

class AddTextDialogFragment : DialogFragment(), OnRecyclerItemClickListener<Int> {

    private val colorPickerAdapter: ColorPickerAdapter = get(named(KoinNames.COLOR_PICKER_ADAPTER))
    private val inputMethodManager by lazy {
        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
    private var listener: OnAddTextFragmentInteractionListener? = null
    private var colorCode = 0
    private var isEditText = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_text_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewTextColorPicker.apply {
            adapter = colorPickerAdapter.also {
                it.updateData(Utils.getColorPickerList(resources))
                it.setOnRecyclerItemClickListener(this@AddTextDialogFragment)
            }
            setHasFixedSize(true)
        }

        arguments?.let {
            colorCode = it.getInt(EXTRA_COLOR_CODE, 0)
            editText.setText(it.getString(EXTRA_INPUT_TEXT))
            editText.setTextColor(colorCode)
            isEditText = it.getBoolean(EXTRA_IS_EDIT_TEXT, false)
        }

        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

        textDone.setOnClickListener {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            val inputText = editText.text.toString()
            if (inputText.isNotEmpty()) {
                listener?.onDone(inputText, colorCode)
            }
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(width, height)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun showItemDetail(item: Int) {
        colorCode = item
        editText.setTextColor(item)
    }

    fun setFragmentInteractionListener(listener: OnAddTextFragmentInteractionListener) {
        this.listener = listener
    }

    interface OnAddTextFragmentInteractionListener {

        fun onDone(inputText: String, colorCode: Int)
    }

    companion object {
        var TAG = AddTextDialogFragment::class.java.simpleName
        const val EXTRA_INPUT_TEXT = "extra_input_text"
        const val EXTRA_COLOR_CODE = "extra_color_code"
        const val EXTRA_IS_EDIT_TEXT = "extra_is_edit_text"

        fun show(
            fragmentManager: FragmentManager,
            inputText: String?,
            @ColorInt colorCode: Int,
            isEditText: Boolean
        ) = AddTextDialogFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_INPUT_TEXT, inputText)
                putInt(EXTRA_COLOR_CODE, colorCode)
                putBoolean(EXTRA_IS_EDIT_TEXT, isEditText)
            }
            show(fragmentManager, TAG)
        }
    }
}
