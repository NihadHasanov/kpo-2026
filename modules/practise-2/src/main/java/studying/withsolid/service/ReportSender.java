package studying.withsolid.service;

import studying.withsolid.model.Report;

/**
 * Контракт отправки отчёта получателю.
 * Реализации могут отправлять отчёт по email, в Telegram или другим каналом.
 */
@FunctionalInterface
public interface ReportSender {
    /**
     * Отправляет отчёт указанному получателю.
     *
     * @param report отчёт для отправки
     * @param email  email получателя
     */
    void send(Report report, String email);
}