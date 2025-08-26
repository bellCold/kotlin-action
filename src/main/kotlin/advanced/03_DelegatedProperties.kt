package advanced

import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {
    println("=== 지연 초기화 (lazy) ===")
    
    val lazyExample = LazyExample()
    println("지연 프로퍼티 접근 전")
    println("값: ${lazyExample.expensiveValue}")
    println("값: ${lazyExample.expensiveValue}") // 두 번째는 캐시된 값 사용
    
    println("\n=== 관찰 가능한 프로퍼티 (observable) ===")
    
    val observableExample = ObservableExample()
    observableExample.name = "김철수"
    observableExample.name = "이영희"
    
    println("\n=== 변경 가로채기 (vetoable) ===")
    
    val vetoableExample = VetoableExample()
    vetoableExample.positiveNumber = 10 // 성공
    vetoableExample.positiveNumber = -5 // 실패 (음수)
    println("현재 값: ${vetoableExample.positiveNumber}")
    
    println("\n=== 맵에 위임 ===")
    
    val userData = mapOf(
        "name" to "박민수",
        "age" to 25,
        "email" to "park@example.com"
    )
    
    val userFromMap = UserFromMap(userData)
    println("맵에서 생성된 사용자: ${userFromMap.name}, ${userFromMap.age}, ${userFromMap.email}")
    
    // 가변 맵
    val mutableUserData = mutableMapOf(
        "name" to "최수진",
        "age" to 28
    )
    
    val mutableUser = MutableUserFromMap(mutableUserData)
    println("이름: ${mutableUser.name}, 나이: ${mutableUser.age}")
    
    mutableUser.name = "장영희"
    mutableUser.age = 32
    println("변경 후 맵: $mutableUserData")
    
    println("\n=== 커스텀 위임 ===")
    
    val customExample = CustomDelegateExample()
    customExample.value = "첫 번째 값"
    println("저장된 값: ${customExample.value}")
    customExample.value = "두 번째 값"
    
    println("\n=== 싱글톤 패턴 위임 ===")
    
    val config1 = AppConfig.getInstance()
    val config2 = AppConfig.getInstance()
    
    println("같은 인스턴스인가? ${config1 === config2}")
    config1.setting = "새로운 설정"
    println("config2의 설정: ${config2.setting}")
    
    println("\n=== 캐시 위임 ===")
    
    val cacheExample = CacheExample()
    println("첫 번째 계산: ${cacheExample.expensiveCalculation}")
    println("두 번째 계산: ${cacheExample.expensiveCalculation}") // 캐시됨
    
    cacheExample.clearCache()
    println("캐시 클리어 후: ${cacheExample.expensiveCalculation}")
    
    println("\n=== 프로퍼티 검증 위임 ===")
    
    val validationExample = ValidationExample()
    try {
        validationExample.email = "user@example.com" // 성공
        println("유효한 이메일 설정 완료")
        
        validationExample.email = "invalid-email" // 실패
    } catch (e: IllegalArgumentException) {
        println("이메일 검증 실패: ${e.message}")
    }
    
    println("\n=== 스레드 로컬 위임 ===")
    
    val threadLocalExample = ThreadLocalExample()
    
    // 메인 스레드에서 설정
    threadLocalExample.value = "메인 스레드 값"
    println("메인 스레드: ${threadLocalExample.value}")
    
    // 다른 스레드에서 다른 값 설정
    Thread {
        threadLocalExample.value = "작업 스레드 값"
        println("작업 스레드: ${threadLocalExample.value}")
    }.apply { start(); join() }
    
    // 메인 스레드 값은 유지됨
    println("메인 스레드 (다시): ${threadLocalExample.value}")
}

// 1. 지연 초기화 예제
class LazyExample {
    val expensiveValue: String by lazy {
        println("무거운 계산 수행 중...")
        Thread.sleep(1000) // 시뮬레이션
        "계산 완료된 값"
    }
}

// 2. 관찰 가능한 프로퍼티
class ObservableExample {
    var name: String by Delegates.observable("초기값") { property, oldValue, newValue ->
        println("${property.name}이 '$oldValue'에서 '$newValue'로 변경됨")
    }
}

// 3. 변경 가로채기
class VetoableExample {
    var positiveNumber: Int by Delegates.vetoable(0) { property, oldValue, newValue ->
        if (newValue >= 0) {
            println("${property.name}: $oldValue -> $newValue (승인)")
            true
        } else {
            println("${property.name}: $newValue (거부 - 음수는 허용되지 않음)")
            false
        }
    }
}

// 4. 맵에 위임
class UserFromMap(private val map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
    val email: String by map
}

class MutableUserFromMap(private val map: MutableMap<String, Any?>) {
    var name: String by map
    var age: Int by map
}

// 5. 커스텀 위임 클래스
class LoggingDelegate<T>(private var value: T) : ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        println("[GET] ${property.name} = $value")
        return value
    }
    
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        println("[SET] ${property.name} = $value (이전: ${this.value})")
        this.value = value
    }
}

class CustomDelegateExample {
    var value: String by LoggingDelegate("기본값")
}

// 6. 싱글톤 패턴 위임
class SingletonDelegate<T>(private val initializer: () -> T) : ReadOnlyProperty<Any?, T> {
    @Volatile
    private var _value: T? = null
    
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return _value ?: synchronized(this) {
            _value ?: initializer().also { _value = it }
        }
    }
}

class AppConfig private constructor() {
    var setting: String = "기본 설정"
    
    companion object {
        val getInstance: AppConfig by SingletonDelegate { AppConfig() }
    }
}

// 7. 캐시 위임
class CacheDelegate<T>(private val calculator: () -> T) : ReadOnlyProperty<Any?, T> {
    private var _value: T? = null
    private var _isCalculated = false
    
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return if (_isCalculated) {
            println("캐시에서 반환")
            _value!!
        } else {
            println("새로 계산")
            val result = calculator()
            _value = result
            _isCalculated = true
            result
        }
    }
    
    fun clearCache() {
        _value = null
        _isCalculated = false
    }
}

class CacheExample {
    private val cacheDelegate = CacheDelegate {
        Thread.sleep(500) // 시뮬레이션
        (1..1000).sum()
    }
    
    val expensiveCalculation: Int by cacheDelegate
    
    fun clearCache() = cacheDelegate.clearCache()
}

// 8. 프로퍼티 검증 위임
class ValidatingDelegate<T>(
    private var value: T,
    private val validator: (T) -> Boolean,
    private val errorMessage: String
) : ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = value
    
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (validator(value)) {
            this.value = value
        } else {
            throw IllegalArgumentException("${property.name}: $errorMessage")
        }
    }
}

class ValidationExample {
    var email: String by ValidatingDelegate(
        "",
        { it.contains("@") && it.contains(".") },
        "유효한 이메일 주소를 입력해주세요"
    )
}

// 9. 스레드 로컬 위임
class ThreadLocalDelegate<T>(private val initialValue: () -> T) : ReadWriteProperty<Any?, T> {
    private val threadLocal = ThreadLocal.withInitial(initialValue)
    
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return threadLocal.get()
    }
    
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        threadLocal.set(value)
    }
}

class ThreadLocalExample {
    var value: String by ThreadLocalDelegate { "기본값" }
}