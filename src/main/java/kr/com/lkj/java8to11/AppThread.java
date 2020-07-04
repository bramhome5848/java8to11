package kr.com.lkj.java8to11;

public class AppThread {

    public static void main(String[] args) throws InterruptedException {
        //function1();
        //function2();
        function3();
    }

    /**
     * Java Concurrent 프로그래밍
     * Concurrent 소프트웨어 -> 동시에 여러 작업을 할 수 있는 소프트웨어
     * 자바에서 지원하는 Concurrent Programming -> 멀티프로세싱 (ProcessBuilder), 멀티프로세서
     * 쓰레드의 순서는 보장할 수 없음, 코드상 뒤에 실행한 경우가 더 빨리 실행될 수 있음
     */
    public static void function1() {
        //쓰레드를 만드는 방법
        //1.Thread 상속받은 새로운 클래스에서 필요한 함수들 오버라이드
        MyThread myThread = new MyThread();
        myThread.start();

        //2.Runnable 구현 또는 람다
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000L);    //thread 대기 -> 다른 thread 에 우선권이 감
            } catch (InterruptedException e) {  //자는 동안에 누군가가 thread 를 깨우면 catch
                e.printStackTrace();
            }
            System.out.println("Thread: " + Thread.currentThread().getName());
        });
        thread.start();

        //현재 쓰레드 -> main thread
        System.out.println("Main: " + Thread.currentThread().getName());
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread: " + Thread.currentThread().getName());
        }
    }

    /**
     * Java Concurrent 프로그래밍
     * interrupt
     */
    public static void function2() throws InterruptedException {

        //Interrupt
        Thread thread = new Thread(() ->{
            while(true) {
                System.out.println("Thread: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000L);    //해당 thread 1초
                } catch (InterruptedException e) {
                    System.out.println("exit");
                    return;
                }
            }
        });
        thread.start();

        System.out.println("Main: " + Thread.currentThread().getName());
        Thread.sleep(3000L);    //해당 thread -> 현재는 메인
        thread.interrupt();
    }

    /**
     * Java Concurrent 프로그래밍
     * Join
     */
    public static void function3() throws InterruptedException {

        //Join
        Thread thread = new Thread(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000L);
                System.out.println("wake!!");
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });
        thread.start();

        System.out.println("Main: " + Thread.currentThread().getName());
        thread.join();  //해당 thread가 끝날때 까지 기다림
        System.out.println(thread + "is finished");
    }

    //2개 이상의 thread 만 해도 코드가 많아지는데 수십, 수백개의 thread 를 개발자가 관리할 수 없음
    //따라서 Excutors 가 나오게 됨
}
