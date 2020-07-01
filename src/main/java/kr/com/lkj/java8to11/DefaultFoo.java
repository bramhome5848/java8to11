package kr.com.lkj.java8to11;

/**
 * 만약 서로 다른 인터페이스에 같은 이름의 default함수가 있는 경우
 * 두 인터페이스를 구현하는 클래스는 어떻게 될까? -> 어떤 함수를 실행할지 명학하지 않기 때문에 컴파일 오류
 * 충돌하는 default method가 있는 경우는 직접 오버라이딩 해야 문제를 해결할 수 있음
 */
public class DefaultFoo implements Foo, CompareFoo {

    String name = "defaultFoo";

    public DefaultFoo(String name) {
        this.name = name;
    }

    //interface 에서 정의된 default 함수도 재정의 가능
    @Override
    public void printNameUpperCase() {
        System.out.println(this.name.toUpperCase());
    }

    @Override
    public void printName() {
        System.out.println(this.name);
    }

    @Override
    public String getName() {
        return this.name;
    }


}
