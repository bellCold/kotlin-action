package src

abstract class BaseEntity {
    val id: Long? = null
}

val BaseEntity.requiredId: Long
    get() = id ?: throw RuntimeException("Entity required id not set")