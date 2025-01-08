package src

import src.functional.MyFunctionalInterface

class Result {
}



fun main() {
    MyFunctionalInterface { name ->
        println(name)
    }
}