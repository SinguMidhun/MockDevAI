package `in`.singu.mockdevai.auth

import DotsIndicator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import `in`.singu.mockdevai.auth.eventhandler.AuthEvents
import `in`.singu.mockdevai.util.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.serializer
import mockdevai.composeapp.generated.resources.Res
import mockdevai.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

@Composable
fun AuthScreen(
    onEvent: (AuthEvents) -> Unit
) {

    val images = listOf(
        Res.drawable.compose_multiplatform,
        Res.drawable.compose_multiplatform,
        Res.drawable.compose_multiplatform
    )

    val titles = listOf(
        "Welcome to MockDevAI",
        "Smart Development Tools",
        "Boost Your Productivity"
    )

    val descriptions = listOf(
        "Your personal AI assistant for all development tasks",
        "Leverage AI to write better code, find bugs, and improve quality",
        "Save time and focus on what matters most"
    )

    val pagerState = rememberPagerState(0, pageCount = { images.size })
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var currentPage by remember { mutableStateOf(0) }

    val firebaseDatabase = Network.getFirebaseDatabase()

    val onFirebaseResult: (Result<FirebaseUser?>) -> Unit = { result ->
        if (result.isSuccess) {
            result.getOrNull()?.let { it ->
                val uid = it.uid
                scope.launch {
                    firebaseDatabase.reference().child(uid).child("onBoarded")
                        .setValue(Boolean.serializer(), true){
                            this.encodeDefaults = encodeDefaults
                        }
                }
            }
        } else {
            println("Error Result: ${result.exceptionOrNull()?.message}")
        }

    }

    val isLoggedin by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(3000)
            if (currentPage < images.size - 1) {
                currentPage++
            } else {
                currentPage = 0
            }
            pagerState.animateScrollToPage(
                page = currentPage,
                animationSpec = tween(600)
            )
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.weight(1f)
                    ) { page ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Image(
                                    painter = painterResource(images[page]),
                                    contentDescription = "Onboarding image ${page + 1}",
                                    modifier = Modifier,
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = titles[page],
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = descriptions[page],
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    DotsIndicator(
                        totalDots = images.size,
                        selectedIndex = pagerState.currentPage,
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unSelectedColor = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 48.dp),
                    contentAlignment = Alignment.Center
                ) {



                    GoogleButtonUiContainerFirebase(onResult = onFirebaseResult, linkAccount = false, filterByAuthorizedAccounts = true) {
                        Button(
                            onClick = {
                                this.onClick()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 8.dp,
                                pressedElevation = 12.dp
                            )
                        ) {
                            val auth = Network.getFirebaseAuth()
                            val lable = if(auth.currentUser!=null){
                                "Already done"
                            } else {
                                "Get Started"
                            }

                            Text(
                                text = lable,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }

                }
            }
        }
    }

}