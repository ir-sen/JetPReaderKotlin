package kis.kan.jetreadearapp.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kis.kan.jetreadearapp.data.DataOrException
import kis.kan.jetreadearapp.model.MBook
import kis.kan.jetreadearapp.repository.FireRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: FireRepository
): ViewModel() {

    val data: MutableState<DataOrException<List<MBook>, Boolean, Exception>>
            = mutableStateOf(DataOrException(listOf(), true, Exception("")))

    init {
        getAllBooksFromDatabase()
    }

    private fun getAllBooksFromDatabase() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllBooksFromDataBase()
            if (!data.value.data.isNullOrEmpty()) data.value.loading = false
        }

        Log.d("HomeViewModel", "getAllBooksFromDatabase: ${data!!.value?.data?.toList().toString()}")
    }


}