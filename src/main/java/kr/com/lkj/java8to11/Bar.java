package kr.com.lkj.java8to11;

public interface Bar extends Foo {  //인터페이스 상속

    //Foo에서 제공하는 기본 메소드를 제공하고 싶지 않은 경우
    //다시 추상메서드로 선언해주면 됨
    void printNameUpperCase();
}
