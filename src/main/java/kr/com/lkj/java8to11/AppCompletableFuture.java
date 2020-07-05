package kr.com.lkj.java8to11;

import java.util.concurrent.*;

public class AppCompletableFuture {

    /**
     * 자바에서 비동기(Asynchronous) 프로그래밍을 가능케하는 인터페이스
     * - Future 사용해서도 어느정도 가능했지만 하기 힘들 일들이 많음
     * Future 로는 하기 어렵던 작업들
     * - Future를 외부에서 완료 시킬 수 없다. 취소하거나, get()에 타임아웃을 설정할 수는 있음
     * - 블로킹 코드(get())를 사용하지 않고서는 작업이 끝났을 때 콜백을 실행할 수 없음
     * 여러 Future를 조합할 수 없음
     * - Event 정보 가져온 다음 Event에 참석하는 회원 목록 가져오기
     * 예외 처리용 API를 제공하지 않음
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //function1();
        //function2();
        //function3();
        //function4();
        function5();
    }

    /**
     * function1, function2
     * get() 하지 않아도 쓰레드는 동작함, 단지 main 쓰레드에서는 sleep, get 을 하지 않으면
     * 해당 future의 작업을 볼수가 없음
     */
    public static void function1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Future<String> future = executorService.submit(() -> "hello");

        //future 의 결과값을 가지고 무언가를 하기 위해서는 그 작업들이 모두 .get()이후에 와야함
        //끝났을 때 실행하는 Callback 이 존재하면 좋을 듯
        //get() -> blocking call
        //따라서 이상태에서는 비동기 상태의 프로그래밍을 하기 힘듬
        System.out.println(future.get());
        executorService.shutdown();
    }

    public static void function2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("LKJ"); //실행

        //CompletableFuture<String> future = CompletableFuture.completedFuture("LKJ");
        //System.out.println(future.get());

        //비동기 작업 실행 -> 리턴값이 없는 경우: runAsync()
        CompletableFuture<Void> voidFuture = CompletableFuture.runAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
        });

        //System.out.println(voidFuture.get());

        //리턴값이 있는 경우 -> supplyAsync()
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        //System.out.println(supplyAsync.get());
    }

    /**
     * 기존에는 get() 호출 이전에 콜백함수에 대한 정의를 할 수 없었음
     * thenApply(Function): 리턴값을 받아서 다른 값으로 바꾸는 콜백
     * thenAccept(Consumer): 리턴값을 또 다른 작업을 처리하는 콜백 (리턴없이)
     * thenRun(Runnable): 리턴값 받지 다른 작업을 처리하는 콜백
     * 콜백 자체를 또 다른 쓰레드에서 이용 가능
     */
    public static void function3() throws ExecutionException, InterruptedException {

        //콜백에서 리턴이 있는 경우
        CompletableFuture<String> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("========= supplyAsync1 ========");
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).thenApply((s) -> {
            System.out.println(Thread.currentThread().getName());
            return s.toUpperCase();
        });

        //콜백에서 리턴이 없는 경우
        CompletableFuture<Void> supplyAsync2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("========= supplyAsync2 ========");
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).thenAccept((s) -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(s.toUpperCase());
        });

        //리턴 값은 있지만 callback 에서 리턴 값을 이용하지 않고 단순 실행
        CompletableFuture<Void> supplyAsync3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("========= supplyAsync3 ========");
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).thenRun(() -> {
            System.out.println(Thread.currentThread().getName());
        });
    }

    /**
     * 원하는 Executor(쓰레드풀)를 사용해서 실행할 수도 있음
     * 기본은 ForkJoinPool.commonPool())
     */
    public static void function4() throws ExecutionException, InterruptedException {

        //supplyAsync 두 번째 인자로 원하는 쓰레드풀을 지정
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }, executorService).thenRun(() -> {
            System.out.println(Thread.currentThread().getName());
        });

        executorService.shutdown(); //직접 shutdown 해줘야함
    }

    /**
     * 콜백 자체를 다른 쓰레드에서 수행
     */
    public static void function5() {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }, executorService).thenRunAsync(() -> {
            System.out.println(Thread.currentThread().getName());
        }, executorService);

        executorService.shutdown();
    }

    /**
     * 테스트를 통해 확인한 내용
     * 자바 8에서 callback 함수는 -> main 쓰레드에서 실행
     * 자바 11에서 callback 함수는 -> 기존에 작업하던 쓰레드에서 실행
     * thenRun() 관련해서 자바 8과 11에서 달라진건 없음.
     * 아마 여러번 실행해 보면 어떨 때는 main에서 실행하고 어떨 때는 supplyAsync()를 실행한 쓰레드에서 실행
     * thenRun()으로 전달한 콜백은 앞선 콜백을 실행한 쓰레드나 그 쓰레드를 파생시킨 부모에서 실행하게 되어있음
     * 부모 쓰레드나 이전 콜백을 실행한 쓰레드가 아닌 쓰레드로 실행하고 싶다면 thenRunAsync를 사용해야 합니다.
     */
}
