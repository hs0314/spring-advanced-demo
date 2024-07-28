package heesu.me.springadvanceddemo.autoproxy;

import heesu.me.springadvanceddemo.postprocessor.LogTracePostProcessor;
import heesu.me.springadvanceddemo.proxyfactory.advice.LogTraceAdvice;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AutoProxyConfig {

    private static final String[] PATTERNS = {"request*", "order*", "save*"};


    // aop패키지내 로직에 의해서 AnnotationAwareAspectJAutoProxyCreator(자동 빈 후처리기)가 스프링 빈으로 등록되고
    // advisor를 스프링빈으로 등록하면 자동으로 advisor내 pointcut, advice 정보를 가지고 프록시를 자동 적용
    //@Bean
    public Advisor advisor1(LogTrace logTrace) {

        //pointcut은 두 케이스에서 모두 활용
        // 1. 프록시 적용 여부 판단 (생성 단계)
        // 2. 어드바이스 적용 여부 판단 (사용 단계)
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames(PATTERNS);

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    // AspectJ exp를 사용할 수 있는 정밀한 pointcut 적용
    @Bean
    public Advisor advisor2(LogTrace logTrace) {

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* heesu.me.springadvanceddemo..*(..)) && !execution(* heesu.me.springadvanceddemo..noLog(..))");

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
