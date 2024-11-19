package kis.kan.jetreadearapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kis.kan.jetreadearapp.navigation.ReaderNavigation
import kis.kan.jetreadearapp.ui.theme.JetReadearAppTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetReadearAppTheme {

                val db = FirebaseFirestore.getInstance()
                val user: MutableMap<String, Any> = HashMap()
                user["firstName"] = "Jeo"
                user["lastName"] = "Loseme"
                ReaderApp(modifier = Modifier.fillMaxSize())



            }
        }
    }
}

@Composable
fun ReaderApp(modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier.fillMaxSize()
        .padding(top = 46.dp), ) { innerPadding ->

        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
            ){
            ReaderNavigation()

        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetReadearAppTheme {
    }
}