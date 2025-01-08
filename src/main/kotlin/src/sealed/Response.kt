package src.sealed

sealed class Response {
    data class Success<T>(val data: T) : Response()
    data class Error<E>(val errorMessage: E) : Response()
    data object Loading : Response()
}
