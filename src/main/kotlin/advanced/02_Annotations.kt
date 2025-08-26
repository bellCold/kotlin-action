package advanced

import kotlin.reflect.full.*

fun main() {
    println("=== 기본 애노테이션 사용 ===")
    
    val user = User("김철수", 30, "user@example.com")
    user.greet()
    
    println("\n=== 애노테이션과 리플렉션 ===")
    
    // 클래스의 애노테이션 조회
    val userClass = User::class
    println("클래스 애노테이션: ${userClass.annotations}")
    
    // Entity 애노테이션 정보
    val entityAnnotation = userClass.findAnnotation<Entity>()
    entityAnnotation?.let {
        println("테이블명: ${it.tableName}")
    }
    
    // 프로퍼티의 애노테이션 조회
    userClass.memberProperties.forEach { property ->
        println("\n프로퍼티: ${property.name}")
        property.annotations.forEach { annotation ->
            when (annotation) {
                is Column -> println("  컬럼: ${annotation.name}, 크기: ${annotation.length}")
                is NotNull -> println("  필수 필드")
                is Email -> println("  이메일 형식")
            }
        }
    }
    
    println("\n=== 함수 애노테이션 ===")
    
    val greetMethod = userClass.functions.find { it.name == "greet" }
    greetMethod?.annotations?.forEach { annotation ->
        when (annotation) {
            is Deprecated -> println("  경고: ${annotation.message}")
            is JvmStatic -> println("  정적 메서드")
        }
    }
    
    println("\n=== 커스텀 애노테이션 처리기 ===")
    
    val processor = ValidationProcessor()
    val validationResult = processor.validate(user)
    
    println("검증 결과: $validationResult")
    
    val invalidUser = User("", -5, "invalid-email")
    val invalidResult = processor.validate(invalidUser)
    println("유효하지 않은 사용자 검증: $invalidResult")
    
    println("\n=== 애노테이션 기반 의존성 주입 시뮬레이션 ===")
    
    val serviceContainer = SimpleContainer()
    serviceContainer.register<UserRepository> { UserRepositoryImpl() }
    serviceContainer.register<EmailService> { EmailServiceImpl() }
    
    val userService = UserService()
    serviceContainer.inject(userService)
    
    userService.processUser(user)
}

// 1. 기본 애노테이션들
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Entity(val tableName: String = "")

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Column(val name: String, val length: Int = 255)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class NotNull

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Email

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Cacheable(val ttl: Long = 3600)

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Inject

// 2. 복합 애노테이션
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Validated(
    val min: Int = Int.MIN_VALUE,
    val max: Int = Int.MAX_VALUE,
    val pattern: String = ""
)

// 3. 애노테이션이 적용된 클래스
@Entity(tableName = "users")
data class User(
    @Column(name = "user_name", length = 100)
    @NotNull
    val name: String,
    
    @Column(name = "age")
    @Validated(min = 0, max = 150)
    val age: Int,
    
    @Column(name = "email", length = 255)
    @NotNull
    @Email
    val email: String
) {
    @Deprecated("대신 introduce() 메서드를 사용하세요", ReplaceWith("introduce()"))
    fun greet() {
        println("안녕하세요, $name입니다.")
    }
    
    @Cacheable(ttl = 1800)
    fun introduce() {
        println("안녕하세요, 저는 $name이고 $age살입니다. 연락처: $email")
    }
}

// 4. 애노테이션 처리기
class ValidationProcessor {
    fun validate(obj: Any): Boolean {
        val kClass = obj::class
        
        return kClass.memberProperties.all { property ->
            val value = property.getter.call(obj)
            
            // NotNull 체크
            if (property.findAnnotation<NotNull>() != null && value == null) {
                println("${property.name}은 null이 될 수 없습니다.")
                return@all false
            }
            
            // Email 체크
            if (property.findAnnotation<Email>() != null && value is String) {
                if (!value.contains("@")) {
                    println("${property.name}은 유효한 이메일 형식이어야 합니다.")
                    return@all false
                }
            }
            
            // Validated 체크
            property.findAnnotation<Validated>()?.let { validation ->
                if (value is Int) {
                    if (value < validation.min || value > validation.max) {
                        println("${property.name}은 ${validation.min}과 ${validation.max} 사이여야 합니다.")
                        return@all false
                    }
                }
            }
            
            true
        }
    }
}

// 5. 의존성 주입 시뮬레이션
interface UserRepository {
    fun save(user: User)
}

interface EmailService {
    fun sendWelcomeEmail(email: String)
}

class UserRepositoryImpl : UserRepository {
    override fun save(user: User) {
        println("사용자 저장: ${user.name}")
    }
}

class EmailServiceImpl : EmailService {
    override fun sendWelcomeEmail(email: String) {
        println("환영 이메일 전송: $email")
    }
}

class UserService {
    @Inject
    lateinit var userRepository: UserRepository
    
    @Inject
    lateinit var emailService: EmailService
    
    fun processUser(user: User) {
        userRepository.save(user)
        emailService.sendWelcomeEmail(user.email)
    }
}

// 6. 간단한 DI 컨테이너
class SimpleContainer {
    private val services = mutableMapOf<String, () -> Any>()
    
    inline fun <reified T> register(noinline factory: () -> T) {
        services[T::class.qualifiedName!!] = factory
    }
    
    fun inject(target: Any) {
        val kClass = target::class
        
        kClass.memberProperties.forEach { property ->
            if (property.findAnnotation<Inject>() != null) {
                val serviceKey = property.returnType.toString()
                    .removePrefix("kotlin.reflect.KClass<")
                    .removeSuffix(">")
                    .replace("?", "")
                
                services[serviceKey]?.let { factory ->
                    if (property is kotlin.reflect.KMutableProperty<*>) {
                        property.setter.call(target, factory())
                    }
                }
            }
        }
    }
}