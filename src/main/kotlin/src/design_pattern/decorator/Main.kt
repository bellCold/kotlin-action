package src.design_pattern.decorator

fun main() {
    val responseReader = ResponseReader()
//    val logReader = LoggerReader(responseReader)
    val cacheReader = CacheReader(responseReader)

    val request = Request("example")
//    responseReader.read(request)
//    logReader.read(request)
    cacheReader.read(request)
    cacheReader.read(request)
    cacheReader.read(request)
    cacheReader.read(request)
    cacheReader.read(request)
    cacheReader.read(request)
    cacheReader.read(request)
}