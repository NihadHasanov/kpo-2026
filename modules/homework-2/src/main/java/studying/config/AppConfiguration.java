package studying.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import studying.application.PosFacade;
import studying.application.PosManagementService;
import studying.domain.port.CriterionRepository;
import studying.domain.port.EvaluationRepository;
import studying.domain.port.StudentRepository;
import studying.domain.service.GradeCalculator;
import studying.domain.service.WeightedGradeCalculator;
import studying.infrastructure.persistence.InMemoryCriterionRepository;
import studying.infrastructure.persistence.InMemoryEvaluationRepository;
import studying.infrastructure.persistence.InMemoryStudentRepository;
import studying.infrastructure.ui.ConsoleUI;

/** Конфигурация Spring для сборки графа зависимостей. */
@Configuration
public class AppConfiguration {

    /** @return репозиторий студентов */
    @Bean
    public StudentRepository studentRepository() {
        return new InMemoryStudentRepository();
    }

    /** @return репозиторий критериев */
    @Bean
    public CriterionRepository criterionRepository() {
        return new InMemoryCriterionRepository();
    }

    /** @return репозиторий оценок */
    @Bean
    public EvaluationRepository evaluationRepository() {
        return new InMemoryEvaluationRepository();
    }

    /** @return калькулятор оценок */
    @Bean
    public GradeCalculator gradeCalculator() {
        return new WeightedGradeCalculator();
    }

    /**
     * @param studentRepo репозиторий
     * @param criterionRepo репозиторий
     * @param evalRepo репозиторий
     * @param calculator калькулятор
     * @return сервис
     */
    @Bean
    public PosManagementService posManagementService(
            final StudentRepository studentRepo,
            final CriterionRepository criterionRepo,
            final EvaluationRepository evalRepo,
            final GradeCalculator calculator) {
        return new PosManagementService(
                studentRepo, criterionRepo, evalRepo, calculator);
    }

    /**
     * @param service сервис
     * @return фасад
     */
    @Bean
    public PosFacade posFacade(final PosManagementService service) {
        return new PosFacade(service);
    }

    /**
     * @param facade фасад
     * @return UI
     */
    @Bean
    public ConsoleUI consoleUI(final PosFacade facade) {
        return new ConsoleUI(facade);
    }
}
