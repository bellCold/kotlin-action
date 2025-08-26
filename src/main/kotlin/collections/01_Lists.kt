package collections

fun main() {
    println("=== 리스트 생성과 기본 조작 ===")
    
    // 불변 리스트
    val immutableList = listOf("사과", "바나나", "오렌지")
    println("불변 리스트: $immutableList")
    
    // 가변 리스트
    val mutableList = mutableListOf("딸기", "포도")
    mutableList.add("키위")
    mutableList.addAll(listOf("망고", "파인애플"))
    println("가변 리스트: $mutableList")
    
    // 인덱스 접근
    println("첫 번째 과일: ${immutableList[0]}")
    println("마지막 과일: ${immutableList.last()}")
    println("두 번째부터 끝까지: ${immutableList.drop(1)}")
    
    println("\n=== 리스트 검색과 필터링 ===")
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    
    // 조건에 맞는 요소 찾기
    val firstEven = numbers.first { it % 2 == 0 }
    val lastOdd = numbers.last { it % 2 == 1 }
    println("첫 번째 짝수: $firstEven")
    println("마지막 홀수: $lastOdd")
    
    // 필터링
    val evenNumbers = numbers.filter { it % 2 == 0 }
    val oddNumbers = numbers.filter { it % 2 == 1 }
    println("짝수들: $evenNumbers")
    println("홀수들: $oddNumbers")
    
    // 조건 확인
    val hasLargeNumber = numbers.any { it > 8 }
    val allPositive = numbers.all { it > 0 }
    println("8보다 큰 수가 있는가? $hasLargeNumber")
    println("모든 수가 양수인가? $allPositive")
    
    println("\n=== 리스트 변환 ===")
    val words = listOf("kotlin", "java", "python", "javascript")
    
    // map - 각 요소를 변환
    val upperCaseWords = words.map { it.uppercase() }
    val wordLengths = words.map { it.length }
    println("대문자 변환: $upperCaseWords")
    println("단어 길이: $wordLengths")
    
    // mapIndexed - 인덱스와 함께 변환
    val indexedWords = words.mapIndexed { index, word -> "$index: $word" }
    println("인덱스와 함께: $indexedWords")
    
    // flatMap - 중첩 구조를 평평하게
    val nestedNumbers = listOf(listOf(1, 2), listOf(3, 4), listOf(5, 6))
    val flatNumbers = nestedNumbers.flatten()
    println("중첩 리스트: $nestedNumbers")
    println("평평한 리스트: $flatNumbers")
    
    println("\n=== 리스트 그룹화와 집계 ===")
    val students = listOf(
        Student("김철수", 85),
        Student("이영희", 92), 
        Student("박민수", 78),
        Student("최수진", 95),
        Student("장동건", 88)
    )
    
    // 그룹화
    val groupedByGrade = students.groupBy { student ->
        when {
            student.score >= 90 -> "A"
            student.score >= 80 -> "B"  
            else -> "C"
        }
    }
    
    println("성적별 그룹화:")
    groupedByGrade.forEach { (grade, studentList) ->
        println("$grade 등급: ${studentList.map { it.name }}")
    }
    
    // 집계
    val totalScore = students.sumOf { it.score }
    val averageScore = students.map { it.score }.average()
    val highestScore = students.maxOfOrNull { it.score }
    
    println("총 점수: $totalScore")
    println("평균 점수: %.1f".format(averageScore))
    println("최고 점수: $highestScore")
    
    println("\n=== 리스트 정렬 ===")
    val randomNumbers = listOf(5, 2, 8, 1, 9, 3)
    
    val sortedAsc = randomNumbers.sorted()
    val sortedDesc = randomNumbers.sortedDescending()
    println("원본: $randomNumbers")
    println("오름차순: $sortedAsc")
    println("내림차순: $sortedDesc")
    
    // 객체 정렬
    val sortedByName = students.sortedBy { it.name }
    val sortedByScore = students.sortedByDescending { it.score }
    
    println("이름순 정렬: ${sortedByName.map { "${it.name}(${it.score})" }}")
    println("점수순 정렬: ${sortedByScore.map { "${it.name}(${it.score})" }}")
}

data class Student(val name: String, val score: Int)