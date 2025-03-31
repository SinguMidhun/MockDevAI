package `in`.singu.mockdevai.onboarding.questionscomponent

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

enum class ChartType {
    GROWTH_LINE,
    CONFIDENCE_BAR,
    SKILL_COMPARISON_BAR,
    PROGRESS_CIRCLE
}

@Composable
fun FeaturePromoComposable(
    title: String,
    description: String,
    stats: List<Pair<String, Float>>,
    chartType: ChartType,
    buttonText: String,
    nextButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        animationPlayed = true
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(1500, easing = FastOutSlowInEasing)
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Title
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))


        stats.forEach { (label, percentage) ->
            StatProgressBar(
                label = label,
                targetPercentage = percentage,
                progress = animatedProgress.value
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                .padding(16.dp)
        ) {
            when (chartType) {
                ChartType.GROWTH_LINE -> GrowthLineChart(progress = animatedProgress.value)
                ChartType.CONFIDENCE_BAR -> ConfidenceBarChart(progress = animatedProgress.value)
                ChartType.SKILL_COMPARISON_BAR -> SkillComparisonBarChart(progress = animatedProgress.value)
                ChartType.PROGRESS_CIRCLE -> ProgressCircleChart(progress = animatedProgress.value)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = nextButtonClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = buttonText)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun StatProgressBar(
    label: String,
    targetPercentage: Float,
    progress: Float
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${(targetPercentage * 100).roundToInt()} %",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        LinearProgressIndicator(
            progress = { targetPercentage * progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
private fun GrowthLineChart(progress: Float) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val errorColor = MaterialTheme.colorScheme.error

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val pathWithoutApp = Path().apply {
                moveTo(0f, height * 0.9f)

                cubicTo(
                    width * 0.2f, height * 0.7f,
                    width * 0.25f, height * 0.6f,
                    width * 0.3f, height * 0.65f
                )
                cubicTo(
                    width * 0.35f, height * 0.7f,
                    width * 0.4f, height * 0.8f,
                    width * 0.45f, height * 0.75f
                )
                cubicTo(
                    width * 0.5f, height * 0.7f,
                    width * 0.55f, height * 0.5f,
                    width * 0.6f, height * 0.63f
                )
                cubicTo(
                    width * 0.65f, height * 0.75f,
                    width * 0.7f, height * 0.8f,
                    width, height * 0.8f
                )
            }
            

            val pathWithApp = Path().apply {
                moveTo(0f, height * 0.9f)
                

                cubicTo(
                    width * 0.15f, height * 0.85f,
                    width * 0.25f, height * 0.7f,
                    width * 0.3f, height * 0.6f
                )
                cubicTo(
                    width * 0.35f, height * 0.5f,
                    width * 0.45f, height * 0.25f,
                    width * 0.6f, height * 0.2f
                )

                cubicTo(
                    width * 0.7f, height * 0.17f,
                    width * 0.85f, height * 0.15f,
                    width, height * 0.15f
                )
            }

            drawPath(
                path = pathWithoutApp,
                color = errorColor.copy(alpha = 0.7f),
                style = Stroke(
                    width = 3.dp.toPx(),
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.cornerPathEffect(32.dp.toPx())
                )
            )

            drawPath(
                path = pathWithApp,
                color = primaryColor,
                style = Stroke(
                    width = 4.dp.toPx(),
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.cornerPathEffect(32.dp.toPx())
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Canvas(modifier = Modifier.size(12.dp)) {
                    drawLine(
                        color = errorColor.copy(alpha = 0.7f),
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                        strokeWidth = 3.dp.toPx()
                    )
                }
                Text(
                    text = "Without App",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Canvas(modifier = Modifier.size(12.dp)) {
                    drawLine(
                        color = primaryColor,
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                        strokeWidth = 4.dp.toPx()
                    )
                }
                Text(
                    text = "With App",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ConfidenceBarChart(progress: Float) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)) {
            val width = size.width
            val height = size.height
            val barWidth = width * 0.25f
            val spacing = width * 0.35f
            val cornerRadius = 8.dp.toPx()

            drawRoundRect(
                color = secondaryColor.copy(alpha = 0.5f),
                topLeft = Offset(spacing - barWidth/2, height * 0.7f),
                size = Size(barWidth, height * 0.3f * progress),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )

            drawRoundRect(
                color = primaryColor,
                topLeft = Offset(width - spacing - barWidth/2, height * 0.3f),
                size = Size(barWidth, height * 0.7f * progress),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp)
                    .width(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Before",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "40%",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Column(
                modifier = Modifier
                    .padding(end = 8.dp, top = 8.dp)
                    .width(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "After",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "85%",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun SkillComparisonBarChart(progress: Float) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val surafceColor = MaterialTheme.colorScheme.surfaceVariant

    Box(modifier = Modifier.fillMaxSize()) {
        val skills = listOf(
            "Problem Solving" to 0.9f,
            "Code Quality" to 0.85f,
            "System Design" to 0.8f
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val barHeight = height * 0.15f
            val spacing = height * 0.25f

            skills.forEachIndexed { index, (_, percentage) ->
                val y = spacing + (index * spacing)

                // Background bar
                drawRect(
                    color = surafceColor,
                    topLeft = Offset(0f, y),
                    size = Size(width * 0.7f, barHeight)  // Reduced width to make room for labels
                )

                // Progress bar
                drawRect(
                    color = if (index % 2 == 0) primaryColor else tertiaryColor,
                    topLeft = Offset(0f, y),
                    size = Size(width * 0.7f * percentage * progress, barHeight)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),// Position labels after the bars
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            skills.forEach { (skill, percentage) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = skill,
                        color = Color.White,
                        fontSize = MaterialTheme.typography.labelSmall.fontSize
                    )
                    Text(
                        text = "${(percentage * 100).roundToInt()}%",
                        color = Color.White,
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ProgressCircleChart(progress: Float) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val surfaceColor = MaterialTheme.colorScheme.surfaceVariant
    
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = minOf(size.width, size.height) * 0.4f

            drawCircle(
                color = surfaceColor,
                radius = radius,
                center = center
            )

            val startAngle = -90f
            val sweepAngle = 360f * progress
            
            drawArc(
                color = primaryColor,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 8.dp.toPx()),
                size = Size(radius * 2, radius * 2),
                topLeft = Offset(center.x - radius, center.y - radius)
            )
        }

        Text(
            text = "${(progress * 100).roundToInt()}%",
            color = Color.White,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}