package kr.com.lkj.java8to11;

import java.util.Optional;

public class OnlineClass {

    private Integer id;

    private String title;

    private boolean closed;

    public Progress progress;

    public OnlineClass(Integer id, String title, boolean closed) {
        this.id = id;
        this.title = title;
        this.closed = closed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    /**
     * 예외를 던진다. (비싸다, Exception 발생 전에 대한 스택트레이스를 찍어두니까)
     * 로직을 처리할 때 Exception 사용하는 것은 좋은 습관은 아님
     * java8 부터는 Optional 리턴
     * (클라이언트에 코드에게 명시적으로 빈 값일 수도 있다는 걸 알려주고, 빈 값인 경우에 대한 처리를 강제함
     */
    /*
    public Progress getProgress() {
        if(this.progress == null){
            throw new IllegalStateException();
        }
        return progress;
    }
    */
    public Optional<Progress> getProgress() {
        //리턴값으로만 쓰기를 권장한다. (메소드 매개변수 타입, 맵의 키 타입, 인스턴스 필드 타입으로 쓰지 말 것)
        //Optional Api 확인 후 사용, Null 일 수 있는 값은 Nullable 사용
        //리턴할 것이 없다면 null이 아닌 Optional.empty();를 리턴
        return Optional.ofNullable(progress);
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }
}
