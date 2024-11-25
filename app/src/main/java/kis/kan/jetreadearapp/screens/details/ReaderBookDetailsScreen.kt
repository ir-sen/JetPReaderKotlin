package kis.kan.jetreadearapp.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kis.kan.jetreadearapp.components.ReaderAppBar
import kis.kan.jetreadearapp.data.Resource
import kis.kan.jetreadearapp.model.Item
import kis.kan.jetreadearapp.navigation.ReaderScreens

@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: DetailsScreenViewModel = hiltViewModel()
    ) {

    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "BookDerails",
                icon = Icons.Default.ArrowBack,
                navController =navController,

            ) {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }

        }

    ) { pad ->
        pad

        Surface(modifier = Modifier
            .padding(pad)
            .fillMaxSize()) {

            Column(modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {


                val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
                    value = viewModel.getBookInfo(bookId)
                }.value
                if (bookInfo.data == null) {
                    Row() {
                        LinearProgressIndicator()
                        Text(text = "Loading...")
                    }
                } else {
                    Text("This is book title: ${bookInfo.data?.volumeInfo?.title}")
                }


            }

        }

    }

}