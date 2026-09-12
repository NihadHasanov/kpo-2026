package studying.withsolid.service;

import studying.withsolid.model.Report;

// Контракт "уметь сохранить отчёт". Как именно - решает реализация
@FunctionalInterface
public interface ReportSaver {
    /**
     * Сохраняет переданный отчёт.
     *
     * @param report отчёт, который нужно сохранить
     */
    void save(Report report);
}
