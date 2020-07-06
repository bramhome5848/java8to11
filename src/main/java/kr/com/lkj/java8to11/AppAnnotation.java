package kr.com.lkj.java8to11;

import java.util.Arrays;

@Chicken("양념")
@Chicken("마늘간장")
public class AppAnnotation {

    /**
     * 애노테이션 관련 두가지 큰 변화
     * - 자바 8부터 애노테이션을 타입 선언부에도 사용할 수 있게 됨.
     * - 자바 8부터 애노테이션을 중복해서 사용할 수 있게 됨.
     */
    public static void main(String[] args) {
        //function1();
        function2();
    }

    //type 형태로 사용할 경우
    /*
    public static void function1() throws @Chicken() RuntimeException {
        //List<@Chicken String> names = Arrays.asList("lkj");
    }

    public static class FeelsLikeChicken<@Chicken T> {

        //<C> -> Type Parameter
        //C -> Type
        public static <@Chicken C> void print(C c) {
            System.out.println(c);
        }
    }*/

    public static void function2() {
        //class에서 annotation 타입으로 읽어오는 방법
        Chicken[] chickens = AppAnnotation.class.getAnnotationsByType(Chicken.class);
        Arrays.stream(chickens)
                .forEach((c) -> System.out.println(c.value()));

        //Container 타입으로 가져오는 방법
        ChickenContainer chickenContainer = AppAnnotation.class.getAnnotation(ChickenContainer.class);
        Arrays.stream(chickenContainer.value()).forEach(c -> {
                    System.out.println(c.value());
                });
    }

}
