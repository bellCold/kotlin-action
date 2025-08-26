package oop

import kotlin.math.sqrt

fun main() {
    println("=== Object 선언 (싱글톤) ===")
    DatabaseManager.connect()
    DatabaseManager.query("SELECT * FROM users")
    DatabaseManager.disconnect()
    
    println("\n=== Object 표현식 (익명 객체) ===")
    val clickListener = object : EventListener {
        override fun onClick() {
            println("버튼이 클릭되었습니다!")
        }
        
        override fun onDoubleClick() {
            println("더블 클릭되었습니다!")
        }
    }
    
    clickListener.onClick()
    clickListener.onDoubleClick()
    
    println("\n=== Companion Object ===")
    val person = Person.create("김철수", 30)
    person.introduce()
    
    println("총 생성된 Person 수: ${Person.getPersonCount()}")
    
    val anotherPerson = Person.create("이영희", 25)
    println("총 생성된 Person 수: ${Person.getPersonCount()}")
    
    // 상수 사용
    println("최대 나이: ${Person.MAX_AGE}")
    
    println("\n=== 유틸리티 클래스 ===")
    val result1 = MathUtils.add(10, 20)
    val result2 = MathUtils.multiply(5, 6)
    val distance = MathUtils.distance(0.0, 0.0, 3.0, 4.0)
    
    println("10 + 20 = $result1")
    println("5 * 6 = $result2")
    println("(0,0)에서 (3,4)까지의 거리: $distance")
    
    println("\n=== 팩토리 패턴 ===")
    val circle = Shape.createCircle(5.0)
    val rectangle = Shape.createRectangle(4.0, 6.0)
    
    circle.draw()
    rectangle.draw()
}

// 1. Object 선언 - 싱글톤 패턴
object DatabaseManager {
    private var isConnected = false
    
    fun connect() {
        isConnected = true
        println("데이터베이스에 연결되었습니다.")
    }
    
    fun disconnect() {
        isConnected = false
        println("데이터베이스 연결이 해제되었습니다.")
    }
    
    fun query(sql: String) {
        if (isConnected) {
            println("쿼리 실행: $sql")
        } else {
            println("데이터베이스에 먼저 연결해주세요.")
        }
    }
}

// 2. 인터페이스 정의 (object 표현식을 위해)
interface EventListener {
    fun onClick()
    fun onDoubleClick()
}

// 3. Companion Object를 가진 클래스
class Person private constructor(val name: String, val age: Int) {
    
    fun introduce() {
        println("안녕하세요, 저는 $name이고 $age살입니다.")
    }
    
    companion object {
        const val MAX_AGE = 150
        private var personCount = 0
        
        // 팩토리 메서드
        fun create(name: String, age: Int): Person {
            if (age < 0 || age > MAX_AGE) {
                throw IllegalArgumentException("나이는 0 이상 $MAX_AGE 이하여야 합니다.")
            }
            personCount++
            return Person(name, age)
        }
        
        fun getPersonCount(): Int = personCount
        
        // 정적 메서드 같은 기능
        fun isValidAge(age: Int): Boolean = age in 0..MAX_AGE
    }
}

// 4. 순수한 유틸리티 클래스 (모든 멤버가 static)
object MathUtils {
    fun add(a: Int, b: Int): Int = a + b
    fun multiply(a: Int, b: Int): Int = a * b
    fun distance(x1: Double, y1: Double, x2: Double, y2: Double): Double {
        return sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1))
    }
}

// 5. 팩토리 패턴을 위한 추상 클래스
abstract class Shape {
    abstract fun draw()
    
    companion object {
        fun createCircle(radius: Double): Shape = Circle(radius)
        fun createRectangle(width: Double, height: Double): Shape = Rectangle(width, height)
    }
    
    private class Circle(private val radius: Double) : Shape() {
        override fun draw() {
            println("반지름 $radius 인 원을 그립니다.")
        }
    }
    
    private class Rectangle(private val width: Double, private val height: Double) : Shape() {
        override fun draw() {
            println("${width}x$height 크기의 사각형을 그립니다.")
        }
    }
}