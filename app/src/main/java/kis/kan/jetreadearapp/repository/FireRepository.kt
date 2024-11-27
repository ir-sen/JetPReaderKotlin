package kis.kan.jetreadearapp.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kis.kan.jetreadearapp.data.DataOrException
import kis.kan.jetreadearapp.model.MBook
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(
    private val queryBook: Query
) {

    suspend fun getAllBooksFromDataBase(): DataOrException<List<MBook>, Boolean, Exception> {

        val dataOrException = DataOrException<List<MBook>, Boolean, Exception>()

        try {
            dataOrException.loading = true

            dataOrException.data = queryBook.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(MBook::class.java)!!
            }

            if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false

        } catch (exception: FirebaseFirestoreException) {
            dataOrException.e = exception
        }

        return dataOrException
    }




}