package com.project.konkor.ui.fragment.options

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.google.firebase.analytics.FirebaseAnalytics
import com.project.konkor.R
import com.project.konkor.adapter.FavoriteAdapter
import com.project.konkor.databinding.FragmentFavoriteBinding
import com.project.konkor.interfaces.DialogEventListener
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.model.LessonModel
import com.project.konkor.sharedPref.SharedPreferencesAuth
import com.project.konkor.ui.activity.QuestionActivity
import com.project.konkor.ui.dialog.DialogLesson
import com.project.konkor.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite),
    OnClickManage.OnClickFavorite, DialogEventListener.LessonDialogEvent {

    @Inject
    lateinit var sharedPrefAuth: SharedPreferencesAuth

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var lessonModel: LessonModel
    private lateinit var dialog: DialogLesson
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        favoriteAdapter = FavoriteAdapter(this)


        binding.recyclerViewFavorite.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        }

        setupView()

    }

    private fun setupView() {

        favoriteViewModel.favoriteListCat.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                favoriteEmpty(false)
            } else {
                favoriteEmpty(true)
            }
            favoriteAdapter.setFavoriteList(it)
        }

    }

    override fun onResume() {
        super.onResume()
        val bundle = bundleOf()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Favorite List")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.javaClass.simpleName)
        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    private fun favoriteEmpty(isEmpty: Boolean) {

        binding.lottieFavoriteEmptyState.visibility =
            if (isEmpty) {
                VISIBLE
            } else {
                GONE
            }


    }

    override fun onClickItemFavorite(lessonModel: LessonModel) {

        this.lessonModel = lessonModel

        dialog = DialogLesson(this)
        dialog.isCancelable = false
        dialog.show(childFragmentManager, null)

    }

    override fun onClickQuestion(view: View) {
        startActivity(Intent(requireContext(), QuestionActivity::class.java).apply {
            putExtra("lessonModel",lessonModel)
            putExtra("data",1)
        })
        dialog.dismiss()
    }

    override fun onClickAnswer(view: View) {
        startActivity(Intent(requireContext(), QuestionActivity::class.java).apply {
            putExtra("lessonModel",lessonModel)
            putExtra("data",0)
        })
        dialog.dismiss()
    }


}