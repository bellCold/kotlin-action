package src.solid

class Report

fun interface ReportGenerator {
    fun generateReport(report: Report): String
}

class PdfReportGenerator : ReportGenerator {
    override fun generateReport(report: Report): String {
        return "PDF report"
    }
}

class ExcelReportGenerator : ReportGenerator {
    override fun generateReport(report: Report): String {
        return "Excel report"
    }
}

class ReportGeneratorService(private val reportGenerators: Map<String, ReportGenerator>) {
    fun generateReport(report: Report, reportType: String): String {
        return reportGenerators.getOrDefault(reportType, unsupportedReportGenerator()).generateReport(report)
    }

    private fun unsupportedReportGenerator(): ReportGenerator {
        return ReportGenerator { "Unsupported report type" }
    }
}