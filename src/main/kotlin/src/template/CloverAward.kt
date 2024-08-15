package src.template

import java.time.LocalDateTime

abstract class CloverAward {
    protected abstract fun verifyParticipateDate(date: LocalDateTime)
    protected abstract fun getCloverPolicy(policyType: String)

    fun payCloverTest(date: LocalDateTime, cloverService: CloverService) {
        verifyParticipateDate(date)
        cloverService.pay(3)
    }

}