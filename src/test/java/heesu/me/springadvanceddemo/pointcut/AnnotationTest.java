package heesu.me.springadvanceddemo.pointcut;

import heesu.me.springadvanceddemo.member.MemberService;
import heesu.me.springadvanceddemo.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.lang.reflect.Method;

@Slf4j
@SpringJUnitConfig(AnnotationTest.Config.class)
@EnableAspectJAutoProxy
public class AnnotationTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService proxy:{}", memberService.getClass());
        memberService.hello("helloA");
    }

    static class Config {
        @Bean
        public MemberService memberService() {
            return new MemberServiceImpl();
        }
        @Bean
        public AnnotationAspect annotationAspect() {
            return new AnnotationAspect();
        }
    }

    // 특정 어노테이션이 붇은 메서드에 AOP 적용
    @Slf4j
    @Aspect
    static class AnnotationAspect {
        @Around("@annotation(heesu.me.springadvanceddemo.member.annotation.MethodAop)")
        public Object doAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@annotion] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
