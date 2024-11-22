package kis.kan.jetreadearapp.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kis.kan.jetreadearapp.data.Resource
import kis.kan.jetreadearapp.model.Item
import kis.kan.jetreadearapp.repository.BookRepositoryVer2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModelVersion2 @Inject constructor(private val repository: BookRepositoryVer2): ViewModel() {


    var list: List<Item> by mutableStateOf(listOf())
    var loading: Boolean by mutableStateOf(true)


    private val TAG = "SearchViewModelVersion2TAG"


    init {
        loadBooks()
    }

    private fun loadBooks() {
        searchBooks("android")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.Default) {

            if (query.isEmpty()) {
                return@launch
            }

            try {
                when(val response = repository.getBooks(query)) {

                    is Resource.Error -> {
                        Log.d(TAG, "searchBooks: Failed getting books")
                        loading = false
                    }

                    is Resource.Success -> {
                        list = response.data!!
                        if (list.isNotEmpty()) loading = false
                    }

                    else -> {
                        loading = false
                    }

                }

            } catch (e: Exception) {
                Log.d(TAG, "searchbooks: ${e.message.toString()}")
                loading = false
            }
        }
    }


}