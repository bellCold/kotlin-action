package src.design_pattern.observer

import kotlin.properties.Delegates

interface ValueChangeListener {
    fun onValueChange(newValue: String)
}

class PrintingTextChangedListener : ValueChangeListener {
    override fun onValueChange(newValue: String) {
        println("Value changed to $newValue")
    }
}

class ObservableObjet(listener: ValueChangeListener) {
    var text: String by Delegates.observable(
        initialValue = "",
        onChange = { prop, old, new ->
            listener.onValueChange(new)
        }
    )
}

fun main() {
    val observableObjet = ObservableObjet(PrintingTextChangedListener())
    observableObjet.text = "Hello"
    observableObjet.text = "There"
}