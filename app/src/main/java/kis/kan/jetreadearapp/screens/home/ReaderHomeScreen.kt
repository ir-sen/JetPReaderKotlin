package kis.kan.jetreadearapp.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kis.kan.jetreadearapp.components.FABContent
import kis.kan.jetreadearapp.components.TitleSection
import kis.kan.jetreadearapp.navigation.ReaderScreens


@Composable
fun ReaderHomeScreen(navController: NavController) {
    Scaffold(topBar = {
        ReaderAppBar(title = "Reader home screen", navController = navController)
    },
        floatingActionButton = {
            FABContent {

            }
        }) { paddingValue ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            HomeContent(navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderAppBar(
    title: String,
    showProfile: Boolean = true,
    navController: NavController,
) {

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showProfile) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Icons Bar title",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .scale(0.9f),

                        )
                }
                Text(
                    text = title,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold, fontSize = 20.sp
                    ),
                )
                Spacer(modifier = Modifier.width(150.dp))

            }
        },
        actions = {
                  IconButton(onClick = {
                      FirebaseAuth.getInstance().signOut().run {
                          navController.navigate(ReaderScreens.LoginScreen.name)
                      }
                  }) {
                      Icon(
                          imageVector = Icons.Default.ExitToApp,
                          contentDescription = "logout icon",
                          tint = Color.Green.copy(alpha = 0.4f)
                          )


                  }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
    )
}





@Preview
@Composable
fun HomeContent(navController: NavController = NavController(LocalContext.current)) {

    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (email?.isNotEmpty() == true)
        FirebaseAuth.getInstance().currentUser?.email.toString().split("@").get(0)
        else "N/A"


    Column(Modifier.padding(2.dp),
        verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.align(Alignment.Start), ){
            
            TitleSection(lable = "Your reading \n " + " activity right now")
            
            
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))

            Column {
                Icon(Icons.Default.AccountCircle, contentDescription = "Account Icon",
                    modifier = Modifier
                        .clickable{
                            navController.navigate(ReaderScreens.ReaderStatsScreen.name)
                        }
                )

                Text(
                    text = currentUserName,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Red.copy(alpha = 0.5f),
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                     )
                HorizontalDivider()

            }

        }

    }
}



