package kis.kan.jetreadearapp.data


// create sealed class for wrapping data from api
// This generic sealed class get data, message and can returned
// 3 type Success, Error, Loading.

sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String?, data: T? = null): Resource<T>(data, message)

    class Loading<T>(data: T? = null): Resource<T>(data)
}