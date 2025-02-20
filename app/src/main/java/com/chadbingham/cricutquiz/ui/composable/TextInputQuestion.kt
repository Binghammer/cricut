/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.cricutquiz.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
fun TextInputQuestion(
    modifier: Modifier = Modifier,
    question: Question.TextInput,
    userAnswer: UserAnswer.TextInput,
    onAnswerSelected: (UserAnswer) -> Unit,
) {

    var answer by rememberSaveable { mutableStateOf(userAnswer) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = answer.text,

            onValueChange = {
                answer = UserAnswer.TextInput(it)
                onAnswerSelected(answer)
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            placeholder = {
                Text(question.hint)
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTextInput() {
    CricutQuizTheme {
        TextInputQuestion(
            modifier = Modifier,
            question = PreviewQuizState.getUserInputQuestion(),
            userAnswer = UserAnswer.TextInput()
        ) { }
    }

}