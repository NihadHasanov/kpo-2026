package studying.application;

import studying.domain.exception.DomainException;
import studying.domain.factory.EvaluationFactory;
import studying.domain.model.Criterion;
import studying.domain.model.Evaluation;
import studying.domain.model.Student;
import studying.domain.port.CriterionRepository;
import studying.domain.port.EvaluationRepository;
import studying.domain.port.StudentRepository;
import studying.domain.service.GradeCalculator;
import java.util.List;
import java.util.UUID;

/** Сценарии работы с модулем ПОС. */
public final class PosManagementService {
    private final StudentRepository studentRepository;
    private final CriterionRepository criterionRepository;
    private final EvaluationRepository evaluationRepository;
    private final GradeCalculator gradeCalculator;

    /**
     * Конструктор.
     * @param studentRepo репозиторий студентов
     * @param criterionRepo репозиторий критериев
     * @param evalRepo репозиторий оценок
     * @param calculator калькулятор
     */
    public PosManagementService(final StudentRepository studentRepo,
                                final CriterionRepository criterionRepo,
                                final EvaluationRepository evalRepo,
                                final GradeCalculator calculator) {
        this.studentRepository = studentRepo;
        this.criterionRepository = criterionRepo;
        this.evaluationRepository = evalRepo;
        this.gradeCalculator = calculator;
    }

    /**
     * Добавить студента.
     * @param name имя
     * @return созданный студент
     */
    public Student addStudent(final String name) {
        Student student = new Student(UUID.randomUUID(), name);
        studentRepository.save(student);
        return student;
    }

    /**
     * Добавить критерий.
     * @param name название
     * @param maxScore макс балл
     * @param weight вес
     * @return созданный критерий
     */
    public Criterion addCriterion(final String name,
                                  final double maxScore,
                                  final double weight) {
        Criterion criterion = new Criterion(
                UUID.randomUUID(), name, maxScore, weight);
        criterionRepository.save(criterion);
        return criterion;
    }

    /**
     * Выставить оценку.
     * @param studentId id студента
     * @param criterionId id критерия
     * @param score балл
     */
    public void assignGrade(final UUID studentId,
                            final UUID criterionId,
                            final double score) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new DomainException("Студент не найден"));
        Criterion criterion = criterionRepository.findById(criterionId)
                .orElseThrow(() -> new DomainException("Критерий не найден"));
        Evaluation evaluation = EvaluationFactory.create(
                student, criterion, score);
        evaluationRepository.save(evaluation);
    }

    /**
     * Посчитать итог.
     * @param studentId id студента
     * @return итоговый балл
     */
    public double calculateFinalGrade(final UUID studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new DomainException("Студент не найден"));
        List<Evaluation> evaluations =
                evaluationRepository.findByStudentId(studentId);
        List<Criterion> criteria = criterionRepository.findAll();
        return gradeCalculator.calculateFinalGrade(
                student, evaluations, criteria);
    }
}
