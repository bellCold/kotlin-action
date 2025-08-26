package basics

fun main() {
    println("=== 문자열 템플릿 ===")
    
    val name = "코틀린"
    val version = 1.9
    
    // 1. 기본 문자열 보간
    println("언어: $name")
    println("버전: $version")
    
    // 2. 표현식 사용
    val a = 10
    val b = 20
    println("$a + $b = ${a + b}")
    println("$a > $b: ${a > b}")
    
    // 3. 함수 호출
    fun greet(name: String) = "안녕하세요, ${name}님!"
    println("인사말: ${greet("개발자")}")
    
    // 4. 프로퍼티 접근
    val text = "Hello Kotlin"
    println("문자열 길이: ${text.length}")
    println("대문자: ${text.uppercase()}")
    println("첫 글자: ${text[0]}")
    
    // 5. 복잡한 표현식
    val numbers = listOf(1, 2, 3, 4, 5)
    println("리스트 합계: ${numbers.sum()}")
    println("최대값: ${numbers.maxOrNull()}")
    println("짝수 개수: ${numbers.count { it % 2 == 0 }}")
    
    // 6. 멀티라인 문자열 (삼중 따옴표)
    val multilineString = """
        이것은 
        여러 줄로 된
        문자열입니다.
        변수도 사용 가능: $name
    """.trimIndent()
    
    println("\n=== 멀티라인 문자열 ===")
    println(multilineString)
    
    // 7. Raw 문자열 - 이스케이프 시퀀스 불필요
    val filePath = """C:\Users\Developer\Documents\file.txt"""
    println("\n파일 경로: $filePath")
    
    // 8. 문자열 비교
    val str1 = "kotlin"
    val str2 = "KOTLIN"
    println("\n=== 문자열 비교 ===")
    println("$str1 == $str2: ${str1 == str2}")
    println("대소문자 무시 비교: ${str1.equals(str2, ignoreCase = true)}")
    
    // 9. 문자열 조작
    val sentence = "  Kotlin is awesome!  "
    println("\n=== 문자열 조작 ===")
    println("원본: '$sentence'")
    println("공백 제거: '${sentence.trim()}'")
    println("단어로 분리: ${sentence.trim().split(" ")}")
    println("치환: ${sentence.replace("awesome", "fantastic")}")
    println("포함 여부: ${sentence.contains("Kotlin")}")
    println("시작 여부: ${sentence.trim().startsWith("Kotlin")}")
    println("끝 여부: ${sentence.trim().endsWith("!")}")
}