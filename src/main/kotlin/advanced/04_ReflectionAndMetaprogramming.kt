package advanced

import kotlin.reflect.*
import kotlin.reflect.full.*

fun main() {
    println("=== 클래스 리플렉션 ===")
    
    val person = Person("김철수", 30)
    val personClass = person::class
    
    // 클래스 정보
    println("클래스명: ${personClass.simpleName}")
    println("패키지명: ${personClass.qualifiedName}")
    println("상위 클래스: ${personClass.supertypes}")
    
    // 생성자 정보
    personClass.constructors.forEach { constructor ->
        println("생성자: ${constructor.parameters.map { "${it.name}: ${it.type}" }}")
    }
    
    println("\n=== 프로퍼티 리플렉션 ===")
    
    // 프로퍼티 나열
    personClass.memberProperties.forEach { property ->
        println("프로퍼티: ${property.name}, 타입: ${property.returnType}")
        
        // 프로퍼티 값 읽기
        val value = property.getter.call(person)
        println("  값: $value")
        
        // 가변 프로퍼티인 경우 값 변경
        if (property is KMutableProperty<*>) {
            when (property.name) {
                "name" -> property.setter.call(person, "이영희")
                "age" -> property.setter.call(person, 25)
            }
        }
    }
    
    println("변경 후: $person")
    
    println("\n=== 함수 리플렉션 ===")
    
    // 함수 나열
    personClass.functions.forEach { function ->
        println("함수: ${function.name}")
        println("  파라미터: ${function.parameters.map { "${it.name}: ${it.type}" }}")
        println("  반환 타입: ${function.returnType}")
        
        // 함수 호출
        when (function.name) {
            "greet" -> {
                function.call(person)
            }
            "introduce" -> {
                if (function.parameters.size == 2) { // this + 파라미터
                    function.call(person, "안녕하세요!")
                }
            }
        }
    }
    
    println("\n=== 애노테이션 리플렉션 ===")
    
    val annotatedClass = AnnotatedPerson::class
    
    // 클래스 애노테이션
    annotatedClass.annotations.forEach { annotation ->
        when (annotation) {
            is Entity -> println("엔티티: 테이블명 = ${annotation.tableName}")
        }
    }
    
    // 프로퍼티 애노테이션
    annotatedClass.memberProperties.forEach { property ->
        println("프로퍼티 ${property.name}의 애노테이션:")
        property.annotations.forEach { annotation ->
            when (annotation) {
                is Column -> println("  컬럼: ${annotation.name}")
                is Required -> println("  필수 필드")
            }
        }
    }
    
    println("\n=== 제네릭 타입 정보 ===")
    
    val listProperty = GenericExample::class.memberProperties
        .find { it.name == "stringList" }
    
    listProperty?.let {
        println("프로퍼티 타입: ${it.returnType}")
        val typeArguments = it.returnType.arguments
        println("제네릭 타입 인수: $typeArguments")
    }
    
    println("\n=== 동적 객체 생성과 조작 ===")
    
    // 동적 객체 생성
    val personConstructor = Person::class.constructors.first()
    val dynamicPerson = personConstructor.call("박민수", 28)
    println("동적 생성된 객체: $dynamicPerson")
    
    // 동적 메서드 호출
    val greetFunction = Person::class.functions.find { it.name == "greet" }
    greetFunction?.call(dynamicPerson)
    
    println("\n=== 확장 함수와 리플렉션 ===")
    
    val stringClass = String::class
    
    // 확장 함수는 멤버 함수로 나타나지 않음
    println("String의 멤버 함수 개수: ${stringClass.functions.size}")
    
    // 확장 함수 호출 (함수 참조 사용)
    val reversedString = ::reverseString.call("Hello")
    println("확장 함수 결과: $reversedString")
    
    println("\n=== 실용적 예제: 객체 복사기 ===")
    
    val original = Person("원본", 25)
    val copied = copyObject(original)
    
    println("원본: $original")
    println("복사본: $copied")
    println("같은 객체인가? ${original === copied}")
    
    println("\n=== 직렬화/역직렬화 시뮬레이션 ===")
    
    val serializer = SimpleSerializer()
    val serialized = serializer.serialize(person)
    val deserialized = serializer.deserialize(serialized, Person::class)
    
    println("직렬화: $serialized")
    println("역직렬화: $deserialized")
    
    println("\n=== 프로퍼티 검증기 ===")
    
    val validator = PropertyValidator()
    val validationResult = validator.validate(AnnotatedPerson("", 25, "invalid-email"))
    
    println("검증 결과:")
    validationResult.forEach { (property, errors) ->
        if (errors.isNotEmpty()) {
            println("  $property: ${errors.joinToString(", ")}")
        }
    }
}

// 테스트용 클래스들
data class Person(var name: String, var age: Int) {
    fun greet() {
        println("안녕하세요, $name입니다.")
    }
    
    fun introduce(greeting: String) {
        println("$greeting 저는 $name이고 $age살입니다.")
    }
}

class GenericExample {
    val stringList: List<String> = listOf("a", "b", "c")
    val numberMap: Map<String, Int> = mapOf("one" to 1)
}

// 애노테이션들
@Target(AnnotationTarget.CLASS)
annotation class Entity(val tableName: String)

@Target(AnnotationTarget.PROPERTY)
annotation class Column(val name: String)

@Target(AnnotationTarget.PROPERTY)  
annotation class Required

@Target(AnnotationTarget.PROPERTY)
annotation class Email

@Entity(tableName = "persons")
data class AnnotatedPerson(
    @Column(name = "person_name")
    @Required
    val name: String,
    
    @Column(name = "person_age")
    val age: Int,
    
    @Column(name = "email_address")
    @Email
    val email: String
)

// 확장 함수
fun String.reverseString(): String = this.reversed()

// 전역 확장 함수 (리플렉션 테스트용)
fun reverseString(str: String): String = str.reversed()

// 유틸리티 함수들
inline fun <reified T : Any> copyObject(original: T): T {
    val constructor = T::class.constructors.first()
    val properties = T::class.memberProperties
    
    val args = constructor.parameters.map { param ->
        val property = properties.find { it.name == param.name }
        property?.getter?.call(original)
    }.toTypedArray()
    
    return constructor.call(*args)
}

// 간단한 직렬화기
class SimpleSerializer {
    fun serialize(obj: Any): Map<String, Any?> {
        val result = mutableMapOf<String, Any?>()
        
        obj::class.memberProperties.forEach { property ->
            val value = property.getter.call(obj)
            result[property.name] = value
        }
        
        return result
    }
    
    fun <T : Any> deserialize(data: Map<String, Any?>, clazz: KClass<T>): T {
        val constructor = clazz.constructors.first()
        
        val args = constructor.parameters.map { param ->
            data[param.name]
        }.toTypedArray()
        
        return constructor.call(*args)
    }
}

// 프로퍼티 검증기
class PropertyValidator {
    fun validate(obj: Any): Map<String, List<String>> {
        val errors = mutableMapOf<String, MutableList<String>>()
        
        obj::class.memberProperties.forEach { property ->
            val propertyErrors = mutableListOf<String>()
            val value = property.getter.call(obj)
            
            property.annotations.forEach { annotation ->
                when (annotation) {
                    is Required -> {
                        if (value == null || (value is String && value.isBlank())) {
                            propertyErrors.add("필수 필드입니다")
                        }
                    }
                    is Email -> {
                        if (value is String && !value.contains("@")) {
                            propertyErrors.add("유효한 이메일 형식이 아닙니다")
                        }
                    }
                }
            }
            
            if (propertyErrors.isNotEmpty()) {
                errors[property.name] = propertyErrors
            }
        }
        
        return errors
    }
}