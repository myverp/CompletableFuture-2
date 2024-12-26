# Демонстрація методів CompletableFuture в Java

Цей проект демонструє використання деяких потужних методів класу `CompletableFuture` в Java для виконання асинхронних операцій.

## Опис

Програма містить приклади використання наступних методів `CompletableFuture`:

*   **`thenCombine()`:**  Об'єднання результатів двох незалежних асинхронних завдань.
*   **`thenCompose()`:** Послідовне виконання асинхронних операцій, де результат першої операції передається як вхідні дані для наступної.
*   **`allOf()`:** Очікування завершення всіх переданих `CompletableFuture`.
*   **`anyOf()`:** Отримання результату першого завершеного `CompletableFuture` з набору.

Також продемонстровано використання `CompletableFuture` для вирішення практичного завдання - порівняння різних варіантів програмного забезпечення за заданими критеріями шляхом паралельного отримання даних.

## Запуск програми

1. **Скомпілюйте код:** Виконайте команду `javac CompletableFutureExamples.java`. Це створить файл `CompletableFutureExamples.class`.
2. **Запустіть програму:** Виконайте команду `java CompletableFutureExamples`.

## Пояснення коду

Програма складається з наступних частин:

1. **Демонстрація `thenCombine()` та `allOf()`:**
    *   Створюються два незалежних асинхронних завдання, які імітують виконання певної роботи.
    *   За допомогою `thenCombine()` їх результати об'єднуються.
    *   `allOf()` використовується для очікування завершення обох завдань.

2. **Демонстрація `thenCompose()`:**
    *   Показано, як `thenCompose()` дозволяє зв'язувати асинхронні операції послідовно, де вихід однієї операції є входом для наступної.

3. **Демонстрація `anyOf()`:**
    *   Проілюстровано отримання результату першого завершеного завдання з двох.

4. **Демонстрація порівняння програмного забезпечення:**
    *   Створюється список назв програмного забезпечення.
    *   Для кожної програми асинхронно отримуються деталі (ціна, функціональність, підтримка).
    *   `allOf()` гарантує, що ми дочекаємося отримання деталей для всіх програм.
    *   Далі реалізовано просту логіку вибору найкращого варіанту на основі отриманих даних.

Клас `SoftwareDetails` представляє структуру даних для інформації про програмне забезпечення. Функції `getRandomPrice()`, `getRandomFunctionality()` та `getRandomSupport()` використовуються для імітації отримання реальних даних.

## Ключові концепції

Ця програма демонструє наступні ключові концепції асинхронного програмування з використанням `CompletableFuture`:

*   **Паралельне виконання:** Використання `supplyAsync()` для запуску завдань в окремих потоках.
*   **Об'єднання результатів:**  Об'єднання результатів декількох асинхронних операцій за допомогою `thenCombine()`.
*   **Створення ланцюжків залежностей:** Поєднання асинхронних операцій, де наступна залежить від результату попередньої, за допомогою `thenCompose()`.
*   **Очікування завершення всіх завдань:** Використання `allOf()` для синхронізації після виконання всіх асинхронних завдань.
*   **Отримання першого доступного результату:**  Використання `anyOf()` для отримання результату першого завершеного завдання.