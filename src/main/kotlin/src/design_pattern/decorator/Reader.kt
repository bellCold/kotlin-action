package src.design_pattern.decorator

data class Request(val data: String)
data class Response(val body: String)


interface Reader {
    fun read(request: Request): Response
}