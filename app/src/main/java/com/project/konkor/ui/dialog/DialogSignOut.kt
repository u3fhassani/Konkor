package com.project.konkor.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.DialogFragment
import com.afollestad.materialdialogs.MaterialDialog
import com.project.konkor.R
import com.project.konkor.interfaces.DialogEventListener

class DialogSignOut : DialogFragment() {

    private lateinit var signOutEvent: DialogEventListener.SignOutEvent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signOutEvent = context as DialogEventListener.SignOutEvent
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = MaterialDialog(requireContext())
            .title(text = "آیا میخواهید از حساب خود خارج شوید ؟؟؟")
            .cancelable(true)
            .noAutoDismiss()
            .negativeButton(text = "لغو") {
                it.dismiss()
            }
            .positiveButton(text = "خروج") {
                signOutEvent.onClickSignOut(it)
            }

        (dialog.window!!.decorView as ViewGroup)
            .getChildAt(0).startAnimation(
                AnimationUtils.loadAnimation(
                    requireContext(), R.anim.menu_show_up_center
                )
            )

        return dialog
    }


}