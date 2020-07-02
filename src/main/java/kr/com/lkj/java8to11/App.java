package kr.com.lkj.java8to11;

import java.time.Duration;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) {
        //unction1();
        //function2();
        //function3();
        //function4();
        //function5();
        //function6();
        //function7();
        //function8();
        //function9();
        function10();
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

    /**
     * 인터페이스 기본 메소드와 스태틱 메소드
     * 기본 메소드 (Default Methods)
     * 인터페이스에 메소드 선언이 아니라 구현체를 제공하는 방법
     * 해당 인터페이스를 구현한 클래스를 깨트리지 않고 새 기능을 추가할 수 있음
     * 기본 메소드는 구현체가 모르게 추가된 기능으로 그만큼 리스크가 있음
     */
    public static void function6() {
        Foo foo = new DefaultFoo("KyungJin");
        foo.printName();
        foo.printNameUpperCase();

        //static
        Foo.printAnything();
    }

    /**
     * 자바 8 API의 기본 메소드와 스태틱 메소드
     */
    public static void function7() {

        List<String> name = new ArrayList<>();
        name.add("lkj");
        name.add("lkb");
        name.add("lks");
        name.add("lbj");

        //forEach()
        //action을 등록하면 {}, 내부에서 for문을 수행하며 action수행
        /*
        name.forEach((s) ->{
            System.out.println(s);
        });
        */
        name.forEach(System.out::println);

        //spliterator()
        //쪼갤 수 있는 기능을 가진 iterator

        Spliterator<String> spliterator = name.spliterator();

        /*
        while(spliterator.tryAdvance((s) -> {
            System.out.println(s);
        }));*/

        //단순 스플릿 사용시 유용
        Spliterator<String> spliterator1 = spliterator.trySplit();

        while(spliterator.tryAdvance(System.out::println));
        System.out.println("====================");
        while(spliterator1.tryAdvance(System.out::println));

        //stream()
        long k = name.stream().map(String::toUpperCase)
                .filter((s) -> s.startsWith("L"))
                .count();

        System.out.println("k = " + k);

        //removeIf()
        name.removeIf((s)-> s.startsWith("L"));
        name.forEach(System.out::println);

        //comparator
        //paramter type String
        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
        name.sort(compareToIgnoreCase.reversed());  //역정렬
        name.forEach(System.out::println);


        //함수형 인터페이스 사용시 해당 인터페이스의 실행 함수의 형태를 통해 람다식 구성(parameter), 리턴 타입 등등
        //리턴 타입의 경우 함수형 인터페이스 마다 다 다름 -> 사용자가 지정할 수도 있고 함수 내에서 정해진 경우도 있고
        //따라서 확인한 이후에 람다식을 작성할 수 있도록 하는 것이 효과적
    }

    /**
     * Stream Api 소개
     * 연속된 데이터를 처리하는 오퍼레이션들의 모음
     * stream 자체가 데이터가 아님
     *
     */
    public static void function8() {
        List<String> names = new ArrayList<>();
        names.add("lkj");
        names.add("lkb");
        names.add("lks");
        names.add("lbj");

        //스트림이 처리하는 데이터 소스를 변경하지 않는다.
        //stream 처리는 또다른 stream
        //스트림으로 처리하는 데이터는 오직 한번만 처리한다. -> 컨베이어 벨트에서 슥 지나감
        List<String> collect = names.stream().map((s) -> {
            System.out.println(s);
            return s.toUpperCase();
        }).collect(Collectors.toList());

        collect.forEach(System.out::println);

        //기존 데이터 확인 -> 기존의 데이터가 변경되지 않음
        System.out.println("==================");
        names.forEach(System.out::println);

        /**
         * 스트림 파이프라인
         * - 0 또는 다수의 중개 오퍼레이션과 한개의 종료 오퍼레이션으로 구성
         * 중계 오퍼레이션
         * - stream 리턴 O
         * - lazy 함 -> 터미널 오퍼레이션을 실행할 때에만 처리한다.
         * - 위의 map에 선언된 함수 내용은 종료 오퍼레이션이 없는 경우 실행 X
         * - 뒤에 collect와 같은 종료 오퍼레이션이 붙고 리턴형이 stream이 아니면 실행
         * 종료 오퍼레이션 : stream 리턴 X
         */

        //병렬적 처리 -> 각각을 처리하는 thread 가 다름
        //parallelStream 쓴다고 무조건 빠른건 아님
        //멀티 thread 를 만들고 처리하는 비용, 처리 된 것을 비용
        //thread 간의 context switch 비용 등등
        //유용한 경우 -> 데이터가 정말 방대한 경우
        List<String> collect1 = names.parallelStream().map((s) -> {
            System.out.println(s + " " + Thread.currentThread().getName());
            return s.toUpperCase();
        }).collect(Collectors.toList());

        collect1.forEach(System.out::println);
    }

    /**
     * Stream Api 사용
     */
    public static void function9() {

        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        List<OnlineClass> javaClasses = new ArrayList<>();
        javaClasses.add(new OnlineClass(6, "The Java, Test", true));
        javaClasses.add(new OnlineClass(7, "The Java, Code manipulation", true));
        javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));

        List<List<OnlineClass>> lkjEvents = new ArrayList<>();
        lkjEvents.add(springClasses);
        lkjEvents.add(javaClasses);

        System.out.println("===========================================");
        System.out.println("spring 으로 시작하는 수업");

        springClasses.stream()
                .filter((oc) -> oc.getTitle().startsWith("spring"))
                .forEach((oc) -> System.out.println(oc.getId()));

        System.out.println("===========================================");
        System.out.println("close 되지 않은 수업");

        springClasses.stream()
                .filter((oc) -> !oc.isClosed())
                //.filter(Predicate.not(OnlineClasses::isClosed))   //java 11부터 Predicate not 사용 가능..
                .forEach((oc) -> System.out.println(oc.getId()));

        System.out.println("===========================================");
        System.out.println("수업만 이름으로 모아서 스트림 만들기");

        springClasses.stream()
                .map(OnlineClass::getTitle)  // map은 요소들을 특정조건에 해당하는 값으로 변환
                .forEach(System.out::println);

        System.out.println("===========================================");
        System.out.println("두 수업 목록에 들어 있는 모든 수업 아이디 출력");

        //flat -> stream속에 존재하는 리스트들을 풀어서 모든 요소들을 평평하게..
        lkjEvents.stream()
                .flatMap(Collection::stream)
                .forEach((oc) -> System.out.println(oc.getId()));

        System.out.println("===========================================");
        System.out.println("10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개까지만");

        Stream.iterate(10, (i) -> i+1)
                .skip(10)   //10개 스킵
                .limit(10)
                .forEach(System.out::println);

        System.out.println("===========================================");
        System.out.println("자바 수업 중에 Test가 들어 있는 수업이 있는지 확인");

        boolean test = javaClasses.stream()
                .anyMatch((oc) -> oc.getTitle().contains("Test"));
        System.out.println(test);

        System.out.println("===========================================");
        System.out.println("스프링 수업 중에 제목에 spring이 들어간 제목만 모아서 List로 만들기");
        springClasses.stream()
                .filter((oc) -> oc.getTitle().contains("spring"))
                .map(OnlineClass::getTitle)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    /**
     * Optional 소개
     */
    public static void function10() {

        OnlineClass spring_boot = new OnlineClass(1, "spring boot", true);

        //에러를 만들기 좋은 코드
        //1. 사람이 하는 일이기 때문에 null Check를 잊는 경우가 많음
        //2. null 자체를 리턴 하는 것이 문제
        /*
        Progress progress = spring_boot.getProgress();
        if(progress !=null) {
            System.out.println(progress.getStudyDuration());
        }
        */
        Optional<Progress> progress = spring_boot.getProgress();
        progress.ifPresent((p)-> System.out.println(p.getStudyDuration()));
    }
}
