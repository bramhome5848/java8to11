package kr.com.lkj.java8to11;

import java.util.function.*;

public class Foo {

    public static void main(String[] args) {
        //unction1();
        //function2();
        function3();;
    }

    /**
     * 함수형 인터페이스 (Functional Interface)
     * 람다 표현식 (Lambda Expressions)
     */
    public static void function1() {

        //익명 내부 클래스 anonymous inner class
        //->아래와 같이 인터페이스만 정의 되어 있고 사용하려면 직접 구현체를 만들어서 사용하는 경우를 의미
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
        //함수 형태에서 파라미터 적는 모습을 그대로 -> 전에 적어주면 됨
        /*
        RunSomething runSomething = () -> System.out.println("Hello");
        runSomething.doIt();    //함수 실행
        */

        //파라미터가 있는 경우 + 외부 변수를 사용할 경우
        int baseNumber  = 10;

        RunSomething runSomething = (number) -> {
            return number + baseNumber;     //외부 변수는 final로 인식하고 사용 -> 변경 불가
        };

        System.out.println(runSomething.doIt(10));
    }

    /**
     * Function<T, R> -> T 타입을 받아서 R 타입을 리턴하는 함수 인터페이스
     */
    public static void function2() {

        //자바에서 제공하는 함수형 인터페이를 직접 구현한 클래스 선언으로 사용 가능 -> 기존의 방법
        Plus10 plus10 = new Plus10();
        System.out.println(plus10.apply(1));

        //바로 필요한 함수를 적재하여 사용
        Function<Integer, Integer> plus11 = (number) -> number+11;  //함수는 return 생략하고 줄일 수 있음
        System.out.println(plus11.apply(10));

        Function<Integer, Integer> multiply2 = (number) -> number*2;
        System.out.println(multiply2.apply(20));

        //두 개의 함수를 조합
        //고차 함수 -> 함수가 함수를 매개변수로 받을 수 있고 함수를 리턴할 수 있음
        Function<Integer, Integer> multiply2AndPlus11 = plus11.compose(multiply2);
        System.out.println(multiply2AndPlus11.apply(10));

        //andThen -> 앞의 것을 수행한 결과값을 이어서 ()의 함수를 수행
        System.out.println(plus11.andThen(multiply2).apply(10));
    }

    /**
     * Consumer<T> -> 두개의값(T,U)를 받아서 R 타입을 리턴하는 함수 인터페이스
     * Supplier<T> -> T 타입의 값을 제공하는 함수 인터페이스
     * Predicate<T> -> T 타입을 받아서 boolean 을 리턴하는 함수 인터페이스
     * UnaryOperator<T> -> Function<T, R> 의 특수한 형태로, 입력값 하나를 받아서 동일한 타입을 리턴하는 함수 인터페이스
     * BinaryOperator<T> -> BiFunction<T, U, R>의 특수한 형태로, 동일한 타입의 입렵값 두개를 받아 리턴하는 함수 인터페이스
     */
    public static void function3() {

        Consumer<Integer> printT = (i) -> System.out.println(i);
        printT.accept(10);

        Supplier<Integer> get20 = () -> 20;
        System.out.println(get20.get());

        //predicate 조합 가능 -> and, or 등등
        Predicate<String> startsWithName = (s) -> s.startsWith("KyungJin");
        System.out.println(startsWithName.test("KyungJin"));

        Predicate<Integer> isEven = (num) -> num%2 == 0;
        System.out.println(isEven.test(2));

        //입력값 타입과 리턴 타입이 같은 경우 사용
        UnaryOperator<Integer> plus30 = (num) -> num+30;
        System.out.println(plus30.apply(10));

        BinaryOperator<Integer> sum = (num1, num2) -> num1+num2;
        System.out.println(sum.apply(10, 20));
    }
}
