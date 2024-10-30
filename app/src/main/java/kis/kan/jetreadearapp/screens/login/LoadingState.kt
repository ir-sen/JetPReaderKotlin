package kis.kan.jetreadearapp.screens.login

data class LoadingState(
    val status: Status,
    val message: String? = null,
) {

    companion object {
        val IDLE = LoadingState(Status.IDLE)
        val LOADING = LoadingState(Status.LOADING)
        val FAILED = LoadingState(Status.FAILED)
        val SUCCESS = LoadingState(Status.SUCCESS)

    }
    enum class Status {
        SUCCESS,
        FAILED,
        LOADING,
        IDLE,
    }

}
