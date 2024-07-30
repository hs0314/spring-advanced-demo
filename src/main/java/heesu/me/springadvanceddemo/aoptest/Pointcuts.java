package heesu.me.springadvanceddemo.aoptest;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class Pointcuts {

    @Pointcut("execution(* heesu.me.springadvanceddemo.aoptest..*(..))")
    public void allInAoptestPackage(){} // pointcut signature

    // 클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    // 두개의 다른 포인트컷 조합
    @Pointcut("allInAoptestPackage() && allService()")
    public void combinedPointcut(){}
}
