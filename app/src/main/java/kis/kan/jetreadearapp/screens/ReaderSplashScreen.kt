package kis.kan.jetreadearapp.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kis.kan.jetreadearapp.components.ReaderLogo
import kis.kan.jetreadearapp.navigation.ReaderScreens
import kotlinx.coroutines.delay


@Composable
fun ReaderSplashScreen(navController: NavController) {

    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        // Launches an animation coroutine when this composable enters the composition
        scale.animateTo(
            targetValue = 0.9f, // The target scale value to animate to
            animationSpec = tween( // Defines the animation properties
                durationMillis = 800, // The duration of the animation in milliseconds
                easing = {
                    // Custom easing function to modify the rate of change
                    OvershootInterpolator(8f) // Creates an effect that overshoots slightly before settling
                        .getInterpolation(it) // Converts the animation progress (0.0 to 1.0) into an interpolated value
                }
            )
        )

        delay(2000L) // Waits for 2 seconds

        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navController.navigate(ReaderScreens.LoginScreen.name)
        } else {
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }

//        navController.navigate(ReaderScreens.LoginScreen.name)
    }


    Surface(
        modifier = Modifier
            .padding(15.dp)
            .scale(scale.value)
            .size(330.dp),

        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(
            width = 2.dp,
            color = Color.LightGray
        )
    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            ReaderLogo()
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "\"Read . Change. Yourself\"",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.LightGray
            )

        }
    }
}

