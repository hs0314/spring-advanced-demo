package heesu.me.springadvanceddemo.pointcut;

import heesu.me.springadvanceddemo.member.MemberService;
import heesu.me.springadvanceddemo.member.MemberServiceImpl;
import heesu.me.springadvanceddemo.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@SpringJUnitConfig(ParameterTest.Config.class)
@EnableAspectJAutoProxy
public class ParameterTest {

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
        public ParameterAspect parameterAspect() {
            return new ParameterAspect();
        }
    }

    // 특정 어노테이션이 붇은 메서드에 AOP 적용
    @Slf4j
    @Aspect
    static class ParameterAspect {

        @Pointcut("execution(* heesu.me.springadvanceddemo.member..*.*(..))")
        private void allMember() {

        }

        // MemberService의 hello()메서드의 파타미터 String값을 어떻게 가져올 것인가?
        // 방법1. ProceedingJoinPoint에서 getArgs()로 가져오기
        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1] {}, args:{}", joinPoint.getSignature(), joinPoint.getArgs());
            return joinPoint.proceed();
        }

        // 방법2. args 사용
        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, String arg) throws Throwable {
            String arg1 = arg;
            log.info("[logArgs2] {}, args:{}", joinPoint.getSignature(), arg1);
            return joinPoint.proceed();
        }

        // 방법3. @Before 사용
        @Before("allMember() && args(arg, ..)")
        public void logArgs3(String arg) throws Throwable {
            log.info("[logArgs3] args:{}", arg);
        }

        // 특정 어노테이션에서 넘긴 값 받기
        @Before("allMember() && @annotation(annotation)")
        public void annotation(JoinPoint joinPoint, MethodAop annotation) throws Throwable {
            log.info("[@annotation]{}, annotationValue:{}", joinPoint.getSignature(), annotation.value());
        }
    }
}
