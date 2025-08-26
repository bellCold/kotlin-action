package collections

fun main() {
    println("=== Map 기본 사용법 ===")
    
    // 불변 맵 생성
    val immutableMap = mapOf(
        "kotlin" to "코틀린",
        "java" to "자바", 
        "python" to "파이썬"
    )
    
    // 가변 맵 생성
    val mutableMap = mutableMapOf<String, Int>()
    mutableMap["apple"] = 100
    mutableMap["banana"] = 80
    mutableMap["orange"] = 120
    
    println("언어 번역: $immutableMap")
    println("과일 가격: $mutableMap")
    
    // 맵 접근
    println("kotlin 번역: ${immutableMap["kotlin"]}")
    println("사과 가격: ${mutableMap["apple"]}")
    println("없는 키: ${mutableMap["grape"]}") // null 반환
    
    // 안전한 접근
    val grapePrice = mutableMap.getOrDefault("grape", 0)
    val mangoPrice = mutableMap.getOrElse("mango") { 0 }
    println("포도 가격 (기본값): $grapePrice")
    println("망고 가격 (기본값): $mangoPrice")
    
    println("\n=== Map 조작과 변환 ===")
    
    val studentGrades = mapOf(
        "김철수" to 85,
        "이영희" to 92,
        "박민수" to 78,
        "최수진" to 95,
        "장동건" to 88
    )
    
    // 키와 값 반복
    println("전체 성적:")
    studentGrades.forEach { (name, grade) ->
        val level = when {
            grade >= 90 -> "우수"
            grade >= 80 -> "양호"
            else -> "보통"
        }
        println("  $name: ${grade}점 ($level)")
    }
    
    // 맵 변환
    val gradesByLevel = studentGrades.mapValues { (_, grade) ->
        when {
            grade >= 90 -> "A"
            grade >= 80 -> "B"
            else -> "C"
        }
    }
    println("등급별 변환: $gradesByLevel")
    
    // 필터링
    val highAchievers = studentGrades.filter { (_, grade) -> grade >= 90 }
    val excellentStudents = studentGrades.filterValues { it >= 90 }
    println("90점 이상 학생: $highAchievers")
    println("우수 학생: $excellentStudents")
    
    println("\n=== Set 기본 사용법 ===")
    
    // 불변 셋
    val fruits = setOf("사과", "바나나", "오렌지", "사과") // 중복 제거됨
    println("과일 셋: $fruits")
    println("과일 개수: ${fruits.size}")
    
    // 가변 셋
    val colors = mutableSetOf("빨강", "파랑", "노랑")
    colors.add("초록")
    colors.add("빨강") // 이미 있으므로 추가되지 않음
    println("색상 셋: $colors")
    
    // 셋 연산
    val primaryColors = setOf("빨강", "파랑", "노랑")
    val warmColors = setOf("빨강", "주황", "노랑")
    
    val union = primaryColors union warmColors
    val intersection = primaryColors intersect warmColors
    val difference = primaryColors - warmColors
    
    println("기본색: $primaryColors")
    println("따뜻한색: $warmColors")
    println("합집합: $union")
    println("교집합: $intersection")
    println("차집합: $difference")
    
    println("\n=== 실용적 예제 - 단어 카운터 ===")
    
    val text = """
        코틀린은 JetBrains에서 개발한 프로그래밍 언어입니다.
        코틀린은 자바와 100% 호환되며, 안드로이드 개발에도 사용됩니다.
        코틀린은 간결하고 안전한 언어입니다.
    """.trimIndent()
    
    // 단어별 출현 횟수 계산
    val wordCount = text
        .lowercase()
        .replace(Regex("[^가-힣a-z\\s]"), "") // 한글, 영문, 공백만 남기기
        .split("\\s+".toRegex())
        .filter { it.isNotBlank() }
        .groupingBy { it }
        .eachCount()
    
    println("단어 출현 횟수:")
    wordCount.toList()
        .sortedByDescending { it.second }
        .take(5)
        .forEach { (word, count) ->
            println("  '$word': ${count}번")
        }
    
    println("\n=== Map과 Set 고급 활용 ===")
    
    // 그룹화
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val groupedNumbers = numbers.groupBy { it % 3 }
    println("나머지별 그룹: $groupedNumbers")
    
    // 중복 제거 후 변환
    val duplicatedList = listOf("a", "b", "a", "c", "b", "d")
    val uniqueUpperCase = duplicatedList.toSet().map { it.uppercase() }
    println("중복 제거 후 대문자: $uniqueUpperCase")
    
    // 맵을 리스트로 변환
    val mapToList = studentGrades.toList().sortedByDescending { it.second }
    println("성적순 정렬: $mapToList")
    
    // 복합 키를 가진 맵
    val coordinates = mapOf(
        Pair(0, 0) to "원점",
        Pair(1, 1) to "대각선",
        Pair(0, 1) to "위쪽"
    )
    
    println("좌표 맵: $coordinates")
    println("(0,0) 위치: ${coordinates[Pair(0, 0)]}")
}