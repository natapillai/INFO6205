package edu.neu.coe.info6205.util;

import edu.neu.coe.info6205.sort.elementary.InsertionSort;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class InsertionSORT_Benchmark {

    private static final String[] ARRAY_TYPES = {"Randomly ordered", "Ordered", "Partially ordered", "Reverse-ordered"};


    public static void main(String[] args) {
        for (int arrayType = 0; arrayType < ARRAY_TYPES.length; arrayType++) {
            System.out.println();
            System.out.println("Insertion sort for " + ARRAY_TYPES[arrayType] + " arrays:");
            for (int n = 10; n <= 1280; n=n*2) {
                Supplier<Integer[]> supplier = getArraySupplier(arrayType, n);
                runBenchmark(n, supplier);
            }
        }
    }

    private static Supplier<Integer[]> getArraySupplier(int arrayType, int n) {
        switch (arrayType) {
            case 0:
                return () -> createRandomArray(n);
            case 1:
                return () -> createOrderedArray(n);
            case 2:
                return () -> createPartiallyOrderedArray(n);
            case 3:
                return () -> createReverseOrderedArray(n);
            default:
                throw new IllegalArgumentException("Invalid array type");
        }
    }

    private static void runBenchmark(int n, Supplier<Integer[]> supplier) {
        InsertionSort<Integer> sorter = new InsertionSort<>();
        Consumer<Integer[]> con = (t)-> {sorter.sort(t, 0,t.length);};
        Benchmark_Timer<Integer[]> timer = new Benchmark_Timer<Integer[]>("Insertion Sort-Benchmarking", con);
        double meanTime = timer.runFromSupplier(supplier,10000);
        System.out.println("Array size " + n + " takes a mean time of " + meanTime);
    }

    private static Integer[] createRandomArray(int n) {
        Random random = new Random();
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(n);
        }
        return array;
    }

    private static Integer[] createOrderedArray(int n) {
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++) {
            array[i] = i;
        }
        return array;
    }

    private static Integer[] createPartiallyOrderedArray(int n) {
        Random random = new Random();
        Integer[] array = new Integer[n];
        int m = n / 2;
        for (int i = 0; i < m; i++) {
            array[i] = i;
        }
        for (int i = m; i < n; i++) {
            array[i] = random.nextInt(n);
        }
        return array;
    }

    private static Integer[] createReverseOrderedArray(int n) {
        Integer[] array = new Integer[n];
        int count = 1;
        for (int i = 0; i < n; i++) {
            array[i] = n + 2 - count;
            count++;
        }
        return array;
    }

}
