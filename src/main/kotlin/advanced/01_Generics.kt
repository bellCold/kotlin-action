package advanced

fun main() {
    println("=== 기본 제네릭 ===")
    
    // 제네릭 클래스 사용
    val intBox = Box(42)
    val stringBox = Box("Hello")
    
    println("Int Box: ${intBox.value}")
    println("String Box: ${stringBox.value}")
    
    // 제네릭 함수 사용
    val swappedPair = swap(Pair("first", "second"))
    println("원본: ${Pair("first", "second")}")
    println("교환: $swappedPair")
    
    println("\n=== 제약이 있는 제네릭 ===")
    
    val comparableBox = ComparableBox(10)
    comparableBox.updateIfGreater(15)
    println("업데이트 후: ${comparableBox.value}")
    
    comparableBox.updateIfGreater(5) // 더 작으므로 업데이트 안됨
    println("업데이트 시도 후: ${comparableBox.value}")
    
    // 여러 제약
    val person = Person("김철수")
    val manager = Manager("이영희")
    
    processEmployee(person)
    processEmployee(manager)
    
    println("\n=== 공변성(Covariance) - out ===")
    
    val stringProducer: Producer<String> = StringProducer()
    val anyProducer: Producer<Any> = stringProducer // 공변성으로 가능
    
    println("문자열 생산: ${anyProducer.produce()}")
    
    // 리스트는 공변성
    val stringList: List<String> = listOf("a", "b", "c")
    val anyList: List<Any> = stringList // 가능
    
    println("\n=== 반공변성(Contravariance) - in ===")
    
    val anyComparator: Comparator<Any> = object : Comparator<Any> {
        override fun compare(o1: Any, o2: Any): Int {
            return o1.toString().compareTo(o2.toString())
        }
    }
    
    val stringComparator: Comparator<String> = anyComparator // 반공변성으로 가능
    val result = stringComparator.compare("apple", "banana")
    println("비교 결과: $result")
    
    println("\n=== 타입 소거와 reified ===")
    
    // 인라인 함수의 reified로 타입 정보 보존
    val strings = listOf("a", "b", 1, "c", 2)
    val filteredStrings = strings.filterByType<String>()
    val filteredInts = strings.filterByType<Int>()
    
    println("원본 리스트: $strings")
    println("문자열만: $filteredStrings")
    println("정수만: $filteredInts")
    
    println("\n=== 제네릭 확장 함수 ===")
    
    val numbers = listOf(1, 2, 3, 4, 5)
    val doubled = numbers.mapToList { it * 2 }
    
    println("원본: $numbers")
    println("두 배: $doubled")
    
    println("\n=== 복잡한 제네릭 예제 ===")
    
    val repository = Repository<User>()
    val user1 = User(1, "김철수")
    val user2 = User(2, "이영희")
    
    repository.save(user1)
    repository.save(user2)
    
    println("모든 사용자: ${repository.findAll()}")
    println("ID 1 사용자: ${repository.findById(1)}")
    
    // 타입 안전한 빌더 패턴
    val config = buildConfig<DatabaseConfig> {
        host = "localhost"
        port = 5432
        database = "myapp"
    }
    
    println("설정: $config")
}

// 1. 기본 제네릭 클래스
class Box<T>(val value: T)

// 2. 제네릭 함수
fun <T> swap(pair: Pair<T, T>): Pair<T, T> {
    return Pair(pair.second, pair.first)
}

// 3. 제약이 있는 제네릭 (upper bound)
class ComparableBox<T : Comparable<T>>(var value: T) {
    fun updateIfGreater(newValue: T) {
        if (newValue > value) {
            value = newValue
        }
    }
}

// 4. 여러 제약을 가진 제네릭
interface Named {
    val name: String
}

interface Employee

open class Person(override val name: String) : Named, Employee
class Manager(name: String) : Person(name)

fun <T> processEmployee(employee: T) where T : Named, T : Employee {
    println("직원 처리: ${employee.name}")
}

// 5. 공변성 (out) - 생산자
interface Producer<out T> {
    fun produce(): T
}

class StringProducer : Producer<String> {
    override fun produce(): String = "생산된 문자열"
}

// 6. 반공변성 (in) - 소비자
interface Consumer<in T> {
    fun consume(item: T)
}

// 7. reified 타입 파라미터
inline fun <reified T> List<*>.filterByType(): List<T> {
    return filterIsInstance<T>()
}

// 8. 제네릭 확장 함수
fun <T, R> Iterable<T>.mapToList(transform: (T) -> R): List<R> {
    val result = mutableListOf<R>()
    for (item in this) {
        result.add(transform(item))
    }
    return result
}

// 9. 복잡한 제네릭 예제 - Repository 패턴
class Repository<T : Any> {
    private val items = mutableListOf<T>()
    
    fun save(item: T) {
        items.add(item)
    }
    
    fun findAll(): List<T> = items.toList()
    
    fun findById(id: Int): T? {
        // 간단화를 위해 인덱스를 ID로 사용
        return items.getOrNull(id - 1)
    }
}

data class User(val id: Int, val name: String)

// 10. 제네릭 빌더 패턴
class ConfigBuilder<T> {
    var config: T? = null
    
    fun build(): T = config ?: throw IllegalStateException("Config not set")
}

data class DatabaseConfig(
    var host: String = "",
    var port: Int = 0,
    var database: String = ""
)

inline fun <reified T> buildConfig(init: T.() -> Unit): T {
    val instance = T::class.java.getDeclaredConstructor().newInstance()
    instance.init()
    return instance
}