package com.company;

class MyRunnable implements Runnable {
    private final int[] array;
    private final int from, to;
    private volatile int maxValue;

    MyRunnable(int[] array, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
    }

    // Ищем максимум
    public void run() {
        int currentMax = array[from];
        for(int i = from + 1; i < to; i++) {
            currentMax = Math.max(currentMax, array[i]);
        }
        maxValue = currentMax;
    }

    public int getMaxValue() {
        return maxValue;
    }
}
