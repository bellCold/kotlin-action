package src.higer

class Main {

    suspend fun test() {
        retryWithBackoff(3, 100, 2.0) {

        }
    }

}

suspend fun <T> retryWithBackoff(
    maxRetries: Int = 3,
    initialDelay: Long = 100,
    backoffFactor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelay
    repeat(maxRetries - 1) { attempt ->
        try {
            return block()
        } catch (e: Exception) {
            println("시도 ${attempt + 1} 실패: ${e.message}")
            currentDelay = (currentDelay * backoffFactor).toLong()
        }
    }
    return block()
}