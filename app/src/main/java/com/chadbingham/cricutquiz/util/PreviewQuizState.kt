/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.cricutquiz.util

import com.chadbingham.cricutquiz.data.Question
import com.chadbingham.cricutquiz.ui.viewmodel.QuizState

object PreviewQuizState {

    fun getQuizState(): QuizState {
        return QuizState(getQuestions())
    }

    private fun getQuestions(): List<Question> {
        return listOf(
            getTrueFalseQuestion(),
            getSingleChoiceQuestion(),
            getMultiChoiceQuestion(),
            getUserInputQuestion()
        )
    }

    fun getTrueFalseQuestion(): Question.TrueFalse {
        return Question.TrueFalse(
            question = "Is Kotlin awesome?",
        )
    }

    fun getSingleChoiceQuestion(): Question.SingleChoice {
        return Question.SingleChoice(
            question = "What is the capital of France?",
            options = listOf("Berlin", "Madrid", "Paris", "Rome"),
        )
    }

    fun getMultiChoiceQuestion(): Question.MultipleChoice {
        return Question.MultipleChoice(
            question = "Select prime numbers",
            options = listOf("2", "4", "5", "9"),
        )
    }

    fun getUserInputQuestion(): Question.TextInput {
        return Question.TextInput(
            question = "What is the name of your pet?",
        )
    }
}