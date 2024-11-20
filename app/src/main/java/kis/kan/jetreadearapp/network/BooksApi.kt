package kis.kan.jetreadearapp.network

import kis.kan.jetreadearapp.model.BookItem
import kis.kan.jetreadearapp.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface BooksApi {


    // this is requests retrofit for api
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): BookItem

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId: String): Item






}