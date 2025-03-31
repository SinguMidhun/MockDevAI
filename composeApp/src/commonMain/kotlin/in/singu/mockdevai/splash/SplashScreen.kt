package `in`.singu.mockdevai.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.database.DatabaseReference
import `in`.singu.mockdevai.components.TypewriterText
import `in`.singu.mockdevai.splash.eventhandler.SplashEvent
import `in`.singu.mockdevai.util.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer

@Composable
fun SplashScreen(
    onEvent: (SplashEvent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Surface(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(16.dp)),
                color = Color.White
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "MD",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))

            TypewriterText(
                text = "MockDev AI",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ){
                delay(250)
                checkForAuthStatus {
                    println("onBoarding event listner value is ${it.toString()}")
                    onEvent(SplashEvent.onVerificationSuccess)
                }
            }
        }
    }
}

suspend fun checkForAuthStatus(event: (SplashEvent)->Unit) {
    val firebaseAuth = Network.getFirebaseAuth()
    if(firebaseAuth.currentUser==null) event(SplashEvent.authPending)
//    firebaseAuth.currentUser?.let {
//        val uid = it.uid
//        val realtimeDB = Network.getFirebaseDatabase()
//        val valueexists = realtimeDB.reference().child(uid).child("onBoarded")
//            .runTransaction(Boolean.serializer()){
//                return@runTransaction it
//            }.exists
//        if(valueexists) {
//            val actualValue = realtimeDB.reference().child(uid).child("onBoarded").runTransaction(Boolean.serializer()){
//                it
//            }.value as Boolean
//            if(actualValue) event(SplashEvent.onVerificationSuccess)
//            else event(SplashEvent.onBoardingPending)
//        } else {
//            event(SplashEvent.onBoardingPending)
//        }
//    }
    event(SplashEvent.onVerificationSuccess)
}

