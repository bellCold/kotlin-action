package src

interface MemberRepository : JpaRepository, MemberQueryDslPersistence {
}

interface MemberQueryDslPersistence {
    fun someQuery()
}

class MemberQueryDsl : MemberQueryDslPersistence {
    override fun someQuery() {
    }

}


interface JpaRepository