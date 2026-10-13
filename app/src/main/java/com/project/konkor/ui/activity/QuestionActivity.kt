package com.project.konkor.ui.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.konkor.R
import com.project.konkor.ui.dialog.DialogQuestionBackPressed
import com.project.konkor.ui.fragment.yearKonkor.AnswerFragment
import com.project.konkor.ui.fragment.yearKonkor.QuestionFragment
import com.project.konkor.util.Constants
import com.project.konkor.viewmodel.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class QuestionActivity : AppCompatActivity() {

    private val viewModel: QuestionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (viewModel.fontMode()) {
            Constants.FONT_BADR -> setTheme(R.style.ThemeChangeFontToBadr)
            Constants.FONT_B_NAZANIN -> setTheme(R.style.ThemeChangeFontToBNazanin)
            Constants.FONT_MITRA -> setTheme(R.style.ThemeChangeFontToMitra)
            Constants.FONT_MORVARID -> setTheme(R.style.ThemeChangeFontToMorvarid)
        }
        setContentView(R.layout.activity_question)

        setupOnBackPressed()

        if (viewModel.questionOrAnswer == 1) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerQuestion, QuestionFragment())
            }.commit()
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerQuestion, AnswerFragment())
            }.commit()
        }

    }

    private fun setupOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerQuestion)
                if (fragment is QuestionFragment && fragment.isTimerRunning()) {
                    DialogQuestionBackPressed(
                        backPressed = {
                            isEnabled = false
                            onBackPressedDispatcher.onBackPressed()
                            isEnabled = true
                        },
                        endExam = {
                            fragment.onClickEnd()
                        }
                    ).show(supportFragmentManager, null)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }
            }
        })
    }

}