package kr.com.lkj.java8to11;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class AppCompletableFutureNext {

    /**
     * 조합하기
     * - thenCompose(): 두 작업이 서로 이어서 실행하도록 조합
     * - thenCombine(): 두 작업을 독립적으로 실행하고 둘 다 종료 했을 때 콜백 실행
     * - allOf(): 여러 작업을 모두 실행하고 모든 작업 결과에 콜백 실행
     * - anyOf():여러작업중에가장빨리끝난하나의결과에콜백실행
     * 예외처리
     * - exceptionally(Function)
     * - handle(BiFunction):
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //function1();
        //function2();
        //function3();
        //function4();
        //function5();
        function6();
    }

    /**
     *  thenCompose : 두 작업이 서로 이어서 실행하도록 조합
     */
    public static void function1() throws ExecutionException, InterruptedException {

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        //hello 작업 후 그 결과를 받아서 이어서 world 작업
        CompletableFuture<String> future = hello.thenCompose(AppCompletableFutureNext::getWorld);
        System.out.println(future.get());

        //둘의 연관관계 없이 비동기적으로 따로 따로 실행
    }

    private static CompletableFuture<String> getWorld(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("World " + Thread.currentThread().getName());
            return message + " World";
        });
    }

    /**
     * thenCombine : 두 작업을 독립적으로 실행하고 둘 다 종료 했을 때 콜백 실행
     * allOf(): 여러 작업을 모두 실행하고 모든 작업 결과에 콜백 실행
     */
    public static void function2() throws ExecutionException, InterruptedException {

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            System.out.println("World " + Thread.currentThread().getName());
            return "Hello";
        });

        //둘 다 병렬로 처리를 하고 둘 다 결과가 왔을 때 진행
        //각 쓰레드는 실행됨
        /*
        CompletableFuture<String> future = hello.thenCombine(world, (h, w) -> h + " " + w);
        System.out.println(future.get());
        */

        //각 테스크의 결과값이 다른 타입일 수 있고, 어떤 테스크는 오류일 수도 있음
        //따라서 결과값도 적저한 결과값을 얻을 수가 없음
        CompletableFuture<Void> future = CompletableFuture.allOf(hello, world)
                .thenAccept(System.out::println);

        System.out.println(future.get());   //null 출력
    }

    /**
     * allOf 사용 예
     */
    public static void function3() throws ExecutionException, InterruptedException {

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            System.out.println("World " + Thread.currentThread().getName());
            return "World";
        });

        List<CompletableFuture<String>> futures = Arrays.asList(hello, world);
        CompletableFuture<String>[] futureArray = futures.toArray(new CompletableFuture[futures.size()]);

        CompletableFuture<List<String>> results = CompletableFuture.allOf(futureArray)
                .thenApply(v -> {  //테스크들을 다 실행하고 콜백
                    return futures.stream()
                            .map(CompletableFuture::join)    //get사용시 exception 코드 필요
                            .collect(Collectors.toList());
                });

        results.get().forEach(System.out::println);
    }

    /**
     * anyOf(): 여러 작업 중에 가장 빨리 끝난 하나의 결과에 콜백 실행
     */
    public static void function4() throws ExecutionException, InterruptedException {

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            System.out.println("World " + Thread.currentThread().getName());
            return "World";
        });

        //아무거나 빨리 끝나는거 하나
        CompletableFuture<Void> future = CompletableFuture.anyOf(hello, world).thenAccept(System.out::println);
        future.get();
    }

    /**
     * exceptionally(Function)
     * 비동기적 처리중인 테스크 안에서 에러가 발생할 경우 처리
     */
    public static void function5() throws ExecutionException, InterruptedException {

        boolean throwError = true;

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {

            if(throwError){
                throw new IllegalArgumentException();
            }
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).exceptionally(ex -> {
            System.out.println(ex);
            return "Error";
        });

        System.out.println(hello.get());
    }

    /**
     * handle(BiFunction): 정상적으로 종료되었을 경우와, 에러가 난 상황에 둘다 사용 가능
     * exeptionally 조금 더 일반적인 방법
     */
    public static void function6() throws ExecutionException, InterruptedException {

        boolean throwError = false;

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {

            if(throwError){
                throw new IllegalArgumentException();
            }
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).handle((result, ex) -> {
           if(ex != null) {
               System.out.println(ex);
               return "Error";
           }
           return result;
        });

        System.out.println(hello.get());
    }
}
