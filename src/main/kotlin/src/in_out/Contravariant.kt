package src.in_out

interface InExample<in T> {
    fun consume(input: T)
}

interface OutExample<out T> {
    fun returnValue(name: String): String
}

class StringInExample : InExample<String> {
    override fun consume(input: String) {
        println("consume $input")
    }
}

class StringOutExample : OutExample<String> {
    override fun returnValue(name: String): String {
        return "**** ${name} ****"
    }
}

fun main() {
    val example1 = StringInExample()
    example1.consume("Just a string")

    val example2 = StringOutExample()
    val returnValue = example2.returnValue("start")
    println(returnValue)
}