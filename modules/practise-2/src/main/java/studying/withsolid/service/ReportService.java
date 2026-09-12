package studying.withsolid.service;

import studying.withsolid.model.Report;

/**
 * Основной сценарий работы с отчётами: сначала сохранить, затем отправить.
 * Зависит только от контрактов ReportSaver и ReportSender.
 */
public final class ReportService {

    private final ReportSaver reportSaver;
    private final ReportSender reportSender;

    public ReportService(ReportSaver reportSaver, ReportSender reportSender) {
        this.reportSaver = reportSaver;
        this.reportSender = reportSender;
    }

    /**
     * Сохраняет отчёт, а затем отправляет его на указанный email.
     *
     * @param report отчёт для обработки
     * @param email  email получателя
     */
    public void process(Report report, String email) {
        // Порядок важен по заданию: сначала сохранение, потом отправка.
        reportSaver.save(report);
        reportSender.send(report, email);
    }
}