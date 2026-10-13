package com.project.konkor.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.project.konkor.R
import com.project.konkor.databinding.DialogEditPasswordBinding
import com.project.konkor.interfaces.DialogEventListener

class DialogEditPassword(private val onClickEditPassword: DialogEventListener.EditPasswordEvent) :
    DialogFragment() {

    private lateinit var binding: DialogEditPasswordBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogEditPasswordBinding.inflate(
            layoutInflater,
            null,
            false
        )

        binding.editTextEditPasswordOldPassword.doOnTextChanged { _, _, _, count ->
            if (count > 0) {
                binding.textInputLayoutEditPasswordOldPassword.isErrorEnabled = false
            }
        }
        binding.editTextEditPasswordNewPassword.doOnTextChanged { _, _, _, count ->
            if (count > 0) {
                binding.textInputLayoutEditPasswordNewPassword.isErrorEnabled = false
            }
        }
        binding.editTextEditPasswordRepeatNewPassword.doOnTextChanged { _, _, _, count ->
            if (count > 0) {
                binding.textInputLayoutEditPasswordRepeatNewPassword.isErrorEnabled = false
            }
        }

        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(view = binding.root)
            .negativeButton(text = "انصراف") {
                it.dismiss()
            }
            .positiveButton(text = "ثبت") {

                binding.apply {
                    if (editTextEditPasswordOldPassword.text.toString().isNotEmpty()) {
                        if (editTextEditPasswordOldPassword.text.toString().length >= 8) {
                            if (editTextEditPasswordNewPassword.text.toString().isNotEmpty()) {
                                if (editTextEditPasswordNewPassword.text.toString().length >= 8) {
                                    if (editTextEditPasswordNewPassword.text.toString() == editTextEditPasswordRepeatNewPassword.text.toString()) {
                                        onClickEditPassword.onClickEditPassword(
                                            editTextEditPasswordOldPassword.text.toString(),
                                            editTextEditPasswordNewPassword.text.toString()
                                        )
                                        it.dismiss()
                                    } else {
                                        textInputLayoutEditPasswordRepeatNewPassword.error =
                                            "رمز جدید و تکرار آن با هم برابر نیستند"
                                    }
                                } else {
                                    textInputLayoutEditPasswordNewPassword.error =
                                        "تعداد کاراکتر نمیتواند کمتر از ۸ عدد باشد"
                                }
                            } else {
                                textInputLayoutEditPasswordNewPassword.error =
                                    "رمز عبور جدید را وارد کنید"
                            }
                        } else {
                            textInputLayoutEditPasswordOldPassword.error =
                                "رمز عبور فعلی نمیتواند کمتر از ۸ کاراکتر باشد"
                        }
                    } else {
                        textInputLayoutEditPasswordOldPassword.error =
                            "رمز عبور فعلی نمیتواند خالی باشد"
                    }


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