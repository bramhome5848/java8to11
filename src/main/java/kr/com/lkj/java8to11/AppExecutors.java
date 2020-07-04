package kr.com.lkj.java8to11;

import java.util.concurrent.*;

public class AppExecutors {

    /**
     * 고수준 (High-Level) Concurrency 프로그래밍
     * - 쓰레드를 만들고 관리하는 작업을 애플리케이션에서 분리.
     * - 그런 기능을 Executors 에게 위임.
     * Executors 가 하는 일
     * - 쓰레드 만들기: 애플리케이션이 사용할 쓰레드 풀을 만들어 관리한다.
     * - 쓰레드 관리: 쓰레드 생명 주기를 관리한다.
     * - 작업처리및실행: 쓰레드로 실행 할 작업을 제공할 수 있는 API를 제공한다.
     * 주요 인터페이스
     * - Executor: execute(Runnable)
     * - ExecutorService: Executor 상속 받은 인터페이스로, Callable도 실행할 수 있으며,
     * - Executor를 종료 시키거나, 여러 Callable을 동시에 실행하는 등의 기능을 제공한다.
     * - ScheduledExecutorService: ExecutorService를 상속 받은 인터페이스로 특정 시간
     *   이후에 또는 주기적으로 작업을 실행할 수 있다.
     */
    public static void main(String[] args) {
        function1();
    }

    public static void function1() {
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();  //쓰레드를 1개만 쓰는 Executor
        //고전적인 방법 Runnable 구현
        //.execute 또는 .submit
        executorService1.submit(() -> {
            System.out.println("Thread " + Thread.currentThread().getName());
            System.out.println("------------------------------------------");

        });

        //주의 -> ExecutorService 는 실행 후 다음 작업이 들어올 때 까지 계속 살아 있음
        //따라서 명시적으로 shutdown 을 해줘야함
        executorService1.shutdown(); //진행중인 작업은 끝까지 마치고 종료
        //executorService1.shutdownNow(); // 당장 종료

        ExecutorService executorService2 = Executors.newFixedThreadPool(2);  //쓰레드 2개

        //쓰레드는 2개지만 작업은 5개
        //ExecutorService 안에는 Queue 와 Thread Pool 존재(pool 안에 thread 존재)
        //대기중인 작업들은 Queued 에서 대기
        executorService2.submit(getRunnable("Hello "));
        executorService2.submit(getRunnable("KyungJin "));
        executorService2.submit(getRunnable("The "));
        executorService2.submit(getRunnable("Java "));
        executorService2.submit(getRunnable("Thread "));

        executorService2.shutdown();
        System.out.println("------------------------------------------");

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(getRunnable("Hello1"), 1, TimeUnit.SECONDS);   //3초 있다가 실행
        //초기 1초 딜레이 후 2초바다 실행
        scheduledExecutorService.scheduleAtFixedRate(getRunnable("Hello2"),1, 2, TimeUnit.SECONDS);
    }

    public static Runnable getRunnable(String message) {
        return () -> System.out.println(message + Thread.currentThread().getName());
    }

}
