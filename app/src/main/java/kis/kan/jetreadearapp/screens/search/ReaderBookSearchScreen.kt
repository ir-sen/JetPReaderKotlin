package kis.kan.jetreadearapp.screens.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import kis.kan.jetreadearapp.components.InputField
import kis.kan.jetreadearapp.components.ReaderAppBar
import kis.kan.jetreadearapp.model.Item
import kis.kan.jetreadearapp.navigation.ReaderScreens


private val TAG = "ReaderBookSearchScreenTAG"

@Composable
fun ReaderBookSearchScreen(
    navController: NavController,
    viewModel: SearchViewModelVersion2 = hiltViewModel()
) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Search books",
            navController = navController,
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            showProfile = false,
        ) {
            // if we click on button back
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }) { paddingValues ->
        paddingValues
        Column(modifier = Modifier.padding(paddingValues)) {
            SearchForm(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                viewModel = viewModel
            ) { query ->

                viewModel.searchBooks(query)

                Log.d(TAG, "SearchForm: ${viewModel.list}")
            }

            Spacer(modifier = Modifier.height(13.dp))
            BookList(navController, viewModel)

        }


    }
}

@Composable
fun BookList(navController: NavController, viewModel: SearchViewModelVersion2 = hiltViewModel()) {
    val listOfBooks = viewModel.list

    if (viewModel.loading) {
        Row(modifier = Modifier.padding(end = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ) {
            LinearProgressIndicator()
            Text(text = "Loading...")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(items = listOfBooks) { book ->
                BookRow(book, navController)
            }


        }
    }


//        listOf(
//        MBook(id = "dakd2j", title = "Hello Again", authors = "All of us", notes = null),
//        MBook(id = "dak3dj", title = "Hello Again1", authors = "All of us", notes = null),
//        MBook(id = "dakd4j", title = "Hello Again2", authors = "All of us", notes = null),
//        MBook(id = "dakd5j", title = "Hello Again3", authors = "All of us", notes = null),
//        MBook(id = "dakdj", title = "Hello Again4", authors = "All of us", notes = null),
//    )



}

@Composable
fun BookRow(
    book: Item,
    navController: NavController
) {

    Card(
        modifier = Modifier
            .clickable {
                navController.navigate(ReaderScreens.DetailsScreen.name + "/${book.id}")

            }
            .fillMaxWidth()
            .height(130.dp)
            .padding(8.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {


//            val urlImage: String = book.volumeInfo.imageLinks.smallThumbnail.ifEmpty { "http://books.google.com/books/content?id=f_DuEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api" }


            val urlImage: String = if (book.volumeInfo.imageLinks?.smallThumbnail.isNullOrEmpty()) {
                "https://books.google.com/books/content?id=f_DuEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            } else {
                book.volumeInfo.imageLinks!!.smallThumbnail
            }


//            val urlImage: String = book.volumeInfo.imageLinks.smallThumbnail ?: "http://books.google.com/books/content?id=f_DuEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"


            Image(
                painter = rememberAsyncImagePainter(model = urlImage),
                contentDescription = "book image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp)
            )


            Column() {
                Text(text = book.volumeInfo.title, overflow = TextOverflow.Ellipsis)
                Text(
                    text = "Author: ${book.volumeInfo.authors}", overflow = TextOverflow.Ellipsis,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "${book.volumeInfo.categories}", overflow = TextOverflow.Ellipsis,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.titleMedium
                )



            }

        }
    }
}

@Composable
fun SearchForm(
    modifier: Modifier,
    viewModel: SearchViewModelVersion2,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {},
) {
    Column() {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }



        InputField(valueState = searchQueryState, labelId = "Search", enabled = true,
            onAction = KeyboardActions {

                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            })

    }

}