package heesu.me.springadvanceddemo.aop.aspect;

import heesu.me.springadvanceddemo.proxyfactory.advice.LogTraceAdvice;
import heesu.me.springadvanceddemo.trace.TraceStatus;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Slf4j
// AnnotationAwareAspectJAutoProxyCreator (자동프록시 생성기)에서 @Aspect 어노테이션을 찾아서 Advisor로 만들어줌
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    // around 어노테이션에 pointcut 명시
    // advice로직은 @Around 어노테이션 붙은 메서드
    @Around("execution(* heesu.me.springadvanceddemo..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        TraceStatus status = null;

        /*
        joinPoint.getArgs() // 메서드 인자
        jjoinPoint.getTarget() // 실제 호출 대상
         */

        try {

            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            //target call
            Object result = joinPoint.proceed();

            logTrace.end(status);

            return result;
        } catch(Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
