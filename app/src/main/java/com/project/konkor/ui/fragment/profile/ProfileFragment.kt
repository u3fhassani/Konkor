package com.project.konkor.ui.fragment.profile

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.analytics.FirebaseAnalytics
import com.project.konkor.R
import com.project.konkor.adapter.TargetAdapter
import com.project.konkor.databinding.FragmentProfileBinding
import com.project.konkor.interfaces.DialogEventListener
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.model.AuthModel
import com.project.konkor.model.TargetModel
import com.project.konkor.sharedPref.SharedPreferencesAuth
import com.project.konkor.ui.dialog.DialogEditPassword
import com.project.konkor.ui.dialog.DialogEditProfile
import com.project.konkor.util.Constants.persianName
import com.project.konkor.util.DataState
import com.project.konkor.util.ManageProfileImage
import com.project.konkor.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile),
    OnClickManage.OnClickTarget, DialogEventListener.EditDialogEvent,
    DialogEventListener.EditPasswordEvent {

    @Inject
    lateinit var sharedPrefAuth: SharedPreferencesAuth

    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var targetAdapter: TargetAdapter
    private lateinit var editTextTarget: TextInputEditText
    private lateinit var editTextDay: TextInputEditText
    private lateinit var editTextMonth: TextInputEditText
    private lateinit var editTextYear: TextInputEditText
    private lateinit var dialog: MaterialDialog
    private var day = ""
    private var month = ""
    private lateinit var targetModel: TargetModel
    private var inputTargetMode: Int = 0
    private lateinit var manageProfileImage: ManageProfileImage
    private val photoName = "profilePhoto"

    private val takePhoto =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            if (sharedPrefAuth.isPhotoExist) {
                manageProfileImage.deletePhotoFromInternalStorage(photoName)
                sharedPrefAuth.savePhotoExist(false)
            }

            val isSavedSuccessfully =
                it?.let { it1 ->
                    manageProfileImage.savePhotoToInternalStorage(photoName, it1)
                }

            if (isSavedSuccessfully == true) {
                binding.profileImage.setImageBitmap(it)
                sharedPrefAuth.savePhotoExist(true)
            }


        }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                if (sharedPrefAuth.isPhotoExist) {
                    manageProfileImage.deletePhotoFromInternalStorage(photoName)
                    sharedPrefAuth.savePhotoExist(false)
                }

                Picasso.get().load(uri).placeholder(R.drawable.ic_graduate)
                    .error(R.drawable.ic_baseline_error_outline_24).into(object : Target {
                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            val isSuccess = manageProfileImage.savePhotoToInternalStorage(
                                "profilePhoto",
                                bitmap!!
                            )
                            if (isSuccess) {
                                binding.profileImage.setImageURI(uri)
                                sharedPrefAuth.savePhotoExist(true)
                            }
                        }

                        override fun onBitmapFailed(
                            e: java.lang.Exception?,
                            errorDrawable: Drawable?
                        ) {
                            binding.profileImage.setImageDrawable(errorDrawable)
                        }

                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                            binding.profileImage.setImageDrawable(placeHolderDrawable)
                        }
                    })


            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        profileViewModel.getTargetList()
        manageProfileImage = ManageProfileImage(requireContext())

        setupView()

    }

    private fun setupEditProfile() {

            profileViewModel.editProfile.observe(viewLifecycleOwner) { editProfile ->
                when (editProfile) {
                    DataState.Loading -> {
                        setupLoading(true)
                    }
                    is DataState.Success -> {
                        setupViewSuccess(EditStateEvent.EditProfile, editProfile.data, null)
                        setupLoading(false)
                    }
                    is DataState.Error -> {
                        setupException(EditStateEvent.EditProfile, editProfile.exception)
                        setupLoading(false)
                    }
                }
            }

    }

    private fun setupEditPassword() {

        profileViewModel.editPassword.observe(viewLifecycleOwner) { editPassword ->
            when (editPassword) {
                DataState.Loading -> {
                    setupLoading(true)
                }
                is DataState.Success -> {
                    setupViewSuccess(EditStateEvent.EditPassword, null, editPassword.data)
                    setupLoading(false)
                }
                is DataState.Error -> {
                    setupException(EditStateEvent.EditPassword, editPassword.exception)
                    setupLoading(false)
                }
            }
        }
    }

    private fun snackBar(
        message: String,
        duration: Int = Snackbar.LENGTH_LONG,
        listener: View.OnClickListener? = null
    ) {
        if (listener == null) {
            Snackbar.make(binding.coordinatorLayoutProfileRoot, message, duration).show()
        } else {
            Snackbar.make(binding.coordinatorLayoutProfileRoot, message, duration)
                .setAction("تایید", listener).show()
        }
    }

    private fun setupViewSuccess(
        editStateEvent: EditStateEvent,
        editProfile: AuthModel?,
        editPassword: Boolean?
    ) {
        when (editStateEvent) {
            EditStateEvent.EditPassword -> {
                if (editPassword != null) {

                    if (editPassword) {
                        snackBar("رمز عبور شما با موفقیت تغییر کرد")
                    } else {
                        toast("متأسفانه رمز تغییر نکرد لطفا دوباره تلاش کنید")
                    }

                }
            }
            EditStateEvent.EditProfile -> {
                if (editProfile != null) {
                    binding.textViewProfileNameIn.text = editProfile.firstName
                    binding.textViewProfileFamilyIn.text = editProfile.lastName
                    binding.textViewProfileAgeIn.text = editProfile.age.toString()
                    binding.textViewProfileCategoryIn.text = editProfile.category.persianName()
                    snackBar("اطلاعات با موفقیت تغییر کرد .")
                    Log.d("profileFragment", "setupViewSuccess: ")
                }
            }
        }

    }

    private fun setupException(editStateEvent: EditStateEvent, exception: Exception) {
        when (editStateEvent) {
            EditStateEvent.EditPassword -> {
                when (exception.message?.trim()) {
                    "HTTP 403" -> {
                        toast("رمز عبور قدیمی را اشتباه وارد کردید")
                    }
                    "HTTP 404" -> {
                        toast("با این ایمیل هنوز کسی ثبت نام نکرده است !")
                    }
                    else -> {
                        toast("لطفا اینترنت خود را برسی کنید .")
                    }
                }
            }
            EditStateEvent.EditProfile -> {
                when (exception.message?.trim()) {
                    "HTTP 404" -> {
                        toast("با این ایمیل هنوز کسی ثبت نام نکرده است !")
                    }
                    else -> {
                        Log.d("profileFragment", "setupException: ")
                        toast("لطفا اینترنت خود را برسی کنید .")
                    }
                }
            }
        }
    }

    private fun setupLoading(loading: Boolean) {
        binding.progressProfileLoading.visibility =
            if (loading) {
                VISIBLE
            } else {
                GONE
            }
    }

    private fun cardTarget(listNotEmpty: Boolean) {

        if (listNotEmpty) {
            binding.materialCardViewProfileListTarget.visibility = VISIBLE
            binding.textViewProfileTarget.visibility = VISIBLE
        } else {
            binding.materialCardViewProfileListTarget.visibility = GONE
            binding.textViewProfileTarget.visibility = GONE
        }

    }

    private fun setupView() {

        targetAdapter = TargetAdapter(this)

        binding.apply {

            val createAt = sharedPrefAuth.dateCreateAt.split(",")[1]

            textViewProfileDateIn.text = createAt
            // TODO: 12/03/2021 change when pay manage
            textViewProfileRemainIn.text = "0"
            textViewProfileNameIn.text = sharedPrefAuth.firstName
            textViewProfileFamilyIn.text = sharedPrefAuth.lastName
            textViewProfileAgeIn.text = "${sharedPrefAuth.ageUser}"
            textViewProfileCategoryIn.text = sharedPrefAuth.categoryLesson.persianName()


            recyclerViewProfileTarget.apply {
                adapter = targetAdapter

                layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            }

            buttonProfileAddTarget.setOnClickListener {
                inputTargetMode = 1
                materialDialogTarget()
            }

            profileImage.setOnClickListener {

                val dialog = MaterialDialog(requireContext())
                    .noAutoDismiss()
                    .customView(R.layout.dialog_chosse_photo)
                    .cancelable(true)
                    .negativeButton(text = "انصراف") {
                        it.dismiss()
                    }

                dialog.getCustomView().apply {

                    findViewById<ImageView>(R.id.imageView_dialogPhoto_camera).setOnClickListener {
                        takePhoto.launch()
                        dialog.dismiss()
                    }
                    findViewById<ImageView>(R.id.imageView_dialogPhoto_gallery).setOnClickListener {
                        getContent.launch("image/*")
                        dialog.dismiss()
                    }

                }


                (dialog.window!!.decorView as ViewGroup)
                    .getChildAt(0).startAnimation(
                        AnimationUtils.loadAnimation(
                            context, R.anim.menu_show_up_center
                        )
                    )

                dialog.show()


            }


            profileImage.setOnLongClickListener {


                if (sharedPrefAuth.isPhotoExist) {
                    val dialog = MaterialDialog(requireContext())
                        .title(text = "مخواهید عکس پروفایل را حذف کنید ؟")
                        .positiveButton(text = "حذف") {
                            manageProfileImage.deletePhotoFromInternalStorage(photoName)
                            sharedPrefAuth.savePhotoExist(false)
                            profileImage.setImageResource(R.drawable.ic_graduate)
                            it.dismiss()
                        }
                        .negativeButton(text = "انصراف") {
                            it.dismiss()
                        }

                    (dialog.window!!.decorView as ViewGroup)
                        .getChildAt(0).startAnimation(
                            AnimationUtils.loadAnimation(
                                context, R.anim.menu_show_up_center
                            )
                        )

                    dialog.show()
                }

                true
            }

            if (sharedPrefAuth.isPhotoExist) {
                lifecycleScope.launch {
                    val imageModel = manageProfileImage.loadPhotosFromInternalStorage()[0]
                    withContext(Main) {
                        binding.profileImage.setImageBitmap(imageModel.bitmap)
                    }
                }
            } else {
                binding.profileImage.setImageResource(R.drawable.ic_graduate)
            }


        }

        profileViewModel.targetList.observe(viewLifecycleOwner) {
            targetAdapter.addListTarget(it)
            if (it.isNullOrEmpty()) {
                cardTarget(false)
            } else {
                cardTarget(true)
            }
        }

    }

    fun editProfile() {
        val dialog = DialogEditProfile(sharedPrefAuth, this)
        dialog.isCancelable = false
        dialog.show(childFragmentManager, null)
        setupEditProfile()
    }

    override fun onClickEditProfile(name: String, family: String, age: Int, category: String) {
        Log.d("profileFragment", "onClickEditProfile: ")
        profileViewModel.editProfile(name, family, age, category)
    }

    fun editPassword() {
        val dialog = DialogEditPassword(this)
        dialog.isCancelable = false
        dialog.show(childFragmentManager, null)
        setupEditPassword()
    }

    override fun onClickEditPassword(oldPassword: String, newPassword: String) {
        profileViewModel.editPassword(oldPassword, newPassword)
    }


    private fun materialDialogTarget() {


        dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.dialog_target)
            .cancelable(false)
            .negativeButton(res = R.string.cancel) {
                it.dismiss()
            }
            .positiveButton(res = R.string.confirm, click = this::onClickConfirm)


        dialog.getCustomView().apply {


            lifecycleScope.launch {


                withContext(IO) {
                    editTextTarget = findViewById(R.id.textInput_dialogTarget_target)
                    editTextDay = findViewById(R.id.textInputEditText_dialogTarget_day)
                    editTextMonth = findViewById(R.id.textInputEditText_dialogTarget_month)
                    editTextYear = findViewById(R.id.textInputEditText_dialogTarget_year)
                }


            }

        }

//        dialog.cornerRadius(res = R.dimen.materialDialogCorner)


        (dialog.window!!.decorView as ViewGroup)
            .getChildAt(0).startAnimation(
                AnimationUtils.loadAnimation(
                    context, R.anim.menu_show_up_center
                )
            )


        dialog.show()


    }

    private fun onClickConfirm(dialog: MaterialDialog) {


        if (editTextTarget.text.toString().trim().isNotEmpty()) {

            if (editTextYear.text.toString().trim().isNotEmpty() && editTextYear.text.toString()
                    .trim().toInt() >= 1400 && editTextYear.text.toString()
                    .trim().toInt() < 1500
            ) {

                if (editTextDay.text.toString().trim().isNotEmpty() && editTextDay.text.toString()
                        .trim().toInt() <= 31
                ) {

                    if (editTextMonth.text.toString().trim()
                            .isNotEmpty() && editTextMonth.text.toString()
                            .trim().toInt() <= 12
                    ) {


                        month = if (editTextMonth.text.toString().trim().length == 1) {
                            "0${editTextMonth.text.toString().trim()}"
                        } else {
                            editTextMonth.text.toString().trim()
                        }

                        day = if (editTextDay.text.toString().trim().length == 1) {
                            "0${editTextDay.text.toString().trim()}"
                        } else {
                            editTextDay.text.toString().trim()
                        }

                        val date = "${editTextYear.text.toString().trim()}/$month/$day"

                        when (inputTargetMode) {
                            1 -> {
                                val targetModel =
                                    TargetModel(
                                        randomId(),
                                        editTextTarget.text.toString(),
                                        date,
                                        false
                                    )
                                profileViewModel.insert(targetModel)
                                targetAdapter.addTarget(targetModel)
                                cardTarget(true)
                            }
                            -1 -> {
                                targetModel.date = date
                                targetModel.title = editTextTarget.text.toString()
                                profileViewModel.update(targetModel)
                                targetAdapter.updateItem(targetModel)
                            }
                        }


                        dialog.dismiss()


                    } else {
                        toast("لطفا تاریخ معتبر را وارد کنید")
                    }

                } else {
                    toast("لطفا تاریخ معتبر را وارد کنید")
                }
            } else {
                toast("لطفا تاریخ معتبر را وارد کنید")
            }
        } else {
            toast("ورودی نمیتواند خالی باشد")
        }

    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), "$message ", Toast.LENGTH_SHORT).show()
    }

    private fun randomId(): String {
        return UUID.randomUUID().toString()
    }

    override fun removeItemTarget(targetModel: TargetModel, size: Int) {
        profileViewModel.delete(targetModel)
        if (size == 0) {
            cardTarget(false)
        } else {
            cardTarget(true)
        }

//        targetAdapter.removeItemTarget(targetModel,size)
    }

    override fun editItemTarget(targetModel: TargetModel, isEnabled: Boolean) {

        inputTargetMode = -1
        materialDialogTarget()
        editTextTarget.setText(targetModel.title)
        editTextYear.setText(targetModel.date.substring(0, 4))
        editTextMonth.setText(targetModel.date.substring(5, 7))
        editTextDay.setText(targetModel.date.substring(8, targetModel.date.length))
        this.targetModel = targetModel


        profileViewModel.update(targetModel)
    }

    override fun checkBoxClick(targetModel: TargetModel) {
        profileViewModel.update(targetModel)
    }

    override fun onResume() {
        super.onResume()
        val bundle = bundleOf()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Profile")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.javaClass.simpleName)
        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }


}

sealed class EditStateEvent {
    object EditProfile : EditStateEvent()
    object EditPassword : EditStateEvent()
}