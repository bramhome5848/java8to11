package kr.com.lkj.java8to11;

import java.lang.annotation.*;

/**
 * 중복 사용할 수 있는 애노테이션을 만들기
 * - 중복 사용할 애노테이션 만들기
 * - 중복 애노테이션 컨테이너 만들기
 * -- 컨테이너 애노테이션은 중복 애노테이션과 @Retention 및 @Target이 같거나 더 넓어야 함
 */
@Retention(RetentionPolicy.RUNTIME)    //언제까지 이 어노테이션 정보를 유지할 것인지
@Target(ElementType.TYPE_USE)   //이 어노테이션을 사용할 곳)
public @interface ChickenContainer {

    //자신이 감싸고 있어야할 Annotaion 배열을 가지고 있으면 됨
    Chicken[] value();
}
