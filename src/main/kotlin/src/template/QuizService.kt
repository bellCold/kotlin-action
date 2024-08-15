package src.template

import java.time.LocalDateTime

class QuizService(private val cloverService: CloverService) : CloverAward() {

    fun choiceQuiz() {
        payCloverTest(LocalDateTime.now(), cloverService)
    }

    override fun verifyParticipateDate(date: LocalDateTime) {
        if (date.isBefore(LocalDateTime.now().plusDays(7))) {
            return
        }

        if (date.isAfter(LocalDateTime.now().plusDays(7))) {
            return
        }
    }

    override fun getCloverPolicy(policyType: String) {

    }
}