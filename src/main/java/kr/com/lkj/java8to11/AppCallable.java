package kr.com.lkj.java8to11;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class AppCallable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //function1();
        function2();
    }

    /**
     * Callable : Runnable 과 유사하지만 작업의 결과를 받을 수 있다. -> Runnable은 void라 리턴 받을 수 없음
     * Future : 비동기적인 작업의 현재 상태를 조회하거나 결과를 가져올 수 있다.
     */
    public static void function1() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            return "Hello";
        };

        Future<String> helloFuture = executorService.submit(hello);
        System.out.println(helloFuture.isDone());   //끝났으면 true, 아니면 false
        System.out.println("Started!!");

        //get 이전까지 코드는 쭉 실행됨
        helloFuture.get();   //get을 만난 순간 멈춤, 결과 값을 가져올때 까지 기다림 -> blocking call

        //parameter 로 true 를 전달하면 현재 진행중인 쓰레드를 interrupt 하고 그러지 않으면 현재
        //진행중인 작업이 끝날때까지 기다린다.
        helloFuture.cancel(true);   //취소 했으면 true 못했으면 false 를 리턴한다.
        //helloFuture.get();  //취소된 것에 대한 정보를 가져오게 되면 Exception

        System.out.println(helloFuture.isDone());   //끝났으면 true, 아니면 false
        System.out.println("End!!");
        executorService.shutdown();

    }

    public static void function2() throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            return "Hello";
        };

        Callable<String> java = () -> {
            Thread.sleep(3000L);
            return "Java";
        };

        Callable<String> spring = () -> {
            Thread.sleep(1000L);
            return "Spring";
        };

        //invokeAll -> 여러 작업 동시에 실행하기
        //모든 처리 가 끝날때 까지 기다림 -> spring, hello 가 끝나도 java 가 끝날때 까지 기다림
        //동시에 실행한 작업 중에 제일 오래 걸리는 작업만큼 시간이 걸린다.
        List<Future<String>> futures = executorService.invokeAll(Arrays.asList(hello, java, spring));

        for(Future<String> f : futures) {
            System.out.println(f.get());
        }

        //invokeAny
        //여러 작업 중에 하나라도 먼저 응답이 오면 끝내기 invokeAny()
        //블록킹 콜
        String s = executorService.invokeAny(Arrays.asList(hello, java, spring));
        System.out.println(s);

        executorService.shutdown();
    }
}
