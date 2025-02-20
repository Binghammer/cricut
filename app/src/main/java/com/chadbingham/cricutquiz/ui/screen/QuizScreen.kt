/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.cricutquiz.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chadbingham.cricutquiz.R
import com.chadbingham.cricutquiz.data.Question
import com.chadbingham.cricutquiz.data.QuestionType
import com.chadbingham.cricutquiz.data.UserAnswer
import com.chadbingham.cricutquiz.ui.composable.MultipleChoiceQuestion
import com.chadbingham.cricutquiz.ui.composable.QuestionTitle
import com.chadbingham.cricutquiz.ui.composable.SingleChoiceQuestion
import com.chadbingham.cricutquiz.ui.composable.TextInputQuestion
import com.chadbingham.cricutquiz.ui.composable.TrueFalseQuestion
import com.chadbingham.cricutquiz.ui.theme.CricutQuizTheme
import com.chadbingham.cricutquiz.ui.viewmodel.QuizState
import com.chadbingham.cricutquiz.util.PreviewQuizState

@Composable
fun QuizScreen(
    modifier: Modifier,
    quizState: QuizState,
    onNext: () -> Unit = {},
    onPrevious: () -> Unit = {},
    submitAnswer: (UserAnswer) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val question = quizState.getQuestion()
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            QuestionTitle(
                modifier = Modifier,
                question.question
            )

            when (question) {
                is Question.TrueFalse -> {
                    TrueFalseQuestion(
                        modifier = Modifier,
                        userAnswer = quizState.getAnswer(),
                        onAnswerSelected = submitAnswer
                    )
                }

                is Question.SingleChoice -> {
                    SingleChoiceQuestion(
                        modifier = Modifier,
                        question = question,
                        userAnswer = quizState.getAnswer(),
                        onAnswerSelected = submitAnswer
                    )
                }

                is Question.MultipleChoice -> {
                    MultipleChoiceQuestion(
                        modifier = Modifier,
                        question = question,
                        userAnswer = quizState.getAnswer(),
                        onAnswerSelected = submitAnswer
                    )
                }

                is Question.TextInput -> {
                    TextInputQuestion(
                        modifier = Modifier,
                        question = question,
                        userAnswer = quizState.getAnswer(),
                        onAnswerSelected = submitAnswer
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = if (quizState.currentIndex > 0)
                Arrangement.SpaceBetween else Arrangement.End
        ) {
            if (quizState.currentIndex > 0) {
                Button(onClick = { onPrevious() }) {
                    Text(stringResource(R.string.all_previous))
                }
            }

            Button(
                onClick = { onNext() },
                enabled = quizState.nextEnabled || question.type == QuestionType.TRUE_FALSE
            ) {
                Text(if(quizState.currentIndex == quizState.questions.size - 1) {
                    stringResource(R.string.all_submit)

                } else {
                    stringResource(R.string.all_next)
                })
            }
        }
    }
}

@Preview
@Composable
private fun QuizScreenPreview() {
    CricutQuizTheme {
        QuizScreen(Modifier, PreviewQuizState.getQuizState(3))
    }
}
