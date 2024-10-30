package kis.kan.jetreadearapp.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LogInScreenViewModel : ViewModel() {

//    val loadingState = MutableStateFlow(LoadingState.IDLE)

    private val TAG = "LogInScreenViewModel"

    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun singInWithEmailAndPassword(email: String, password: String, home: () -> Unit) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        //TODO("take theme home")
                        home()
                        Log.d(TAG, "singIN succsess yeas: ${it.result}")
                    } else {
                        Log.d(TAG, "SingInWithEmailAndPassword: ${it.result}")
                    }
                }

        } catch (e: Exception) {
            Log.d(TAG, "Error in singInEmailAndPassword: $e")
        }
    }


    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        home: () -> Unit,
    ) {

        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val displayName = task.result?.user?.email?.split('@')?.get(0)
                        home()
                        createUser(displayName)

                    } else {
                        Log.d(TAG, "Create Account error : ${task.result}")
                    }
                    _loading.value = false

            }
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = mutableMapOf<String, Any>()
        user["user_id"] = userId.toString()
        user["display_name"] = displayName.toString()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)

    }


}