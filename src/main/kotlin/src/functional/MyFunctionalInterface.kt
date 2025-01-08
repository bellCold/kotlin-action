package src.functional

@FunctionalInterface
fun interface MyFunctionalInterface {
    fun performAction(name: String)
}

fun runAction(action: MyFunctionalInterface) {
    action.performAction("SAM Conversion")
}

fun interface Worker {
    fun work(task: String)

    fun rest() {
        println("Tasking a break")
    }
}

fun executeTask(worker: Worker, task: String) {
    worker.work(task)
    worker.rest()
}

fun main() {
    // v1
    val worker = Worker { task ->
        println("Working on task ${task}")
    }

    worker.work("Project")

    worker.rest()

    // v2
    executeTask(worker, "Project")
}