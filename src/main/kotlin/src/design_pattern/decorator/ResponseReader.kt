package src.design_pattern.decorator

import java.util.concurrent.ConcurrentHashMap

class ResponseReader : Reader {
    override fun read(request: Request): Response {
        return Response("success")
    }
}

class CacheReader(private val reader: Reader) : Reader {
    private val cache = ConcurrentHashMap<Request, Response>()

    override fun read(request: Request): Response {
        val response = cache[request]
        return if (response != null) {
            println("cache")
            response
        } else {
            println("no-cache")
            val readResponse = reader.read(request)
            cache[request] = readResponse
            readResponse
        }
    }
}

class LoggerReader(private val reader: Reader) : Reader {
    override fun read(request: Request): Response {
        val response = reader.read(request)
        println("Request: $request || Response: $response")
        return response
    }
}