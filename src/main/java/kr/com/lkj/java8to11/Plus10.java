package kr.com.lkj.java8to11;

import java.util.function.Function;

//<입력타입, 리턴타입>
public class Plus10 implements Function<Integer, Integer> {
    @Override
    public Integer apply(Integer integer) {
        return integer + 10;
    }
}
