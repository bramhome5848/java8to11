package kr.com.lkj.java8to11;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.*;

public class Foo {

    public static void main(String[] args) {
        //unction1();
        //function2();
        //function3();
        //function4();
        function5();
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

    /**
     * 람다 표현식
     * (인자 리스트) -> {바디} //생략 가능한 부분들이 있음
     * 바디
     * 화상표 오른쪽에 함수 본문을 정의한다.
     * 여러줄인경우에{}를사용해서묶는다.
     * 한 줄인 경우에 생략 가능, return 도 생략 가능.
     */
    public static void function4() {
        //인자가없을때:()
        Supplier<Integer> get10 = () -> 10;

        //인자가 2개 이상일때
        //type 명시도 가능
        //인자의 타입은 생략 가능, 컴파일러가 추론(infer)하지만 명시할 수도 있음
        BinaryOperator<Integer> sum = (Integer num1, Integer num2) -> num1+num2;

        //로컬 변수 캡쳐
        //final 이거나 effective final 인 경우에만 참조 가능.
        //자바 8부터 지원하는 기능으로 “사실상" final 인 변수.
        //effective final인 경우는 final 키워드 없이도 참조가 가능(로컬, 익명, 람다)
        //차이점은 쉐도잉 (로컬, 익명) -> 쉐도잉 O, 람다 -> 쉐도잉 X

        int baseNumber = 10;
        //baseNumber++; //값이 변하는 경우 effective final 로 인정되지 않기 때문에 사용X

        //로컬 클래스
        class LocalClass {
            void printBaseNumber() {
                int baseNumber = 11;
                System.out.println(baseNumber); //11 -> 뒤의 스콥이 앞의 스콥을 가려버림 -> 쉐도잉
            }
        }

        //익명 클래스
        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer baseNumber) {
                System.out.println(baseNumber); //위와 마찬가지로 쉐도잉
            }
        };

        //람다
        IntConsumer printInt = (num) -> {
            //int baseNumber = 0; //람다를 감싸고 있는 스콥과 같기 때문에 같은 이름으로 변수 선언 자체를 할 수 없음
            System.out.println(num+baseNumber); //익명 클래스는 새로 스콥을 만들지만, 람다는 람다를 감싸고 있는 스콥과 같다.

        };
    }

    /**
     * 메소드 레퍼런스
     * 기존 메소드 또는 생성자를 호출하는 거라면, 메소드 레퍼런스를 사용해서
     * 매우 간결하게 표현 가능
     */
    public static void function5() {

        //스태틱 메소드 참조
        //UnaryOperator<String> hi = (s) -> "hi " + s;
        UnaryOperator<String> hi = Greeting::hi;    //hi의 구현체로 선언되어 있던 함수를 사용
        System.out.println(hi.apply("KyungJin"));   //함수 실행

        //특정 객체의 인스턴스 메소드 참조
        Greeting greeting = new Greeting();
        UnaryOperator<String> hello = greeting::hello;  //멤버함수
        System.out.println(hello.apply("KyungJin"));    //함수 실행

        //생성자 참조
        Supplier<Greeting> makeGreeting1 = Greeting::new; //생성자(함수) 이용
        System.out.println(makeGreeting1.get());//생성자가 만들어짐

        Function<String, Greeting> makeGreeting2 = Greeting::new;   //뒤의 생성자 선언은 바로 위와 같지만 다른 생성자를 쓰게됨
        System.out.println(makeGreeting2.apply("KyungJin"));

        //임의 객체의 인스턴스 메소드 참조
        String[] names = {"Kyungjin", "LKJ", "King"};

        //2번째 파라미터가 함수형 인터페이스이기 때문에 람다를 넣을 수 있음
        //람다를 넣을 수 있다는 것은 메서드 레퍼런스를 사용할 수 있음
        Arrays.sort(names, String::compareToIgnoreCase);
        System.out.println(Arrays.toString(names));
    }
}
