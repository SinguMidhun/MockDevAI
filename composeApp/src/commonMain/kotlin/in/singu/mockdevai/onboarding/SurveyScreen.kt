package `in`.singu.mockdevai.onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.io.KmpFile
import `in`.singu.mockdevai.onboarding.eventhandler.OnBoardingEvents
import `in`.singu.mockdevai.onboarding.questionscomponent.FeaturePromoComposable
import `in`.singu.mockdevai.onboarding.questionscomponent.FileUploadQuestion
import `in`.singu.mockdevai.onboarding.questionscomponent.SingleChoiceQuestion
import `in`.singu.mockdevai.onboarding.questionscomponent.SurveyQuestionType
import `in`.singu.mockdevai.onboarding.questionscomponent.TextInputQuestion
import `in`.singu.mockdevai.onboarding.questionscomponent.questions
import kotlinx.coroutines.launch


@Composable
fun SurveyScreen(
    onEvent: (OnBoardingEvents) -> Unit,
) {

    val pagerState = rememberPagerState(0, pageCount = { questions.size })
    val answers = remember { mutableStateMapOf<Int, Any?>() }
    val totalQuestions = questions.size
    val currentQuestion = pagerState.currentPage + 1
    val scope = rememberCoroutineScope()

    val progress by animateFloatAsState(
        targetValue = currentQuestion.toFloat() / totalQuestions,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "Progress Animation"
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(top = 48.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (pagerState.currentPage > 0) {
                IconButton(onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage - 1,
                            animationSpec = tween(700)
                        )
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Go back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 48.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }

        val questionCounterText by remember(currentQuestion) {
            derivedStateOf { "Question $currentQuestion of $totalQuestions" }
        }
        Text(
            text = questionCounterText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) { page ->
            when (val question = questions[page]) {
                is SurveyQuestionType.SingleChoice -> {
                    SingleChoiceQuestion(
                        question = question.question,
                        buttonText = if (page == questions.size - 1) "Finish" else "Next",
                        isLastQuestion = page == questions.size - 1,
                        possibleAnswers = question.options,
                        selectedAnswer = answers[page].toString(),
                        onOptionSelected = { answer ->
                            answers[page] = answer
                            if (page < questions.size - 1) {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        page + 1,
                                        animationSpec = tween(700)
                                    )
                                }
                            }
                        },
                        enabled = answers[page] != null,
                        nextButtonClick = {
                            if (page < questions.size - 1) {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        page + 1,
                                        animationSpec = tween(700)
                                    )
                                }
                            } else {
                                onEvent(OnBoardingEvents.onboardingFinished)
                            }
                        }
                    )
                }

                is SurveyQuestionType.TextInput -> {
                    TextInputQuestion(
                        question = question.question,
                        buttonText = if (page == questions.size - 1) "Finish" else "Next",
                        answer = answers[page] as String?,
                        onAnswerChange = { answer ->
                            answers[page] = answer
                        },
                        nextButtonClick = {
                            if (page < questions.size - 1) {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        page + 1,
                                        animationSpec = tween(700)
                                    )
                                }
                            } else {
                                onEvent(OnBoardingEvents.onboardingFinished)
                            }
                        }
                    )
                }

                is SurveyQuestionType.FileUpload -> {
                    FileUploadQuestion(
                        question = question.question,
                        buttonText = if (page == questions.size - 1) "Finish" else "Next",
                        file = answers[page] as KmpFile?,
                        onFilePicked = { file ->
                            answers[page] = file
                        },
                        nextButtonClick = {
                            if (page < questions.size - 1) {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        page + 1,
                                        animationSpec = tween(700)
                                    )
                                }
                            } else {
                                onEvent(OnBoardingEvents.onboardingFinished)
                            }
                        }
                    )
                }

                is SurveyQuestionType.FeaturePromo -> {
                    FeaturePromoComposable(
                        title = question.title,
                        description = question.description,
                        stats = question.stats,
                        chartType = question.chartType,
                        buttonText = if (page == questions.size - 1) "Get Started" else "Next",
                        nextButtonClick = {
                            if (page < questions.size - 1) {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        page + 1,
                                        animationSpec = tween(700)
                                    )
                                }
                            } else {
                                onEvent(OnBoardingEvents.onboardingFinished)
                            }
                        }
                    )
                }
            }
        }
    }
}