/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

@file:OptIn(SavedStateHandleSaveableApi::class)

package com.chadbingham.cricutquiz.ui.viewmodel

import android.app.Application
import android.net.http.SslCertificate.saveState
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import com.chadbingham.cricutquiz.data.Question
import com.chadbingham.cricutquiz.data.QuestionType
import com.chadbingham.cricutquiz.data.SudoQuestionRepo
import com.chadbingham.cricutquiz.data.UserAnswer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

class QuizViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    //normally injected
    private val fakeRepo: SudoQuestionRepo = SudoQuestionRepo(application)

    private val _quizState = MutableStateFlow(
        savedStateHandle.get<QuizState>("quizState") ?: QuizState(fakeRepo.getQuestions())
    )
    val quizState: StateFlow<QuizState> = _quizState

    private var state: QuizState
        get() = _quizState.value
        set(value) {
            _quizState.value = value
            savedStateHandle["quizState"] = _quizState.value
        }

    private val currentIndex: Int
        get() = state.currentIndex

    fun submitAnswer(answer: UserAnswer) {
        val updatedAnswers = state.userAnswers.toMutableMap().apply { this[currentIndex] = answer }

        state = state.copy(
            userAnswers = updatedAnswers,
            nextEnabled = answer.isValid
        )
    }

    fun nextQuestion() {
        if (state.nextEnabled) {
            state = if (currentIndex < state.questions.size - 1) {
                state.copy(currentIndex = currentIndex + 1)
            } else {
                state.copy(showSummary = true)
            }
        }
    }

    fun previousQuestion() {
        state = if (currentIndex > 0) {
            state.copy(currentIndex = currentIndex - 1)
        } else if (currentIndex == state.questions.size && state.showSummary) {
            state.copy(showSummary = false)
        } else {
            state //shouldn't happen
        }
    }
}

@Parcelize
data class QuizState(
    val questions: List<Question> = emptyList(),
    val userAnswers: Map<Int, UserAnswer> = emptyMap(),
    val currentIndex: Int = 0,
    val nextEnabled: Boolean = false,
    val showSummary: Boolean = false,
) : Parcelable {

    /**
     * This will return an answer even if there is currently not answer
     * available. It WILL NOT set a default answer if the UserAnswer is
     * missing from userAnswers
     */
    @Suppress("UNCHECKED_CAST")
    fun<T: UserAnswer> getAnswer(): T {
        return userAnswers.getOrDefault(currentIndex, defaultAnswer()) as T
    }

    private fun defaultAnswer(): UserAnswer {
        return when (questions[currentIndex].type) {
            QuestionType.TRUE_FALSE -> UserAnswer.TrueFalse()
            QuestionType.SINGLE_CHOICE -> UserAnswer.SingleChoice()
            QuestionType.MULTI_CHOICE -> UserAnswer.MultipleChoice()
            QuestionType.TEXT_INPUT -> UserAnswer.TextInput()
        }
    }
}