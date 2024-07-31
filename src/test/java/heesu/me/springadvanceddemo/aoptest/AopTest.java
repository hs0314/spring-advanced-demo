package heesu.me.springadvanceddemo.aoptest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

/*
스프링은 프록시 방식의 AOP를 사용하고 AspectJ가 제공하는 어노테이션, 인터페이스를 사용하긴하나
AspectJ제공 컴파일, 로드타임 위버를 사용하지는 않음
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AopTest {

    @Autowired
    TestService testService;

    @Autowired
    TestRepository testRepository;

    @Test
    void aopInfo() {
        log.info("isAopProxy, testService:{}", AopUtils.isAopProxy(testService));
        log.info("isAopProxy, testRepository:{}", AopUtils.isAopProxy(testRepository));
    }

    @Test
    void success() {
        testService.test();
    }

    @Test
    void exception() {

        assertThrows(IllegalStateException.class, () -> {
            testService.testException();
        });
    }

    @Configuration
    @EnableAspectJAutoProxy
    @ComponentScan(basePackages = "heesu.me.springadvanceddemo.aoptest")
    static class TestConfig {

    }
}
