package studying.withsolid.service.impl;

import studying.withsolid.exception.ApplicationErrorCode;
import studying.withsolid.exception.ApplicationException;
import studying.withsolid.model.Report;
import studying.withsolid.service.ReportSender;

/**
 * Имитация отправки отчёта по email через вывод сообщения в консоль.
 */
public class EmailReportSender implements ReportSender {

    /**
     * Имитирует отправку отчёта на указанный email.
     *
     * @param report отчёт для отправки
     * @param email  email получателя
     * @throws ApplicationException если отчёт или email равны null/пустой строке
     */
    @Override
    public void send(Report report, String email) {
        if (report == null) {
            throw new ApplicationException(ApplicationErrorCode.VALIDATION_ERROR, "Отчёт не может быть пустым");
        }
        if (email == null || email.isBlank()) {
            throw new ApplicationException(ApplicationErrorCode.VALIDATION_ERROR,
                    "Email получателя не может быть пустым");
        }

        System.out.printf("Отправка отчёта \"%s\" на email: %s%n", report.title(), email);
    }
}