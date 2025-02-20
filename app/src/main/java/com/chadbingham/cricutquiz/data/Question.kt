/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.cricutquiz.data

import android.os.Parcelable
import com.chadbingham.cricutquiz.data.QuestionType.MULTI_CHOICE
import com.chadbingham.cricutquiz.data.QuestionType.SINGLE_CHOICE
import com.chadbingham.cricutquiz.data.QuestionType.TEXT_INPUT
import com.chadbingham.cricutquiz.data.QuestionType.TRUE_FALSE
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class Question : Parcelable {

    abstract val type: QuestionType
    abstract val question: String

    @Parcelize
    data class TrueFalse(
        override val question: String,
    ) : Question() {
        @IgnoredOnParcel
        override val type: QuestionType = TRUE_FALSE
    }

    @Parcelize
    data class SingleChoice(
        override val question: String,
        val options: List<String>,
    ) : Question() {
        @IgnoredOnParcel
        override val type: QuestionType = SINGLE_CHOICE
    }

    @Parcelize
    data class MultipleChoice(
        override val question: String,
        val options: List<String>,
    ) : Question() {
        @IgnoredOnParcel
        override val type: QuestionType = MULTI_CHOICE
    }

    @Parcelize
    data class TextInput(
        override val question: String,
        val hint: String = "",
    ) : Question() {
        @IgnoredOnParcel
        override val type: QuestionType = TEXT_INPUT
    }

    fun defaultAnswer(): UserAnswer {
        return when (type) {
            TRUE_FALSE -> UserAnswer.TrueFalse()
            SINGLE_CHOICE -> UserAnswer.SingleChoice()
            MULTI_CHOICE -> UserAnswer.MultipleChoice()
            TEXT_INPUT -> UserAnswer.TextInput()
        }
    }
}
