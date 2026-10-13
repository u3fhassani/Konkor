package com.project.konkor.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.DialogFragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.project.konkor.R
import com.project.konkor.databinding.DialogBackPressedQuestionBinding

class DialogQuestionBackPressed(
    private val backPressed: () -> Unit,
    private val endExam: () -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogBackPressedQuestionBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogBackPressedQuestionBinding.inflate(layoutInflater)


        val dialog = MaterialDialog(requireContext())
            .customView(view = binding.root)
            .negativeButton(text = "انصراف") {
                it.dismiss()
            }

        binding.apply {

            binding.buttonDialogQuestionBackPressed.setOnClickListener {
//                onBackPressedClickListener?.let { it() }
                backPressed()
                dialog.dismiss()
            }

            binding.buttonDialogQuestionEndExam.setOnClickListener {
                endExam()
                dialog.dismiss()
            }

        }

        (dialog.window!!.decorView as ViewGroup)
            .getChildAt(0).startAnimation(
                AnimationUtils.loadAnimation(
                    context, R.anim.menu_show_up_center
                )
            )


        return dialog
    }

//    private var onBackPressedClickListener: (()-> Unit)? = null
//
//    fun setOnClickBackPressed(backPressed: () -> Unit) {
//        onBackPressedClickListener = backPressed
//    }

}