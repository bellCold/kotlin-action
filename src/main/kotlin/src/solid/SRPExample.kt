package src.solid

class ReportController(private val reportService: ReportService, private val emailService: EmailService) {
    fun generateAndSendReport(reportRequestDto: ReportRequestDto) {
        // 하나씩 하도록
        reportService.generateReport(reportRequestDto.reportContent)
        emailService.sendReportByEmail()
    }
}

class ReportService {
    fun generateReport(reportContent: String) {
        TODO("Not yet implemented")
    }

}

class ReportRepository {

}

class EmailService {
    fun sendReportByEmail() {
        TODO("Not yet implemented")
    }
}

data class ReportRequestDto(val reportContent: String, val to: String, val subject: String)