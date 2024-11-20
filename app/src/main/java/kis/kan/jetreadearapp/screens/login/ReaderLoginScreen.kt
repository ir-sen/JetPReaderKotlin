package kis.kan.jetreadearapp.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kis.kan.jetreadearapp.R
import kis.kan.jetreadearapp.components.EmailInput
import kis.kan.jetreadearapp.components.PsswordInput
import kis.kan.jetreadearapp.components.ReaderLogo
import kis.kan.jetreadearapp.navigation.ReaderScreens


const val TAG = "ReaderLoginScreenTAG"

@Composable
fun ReaderLoginScreen(
    navController: NavController,
    viewModel: LogInScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val showLoinForm = rememberSaveable {
        mutableStateOf(true)
    }


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ReaderLogo()
            if(showLoinForm.value) UserForm(loading = false, isCreateAccount = false) { email, password ->
                viewModel.singInWithEmailAndPassword(
                    email = email,
                    password = password,
                    ) {
                    navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                }
            }
            else {


                UserForm(loading = false, isCreateAccount = true) { email, password ->
                    Log.d(TAG, "Create form ok $email $password")
                    viewModel.createUserWithEmailAndPassword(email, password) {
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                    }
                }

            }

        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            val text = if (showLoinForm.value) "Sign Up" else "Login"
            Text(text = "New User?")
            Text(text = text,
                modifier = Modifier
                    .clickable {
                        showLoinForm.value = !showLoinForm.value
                    }
                    .padding(start = 5.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary)


        }


    }

}


@Preview
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = {email, pwd ->

    },

) {

    val email = rememberSaveable() {
        mutableStateOf("")
    }

    val password = rememberSaveable() {
        mutableStateOf("")
    }

    val passwordVisibility = rememberSaveable {
        mutableStateOf(false)
    }

    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    // if we add some sort of place where you can add data, or text fields
    // we need add scroll
    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())

    Column(modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        if (isCreateAccount) Text(text = stringResource(id = R.string.create_account),
            modifier = Modifier.padding(4.dp))
        else Text("")

        EmailInput(emailState = email, enable = true, onAction = KeyboardActions {
            passwordFocusRequest.requestFocus()
        })

        PsswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enable = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            }
        )

        SubmitButton(
            textId = if (isCreateAccount) "Create account" else "Login",
            loading = loading,
            validInputs = valid,
        ) {
            Log.d(TAG, "Click ok $email $password")
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }


    }

}

@Composable
fun SubmitButton(
    textId: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
) {
    Button(onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape
        ) {

        if (loading ) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))


    }
}


@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visible }) {
        Icons.Default.Close
    }
}


