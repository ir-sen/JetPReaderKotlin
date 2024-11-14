package kis.kan.jetreadearapp.repository

import kis.kan.jetreadearapp.data.DataOrException
import kis.kan.jetreadearapp.model.Item
import kis.kan.jetreadearapp.network.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val api: BooksApi
) {

    // values we need return and use
    private val dataOrException =
        DataOrException<List<Item>, Boolean, Exception>()

    private val bookInfoDataOrException =
        DataOrException<Item, Boolean, Exception>()

    // this for get all book list
    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>, Boolean, Exception> {

        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllBooks(searchQuery).items
            if (dataOrException.data!!.isNotEmpty()) {dataOrException.loading = true}

        } catch (e: Exception) {
            dataOrException.e = e
        }

        return dataOrException
    }

    // this for get books info item
    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {

        try {
            bookInfoDataOrException.loading = true
            bookInfoDataOrException.data = api.getBookInfo(bookId)

            if (bookInfoDataOrException.data.toString().isNotEmpty())
                bookInfoDataOrException.loading = false

        } catch (e: Exception) {
            bookInfoDataOrException.e = e
        }

        return bookInfoDataOrException
    }

}