package oop

fun main() {
    println("=== 인터페이스 구현 ===")
    val dog = Dog("멍멍이")
    val cat = Cat("야옹이")
    val bird = Bird("짹짹이")
    
    // 다형성 활용
    val animals: List<Animal> = listOf(dog, cat, bird)
    
    animals.forEach { animal ->
        animal.makeSound()
        animal.eat()
        
        // 타입 체크 및 캐스팅
        when (animal) {
            is Flyable -> {
                println("${animal.name}은 날 수 있습니다.")
                animal.fly()
            }
            is Swimmable -> {
                println("${animal.name}은 수영할 수 있습니다.")
                animal.swim()
            }
        }
        println()
    }
    
    println("=== 인터페이스 위임 ===")
    val fish = Fish("물고기")
    fish.makeSound()
    fish.swim()
    
    println("\n=== 추상 클래스 ===")
    val circle = Circle(5.0)
    val rectangle = Rectangle(4.0, 6.0)
    
    println("원 - 넓이: ${circle.area()}, 둘레: ${circle.perimeter()}")
    println("사각형 - 넓이: ${rectangle.area()}, 둘레: ${rectangle.perimeter()}")
}

// 1. 기본 인터페이스
interface Animal {
    val name: String
    
    fun makeSound()
    
    // 기본 구현을 가진 메서드
    fun eat() {
        println("$name이 음식을 먹고 있습니다.")
    }
}

// 2. 추가 기능 인터페이스들
interface Flyable {
    fun fly() {
        println("날고 있습니다!")
    }
}

interface Swimmable {
    fun swim() {
        println("수영하고 있습니다!")
    }
}

// 3. 인터페이스 구현 클래스들
class Dog(override val name: String) : Animal, Swimmable {
    override fun makeSound() {
        println("$name: 멍멍!")
    }
    
    override fun swim() {
        println("$name이 개헤엄을 치고 있습니다.")
    }
}

class Cat(override val name: String) : Animal {
    override fun makeSound() {
        println("$name: 야옹!")
    }
    
    override fun eat() {
        println("$name이 우아하게 음식을 먹고 있습니다.")
    }
}

// 4. 다중 인터페이스 구현
class Bird(override val name: String) : Animal, Flyable {
    override fun makeSound() {
        println("$name: 짹짹!")
    }
    
    override fun fly() {
        println("$name이 하늘 높이 날아갑니다!")
    }
}

// 5. 인터페이스 위임 (by 키워드)
class Fish(override val name: String) : Animal, Swimmable by SwimmerImpl() {
    override fun makeSound() {
        println("$name: 뻐끔뻐끔")
    }
}

class SwimmerImpl : Swimmable {
    override fun swim() {
        println("물속에서 자유롭게 헤엄치고 있습니다.")
    }
}

// 6. 추상 클래스
abstract class Shape {
    abstract fun area(): Double
    abstract fun perimeter(): Double
    
    // 구현된 메서드
    fun printInfo() {
        println("이 도형의 넓이는 ${area()}이고, 둘레는 ${perimeter()}입니다.")
    }
}

// 7. 추상 클래스 상속
class Circle(private val radius: Double) : Shape() {
    override fun area(): Double = Math.PI * radius * radius
    override fun perimeter(): Double = 2 * Math.PI * radius
}

class Rectangle(private val width: Double, private val height: Double) : Shape() {
    override fun area(): Double = width * height
    override fun perimeter(): Double = 2 * (width + height)
}