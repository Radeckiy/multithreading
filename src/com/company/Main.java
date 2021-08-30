package com.company;

public class Main {

    public static void main(String[] args) {

          /* !!! ВЫВОД
        Если поиграться с кол-вом работающих потоков (COUNT_THREAD), то можно заметить, что
        при увеличении кол-ва потоков уменьшается время работы программы, что логично.
        Но время уменьшается только до той поры, пока кол-во потоков не станет равно
        кол-ву ядер в вашей пекарне. Дальше потоки будут работать попеременно...

        Мультитрединг во все дыры.
        */

        final int COUNT_THREAD = 1; // Кол-во потоков для работы
        int[] array = new int[100_000_000]; // Масив чиселок
        MyRunnable[] myRunners = new MyRunnable[COUNT_THREAD]; // Массив раннов
        Thread[] myThreads = new Thread[COUNT_THREAD]; // Массив тредов

        // Заполняем массив порялковыми чиселками
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        // Фиксируем время старта
        long t1 = System.currentTimeMillis();

        // Инициализируем заданное кол-во (COUNT_THREAD) потоков и запускаем их
        for (int i = 0; i < COUNT_THREAD; i++) {
            int len = array.length / COUNT_THREAD;
            myRunners[i] = new MyRunnable(array, len * i, len * (i + 1));
            myThreads[i] = new Thread(myRunners[i]);
            myThreads[i].start();
        }

        // Ждем все потоки
        for (int i = 0; i < COUNT_THREAD; i++) {
            try {
                myThreads[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        // Указываем что максимальное из полученных максимумов раннов = нулевому максимуму ранна
        int max = myRunners[0].getMaxValue();

        // Ищем максимум из полученных максимумов раннов, если потоков больше чем 1
        if (COUNT_THREAD > 1) {
            for (int i = 1; i < COUNT_THREAD; i++) {
                max = Math.max(max, myRunners[i].getMaxValue());
            }
        }
        // Выводим результ
        System.out.println(max);

        // Фиксируем время окончания программы и выводим время работы (тайм финиша - тайм старта, ага)
        long t2 = System.currentTimeMillis();
        System.out.println("Время работы программы: " + (t2 - t1) + " мс");
    }
}
