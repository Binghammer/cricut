/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.cricutquiz.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chadbingham.cricutquiz.data.Question
import com.chadbingham.cricutquiz.data.UserAnswer

@Composable
fun SingleChoiceQuestion(
    question: Question.SingleChoice,
    userAnswer: UserAnswer.SingleChoice,
    onAnswerSelected: (UserAnswer) -> Unit,
) {

    var answer by rememberSaveable { mutableStateOf(userAnswer) }

    Column {
        Text(text = question.question)
        question.options.forEachIndexed { index, option ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = answer.index == index,
                    onClick = {
                        answer = UserAnswer.SingleChoice(index)
                        onAnswerSelected(answer)
                    }
                )
                Text(text = option, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}