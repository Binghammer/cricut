package com.chadbingham.cricutquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.chadbingham.cricutquiz.ui.screen.QuizScreen
import com.chadbingham.cricutquiz.ui.theme.CricutQuizTheme
import com.chadbingham.cricutquiz.ui.viewmodel.QuizViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel: QuizViewModel by viewModels()

        setContent {
            val quizState by viewModel.quizState.collectAsState()
            CricutQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizScreen(
                        modifier = Modifier.padding(innerPadding),
                        quizState = quizState,
                        viewModel::nextQuestion,
                        viewModel::previousQuestion,
                        viewModel::submitAnswer
                    )
                }
            }
        }
    }
}
