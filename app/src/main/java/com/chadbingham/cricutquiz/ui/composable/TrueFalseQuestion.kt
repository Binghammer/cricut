/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.cricutquiz.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chadbingham.cricutquiz.data.UserAnswer
import com.chadbingham.cricutquiz.ui.theme.CricutQuizTheme

@Composable
fun TrueFalseQuestion(
    modifier: Modifier = Modifier,
    userAnswer: UserAnswer.TrueFalse,
    onAnswerSelected: (UserAnswer) -> Unit,
) {

    var answer by rememberSaveable { mutableStateOf(userAnswer) }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Switch(
            checked = answer.answer,
            onCheckedChange = { checked ->
                answer = UserAnswer.TrueFalse(checked)
                onAnswerSelected(answer)
            },
            thumbContent = {
                if (answer.answer) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTrueFalse() {
    CricutQuizTheme {
        TrueFalseQuestion(
            modifier = Modifier,
            userAnswer = UserAnswer.TrueFalse()
        ) { }
    }
}