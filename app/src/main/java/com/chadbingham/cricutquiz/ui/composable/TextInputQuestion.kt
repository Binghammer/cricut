/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.cricutquiz.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
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
fun TextInputQuestion(
    question: Question.TextInput,
    userAnswer: UserAnswer.TextInput,
    onAnswerSelected: (UserAnswer) -> Unit,
) {

    var answer by rememberSaveable { mutableStateOf(userAnswer) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = question.question)
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            value = answer.text,
            onValueChange = {
                answer = UserAnswer.TextInput(it)
                onAnswerSelected(userAnswer)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}