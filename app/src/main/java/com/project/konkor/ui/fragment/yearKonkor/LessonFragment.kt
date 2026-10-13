package com.project.konkor.ui.fragment.yearKonkor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.project.konkor.R
import com.project.konkor.adapter.LessonAdapter
import com.project.konkor.databinding.FragmentLessonBinding
import com.project.konkor.interfaces.DialogEventListener
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.model.LessonModel
import com.project.konkor.ui.activity.MainActivity
import com.project.konkor.ui.activity.QuestionActivity
import com.project.konkor.ui.dialog.DialogLesson
import com.project.konkor.util.Constants.ENSANI
import com.project.konkor.util.Constants.HONAR
import com.project.konkor.util.Constants.RIAZI
import com.project.konkor.util.Constants.TAJROBI
import com.project.konkor.util.Constants.ZABAN
import com.project.konkor.viewmodel.LessonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LessonFragment : Fragment(R.layout.fragment_lesson),
    OnClickManage.OnClickLesson, DialogEventListener.LessonDialogEvent {


    private val lessonViewModel: LessonViewModel by viewModels()
    private val args: LessonFragmentArgs by navArgs()
    private lateinit var binding: FragmentLessonBinding
    private lateinit var lessonModel: LessonModel
    private lateinit var lessonAdapter: LessonAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var titleToolbar: String
    private lateinit var dialog: DialogLesson

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLessonBinding.bind(view)

        lessonAdapter = LessonAdapter(requireContext(), this)


        setupLayoutManager()

        if (::titleToolbar.isInitialized) {
            (activity as MainActivity).setupTextToolbar(titleToolbar)
        }



        binding.recyclerViewLesson.apply {
            adapter = lessonAdapter
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
        }


        lessonViewModel.getLessenList().observe(viewLifecycleOwner) {
            lessonAdapter.setAdapterList(it)
        }


    }

    @SuppressLint("SetTextI18n")
    private fun setupLayoutManager() {

        gridLayoutManager = GridLayoutManager(context, 2)

        when (args.yearModel.category) {
            RIAZI -> {
                titleToolbar = "رشته ریاضی سال ${args.yearModel.year} "


                gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (position) {
                            0, 5, 8 -> 2
                            else -> 1
                        }
                    }
                }

            }
            TAJROBI -> {
                titleToolbar = "رشته تجربی سال ${args.yearModel.year} "


                gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (position) {
                            0, 5, 10 -> 2
                            else -> 1
                        }
                    }
                }

            }
            ENSANI -> {
                titleToolbar = "رشته انسانی سال ${args.yearModel.year} "

                gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (position) {
                            0, 5 -> 2
                            else -> 1
                        }
                    }
                }


            }
            HONAR -> {
                titleToolbar = "رشته هنر سال ${args.yearModel.year} "

                gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (position) {
                            0, 5, 12 -> 2
                            else -> 1
                        }
                    }
                }

            }
            ZABAN -> {
                titleToolbar = "رشته زبان سال ${args.yearModel.year} "

                gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (position) {
                            0, 5 -> 2
                            else -> 1
                        }
                    }
                }

            }


        }

    }


    override fun onClickLessen(lessonModel: LessonModel) {
        lessonModel.idYear = args.yearModel.year.toString()
        lessonModel.isNewSystem = args.yearModel.isNewSystem
        lessonModel.isAbroad = args.yearModel.isAbroad
        lessonModel.category = args.yearModel.category

        if (lessonModel.id.length < 5) {
            val newSystem = if (args.yearModel.isNewSystem) {
                1
            } else {
                0
            }
            val abroad = if (args.yearModel.isAbroad) {
                1
            } else {
                0
            }
            lessonModel.id = "${lessonModel.id}${lessonModel.idYear}${newSystem}${abroad}"
        }

        this.lessonModel = lessonModel

        dialog = DialogLesson(this)
        dialog.isCancelable = false
        dialog.show(childFragmentManager, null)


    }


    override fun onClickQuestion(view: View) {
        startActivity(Intent(requireContext(), QuestionActivity::class.java).apply {
            putExtra("lessonModel", lessonModel)
            putExtra("data", 1)
        })
        dialog.dismiss()
    }

    override fun onClickAnswer(view: View) {
        startActivity(Intent(requireContext(), QuestionActivity::class.java).apply {
            putExtra("lessonModel", lessonModel)
            putExtra("data", 0)
        })
        dialog.dismiss()
    }

    override fun onResume() {
        super.onResume()
        val bundle = bundleOf()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Lesson List")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.javaClass.simpleName)
        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }


}