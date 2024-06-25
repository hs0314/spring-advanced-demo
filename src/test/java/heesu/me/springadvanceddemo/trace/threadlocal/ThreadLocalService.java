package heesu.me.springadvanceddemo.trace.threadlocal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalService {

    // ThreadLocal 사용 완료 이후, remove()를 통해서 제거 필수
    private ThreadLocal<String> nameStore = new ThreadLocal<>();

    public String logic(String name){
        log.info("save name:{} -> nameStore:{}", name, nameStore.get());
        nameStore.set(name);
        this.sleep(500);
        log.info("조회 nameStore:{}", nameStore.get());

        return nameStore.get();
    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
