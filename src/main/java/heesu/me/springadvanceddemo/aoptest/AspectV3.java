package heesu.me.springadvanceddemo.aoptest;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
//@Component
public class AspectV3 {

    @Pointcut("execution(* heesu.me.springadvanceddemo.aoptest..*(..))")
    private void allInAoptestPackage(){} // pointcut signature

    // 클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService(){}

    @Around("allInAoptestPackage()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());

        return joinPoint.proceed();
    }

    // ~Service 클래스에만 적용
    @Around("allInAoptestPackage() && allService()")
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
