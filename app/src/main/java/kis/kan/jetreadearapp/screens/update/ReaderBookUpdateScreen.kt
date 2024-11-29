package kis.kan.jetreadearapp.screens.update

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.google.firebase.Timestamp
import kis.kan.jetreadearapp.components.InputField
import kis.kan.jetreadearapp.components.RatingBarOwn
import kis.kan.jetreadearapp.components.ReaderAppBar
import kis.kan.jetreadearapp.components.RoundedButton
import kis.kan.jetreadearapp.data.DataOrException
import kis.kan.jetreadearapp.model.MBook
import kis.kan.jetreadearapp.screens.home.HomeScreenViewModel

@Composable
fun BookUpdateScreen(
    navController: NavHostController,
    bookItemId: String,
    viewModelThis: HomeScreenViewModel = hiltViewModel(),
) {

    Scaffold(topBar = {
        ReaderAppBar(
            title = "Update Book",
            navController = navController,
            showProfile = false,
            icon = Icons.AutoMirrored.Filled.ArrowBack,

            ) {
            navController.popBackStack()
        }
    }
    ) { padd ->
        padd
        val bookInfo = produceState<DataOrException<List<MBook>,
                Boolean,
                Exception
                >>(initialValue = DataOrException(data = emptyList(), true, Exception(""))) {

            value = viewModelThis.data.value
        }.value

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padd)
        ) {

            Column(
                modifier = Modifier.padding(top = 3.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Log.d("INFO", "BookUpdateInfo ${viewModelThis.data.value.data.toString()}")
                if (bookInfo.loading == true) {
                    LinearProgressIndicator()
                    bookInfo.loading = false
                } else {
                    Text(text = viewModelThis.data.value.data?.get(0)?.title.toString())

                    Surface(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth(),
                        shape = CircleShape,
                        shadowElevation = 4.dp,
                    ) {
                        ShowBookUpdate(bookInfo = viewModelThis.data.value, bookItemId = bookItemId)

                    }
                    // get first book and check if this book right
                    ShowSimpleForm(book = viewModelThis.data.value.data?.first { mBook ->
                        mBook.googleBookId == bookItemId
                    }!!, navController)

                }

            }
        }


    }

}

// this is get book and show it
@Composable
fun ShowSimpleForm(book: MBook, navController: NavHostController) {

    val notesText = remember {
        mutableStateOf("")
    }
    // change this state if we click to text
    val isStartedReading = remember {
        mutableStateOf(false)
    }

    val isFinishedReading = remember {
        mutableStateOf(false)
    }

    val ratingVal = remember {
        mutableStateOf(0)
    }


    // check if we have notes and sent it in default value
    SimpleForm(
        defaultValue = if (book.notes.toString().isNotEmpty()) book.notes.toString()
        else "No thoughts yet",
    ) { note ->
        notesText.value = note // set notes to value
    }

    Row(
        modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextButton(onClick = {
            isStartedReading.value = true

        }, enabled = book.startedReading == null) {
            // this for start reading
            if (book.startedReading == null) {

                if (!isStartedReading.value) {
                    Text(text = "Start Reading")
                } else {
                    Text(
                        text = "Started Reading!",
                        modifier = Modifier.alpha(0.6f),
                        color = Color.Red.copy(alpha = 0.5f)
                    )
                }
            } else {
                Text("Started on: ${book.startedReading}")
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        TextButton(onClick = {
            isFinishedReading.value = true

        }, enabled = book.finishReading == null) {
            if (book.finishReading == null) {
                if (!isFinishedReading.value) {
                    Text(text = "Mark as Read")
                } else {
                    Text(text = "Finish Reading!")
                }

            } else {
                Text(text = "Finished on: ${book.finishReading}")
            }
        }


    }

    Text(text = "Rating", modifier = Modifier.padding(bottom = 3.dp))

    book.rating?.toInt().let {
//        RatingBarOwn
        RatingBarOwn(rating = it!!) { rating ->
            ratingVal.value = rating
        }
    }

    Spacer(modifier = Modifier.padding(15.dp))

    val changeNotes = book.notes != notesText.value
    val changeRating = book.rating?.toInt() != ratingVal.value

    val isFinishedTimeStamp = if (isFinishedReading.value) Timestamp.now()
    else book.finishReading

    val isStartedTimeStamp = if (isStartedReading.value) Timestamp.now()
    else book.startedReading

    val bookUpdate = changeNotes ||
            changeRating ||
            isStartedReading.value ||
            isFinishedReading.value


    val bookToUpdate = hashMapOf(
        "finished_reading_at" to isFinishedTimeStamp,
        "started_reading at" to  isStartedReading,
        "rating" to ratingVal.value,
        "notes" to notesText.value,
    ).toMap()


    Row {
        RoundedButton(
            label = "Update"
        ) {


        }

        Spacer(modifier = Modifier.width(100.dp))

        RoundedButton(
            "Delete"
        ) {

        }


    }


}

@Composable
fun SimpleForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    defaultValue: String = "Great Book!",
    onSearch: (String) -> Unit,
) {

    Column() {
        val textFieldValue = rememberSaveable {
            mutableStateOf(defaultValue)
        }
        val keyboardController = LocalSoftwareKeyboardController.current

        val valid = remember(textFieldValue.value) {
            textFieldValue.value.trim().isNotEmpty()
        }
        // input text field
        InputField(
            valueState = textFieldValue,
            labelId = "Enter your thoughts",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(textFieldValue.value.trim())
                keyboardController?.hide()
            }
        )
    }

}

@Composable
fun ShowBookUpdate(
    bookInfo: DataOrException<List<MBook>, Boolean, Exception>,
    bookItemId: String,
) {

    Surface(modifier = Modifier.padding(43.dp)) {

        if (bookInfo?.data != null) {
            Column(
                modifier = Modifier.padding(4.dp),
                verticalArrangement = Arrangement.Center
            ) {

                CardListItem(book = bookInfo.data!!.first { mBook ->
                    mBook.googleBookId == bookItemId

                }, onPressDetails = {})
            }
        }

    }


    //

}

@Composable
fun CardListItem(book: MBook, onPressDetails: () -> Unit) {

    Card(modifier = Modifier
        .padding(
            start = 4.dp,
            end = 4.dp,
            top = 4.dp,
            bottom = 8.dp
        )
        .clip(RoundedCornerShape(20.dp))

        .clickable {

        }
    ) {

        Row(horizontalArrangement = Arrangement.Start) {
            Image(
                painter = rememberAsyncImagePainter(model = book.photoUrl),
                contentDescription = "Photo image",
                modifier = Modifier
                    .height(100.dp)
                    .width(120.dp)
                    .padding(4.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 120.dp,
                            topEnd = 20.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    )
            )

            Column {
                Text(
                    text = book.title.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .width(120.dp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = book.publishedDate.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 0.dp,
                        bottom = 0.dp
                    )
                )

            }

        }


    }
}
