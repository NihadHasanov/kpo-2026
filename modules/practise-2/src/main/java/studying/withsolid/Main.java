package studying.withsolid;

import studying.withsolid.model.Report;
import studying.withsolid.service.ReportService;
import studying.withsolid.service.impl.EmailReportSender;
import studying.withsolid.service.impl.TextReportSaver;

import java.time.LocalDateTime;

public class Main {
    /**
     * Runs the report creation, persistence, and delivery demonstration.
     */
    static void main() {
        var now = LocalDateTime.now();
        var report = Report.builder()
                .title("Отчёт")
                .date(now.toLocalDate())
                .time(now.toLocalTime())
                .carsSold(100)
                .motorcyclesSold(50)
                .build();

        var reportService = new ReportService(
                new TextReportSaver(),      // реализует ReportSaver
                new EmailReportSender()     // реализует ReportSender
        );

        reportService.process(report, "example@example.com");
    }
}
