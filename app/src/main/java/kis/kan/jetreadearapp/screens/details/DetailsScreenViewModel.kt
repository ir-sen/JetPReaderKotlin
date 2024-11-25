package kis.kan.jetreadearapp.screens.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kis.kan.jetreadearapp.data.Resource
import kis.kan.jetreadearapp.model.Item
import kis.kan.jetreadearapp.repository.BookRepositoryVer2
import javax.inject.Inject


@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val repositoryVer2: BookRepositoryVer2
    ): ViewModel() {

        suspend fun getBookInfo(bookId: String): Resource<Item> {
            return repositoryVer2.getBookInfo(bookId)
        }


}