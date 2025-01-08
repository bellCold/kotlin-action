package src

class Member(
    val memberStatus: MemberStatus
) : BaseEntity()

enum class MemberStatus {
    ACTIVE,
    NONE,
}