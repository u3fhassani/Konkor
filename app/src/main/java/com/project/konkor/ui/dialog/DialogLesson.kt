package com.project.konkor.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.DialogFragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.project.konkor.R
import com.project.konkor.databinding.DialogLessonBinding
import com.project.konkor.interfaces.DialogEventListener

class DialogLesson(
    private val lessonDialogEvent: DialogEventListener.LessonDialogEvent
) : DialogFragment() {

    private lateinit var binding: DialogLessonBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogLessonBinding.inflate(
            layoutInflater,
            null,
            false
        )

        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(view = binding.root)
            .negativeButton(text = "انصراف") {
                it.dismiss()
            }


        binding.apply {

            binding.materialButtonLessonDialogQuestion.setOnClickListener {
                lessonDialogEvent.onClickQuestion(it)
            }
            binding.materialButtonLessonDialogAnswer.setOnClickListener {
                lessonDialogEvent.onClickAnswer(it)
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

}