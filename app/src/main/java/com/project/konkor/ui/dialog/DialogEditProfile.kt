package com.project.konkor.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.project.konkor.R
import com.project.konkor.databinding.DialogEditProfileBinding
import com.project.konkor.interfaces.DialogEventListener
import com.project.konkor.sharedPref.SharedPreferencesAuth
import com.project.konkor.util.Constants
import com.project.konkor.util.Constants.englishName
import com.project.konkor.util.Constants.persianName

class DialogEditProfile(
    private val sharedPrefAuth: SharedPreferencesAuth,
    private val onClickEditProfile: DialogEventListener.EditDialogEvent
) : DialogFragment(),
    AdapterView.OnItemSelectedListener {

    private lateinit var binding: DialogEditProfileBinding
    private var category: String = sharedPrefAuth.categoryLesson.persianName()
    private var categorySpinnerMode = -1


    init {
        categorySpinnerMode = when (sharedPrefAuth.categoryLesson) {
            Constants.RIAZI -> 0
            Constants.TAJROBI -> 1
            Constants.ENSANI -> 2
            Constants.HONAR -> 3
            Constants.ZABAN -> 4
            else -> -1
        }
    }

// اینترفیس رو میفرسته به اکتیویتی اصلی
//    private lateinit var onClickEditProfile: DialogEventListener.EditDialogEvent
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        onClickEditProfile = context as DialogEventListener.EditDialogEvent
//    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogEditProfileBinding.inflate(
            layoutInflater,
            null,
            false
        )

        binding.editTextEditProfileName.setText(sharedPrefAuth.firstName)
        binding.editTextEditProfileFamily.setText(sharedPrefAuth.lastName)
        binding.editTextEditProfileAge.setText("${sharedPrefAuth.ageUser}")

        binding.editTextEditProfileName.doOnTextChanged { _, _, _, count ->
            if (count > 0) {
                binding.textInputLayoutEditProfileName.isErrorEnabled = false
            }
        }
        binding.editTextEditProfileFamily.doOnTextChanged { _, _, _, count ->
            if (count > 0) {
                binding.textInputLayoutEditProfileFamily.isErrorEnabled = false
            }
        }
        binding.editTextEditProfileAge.doOnTextChanged { _, _, _, count ->
            if (count > 0) {
                binding.textInputLayoutEditProfileAge.isErrorEnabled = false
            }
        }


        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(view = binding.root)
            .positiveButton(text = "ثبت") {
                binding.apply {
                    if (editTextEditProfileName.text.toString().isNotEmpty()) {
                        if (editTextEditProfileFamily.text.toString().isNotEmpty()) {
                            if (editTextEditProfileAge.text.toString().isNotEmpty()) {
                                onClickEditProfile.onClickEditProfile(
                                    editTextEditProfileName.text.toString().trim(),
                                    editTextEditProfileFamily.text.toString().trim(),
                                    editTextEditProfileAge.text.toString().toInt(),
                                    category
                                )
                                it.dismiss()
                            } else {
                                textInputLayoutEditProfileAge.error =
                                    "سال تولد نمیتواند خالی باشد"
                            }
                        } else {
                            textInputLayoutEditProfileFamily.error =
                                "نام خانوادگی نمیتواند خالی باشد"
                        }
                    } else {
                        textInputLayoutEditProfileName.error = "نام نمیتواند خالی باشد"
                    }
                }
            }
            .negativeButton(text = "انصراف") {
                it.dismiss()
            }


        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.appCompatSpinnerEditProfile.apply {
                adapter = arrayAdapter
                setSelection(categorySpinnerMode, false)
                onItemSelectedListener = this@DialogEditProfile
//                prompt = "برای تغییر رشته انتخاب کنید"
                gravity = Gravity.CENTER
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

    // spinner interface
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position).toString()
        category = item.englishName()
    }

    // spinner interface
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


}