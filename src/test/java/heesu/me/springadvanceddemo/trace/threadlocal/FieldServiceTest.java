package heesu.me.springadvanceddemo.trace.threadlocal;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

// 동시성 이슈 재현용 테스트
// 여러 스레드가 동시에 공유자원의 값을 변경하면서 발생(스프링 빈 같은 싱글톤 객체 조심)
// ThreadLocal을 사용해서 해결 (ThreadLocalServiceTest 참고)
@Slf4j
public class FieldServiceTest {

    private FieldService fieldService = new FieldService();

    @Test
    void test(){
        log.info("main start");

        Runnable userA = () -> {
            fieldService.logic("userA");
        };

        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");

        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        this.sleep(100);
        threadB.start();

        this.sleep(3000);
        log.info("main exit");
    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
