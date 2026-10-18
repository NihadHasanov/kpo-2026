# Домашнее задание №1. Сервис «ВышКат»

Консольное приложение учёта парка кикшеринга «ВышКат». Реализована доменная модель транспорта и вещей, сервис техосмотра, отчёты по парку, а также покрытие тестами с подменой зависимости техосмотра через Spring DI.

## Запуск

Программа сначала выполняет демонстрационный сценарий
(показывает все возможности на фиксированных данных), а затем
переходит в интерактивный режим с меню:

- добавить самокат, велосипед, электровелосипед, шлем, док-станцию,
  зарядный шкаф;
- показать отчёт по парку (количество, суммарная энергия, список
  для новичков, инвентарные номера);
- выход по пункту `0`.

Полная проверка модуля (компиляция, тесты, Checkstyle, JaCoCo):

    ./gradlew :modules:homework-1:clean :modules:homework-1:check

Запуск интерактивного приложения (рекомендуется — работает с
консольным вводом):

    ./gradlew :modules:homework-1:installDist
    ./modules/homework-1/build/install/homework-1/bin/homework-1

Либо через Gradle:

    ./gradlew :modules:homework-1:run

Отчёты после `check`:

- тесты: `modules/homework-1/build/reports/tests/test/index.html`;
- покрытие JaCoCo: `modules/homework-1/build/reports/jacoco/test/html/index.html`;
- Checkstyle: `modules/homework-1/build/reports/checkstyle/main.html` и `modules/homework-1/build/reports/checkstyle/test.html`.

## Архитектура

    studying/
    ├── Main.java                — точка входа: демо + интерактивное меню
    ├── configuration/           — Spring-конфигурация (DI-контейнер)
    ├── domain/                  — модель: транспорт, вещи, абстракции
    ├── exception/               — прикладная ошибка и её коды
    ├── fleet/                   — хранение парка и отчёты
    ├── inspection/              — сервис техосмотра
    └── presentation/            — UI: демо, консольное меню, печать отчёта
    


- `domain/` — интерфейсы `EnergyConsumer` и `Inventoried`, иерархии `Vehicle → MotorizedVehicle → Scooter / ElectricBicycle`, `Bicycle`, а также `Thing → Helmet / DockingStation / ChargingCabinet`.
- `inspection/` — контракт `ServiceCenter`, результат `InspectionResult` и реализация по умолчанию `DefaultServiceCenter`.
- `fleet/` — `Fleet`: хранение единиц парка и отчёты, зависит только от `ServiceCenter`.
- `configuration/` — Spring-конфигурация с `@Bean` для `ServiceCenter` и `Fleet`.
- `exception/` — `ApplicationException` с кодами `VALIDATION_ERROR` и `INSPECTION_REJECTED`.
- `presentation/` — `DemoRunner` (демо-сценарий), `FleetConsoleApp` (интерактивное меню), `FleetReporter` (печать отчёта). Бизнес-логика (`Fleet`) не знает о `System.out`.

Слои разделены:

- UI (`presentation/`) — работает с `System.in` / `System.out`, не знает о внутреннем устройстве `Fleet`.
- Бизнес-логика (`fleet/`) — `Fleet` не знает ни о консоли, ни о `PrintStream`. Это позволяет тестировать логику независимо от UI и подменять ввод в тестах через `ByteArrayInputStream`.
- Правило техосмотра (`inspection/`) — `ServiceCenter`, подменяется в тестах через DI (`@MockitoBean` или лямбду).
- Модель (`domain/`) — только данные и правила, без зависимостей наружу.

## Обязательные вопросы

### Наиболее явно применённый принцип SOLID

**Dependency Inversion.** `Fleet` (см. `fleet/Fleet.java`) зависит от интерфейса `ServiceCenter`, а не от конкретной реализации. В `configuration/ApplicationConfiguration.java` Spring собирает `DefaultServiceCenter` и передаёт его в конструктор `Fleet`. Именно поэтому в `FleetSpringIntegrationTest` мы подменяем зависимость через `@MockitoBean` и проверяем сценарий отказа техосмотра.

Дополнительно явно работают:

- **Open/Closed** — новый вид транспорта (например, `Moped`) добавляется как новый класс-наследник `MotorizedVehicle`; `Fleet`, `ServiceCenter` и `Main` не меняются.
- **Interface Segregation** — `EnergyConsumer` и `Inventoried` — маленькие независимые интерфейсы; не каждый потребитель энергии обязан быть инвентаризируемым, и наоборот.

### Сознательно ограниченный принцип

**Single Responsibility.** `Fleet` одновременно хранит единицы парка и формирует отчёты (`totalUnits`, `totalDailyEnergyKwh`, `noviceFriendlyVehicles`, `allItems`). Строгий SRP потребовал бы вынести отчёты в отдельный `FleetReporter` и разделить хранение и отчётность. Для размера этого ДЗ это было бы overengineering: два класса вместо одного, дополнительная связка, никакой реальной выгоды. Решение осознанное, не декоративное.

### Что сломается при расширении

**Новый вид транспорта** (например, `Moped`): добавляется новый класс-наследник `MotorizedVehicle`. Существующий код не меняется. Если у нового вида будет особенность в техосмотре — появится новая реализация `ServiceCenter`, старая не тронута.

**Сотрудник службы эксплуатации**: сотрудники не идут на баланс, поэтому в `Fleet` не добавляются. Создаётся новый класс (или интерфейс) `Employee`, не реализующий `Inventoried`. Существующий `Fleet` не меняется.

**Склад запчастей**: аналогично вещам. Если запчасти идут на баланс — новый наследник `Thing`; если нет — отдельная иерархия. `Fleet` не меняется в обоих случаях.

**Станция аренды**: новая сущность, не входит в `Fleet` как единица парка. Отдельный класс + сервис. `Fleet` не меняется.

### Тест, фиксирующий правила

- **Правило техосмотра** закреплено тестом `FleetTest.rejectsVehicleWhenInspectionRejects` (unit) и `FleetSpringIntegrationTest.mockCanReject` (интеграционный, с подменой через `@MockitoBean`).
- **Правило «для новичков»** — параметризованными тестами `VehicleTest.suitableForNoviceWhenSimplicityAtLeastSix` и `VehicleTest.notSuitableForNoviceBelowSix`.

**Как подменяется зависимость.** `Fleet` принимает `ServiceCenter` через конструктор. В unit-тестах передаётся лямбда-реализация (`item -> InspectionResult.ok()`), в интеграционном — Spring подменяет бин через `@MockitoBean` на интерфейс `ServiceCenter`.

### Роль ИИ

ИИ использовался как ассистент-черновик: с ним обсуждалась структура
пакетов, генерировались заготовки классов, подсказки по API
(Spring, JUnit, Lombok-подобные приёмы) и синтаксису. Готовая
финальная архитектура, работающая логика, тесты и конфигурация
собраны вручную — ИИ выдавал заготовки, которые приходилось
переписывать под требования задания и практики семинариста.

**Что именно делал ИИ (черновики, которые потом перерабатывались):**

- **Структура пакетов (черновик).** Изначально ИИ предлагал
  минималистичную структуру `studying.model`, `studying.service`,
  `studying.factory`. Итоговая структура — `domain/`, `exception/`,
  `inspection/`, `fleet/`, `configuration/`, `presentation/` —
  спроектирована мной по образцу практик семинариста.

- **Заготовки классов модели (черновик).** ИИ написал скелеты
  `Vehicle`, `MotorizedVehicle`, `Scooter`, `Bicycle`,
  `ElectricBicycle`, `Thing`, `Helmet`, `DockingStation`,
  `ChargingCabinet`. В них не было валидации полей, часть
  конструкторов не учитывала требования задания (диапазон 1–10 для
  простоты, положительная энергия). Валидация, диапазоны и проверки
  добавлены мной.

- **Заготовки интерфейсов (черновик).** `EnergyConsumer`,
  `Inventoried` — базовые варианты были от ИИ, но с лишними
  методами. Оставлены только те, что нужны по ТЗ: `dailyEnergyKwh()`
  и `inventoryNumber()` / `displayName()`. Аннотация
  `@FunctionalInterface` — моя правка.

- **Черновик `Fleet`.** Первая версия `Fleet` от ИИ содержала
  хардкод отчётов, дублирующиеся методы и не имела зависимости от
  `ServiceCenter` — техосмотр был внутри самой фабрики. Я вынес
  `ServiceCenter` в отдельный интерфейс, добавил инъекцию через
  конструктор, реализовал методы `totalUnits()`,
  `totalDailyEnergyKwh()`, `noviceFriendlyVehicles()`,
  `allItems()`, `accept(Inventoried)` с проверкой результата.

- **Подсказки по API Spring / JUnit.** ИИ подсказывал, как
  использовать `@SpringBootTest`, `@MockitoBean`,
  `@Configuration(proxyBeanMethods = false)`, `@ParameterizedTest` с
  `@ValueSource`, `@TempDir`. Финальный вид конфигурации и тестов —
  результат ручной доработки под структуру практик 3 и 4.

- **Подсказки по `build.gradle.kts`.** ИИ подсказал идею настройки
  Checkstyle через `resources.text.fromArchiveEntry(sunChecks, ...)`,
  JaCoCo через `finalizedBy(tasks.jacocoTestReport)`,
  `standardInput = System.in` для `JavaExec`. Точная конфигурация
  скопирована из практик семинариста и дополнена моими правками —
  `.replace(...)` для отключения `JavadocPackage` и
  `JavadocVariable`, `.replace(...)` для подключения
  `SuppressWarningsFilter` и `SuppressWarningsHolder`.

- **Часть Javadoc.** ИИ писал черновики Javadoc для публичных
  методов. Формулировки переписаны мной, чтобы соответствовать
  Checkstyle Sun и избегать `@throws` без обоснования.

- **Черновики тестов (часть).** ИИ подготовил первые версии
  `VehicleTest`, `MotorizedVehicleTest`, `FleetTest`,
  `DefaultServiceCenterTest`. В них не было граничных случаев
  (нулевое значение, пустая строка, `null`), параметризованных
  тестов, проверки исключений через `assertThrows`. Всё это
  добавлено вручную.

- **Черновик `Main`.** ИИ предлагал один класс со всем
  функционалом. Разделение на `Main` + `DemoRunner` +
  `FleetConsoleApp` + `FleetReporter` — моё решение.

- **Черновик `FleetConsoleApp`.** Базовое меню было от ИИ, но без
  обработки EOF (`readLine() == null`), без `String.strip()`, без
  поддержки запятой как десятичного разделителя. Всё это —
  мои правки после падения приложения на реальном вводе.

**Что сделано мной самостоятельно:**

- разобрал практики семинариста (1–6) — оттуда взят формат
  пакетов, Checkstyle-конфиг, JaCoCo, Spring DI через
  `@Configuration` + `@Bean`, `@MockitoBean` в тестах;
- выделил слой `presentation/` (`DemoRunner`, `FleetConsoleApp`,
  `FleetReporter`), чтобы отделить UI от бизнес-логики. Изначально
  у ИИ всё было в одном `Main`;
- добавил интерактивное меню (`FleetConsoleApp`) после уточнения
  от лектора — в исходном задании был только демо-сценарий;
- переписал `FleetConsoleApp.readLine` — обработка `null` (EOF),
  `String.strip()` вместо `trim()`, поддержка запятой как
  десятичного разделителя (`1,5`);
- переписал `Fleet`: убрал хардкод из ИИ, добавил методы
  `totalUnits`, `totalDailyEnergyKwh`, `noviceFriendlyVehicles`,
  `allItems`, реализовал проверку через `accept(Inventoried)`;
- добавил валидацию в `Vehicle` (диапазон простоты 1–10) и в
  `MotorizedVehicle` (`energy > 0`) — у ИИ этого не было;
- переименовал `InspectionResult.accepted()` в `InspectionResult.ok()`
  — статический фабричный метод конфликтовал с accessor-методом
  record-а `accepted()`;
- переписал `ApplicationConfiguration` под
  `@Configuration(proxyBeanMethods = false)`;
- дополнил тесты: `FleetConsoleAppTest` (12 сценариев на все пункты
  меню), `FleetReporterTest` (пустой и непустой парк),
  `DemoRunnerTest` — это подняло покрытие `presentation` с 29 % до
  93 % и общий JaCoCo до 90 %;
- исправил нарушения Checkstyle: `HiddenField` в конструкторах
  (переименовал параметры в `number`, `name`, `simplicity`,
  `energyKwh`), `DesignForExtension` (добавил `final` к геттерам),
  `NewlineAtEndOfFile`, `MagicNumber` через `@SuppressWarnings`;
- добавил `standardInput = System.`in`` в `build.gradle.kts`, чтобы
  `./gradlew run` работал с консольным вводом;
- сформулировал обоснование SOLID в этом README (DIP, OCP, ISP,
  ограниченный SRP) на основе разбора кода и практик;
- прогнал `./gradlew :modules:homework-1:check` итеративно, исправлял
  падения компиляции, Checkstyle и JaCoCo до полностью зелёной сборки.

## Соответствие критериям оценки

| № | Критерий | Где смотреть | Кратко |
|---|---|---|---|
| 1 | ООП и SOLID | `domain/EnergyConsumer.java`, `domain/Inventoried.java`, `domain/Vehicle.java`, `domain/MotorizedVehicle.java`, `domain/Thing.java`, `fleet/Fleet.java` | Абстракции ISP. Иерархии `Vehicle` и `Thing`. OCP на новом виде транспорта. DIP в `Fleet` через `ServiceCenter`. |
| 2 | README | этот файл | Ответы на обязательные вопросы, запуск, роль ИИ, соответствие критериям. |
| 3 | DI-контейнер и подмена техосмотра | `configuration/ApplicationConfiguration.java`, `inspection/ServiceCenter.java`, `inspection/DefaultServiceCenter.java`, `test/.../FleetSpringIntegrationTest.java` | Spring `@Bean` собирает граф. `@MockitoBean` подменяет `ServiceCenter` в тестах. |
| 4 | Юнит-тесты, поведение, ≥60% | `test/.../VehicleTest.java`, `test/.../MotorizedVehicleTest.java`, `test/.../FleetTest.java`, `test/.../DefaultServiceCenterTest.java`, `test/.../FleetSpringIntegrationTest.java`, `test/.../presentation/DemoRunnerTest.java`, `test/.../presentation/FleetReporterTest.java`, `test/.../presentation/FleetConsoleAppTest.java` | Параметризованные тесты правила для новичков, accept/reject техосмотра, интеграционный Spring-тест, тесты консольного меню и отчёта. Отчёт: `build/reports/jacoco/test/html/index.html`. |
| 5 | Демонстрация сценария | `Main.java`, `presentation/FleetConsoleApp.java`, `presentation/DemoRunner.java`, `presentation/FleetReporter.java` | Демо-сценарий + интерактивное меню с вводом из консоли. |
| 6 | Code style | весь `src/` | Sun Code Conventions через Checkstyle, 0 violations. Javadoc на всех public-методах, `package-info.java` в каждом пакете, пустая строка в конце файла. |

## Самопроверка

- [x] SOLID обоснован в README, overengineering отсутствует.
- [x] README содержит обязательные вопросы и таблицу критериев.
- [x] Использован Spring DI, `ServiceCenter` подменяется в тестах.
- [x] Юнит-тесты поведения, параметризованные и интеграционный.
- [x] Покрытие JaCoCo ≥ 60% (см. отчёт).
- [x] `./gradlew :modules:homework-1:run` демонстрирует сценарии.
- [x] `checkstyleMain` и `checkstyleTest` — 0 violations.
- [x] `./gradlew :modules:homework-1:clean :modules:homework-1:check` — зелёный.

