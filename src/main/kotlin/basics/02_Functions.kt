package basics

fun main() {
    // 함수 호출 예제들
    println("=== 기본 함수 ===")
    greet()
    greet("코틀린")
    
    println("\n=== 반환값이 있는 함수 ===")
    val sum = add(5, 3)
    println("5 + 3 = $sum")

    val product = multiply(4, 7)
    println("4 * 7 = $product")
    
    println("\n=== 단일 표현식 함수 ===")
    println("10의 제곱: ${square(10)}")
    println("15는 짝수인가? ${isEven(15)}")
    
    println("\n=== 기본 파라미터 ===")
    printInfo("김철수") // age는 기본값 사용
    printInfo("이영희", 25)
    
    println("\n=== 명명된 인수 ===")
    printInfo(age = 30, name = "박민수") // 순서 바꿔서 호출 가능
    
    println("\n=== 가변인자 함수 ===")
    println("합계: ${sum(1, 2, 3, 4, 5)}")
    println("최대값: ${findMax(10, 5, 8, 15, 3)}")
    
    println("\n=== 지역 함수 ===")
    processNumbers()
}

// 1. 기본 함수 - 반환값 없음
fun greet() {
    println("안녕하세요!")
}

// 2. 파라미터가 있는 함수
fun greet(name: String) {
    println("안녕하세요, ${name}님!")
}

// 3. 반환값이 있는 함수
fun add(a: Int, b: Int): Int {
    return a + b
}

// 4. 단일 표현식 함수 (= 사용)
fun multiply(a: Int, b: Int): Int = a * b

fun square(x: Int) = x * x // 반환 타입 추론

fun isEven(number: Int): Boolean = number % 2 == 0

// 5. 기본 파라미터
fun printInfo(name: String, age: Int = 0) {
    println("이름: $name, 나이: $age")
}

// 6. 가변인자 함수 (vararg)
fun sum(vararg numbers: Int): Int {
    var total = 0
    for (number in numbers) {
        total += number
    }
    return total
}

fun findMax(vararg numbers: Int): Int {
    if (numbers.isEmpty()) return 0
    var max = numbers[0]
    for (number in numbers) {
        if (number > max) {
            max = number
        }
    }
    return max
}

// 7. 지역 함수 (함수 내부의 함수)
fun processNumbers() {
    val numbers = listOf(1, 2, 3, 4, 5)
    
    // 지역 함수 정의
    fun isOdd(n: Int): Boolean = n % 2 == 1
    
    fun printOddNumbers() {
        println("홀수들:")
        for (number in numbers) {
            if (isOdd(number)) {
                println("  $number")
            }
        }
    }
    
    printOddNumbers()
}