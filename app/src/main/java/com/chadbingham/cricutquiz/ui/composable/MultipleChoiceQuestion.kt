/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.cricutquiz.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chadbingham.cricutquiz.data.Question
import com.chadbingham.cricutquiz.data.UserAnswer
import com.chadbingham.cricutquiz.ui.theme.CricutQuizTheme
import com.chadbingham.cricutquiz.util.PreviewQuizState

@Composable
fun MultipleChoiceQuestion(
    modifier: Modifier = Modifier,
    question: Question.MultipleChoice,
    userAnswer: UserAnswer.MultipleChoice,
    onAnswerSelected: (UserAnswer) -> Unit,
) {

    var answer by rememberSaveable { mutableStateOf(userAnswer) }

    Column(modifier = modifier) {
        question.options.forEachIndexed { index, option ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = answer.indices.contains(index),

                    onCheckedChange = { checked ->
                        val indices = answer.indices.toMutableSet()
                        if (checked) {
                            indices.add(index)
                        } else {
                            indices.remove(index)
                        }
                        answer = UserAnswer.MultipleChoice(indices)

                        onAnswerSelected(answer)
                    }
                )
                Text(text = option, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMultiChoice() {
    CricutQuizTheme {
        MultipleChoiceQuestion(
            modifier = Modifier,
            question = PreviewQuizState.getMultiChoiceQuestion(),
            userAnswer = UserAnswer.MultipleChoice()
        ) {}
    }
}