package basics

fun main() {
    // 1. 변수 선언 - var (가변), val (불변)
    var mutableVariable = "변경 가능한 변수"
    val immutableVariable = "변경 불가능한 변수"
    
    println("mutableVariable: $mutableVariable")
    println("immutableVariable: $immutableVariable")
    
    // var는 변경 가능
    mutableVariable = "변경된 값"
    println("변경 후: $mutableVariable")
    
    // val은 변경 불가능 - 아래 코드는 컴파일 에러
    // immutableVariable = "변경 시도" // 에러!
    
    // 2. 타입 추론과 명시적 타입 선언
    val inferredType = 42 // Int로 추론
    val explicitType: String = "명시적 String 타입"
    
    println("inferredType: $inferredType (${inferredType::class.simpleName})")
    println("explicitType: $explicitType")
    
    // 3. 기본 데이터 타입들
    val byteValue: Byte = 127
    val shortValue: Short = 32767
    val intValue: Int = 2147483647
    val longValue: Long = 9223372036854775807L
    
    val floatValue: Float = 3.14f
    val doubleValue: Double = 3.141592653589793
    
    val booleanValue: Boolean = true
    val charValue: Char = 'K'
    
    println("=== 기본 데이터 타입 ===")
    println("Byte: $byteValue")
    println("Short: $shortValue") 
    println("Int: $intValue")
    println("Long: $longValue")
    println("Float: $floatValue")
    println("Double: $doubleValue")
    println("Boolean: $booleanValue")
    println("Char: $charValue")
    
    // 4. null 안전성
    val nonNullString: String = "null이 될 수 없음"
    val nullableString: String? = null // ?를 붙여 nullable 타입으로 선언
    
    println("nonNullString: $nonNullString")
    println("nullableString: $nullableString")
    
    // null 체크
    if (nullableString != null) {
        println("nullableString 길이: ${nullableString.length}")
    } else {
        println("nullableString은 null입니다")
    }
}