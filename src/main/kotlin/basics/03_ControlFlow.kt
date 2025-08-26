package basics

fun main() {
    println("=== if-else 문 ===")
    val score = 85
    val grade = if (score >= 90) {
        "A"
    } else if (score >= 80) {
        "B"
    } else if (score >= 70) {
        "C"
    } else {
        "F"
    }
    println("점수: $score, 등급: $grade")
    
    // if를 표현식으로 사용
    val message = if (score >= 60) "합격" else "불합격"
    println("결과: $message")
    
    println("\n=== when 문 ===")
    val dayOfWeek = 3
    val dayName = when (dayOfWeek) {
        1 -> "월요일"
        2 -> "화요일"
        3 -> "수요일"
        4 -> "목요일"
        5 -> "금요일"
        6, 7 -> "주말"
        else -> "잘못된 요일"
    }
    println("요일: $dayName")
    
    // when으로 범위 체크
    val temperature = 25
    val weather = when (temperature) {
        in 0..10 -> "추운 날씨"
        in 11..20 -> "선선한 날씨"
        in 21..30 -> "따뜻한 날씨"
        else -> "더운 날씨"
    }
    println("기온: ${temperature}도, $weather")
    
    println("\n=== for 문 ===")
    // 범위 반복
    print("1부터 5까지: ")
    for (i in 1..5) {
        print("$i ")
    }
    println()
    
    // until 사용 (마지막 값 제외)
    print("0부터 4까지: ")
    for (i in 0 until 5) {
        print("$i ")
    }
    println()
    
    // 감소 순서
    print("5부터 1까지: ")
    for (i in 5 downTo 1) {
        print("$i ")
    }
    println()
    
    // 단계별 반복
    print("0부터 10까지 2씩 증가: ")
    for (i in 0..10 step 2) {
        print("$i ")
    }
    println()
    
    // 리스트 반복
    val fruits = listOf("사과", "바나나", "오렌지")
    println("\n과일 목록:")
    for (fruit in fruits) {
        println("- $fruit")
    }
    
    // 인덱스와 함께 반복
    println("\n인덱스와 함께:")
    for ((index, fruit) in fruits.withIndex()) {
        println("$index: $fruit")
    }
    
    println("\n=== while 문 ===")
    var count = 0
    while (count < 3) {
        println("while: $count")
        count++
    }
    
    println("\n=== do-while 문 ===")
    var number = 0
    do {
        println("do-while: $number")
        number++
    } while (number < 3)
    
    println("\n=== break와 continue ===")
    println("1부터 10까지, 짝수만 출력 (8에서 중단):")
    for (i in 1..10) {
        if (i % 2 == 1) continue // 홀수는 건너뛰기
        if (i == 8) break // 8에서 중단
        println("짝수: $i")
    }
    
    println("\n=== 레이블을 사용한 break ===")
    outer@ for (i in 1..3) {
        for (j in 1..3) {
            if (i == 2 && j == 2) {
                println("중첩 루프에서 완전히 빠져나감")
                break@outer
            }
            println("($i, $j)")
        }
    }
}