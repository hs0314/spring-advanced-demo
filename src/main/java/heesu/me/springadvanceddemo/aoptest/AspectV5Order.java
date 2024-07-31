package heesu.me.springadvanceddemo.aoptest;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class AspectV5Order {

    // Advice 적용 순서를 변경하려면, 각 advice별 클래스 생성 + @Order() 적용으로 해결

    @Aspect
    @Order(1)
    //@Component
    public static class LogAspect {
        @Around("heesu.me.springadvanceddemo.aoptest.Pointcuts.allInAoptestPackage()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[log] {}", joinPoint.getSignature());

            return joinPoint.proceed();
        }
    }

    @Aspect
    @Order(2)
    //@Component
    public static class txAspect {

        // ~Service 클래스에만 적용
        @Around("heesu.me.springadvanceddemo.aoptest.Pointcuts.combinedPointcut()")
        public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

            try {
                log.info("[트랜잭션 시작] {} ", joinPoint.getSignature());
                Object result = joinPoint.proceed();
                log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            } catch (Exception e) {
                log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
                throw e;
            } finally {
                log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
            }
            return joinPoint.proceed();
        }
    }
}
