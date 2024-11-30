package kis.kan.jetreadearapp.utils

import com.google.firebase.Timestamp
import java.text.DateFormat

// format data from firebase store to api
fun formatData(timeStamp: Timestamp): String {

    val date = DateFormat.getDateInstance()
        .format(timeStamp.toDate())
        .toString().split(",")[0] // like March 12

    return date

}