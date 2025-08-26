package oop

fun main() {
    println("=== 기본 클래스 ===")
    val person1 = Person("김철수", 30)
    person1.introduce()
    person1.celebrateBirthday()
    person1.introduce()
    
    println("\n=== 프로퍼티와 접근자 ===")
    val person2 = PersonWithProperties("이영희", 25)
    println("이름: ${person2.name}")
    println("나이: ${person2.age}")
    println("성인 여부: ${person2.isAdult}")
    
    person2.age = 17
    println("나이 변경 후 성인 여부: ${person2.isAdult}")
    
    println("\n=== 데이터 클래스 ===")
    val student1 = Student("박민수", 20, "컴퓨터공학")
    val student2 = Student("박민수", 20, "컴퓨터공학")
    val student3 = student1.copy(major = "수학과")
    
    println("student1: $student1")
    println("student2: $student2")
    println("student1 == student2: ${student1 == student2}") // true
    println("student3: $student3")
    
    // 구조 분해 선언
    val (name, age, major) = student1
    println("이름: $name, 나이: $age, 전공: $major")
    
    println("\n=== 상속 ===")
    val teacher = Teacher("최교수", 45, "물리학")
    teacher.introduce()
    teacher.teach()
    
    val student = StudentClass("김학생", 22, "화학과")
    student.introduce()
    student.study()
}

// 1. 기본 클래스
class Person(var name: String, var age: Int) {
    fun introduce() {
        println("안녕하세요, 저는 $name이고 $age살입니다.")
    }
    
    fun celebrateBirthday() {
        age++
        println("생일 축하합니다! 이제 $age살이 되었습니다.")
    }
}

// 2. 프로퍼티와 커스텀 접근자
class PersonWithProperties(
    var name: String,
    ageValue: Int
) {
    var age: Int = ageValue
        set(value) {
            if (value >= 0) {
                field = value
            } else {
                println("나이는 0 이상이어야 합니다.")
            }
        }
    
    // 계산된 프로퍼티
    val isAdult: Boolean
        get() = age >= 18
}

// 3. 데이터 클래스 - equals, hashCode, toString, copy 자동 생성
data class Student(
    val name: String,
    val age: Int,
    val major: String
)

// 4. 상속을 위한 기본 클래스 (open 키워드 필요)
open class Human(val name: String, val age: Int) {
    open fun introduce() {
        println("저는 $name이고, $age살입니다.")
    }
}

// 5. 상속받은 클래스들
class Teacher(name: String, age: Int, val subject: String) : Human(name, age) {
    override fun introduce() {
        super.introduce() // 부모 클래스 메서드 호출
        println("저는 $subject을 가르치는 교사입니다.")
    }
    
    fun teach() {
        println("$subject 수업을 진행합니다.")
    }
}

class StudentClass(name: String, age: Int, val major: String) : Human(name, age) {
    override fun introduce() {
        super.introduce()
        println("저는 $major을 전공하는 학생입니다.")
    }
    
    fun study() {
        println("$major을 열심히 공부하고 있습니다.")
    }
}