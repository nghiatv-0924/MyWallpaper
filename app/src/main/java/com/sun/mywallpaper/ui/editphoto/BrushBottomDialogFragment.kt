package com.sun.mywallpaper.ui.editphoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sun.mywallpaper.R
import com.sun.mywallpaper.adapter.ColorPickerAdapter
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.di.KoinNames
import com.sun.mywallpaper.util.Utils
import kotlinx.android.synthetic.main.fragment_brush_bottom_dialog.*
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

class BrushBottomDialogFragment : BottomSheetDialogFragment(),
    SeekBar.OnSeekBarChangeListener,
    OnRecyclerItemClickListener<Int> {

    private var listener: OnBrushBottomDialogFragmentInteractionListener? = null
    private val colorPickerAdapter: ColorPickerAdapter = get(named(KoinNames.COLOR_PICKER_ADAPTER))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_brush_bottom_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewBrushColorPicker.apply {
            adapter = colorPickerAdapter.also {
                it.updateData(Utils.getColorPickerList(resources))
                it.setOnRecyclerItemClickListener(this@BrushBottomDialogFragment)
            }
            setHasFixedSize(true)
        }

        seekBarBrushOpacity.setOnSeekBarChangeListener(this)
        seekBarBrushSize.setOnSeekBarChangeListener(this)
    }

    override fun showItemDetail(item: Int) {
        listener?.onColorChanged(item)
        dismiss()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when (seekBar?.id) {
            R.id.seekBarBrushOpacity -> listener?.onOpacityChanged(progress)

            R.id.seekBarBrushSize -> listener?.onBrushSizeChanged(progress)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    fun setFragmentInteractionListener(listener: OnBrushBottomDialogFragmentInteractionListener) {
        this.listener = listener
    }

    interface OnBrushBottomDialogFragmentInteractionListener {

        fun onColorChanged(colorCode: Int)

        fun onOpacityChanged(opacity: Int)

        fun onBrushSizeChanged(brushSize: Int)
    }

    companion object {

        fun newInstance() = BrushBottomDialogFragment()
    }
}
