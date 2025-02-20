/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.cricutquiz.data

import android.content.Context
import androidx.annotation.StringRes

/**
 * Fake repo to return awesome questions
 */
class SudoQuestionRepo(private val context: Context) {

    fun getQuestions(): List<Question> {
        return listOf(
            Question.TrueFalse(
                question = "Is Kotlin awesome?",
            ),

            Question.SingleChoice(
                question = "What is the capital of France?",
                options = listOf("Berlin", "Madrid", "Paris", "Rome"),
            ),

            Question.MultipleChoice(
                question = "Select prime numbers",
                options = listOf("2", "4", "5", "9"),
            ),

            Question.TextInput(
                question = "What is the name of your pet?",
                hint = "Name"
            )
        )
    }

    private fun string(@StringRes id: Int): String {
        return context.getString(id)
    }
}