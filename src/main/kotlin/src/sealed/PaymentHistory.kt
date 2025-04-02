package src.sealed

import java.math.BigDecimal
import java.time.LocalDateTime

sealed class PaymentHistory {
    abstract val amount: BigDecimal
    abstract val timeStamp: LocalDateTime

    companion object {
        fun processPaymentHistory(payment: PaymentHistory){
            when (payment) {
                is CardPayment -> println("💳 카드 결제: ${payment.cardNumber.takeLast(4)} - ${payment.amount}원")
                is BankTransfer -> println("🏦 은행 이체: ${payment.bankName} - ${payment.amount}원")
                is SimplePayment -> println("📱 간편 결제: ${payment.provider} - ${payment.amount}원")
            }
        }
    }
}

data class CardPayment(override val amount: BigDecimal, override val timeStamp: LocalDateTime, val cardNumber: String) : PaymentHistory()
data class BankTransfer(override val amount: BigDecimal, override val timeStamp: LocalDateTime, val bankName: String, val accountNumber: String) : PaymentHistory()
data class SimplePayment(override val amount: BigDecimal, override val timeStamp: LocalDateTime, val provider: String, val transactionId: String) : PaymentHistory()

fun main() {
    val payments: List<PaymentHistory> = listOf(
        CardPayment(BigDecimal(50000), LocalDateTime.now(), "1234-5678-9012-3456"),
        BankTransfer(BigDecimal(100000), LocalDateTime.now(), "국민은행", "110-123-456789"),
        SimplePayment(BigDecimal(30000), LocalDateTime.now(), "카카오페이", "TXN-98765")
    )

    payments.forEach { payment -> PaymentHistory.processPaymentHistory(payment) }
}