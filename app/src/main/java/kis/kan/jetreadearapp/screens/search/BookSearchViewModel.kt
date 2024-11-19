package kis.kan.jetreadearapp.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kis.kan.jetreadearapp.data.DataOrException
import kis.kan.jetreadearapp.model.Item
import kis.kan.jetreadearapp.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: BookRepository): ViewModel() {

    val listOfBooks: MutableState<DataOrException<List<Item>, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception(""),))

    private val TAG = "BookSearchViewModelTAG"

    init {
        searchBooks("android")
    }

    fun searchBooks(query: String) {
        // do this operation in background thread
        viewModelScope.launch(Dispatchers.IO) {
            if (query.isEmpty()) {
                return@launch
            }


            listOfBooks.value.loading = true
            listOfBooks.value = repository.getBooks(query)

            if (listOfBooks.value.data.toString().isNotEmpty()) listOfBooks.value.loading = false

            Log.d(TAG, "SearchForm: ${listOfBooks.value.data}")


        }
    }


}