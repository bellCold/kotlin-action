package collections

fun main() {
    println("=== 고차 함수 (Higher-Order Functions) ===")
    
    // 함수를 파라미터로 받는 함수
    val numbers = listOf(1, 2, 3, 4, 5)
    
    val doubled = numbers.map { it * 2 }
    val tripled = numbers.map(::triple) // 함수 참조
    
    println("원본: $numbers")
    println("2배: $doubled")
    println("3배: $tripled")
    
    // 커스텀 고차 함수
    val result1 = calculate(10, 5) { a, b -> a + b }
    val result2 = calculate(10, 5) { a, b -> a * b }
    println("10 + 5 = $result1")
    println("10 * 5 = $result2")
    
    println("\n=== 람다 표현식 ===")
    val fruits = listOf("사과", "바나나", "오렌지", "포도", "키위")
    
    // 다양한 람다 표현식 형태
    val longNames = fruits.filter { it.length > 2 }
    val shortNames = fruits.filter { name -> name.length <= 2 }
    val upperCased = fruits.map { it.uppercase() }
    
    println("긴 이름: $longNames")
    println("짧은 이름: $shortNames")
    println("대문자: $upperCased")
    
    println("\n=== 함수 타입과 변수 ===")
    
    // 함수를 변수에 저장
    val add: (Int, Int) -> Int = { a, b -> a + b }
    val multiply: (Int, Int) -> Int = { a, b -> a * b }
    
    println("5 + 3 = ${add(5, 3)}")
    println("5 * 3 = ${multiply(5, 3)}")
    
    // 함수를 반환하는 함수
    val mathOperation = getMathOperation("multiply")
    println("함수를 반환: ${mathOperation(4, 6)}")
    
    println("\n=== 스코프 함수 (let, run, with, apply, also) ===")
    
    val person = Person("김철수", 30)
    
    // let - null 체크와 변환에 유용
    person.let { 
        println("let: ${it.name}은 ${it.age}살입니다.")
        it.age + 10 // 반환값
    }.also { newAge ->
        println("10년 후 나이: $newAge")
    }
    
    // run - 객체의 컨텍스트에서 블록 실행
    val introduction = person.run {
        "안녕하세요, 저는 ${name}이고 ${age}살입니다."
    }
    println("run: $introduction")
    
    // with - 객체를 수신자로 하여 블록 실행
    val info = with(person) {
        "이름: $name, 나이: $age, 성인: ${age >= 18}"
    }
    println("with: $info")
    
    // apply - 객체 구성에 유용 (객체 자신을 반환)
    val newPerson = Person("이영희", 25).apply {
        println("apply: ${name}님을 생성했습니다.")
        // 여기서 추가 초기화 가능
    }
    
    // also - 부가 작업에 유용 (객체 자신을 반환)
    newPerson.also {
        println("also: ${it.name}님의 정보가 처리되었습니다.")
    }
    
    println("\n=== 체이닝과 함수 조합 ===")
    
    val data = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    
    val processedData = data
        .filter { it % 2 == 0 }           // 짝수만
        .map { it * it }                  // 제곱
        .filter { it > 10 }               // 10보다 큰 것만
        .sorted()                         // 정렬
        .take(3)                          // 처음 3개
    
    println("체이닝 결과: $processedData")
    
    println("\n=== 컬렉션 연산자 ===")
    
    val list1 = listOf(1, 2, 3, 4, 5)
    val list2 = listOf(4, 5, 6, 7, 8)
    
    // reduce와 fold
    val sum = list1.reduce { acc, n -> acc + n }
    val product = list1.fold(1) { acc, n -> acc * n }
    
    println("합계 (reduce): $sum")
    println("곱셈 (fold): $product")
    
    // zip - 두 컬렉션 결합
    val paired = list1.zip(list2)
    println("zip 결과: $paired")
    
    // partition - 조건에 따라 분할
    val (evens, odds) = list1.partition { it % 2 == 0 }
    println("짝수: $evens, 홀수: $odds")
    
    println("\n=== 지연 계산 (Sequences) ===")
    
    // 큰 컬렉션에서 일부만 처리할 때 효율적
    val largeList = (1..1000000).toList()
    
    // 일반 컬렉션 - 모든 단계를 즉시 실행
    val eagerResult = largeList
        .filter { it % 2 == 0 }
        .map { it * it }
        .take(5)
    
    // 시퀀스 - 필요할 때만 계산
    val lazyResult = largeList.asSequence()
        .filter { it % 2 == 0 }
        .map { it * it }
        .take(5)
        .toList()
    
    println("일반 컬렉션 결과: $eagerResult")
    println("지연 계산 결과: $lazyResult")
}

// 헬퍼 함수들
fun triple(x: Int): Int = x * 3

fun calculate(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
    return operation(a, b)
}

fun getMathOperation(type: String): (Int, Int) -> Int {
    return when (type) {
        "add" -> { a, b -> a + b }
        "multiply" -> { a, b -> a * b }
        "subtract" -> { a, b -> a - b }
        else -> { a, b -> a + b }
    }
}

data class Person(val name: String, val age: Int)