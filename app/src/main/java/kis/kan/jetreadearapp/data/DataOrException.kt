package kis.kan.jetreadearapp.data


// this data class for safe data from api
// We need to wrap data from api
data class DataOrException<T, Boolean, E: Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null,

)
