package kr.com.lkj.java8to11;

@FunctionalInterface
public interface RunSomething {

    //추상 메서드가 딱 하나만 있으면 -> 함수형 인터페이스, 나머지 static, default수는 상관없이
    //2개가 있으면 -> 함수형 인터페이스 X
    //void doIt();
    int doIt(int number);    //abstract 키워드 생략 가능

    //인터페이스에 static 메서드 사용가능
    static void printName() {
        System.out.println("LKJ");
    }

    //default메서드 정의 가능
    default void printAge() {
        System.out.println("33?");
    }
}
