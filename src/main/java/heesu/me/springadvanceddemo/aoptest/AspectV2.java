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
public class AspectV2 {

    // pointcut을 분리해둠으로써 하나의 표현식을 여러 advice에서 같이 사용 가능
    @Pointcut("execution(* heesu.me.springadvanceddemo.aoptest..*(..))")
    private void allInAoptestPackage(){} // pointcut signature

    @Around("allInAoptestPackage()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());

        return joinPoint.proceed();
    }
}
