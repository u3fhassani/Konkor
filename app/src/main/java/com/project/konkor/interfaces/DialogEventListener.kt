package com.project.konkor.interfaces

import android.view.View
import androidx.fragment.app.DialogFragment
import com.afollestad.materialdialogs.MaterialDialog

interface DialogEventListener {

    interface EditDialogEvent {
        fun onClickEditProfile(name: String, family: String, age: Int, category: String)
    }

    interface EditPasswordEvent {
        fun onClickEditPassword(oldPassword: String, newPassword: String)
    }

    interface LessonDialogEvent {
        fun onClickQuestion(view: View)
        fun onClickAnswer(view: View)
    }

    interface SignOutEvent {
        fun onClickSignOut(dialog: MaterialDialog)
    }

}