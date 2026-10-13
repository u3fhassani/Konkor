package com.project.konkor.interfaces

import android.view.View
import com.project.konkor.model.*

interface OnClickManage {

    interface OnClickYear {
        fun onClickYear(yearModel: YearModel, position: Int)
    }

    interface OnClickCategory {
        fun onClickCategory(nameCat: String, view: View)
    }

    interface OnClickLesson {
        fun onClickLessen(lessonModel: LessonModel)
    }

    interface OnClickQuestionChoice {
        fun setChoice(questionModel: QuestionModel)
    }

    interface OnClickTarget {
        fun removeItemTarget(targetModel: TargetModel, size: Int)
        fun editItemTarget(targetModel: TargetModel, isEnabled:Boolean)
        fun checkBoxClick(targetModel: TargetModel)
    }

    interface OnClickYearResult {
        fun onClickYearResult(yearModel: YearModel)
    }

    interface OnClickResponse {
        fun onClickResponse(resultModel: ResultModel)
        fun onLongClickResponse(resultModel: ResultModel)
    }

    interface OnClickFavorite {
        fun onClickItemFavorite(lessonModel: LessonModel)
    }


}