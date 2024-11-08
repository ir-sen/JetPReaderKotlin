package kis.kan.jetreadearapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import kis.kan.jetreadearapp.model.MBook
import kis.kan.jetreadearapp.screens.login.PasswordVisibility

@Composable
fun ReaderLogo(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(bottom = 16.dp),
        text = "A. Reader", style = MaterialTheme.typography.headlineMedium,
        color = Color.Red.copy(alpha = 0.5f)
    )

}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = "Email",
    enable: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
) {
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enable,
        keyBoardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction,
    )

}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyBoardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,

            ),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),

        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyBoardType,
            imeAction = imeAction,
        ),
        keyboardActions = onAction,


        )

}


@Composable
fun PsswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enable: Boolean,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
) {
    val visualTransformation =
        if (passwordVisibility.value) VisualTransformation.None
        else PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = { Text(text = labelId) },
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction,
        ),
        enabled = enable,
        visualTransformation = visualTransformation,
        trailingIcon = { PasswordVisibility(passwordVisibility = passwordVisibility) },
        keyboardActions = onAction,

        )
}


@Composable
fun TitleSection(modifier: Modifier = Modifier, lable: String) {
    Surface(
        modifier = modifier
            .padding(start = 5.dp, top = 1.dp),
    ) {
        Column() {
            Text(
                text = lable,
                fontSize = 19.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left,
            )
        }

    }
}

@Composable
fun ReadingRightNowArea(
    books: List<MBook>,
    navController: NavController,
) {

}


@Composable
fun FABContent(onTap: (String) -> Unit) {

    FloatingActionButton(
        onClick = { onTap("") },
        shape = RoundedCornerShape(50.dp),
        containerColor = Color(0xFF92CBDF)

    ) {
        Icon(
            imageVector = Icons.Default.Add, contentDescription = "Add button ",
            tint = Color.White
        )
    }

}

@Composable
fun BookRating(score: Double = 4.5) {

    Surface(
        modifier = Modifier
            .height(70.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(56.dp),
        shadowElevation = 6.dp,
        color = Color.White,

        ) {

        Column(modifier = Modifier.padding(4.dp)) {
            Image(
                imageVector = Icons.Filled.Star, contentDescription = "start icon",
                modifier = Modifier.padding(3.dp),
            )

            Text(text = score.toString(), style = MaterialTheme.typography.titleMedium)

        }

    }

}

@Composable
fun ListCard(
    book: MBook = MBook(
        "asd13",
        "RunForLife",
        "LotusCloser",
        "this is importent",
    ),
    onPressedDetails: (String) -> Unit = {},
) {

    val context = LocalContext.current
    val resource = context.resources

    val displayMetrics = resource.displayMetrics

    val screenWidth = displayMetrics.widthPixels / displayMetrics.density

    val spacer = 10.dp

    val testImageUrl = "http://books.google.com/books/content?id=f_DuEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"



    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(16.dp)
            .size(width = 240.dp, height = 240.dp)
            .clickable {
                onPressedDetails.invoke(book.title.toString())
            }
    ) {
        Box( // Wrap the content in a Box to control positioning
            modifier = Modifier.fillMaxSize() // Make the Box fill the card
        ) {


            Column(
                modifier = Modifier.width(screenWidth.dp - (spacer * 6)),
                horizontalAlignment = Alignment.Start,
            ) {

                Row(horizontalArrangement = Arrangement.Center) {
                    Image(
                        painter = rememberAsyncImagePainter(model = testImageUrl),

                        contentDescription = "book image",
                        modifier = Modifier
                            .height(140.dp)
                            .width(100.dp)
                            .padding(4.dp)
                    )


                    Spacer(modifier = Modifier.width(50.dp))

                    Column(
                        modifier = Modifier.padding(top = 25.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "favorite icon",
                            modifier = Modifier.padding(bottom = 1.dp),
                        )

                        BookRating(score = 3.5)
                    }

                }


                Text(
                    text = book.title.toString(), modifier = Modifier.padding(4.dp),
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = book.authors.toString(), modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )


            }


            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Aligns the Row at the bottom end of the Box
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
            ) {
                RoundedButton(label = "Reading", radius = 70)
            }


        }


    }


}

@Composable
fun RoundedButton(
    label: String = "Raiding",
    radius: Int = 29,
    onPress: () -> Unit = {},
) {

    Surface(
        modifier = Modifier.clip(
            RoundedCornerShape(
                bottomEndPercent = radius,
                topStartPercent = radius,
            ),

            ), color = Color(0xFF92CBDF)
    ) {

        Column(
            modifier = Modifier
                .width(90.dp)
                .height(40.dp)
                .clickable {
                    onPress.invoke()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = label, style = TextStyle(color = Color.White, fontSize = 15.sp))

        }

    }

}