package heesu.me.springadvanceddemo.trace.template;

import heesu.me.springadvanceddemo.trace.template.code.AbstractTemplate;
import heesu.me.springadvanceddemo.trace.template.code.SubClassLogic1;
import heesu.me.springadvanceddemo.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {


    @Test
    void templateMethodV0(){
        logic1();
        logic2();
    }

    // 템플릿메서드 패턴 적용
    // 단점 - 서브클래스 숫자가 늘어남
    @Test
    void templateMethodV1(){
        AbstractTemplate template1 = new SubClassLogic1();
        AbstractTemplate template2 = new SubClassLogic2();
        template1.execute();
        template2.execute();
    }

    // 익명 내부클래스 사용
    @Test
    void templateMethodV2(){
        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("logic1 working..");
            }
        };
        template1.execute();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();

        //비즈니스로직 실행
        log.info("logic1 working..");
        //비즈니스로직 종료

        long endTime = System.currentTimeMillis();
        log.info("resultTime {}", endTime - startTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();

        //비즈니스로직 실행
        log.info("logic2 working..");
        //비즈니스로직 종료

        long endTime = System.currentTimeMillis();
        log.info("resultTime {}", endTime - startTime);
    }
}
