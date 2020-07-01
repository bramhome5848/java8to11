package kr.com.lkj.java8to11;

public interface CompareFoo {

    default void printNameUpperCase() {
        System.out.println("Test");
    }
}
