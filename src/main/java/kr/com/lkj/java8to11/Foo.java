package kr.com.lkj.java8to11;

public interface Foo {

    void printName();   //초기

    //void prinNameUpperCase();   //추가된 내역 -> 실제 구현된 클래스가 깨짐..

    /**
     * @implSpec 이 구현체는 getName()으로 가져온 문자열을 대문자로 바꿔 출력함
     */
    default void printNameUpperCase() {
        System.out.println(getName().toUpperCase());
    }

    /**
     * Object가 제공하는 기능 (equals, hasCode)는 기본 메소드로 제공할 수 없음
     * 구현체가 재정의해야 함
     * 본인이 수정할 수 있는 인터페이스에만 기본 메소드를 제공할 수 있음
     * 인터페이스를 상속받는 인터페이스에서 다시 추상 메소드로 변경할 수 있음
     * 인터페이스 구현체가 재정의 할 수도 있음
     */
    //default String toString() {}    //Default method 'toString' overrides a member of 'java.lang.Object'

    String getName();

    /**
     * 스태틱 메소드
     * 해당 타입 관련 헬터 또는 유틸리티 메소드를 제공할 때 인터페이스에 스태틱 메소드를 제공할 수 있음
     */
    static void printAnything() {
        System.out.println("Foo");
    }

}
