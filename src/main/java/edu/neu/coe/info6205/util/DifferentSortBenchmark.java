package edu.neu.coe.info6205.util;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.HelperFactory;
import edu.neu.coe.info6205.sort.InstrumentedHelper;
import edu.neu.coe.info6205.sort.elementary.HeapSort;
import edu.neu.coe.info6205.sort.linearithmic.MergeSort;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_DualPivot;

import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import static java.util.concurrent.CompletableFuture.runAsync;

public class DifferentSortBenchmark {
    public static void main(String[] args) {
        try {
            File fheap = new File("HeapBM.csv");
            fheap.createNewFile();
            FileWriter fwHeap = new FileWriter(fheap);
            fwHeap.write(getHeaderString());

            File fmerge = new File("MergeBM.csv");
            fmerge.createNewFile();
            FileWriter fwMerge = new FileWriter(fmerge);
            fwMerge.write(getHeaderString());

            File fquick = new File("QuickBM.csv");
            fquick.createNewFile();
            FileWriter fwQuick = new FileWriter(fquick);
            fwQuick.write(getHeaderString());

            System.out.println("Degree of parallelism: " + ForkJoinPool.getCommonPoolParallelism());
            Config config = Config.setupConfig("true", "", "1", "", "");

            int start = 10000;
            int end = 160000;

            CompletableFuture<FileWriter> heapSort = runHeap(start, end, config, fwHeap);
            heapSort.join();
            CompletableFuture<FileWriter> mergeSort = runMerge(start, end, config, fwMerge);
            mergeSort.join();
            CompletableFuture<FileWriter> quickSort = runQuick(start, end, config, fwQuick);
            quickSort.join();



        } catch (Exception e) {
            System.out.println("error while sorting main" + e);
        }
    }


    private static CompletableFuture runHeap(int start, int end, Config config, FileWriter fileWriter) {
        return runAsync(
                () -> {

                    for (int n = start; n <= end; n *= 2) {
                        Helper<Integer> helper = HelperFactory.create("HeapSort", n, config);
                        HeapSort<Integer> sort = new HeapSort<>(helper);
                        final int val = n;
                        Integer[] arr = helper.random(Integer.class, r -> r.nextInt(val));
                        SorterBenchmark sorterBenchmark = new SorterBenchmark<>(Integer.class,
                                (Integer[] array) -> {
                                    for (int i = 0; i < array.length; i++) {
                                        array[i] = array[i];
                                    }
                                    return array;
                                },
                                sort, arr, 10, timeLoggersLinearithmic);
                        double time = sorterBenchmark.run(n);
                        try {
//                            if (helper instanceof BaseHelper) {
//                                fileWriter.write(createCsvString(n, time, null, config.isInstrumented()));
//                            } else {
                                fileWriter.write(createCsvString(n, time, ((InstrumentedHelper) helper).getStatPack(), config.isInstrumented()));
//                            }
                            //System.out.println(((InstrumentedHelper) helper).getStatPack());
                        } catch (Exception e) {
                            System.out.println("error while writing file Heap" + e);
                        }
                    }
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception e) {
                        System.out.println("error while closing file Heap" + e);
                    }

                }
        );
    }


    private static CompletableFuture runMerge(int start, int end, Config config, FileWriter fileWriter) {
        return runAsync(
                () -> {
                    for (int n = start; n <= end; n *= 2) {
                        Helper<Integer> helper = HelperFactory.create("MergeSort", n, config);
                        MergeSort<Integer> sort = new MergeSort<>(helper);
                        final int val = n;
                        Integer[] arr = helper.random(Integer.class, r -> r.nextInt(val));
                        SorterBenchmark sorterBenchmark = new SorterBenchmark<>(Integer.class,
                                (Integer[] array) -> {
                                    for (int i = 0; i < array.length; i++) {
                                        array[i] = array[i];
                                    }
                                    return array;
                                },
                                sort, arr, 10, timeLoggersLinearithmic);
                        double time = sorterBenchmark.run(n);
                        try {
//                            if (helper instanceof BaseHelper) {
//                                fileWriter.write(createCsvString(n, time, null, config.isInstrumented()));
//                            } else {
                                fileWriter.write(createCsvString(n, time, ((InstrumentedHelper) helper).getStatPack(), config.isInstrumented()));
//                            }
                        } catch (Exception e) {
                            System.out.println("error while writing file Merge" + e);
                        }
                    }
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception e) {
                        System.out.println("error while closing file Merge" + e);
                    }
                }
        );
    }


    private static CompletableFuture runQuick(int start, int end, Config config, FileWriter fileWriter) {
        return runAsync(
                () -> {
                    for (int n = start; n <= end; n *= 2) {
                        Helper<Integer> helper = HelperFactory.create("QuickSort", n, config);
                        QuickSort_DualPivot<Integer> sort = new QuickSort_DualPivot<>(helper);
                        final int val = n;
                        Integer[] arr = helper.random(Integer.class, r -> r.nextInt(val));
                        SorterBenchmark sorterBenchmark = new SorterBenchmark<>(Integer.class,
                                (Integer[] array) -> {
                                    for (int i = 0; i < array.length; i++) {
                                        array[i] = array[i];
                                    }
                                    return array;
                                },
                                sort, arr, 10, timeLoggersLinearithmic);
                        double time = sorterBenchmark.run(n);
                        try {
//                            if (helper instanceof BaseHelper) {
//                                fileWriter.write(createCsvString(n, time, null, config.isInstrumented()));
//                            } else {
                                fileWriter.write(createCsvString(n, time, ((InstrumentedHelper) helper).getStatPack(), config.isInstrumented()));
//                            }
                        } catch (Exception e) {
                            System.out.println("error while writing file Quick" + e);
                        }
                    }
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception e) {
                        System.out.println("error while closing file Quick" + e);
                    }
                }

        );
    }

    public final static TimeLogger[] timeLoggersLinearithmic = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time)
    };

    private static String createCsvString(int n, double time, StatPack statPack, boolean instrumentation) {
        StringBuilder sb = new StringBuilder();
        sb.append(n+",");
        sb.append(time+",");

        if (instrumentation) {

            sb.append(statPack.getStatistics("hits").mean() + ",");
            sb.append(statPack.getStatistics("hits").stdDev() + ",");
            sb.append(statPack.getStatistics("hits").normalizedMean() + ",");

            sb.append(statPack.getStatistics("swaps").mean() + ",");
            sb.append(statPack.getStatistics("swaps").stdDev() + ",");
            sb.append(statPack.getStatistics("swaps").normalizedMean() + ",");

            sb.append(statPack.getStatistics("compares").mean() + ",");
            sb.append(statPack.getStatistics("compares").stdDev() + ",");
            sb.append(statPack.getStatistics("compares").normalizedMean() + ",");

            sb.append(statPack.getStatistics("fixes").mean() + ",");
            sb.append(statPack.getStatistics("fixes").stdDev() + ",");
            sb.append(statPack.getStatistics("fixes").normalizedMean() + "\n");
        } else {
            sb.append("\n");
        }
        System.out.println();
        return sb.toString();

    }

    private static String getHeaderString() {
        StringBuilder sb = new StringBuilder();
        sb.append("N,");
        sb.append("Time,");

        sb.append("hits:Mean,");
        sb.append("hits:StandardDeviation,");
        sb.append("hits:NormalizedMean,");

        sb.append("swaps:Mean,");
        sb.append("swaps:StandardDeviation,");
        sb.append("swaps:NormalizedMean,");

        sb.append("compares:Mean,");
        sb.append("compares:StandardDeviation,");
        sb.append("compares:NormalizedMean,");

        sb.append("fixes:Mean,");
        sb.append("fixes:StandardDeviation,");
        sb.append("fixes:NormalizedMean\n");

        return sb.toString();

    }


}
