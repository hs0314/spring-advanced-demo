package heesu.me.springadvanceddemo.aoptest;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AspectV6Advice {
    /*
    @Around - target메서드 실행 전후 작업
     - target메서드를 호출하려면 꼭 ProceedingJoinPoint의 proceed()호출 필수

    아래 기능은 @Around의 기능 일부 제공 (기능 제약을 통해서 실수를 방지하고 코드 작성 의도를 명확히 할 수 있음)
    @Before - joinPoint 실행 이전 작업
    @AfterReturning - joinPoint 정상 실행 이후 작업
    @AfterThrowing - joinPoint 실행 익셉션 발생 시 작업
    @After - joinPoint 실행 익셉션 발생 여부 상관없이 작업 (일반적으로 리소스해제에 사용)
     */

    @Before("heesu.me.springadvanceddemo.aoptest.Pointcuts.combinedPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[@Before 트랜잭션 시작] {} ", joinPoint.getSignature());
    }

    // return type 매칭 주의
    @AfterReturning(
            value = "heesu.me.springadvanceddemo.aoptest.Pointcuts.combinedPointcut()",
            returning = "result"
    )
    public void doAfterReturning(JoinPoint joinPoint, Object result) throws Throwable {
        log.info("[@AfterReturning 트랜잭션 커밋] {}, return:{}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(
            value = "heesu.me.springadvanceddemo.aoptest.Pointcuts.combinedPointcut()",
            throwing = "e"
    )
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) throws Throwable {
        log.info("[@AfterThrowing 트랜잭션 롤백] {}, exception:{}", joinPoint.getSignature(), e.getMessage());
    }

    @After("heesu.me.springadvanceddemo.aoptest.Pointcuts.combinedPointcut()")
    public void doAfterReturning(JoinPoint joinPoint) throws Throwable {
        log.info("[@After 리소스 해제] {}", joinPoint.getSignature());
    }
}
