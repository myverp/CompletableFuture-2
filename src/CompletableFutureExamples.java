import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExamples {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Починаємо демонстрацію методів CompletableFuture.\n");

        // 1. Демонстрація thenCombine() та allOf()
        System.out.println("--- Демонстрація thenCombine() та allOf() --");
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Завдання 1 виконується в потоці: " + Thread.currentThread().getName());
            sleepRandomTime(1, 3);
            return "Результат завдання 1";
        });

        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Завдання 2 виконується в потоці: " + Thread.currentThread().getName());
            sleepRandomTime(2, 4);
            return 100;
        });

        CompletableFuture<String> combinedResult = task1.thenCombine(task2, (result1, result2) -> {
            System.out.println("Об'єднання результатів в потоці: " + Thread.currentThread().getName());
            return result1 + " та " + result2;
        });

        System.out.println("Результат об'єднання: " + combinedResult.get());

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2);
        allTasks.get(); // Чекаємо завершення обох завдань
        System.out.println("Обидва завдання завершено.\n");

        // 2. Демонстрація thenCompose()
        System.out.println("--- Демонстрація thenCompose() ---");
        CompletableFuture<String> initialStep = CompletableFuture.supplyAsync(() -> {
            System.out.println("Перший крок thenCompose виконується в потоці: " + Thread.currentThread().getName());
            sleepRandomTime(1, 2);
            return "Початкові дані";
        });

        CompletableFuture<String> nextStep = initialStep.thenCompose(data -> CompletableFuture.supplyAsync(() -> {
            System.out.println("Другий крок thenCompose виконується в потоці: " + Thread.currentThread().getName());
            sleepRandomTime(1, 2);
            return data + " -> Оброблені дані";
        }));

        System.out.println("Результат thenCompose: " + nextStep.get() + "\n");

        // 3. Демонстрація anyOf()
        System.out.println("--- Демонстрація anyOf() ---");
        CompletableFuture<String> fastTask = CompletableFuture.supplyAsync(() -> {
            System.out.println("Швидке завдання anyOf виконується в потоці: " + Thread.currentThread().getName());
            sleepRandomTime(1, 2);
            return "Швидкий результат";
        });

        CompletableFuture<String> slowTask = CompletableFuture.supplyAsync(() -> {
            System.out.println("Повільне завдання anyOf виконується в потоці: " + Thread.currentThread().getName());
            sleepRandomTime(3, 5);
            return "Повільний результат";
        });

        CompletableFuture<Object> anyResult = CompletableFuture.anyOf(fastTask, slowTask);
        System.out.println("Перший отриманий результат (anyOf): " + anyResult.get() + "\n");

        // 4. Демонстрація порівняння програмного забезпечення
        System.out.println("--- Демонстрація порівняння програмного забезпечення ---");
        List<String> softwareOptions = Arrays.asList("Програма A", "Програма B", "Програма C");

        System.out.println("Обираємо програмне забезпечення з варіантів: " + softwareOptions);

        CompletableFuture<SoftwareDetails> programADetails = getSoftwareDetails("Програма A");
        CompletableFuture<SoftwareDetails> programBDetails = getSoftwareDetails("Програма B");
        CompletableFuture<SoftwareDetails> programCDetails = getSoftwareDetails("Програма C");

        CompletableFuture<List<SoftwareDetails>> allDetailsFuture = CompletableFuture.allOf(
                programADetails, programBDetails, programCDetails
        ).thenApply(v -> Arrays.asList(programADetails.join(), programBDetails.join(), programCDetails.join()));

        allDetailsFuture.thenAccept(detailsList -> {
            System.out.println("\nОтримано деталі для всіх програм:");
            detailsList.forEach(System.out::println);

            // Логіка вибору найкращого варіанту (приклад)
            SoftwareDetails bestSoftware = detailsList.stream()
                    .reduce((sd1, sd2) -> {
                        // Приклад логіки: пріоритет ціні, потім функціональності
                        if (sd1.getPrice() < sd2.getPrice()) {
                            return sd1;
                        } else if (sd1.getPrice() > sd2.getPrice()) {
                            return sd2;
                        } else if (sd1.getFunctionality() > sd2.getFunctionality()) {
                            return sd1;
                        } else {
                            return sd2;
                        }
                    })
                    .orElse(null);

            if (bestSoftware != null) {
                System.out.println("\nНайкращий варіант за критеріями: " + bestSoftware.getName());
            } else {
                System.out.println("\nНе вдалося визначити найкращий варіант.");
            }
        });

        // Щоб main не завершився до завершення асинхронних операцій
        Thread.sleep(6000);
        System.out.println("\nЗавершення демонстрації.");
    }

    static void sleepRandomTime(int minSeconds, int maxSeconds) {
        try {
            int sleepTime = new Random().nextInt(maxSeconds - minSeconds + 1) + minSeconds;
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static CompletableFuture<SoftwareDetails> getSoftwareDetails(String softwareName) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Отримання деталей для " + softwareName + " в потоці: " + Thread.currentThread().getName());
            sleepRandomTime(2, 3);
            return new SoftwareDetails(softwareName, getRandomPrice(), getRandomFunctionality(), getRandomSupport());
        });
    }

    static double getRandomPrice() {
        return Math.round(new Random().nextDouble() * 1000 * 100.0) / 100.0; // Ціна від 0 до 1000
    }

    static int getRandomFunctionality() {
        return new Random().nextInt(10) + 1; // Функціональність від 1 до 10
    }

    static String getRandomSupport() {
        List<String> supportLevels = Arrays.asList("Базова", "Розширена", "Преміум");
        return supportLevels.get(new Random().nextInt(supportLevels.size()));
    }
}

class SoftwareDetails {
    private String name;
    private double price;
    private int functionality;
    private String support;

    public SoftwareDetails(String name, double price, int functionality, String support) {
        this.name = name;
        this.price = price;
        this.functionality = functionality;
        this.support = support;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getFunctionality() {
        return functionality;
    }

    public String getSupport() {
        return support;
    }

    @Override
    public String toString() {
        return "SoftwareDetails{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", functionality=" + functionality +
                ", support='" + support + '\'' +
                '}';
    }
}