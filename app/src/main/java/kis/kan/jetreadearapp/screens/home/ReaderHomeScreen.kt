package kis.kan.jetreadearapp.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.vector.ImageVector
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
import kis.kan.jetreadearapp.components.ListCard
import kis.kan.jetreadearapp.components.ReaderAppBar
import kis.kan.jetreadearapp.components.TitleSection
import kis.kan.jetreadearapp.model.MBook
import kis.kan.jetreadearapp.navigation.ReaderScreens


@Composable
fun ReaderHomeScreen(navController: NavController) {
    Scaffold(topBar = {
        ReaderAppBar(title = "Reader home screen", navController = navController)
    },
        floatingActionButton = {
            FABContent {
                navController.navigate(ReaderScreens.SearchScreen.name)
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




@Preview
@Composable
fun HomeContent(navController: NavController = NavController(LocalContext.current)) {

    val listOfBooks = listOf(
        MBook(id = "dakd2j", title = "Hello Again", authors = "All of us", notes = null),
        MBook(id = "dak3dj", title = "Hello Again1", authors = "All of us", notes = null),
        MBook(id = "dakd4j", title = "Hello Again2", authors = "All of us", notes = null),
        MBook(id = "dakd5j", title = "Hello Again3", authors = "All of us", notes = null),
        MBook(id = "dakdj", title = "Hello Again4", authors = "All of us", notes = null),
    )

    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (email?.isNotEmpty() == true)
        FirebaseAuth.getInstance().currentUser?.email.toString().split("@").get(0)
    else "N/A"


    Column(
        Modifier.padding(2.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(modifier = Modifier.align(Alignment.Start)) {

            TitleSection(lable = "Your reading \n " + " activity right now")


            Spacer(modifier = Modifier.fillMaxWidth(0.7f))

            Column {
                Icon(Icons.Default.AccountCircle, contentDescription = "Account Icon",
                    modifier = Modifier
                        .clickable {
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

        ReadingRightNowArea(
            books = listOf(),
            navController = navController
        )

        TitleSection(lable = "Reading list")

        BookListArea(listOfBooks = listOfBooks, navController = navController)

    }
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavController) {

    HorizontalScrollableComponent(listOfBooks) {
        Log.d("tagForCheck", "clicked $it")
        //TODO: on click go to details
    }

}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<MBook>, onCardPressed: (String) -> Unit) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier.fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState)
    ) {
        for (book in listOfBooks) {
            ListCard(book) {
                onCardPressed(it)
            }
        }
    }
}


@Composable
fun ReadingRightNowArea(books: List<MBook>,
                        navController: NavController) {
    ListCard()

}







