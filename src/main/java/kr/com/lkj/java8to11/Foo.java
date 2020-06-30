package kr.com.lkj.java8to11;

public class Foo {

    public static void main(String[] args) {
        //익명 내부 클래스 anonymous inner class
        /*
        RunSomething runSomething = new RunSomething() {
            @Override
            public void doIt() {
                System.out.println("Hello");
            }
        };
        */

        //함수형 인터페이스 -> 람다식 변형
        //함수가 특수한 형태의 오브젝트라고 생각하면 됨
        /*
        RunSomething runSomething = () -> System.out.println("Hello");
        runSomething.doIt();    //함수 실행
        */

        int baseNumber  = 10;

        RunSomething runSomething = new RunSomething() {
            @Override
            public int doIt(int number) {
                return number + baseNumber; //외부 변수는 final로 인식하고 사용 -> 변경 불가
            }
        };

        System.out.println(runSomething.doIt(10));
    }
}
