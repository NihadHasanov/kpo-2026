package studying.withsolid.model;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Report - это просто данные отчёта: заголовок, дата, время и цифры продаж.
// Он ничего не знает про файлы, почту или консоль

@Builder
public record Report(
        String title,
        LocalDate date,
        LocalTime time,
        int carsSold,
        int motorcyclesSold
) {
    // Свой формат времени, чтобы секунды не пропадали (12:00   ==>   12:00:00)
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");


    // Как отчёт выглядит в виде текста - для файла и для вывода
    @Override
    public String toString() {
        return title + "\n"
                + "Дата: " + date + "\n"
                + "Время: " + time.format(TIME_FORMAT) + "\n"
                + "--------------------------------\n"
                + "Продано автомобилей: " + carsSold + " шт.\n"
                + "Продано мотоциклов: " + motorcyclesSold + " шт.\n"
                + "--------------------------------\n";
    }
}