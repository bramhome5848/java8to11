package kr.com.lkj.java8to11;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class ParallelSort {

    /**
     * Arrays.parallelSort()
     * - Fork/Join 프레임워크를 사용해서 배열을 병렬로 정렬하는 기능을 제공한다.
     * 병렬 정렬 알고리듬
     * - 배열을 둘로 계속 쪼갬
     * - 합치면서 정렬
     * 알고리즘 효율성은 같음 -> 시간 O(n logN), 공간 O(n)
     */
    public static void main(String[] args) {

        int size = 1500;
        int[] numbers = new int[size];
        Random random = new Random();

        //sort
        IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
        long start = System.nanoTime();
        Arrays.sort(numbers);   //single thread 사용, Dual-Pivot quickSort -> Nlog(N)
        System.out.println("serial sorting took " + (System.nanoTime() - start));

        //parallelSort
        IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
        start = System.nanoTime();
        Arrays.parallelSort(numbers);   //multi thread 사용
        System.out.println("parallel sorting took " + (System.nanoTime() - start));
    }
}
