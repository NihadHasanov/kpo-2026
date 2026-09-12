package studying.withsolid.service.impl;

import studying.withsolid.exception.ApplicationErrorCode;
import studying.withsolid.exception.ApplicationException;
import studying.withsolid.model.Report;
import studying.withsolid.service.ReportSaver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

/**
 * Реализация ReportSaver, сохраняющая текстовое представление отчёта в файл.
 * Имя файла формируется из даты и времени отчёта(report-<дата>-<время>.txt),
 * чтобы каждый новый отчёт создавал отдельный файл и они не затирали друг друга на диске..
 */

public class TextReportSaver implements ReportSaver {

    // Папка, куда складываем все отчёты.
    private static final Path REPORTS_DIRECTORY = Path.of("reports");

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH-mm-ss");


    /**
     * Сохраняет отчёт в текстовый файл.
     *
     * @param report отчёт для сохранения
     * @throws ApplicationException если отчёт равен null, содержит отрицательные
     *                              показатели или произошла ошибка записи файла
     */
    @Override
    public void save(Report report) {
        // Проверяем данные прямо в точке использования - если отчёта нет,
        // сохранять нечего, сразу говорим об этом понятной ошибкой.
        if (report == null) {
            throw new ApplicationException(ApplicationErrorCode.VALIDATION_ERROR, "Отчёт не может быть пустым");
        }
        if (report.carsSold() < 0 || report.motorcyclesSold() < 0) {
            throw new ApplicationException(ApplicationErrorCode.VALIDATION_ERROR,
                    "Показатели отчёта не могут быть отрицательными");
        }

        var fileName = "report-" + report.date().format(DATE_FORMAT)
                + "-" + report.time().format(TIME_FORMAT) + ".txt";
        var filePath = REPORTS_DIRECTORY.resolve(fileName);

        try {
            Files.createDirectories(REPORTS_DIRECTORY);
            Files.writeString(filePath, report.toString());
        } catch (IOException exception) {
            throw new ApplicationException(ApplicationErrorCode.FILE_WRITE_ERROR,
                    "Не удалось сохранить отчёт в файл " + filePath, exception);
        }
    }
}