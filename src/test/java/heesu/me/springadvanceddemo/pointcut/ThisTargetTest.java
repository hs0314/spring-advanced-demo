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
@SpringJUnitConfig(ThisTargetTest.Config.class)
@EnableAspectJAutoProxy(proxyTargetClass = true) // true인 경우 프록시 생성을 CGLIB로, false이면 JDK 동적프록시 사용
public class ThisTargetTest {

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
        public ThieTargetAspect thisTargetAspect() {
            return new ThieTargetAspect();
        }
    }

    // Springboot에서는 프록시 생성을 CGLIB를 통해서 생성하고 CGLIB는 구체클래스를 상속받아서 프록시를 생성하기 때문에
    // 아래 4가지 케이스에 대해서 this(스프링 컨테이너에 빈 등록된 프록시), target(실제 로직)기준으로 인터페이스, 구체클래스에 대해서 모두 AOP적용이 가능함
    @Slf4j
    @Aspect
    static class ThieTargetAspect {

        @Around("this(heesu.me.springadvanceddemo.member.MemberService)")
        private Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("this(heesu.me.springadvanceddemo.member.MemberServiceImpl)")
        private Object doThisConcrete(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-concrete] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(heesu.me.springadvanceddemo.member.MemberService)")
        private Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("this(heesu.me.springadvanceddemo.member.MemberServiceImpl)")
        private Object doTargetConcrete(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-concrete] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
