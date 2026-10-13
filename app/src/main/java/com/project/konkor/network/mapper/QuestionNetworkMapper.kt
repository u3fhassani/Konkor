package com.project.konkor.network.mapper

import com.project.konkor.model.QuestionModel
import com.project.konkor.network.entity.QuestionNetworkEntity
import com.project.konkor.util.EntityMapper
import javax.inject.Inject

class QuestionNetworkMapper
@Inject
constructor() : EntityMapper<QuestionNetworkEntity, QuestionModel> {

    override fun mapFromEntity(entity: QuestionNetworkEntity): QuestionModel {
        return QuestionModel(
            answerText = entity.answerText,
            answerTrueNumber = entity.answerTrueNumber,
            categoryLesson = entity.categoryLesson,
            choice1 = entity.choice1,
            choice2 = entity.choice2,
            choice3 = entity.choice3,
            choice4 = entity.choice4,
            id = entity.id,
            lesson = entity.lesson,
            question = entity.question,
            questionNumber = entity.questionNumber,
            yearsId = entity.yearsId,
            isNewSystem = entity.isNewSystem,
            isAbroad = entity.isAbroad,
            imageUrlQuestion = entity.imageUrlQuestion,
            imageUrlQuestionNight = entity.imageUrlQuestionNight,
            imageUrlAnswer = entity.imageUrlAnswer,
            imageUrlAnswerNight = entity.imageUrlAnswerNight
        )
    }

    override fun mapToEntity(appModel: QuestionModel): QuestionNetworkEntity {
        return QuestionNetworkEntity(
            answerText = appModel.answerText,
            answerTrueNumber = appModel.answerTrueNumber,
            categoryLesson = appModel.categoryLesson,
            choice1 = appModel.choice1,
            choice2 = appModel.choice2,
            choice3 = appModel.choice3,
            choice4 = appModel.choice4,
            id = appModel.id,
            lesson = appModel.lesson,
            question = appModel.question,
            questionNumber = appModel.questionNumber,
            yearsId = appModel.yearsId,
            isNewSystem = appModel.isNewSystem,
            isAbroad = appModel.isAbroad,
            imageUrlQuestion = appModel.imageUrlQuestion,
            imageUrlQuestionNight = appModel.imageUrlQuestionNight,
            imageUrlAnswer = appModel.imageUrlAnswer,
            imageUrlAnswerNight = appModel.imageUrlAnswerNight
        )
    }


    fun mapFromEntityList(entities: List<QuestionNetworkEntity>): List<QuestionModel> {
        return entities.map { mapFromEntity(it) }
    }


}