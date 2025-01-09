package src.solid

interface Flyable {
    fun fly()
}

interface Swimmable {
    fun swim()
}

open class Bird {
    // methods
}

class Penguin : Bird(), Swimmable {
    override fun swim() {
        TODO("Not yet implemented")
    }
}

class Eagle: Bird(), Flyable {
    override fun fly() {
    }
}