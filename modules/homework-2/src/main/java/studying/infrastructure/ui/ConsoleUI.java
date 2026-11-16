package studying.infrastructure.ui;

import studying.application.PosFacade;
import studying.domain.exception.DomainException;
import studying.domain.model.Criterion;
import studying.domain.model.Student;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.UUID;

/** Консольный интерфейс. */
public final class ConsoleUI {
    private final PosFacade facade;

    /**
     * Конструктор.
     * @param apiFacade фасад
     */
    public ConsoleUI(final PosFacade apiFacade) {
        this.facade = apiFacade;
    }

    /**
     * Запуск меню.
     * @param in ввод
     * @param out вывод
     */
    public void start(final Scanner in, final PrintStream out) {
        while (true) {
            out.println("\n=== ПОС: Меню ===");
            out.println("1. Добавить студента");
            out.println("2. Добавить критерий");
            out.println("3. Выставить оценку");
            out.println("4. Показать итог по студенту");
            out.println("0. Выход");
            out.print("Выберите: ");

            if (!in.hasNextLine()) {
                break;
            }
            String choice = in.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> {
                        out.print("Имя студента: ");
                        Student s = facade.registerStudent(
                                in.nextLine().trim());
                        out.println("Студент добавлен! ID: " + s.id());
                    }
                    case "2" -> {
                        out.print("Название критерия: ");
                        String name = in.nextLine().trim();
                        out.print("Макс. балл: ");
                        double max = Double.parseDouble(
                                in.nextLine().trim());
                        out.print("Вес: ");
                        double weight = Double.parseDouble(
                                in.nextLine().trim());
                        Criterion c = facade.registerCriterion(
                                name, max, weight);
                        out.println("Критерий добавлен! ID: " + c.id());
                    }
                    case "3" -> {
                        out.print("ID студента: ");
                        String studentId = in.nextLine().trim();
                        out.print("ID критерия: ");
                        String criterionId = in.nextLine().trim();
                        out.print("Балл: ");
                        double score = Double.parseDouble(
                                in.nextLine().trim());
                        facade.gradeStudent(UUID.fromString(studentId),
                                UUID.fromString(criterionId), score);
                        out.println("Оценка выставлена!");
                    }
                    case "4" -> {
                        out.print("ID студента: ");
                        String studentId = in.nextLine().trim();
                        double result = facade.getStudentResult(
                                UUID.fromString(studentId));
                        out.printf("Итоговый балл: %.2f%n", result);
                    }
                    case "0" -> {
                        out.println("До свидания!");
                        return;
                    }
                    default -> out.println("Неизвестная команда");
                }
            } catch (DomainException e) {
                out.println("Ошибка домена: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                out.println("Ошибка ввода: " + e.getMessage());
            }
        }
    }
}
