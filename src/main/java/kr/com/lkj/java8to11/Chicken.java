package kr.com.lkj.java8to11;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)    //언제까지 이 어노테이션 정보를 유지할 것인지
@Target(ElementType.TYPE_USE)   //이 어노테이션을 사용할 곳
/**
 * TYPE_PARAMETER: 타입 변수에만 사용할 수 있다.
 * TYPE_USE: 타입 변수를 포함해서 모든 타입 선언부에 사용할 수 있다.
 */
@Repeatable(ChickenContainer.class)
public @interface Chicken {
    String value();
}
