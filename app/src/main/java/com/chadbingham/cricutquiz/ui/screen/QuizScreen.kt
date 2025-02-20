/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.cricutquiz.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.chadbingham.cricutquiz.data.QuestionType.TRUE_FALSE
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
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    submitAnswer: (UserAnswer) -> Unit,
    startOver: () -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (quizState.currentIndex != quizState.questionsAndAnswers.size) {
                QuestionTitle(
                    modifier = Modifier,
                    quizState.currentQuestion.question
                )
            }

            AnimatedContent(
                targetState = quizState.currentIndex,
                transitionSpec = {
                    if (targetState == quizState.questionsAndAnswers.size) {
                        slideInVertically { height -> height } + fadeIn() togetherWith
                                slideOutVertically { height -> -height } + fadeOut()
                    } else if (initialState == quizState.questionsAndAnswers.size && targetState < initialState) {
                        slideInVertically { height -> -height } + fadeIn() togetherWith
                                slideOutVertically { height -> height } + fadeOut()
                    } else if (targetState > initialState) {
                        slideInHorizontally { height -> height } + fadeIn() togetherWith
                                slideOutHorizontally { height -> -height } + fadeOut()
                    } else {
                        slideInHorizontally { height -> -height } + fadeIn() togetherWith
                                slideOutHorizontally { height -> height } + fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )

                }, label = "quiz"
            ) { index ->
                if (index >= quizState.questionsAndAnswers.size) {
                    SummaryScreen() {
                        startOver()
                    }
                } else {
                    val qAnda = quizState.getQuestionAndAnswer(index)
                    val question = qAnda.first
                    val answer: UserAnswer = qAnda.second
                    when (question) {
                        is Question.TrueFalse -> {
                            TrueFalseQuestion(
                                modifier = Modifier,
                                userAnswer = answer as UserAnswer.TrueFalse,
                                onAnswerSelected = submitAnswer
                            )
                        }

                        is Question.SingleChoice -> {
                            SingleChoiceQuestion(
                                modifier = Modifier,
                                question = question,
                                userAnswer = answer as UserAnswer.SingleChoice,
                                onAnswerSelected = submitAnswer
                            )
                        }

                        is Question.MultipleChoice -> {
                            MultipleChoiceQuestion(
                                modifier = Modifier,
                                question = question,
                                userAnswer = answer as UserAnswer.MultipleChoice,
                                onAnswerSelected = submitAnswer
                            )
                        }

                        is Question.TextInput -> {
                            TextInputQuestion(
                                modifier = Modifier,
                                question = question,
                                userAnswer = answer as UserAnswer.TextInput,
                                onAnswerSelected = submitAnswer
                            )
                        }
                    }

                }
            }
        }
        if (quizState.currentIndex != quizState.questionsAndAnswers.size) {
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
                    enabled = quizState.nextEnabled || quizState.currentQuestion.type == TRUE_FALSE
                ) {
                    Text(
                        if (quizState.currentIndex == quizState.questionsAndAnswers.size - 1) {
                            stringResource(R.string.all_submit)
                        } else {
                            stringResource(R.string.all_next)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun QuizScreenPreview() {
    CricutQuizTheme {
        QuizScreen(
            Modifier, PreviewQuizState.getQuizState(3),
            onNext = { },
            onPrevious = { },
            submitAnswer = { },
            startOver = { }
        )
    }
}
