package heesu.me.springadvanceddemo.aop.aspect;

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
public class AopConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {

        return new LogTraceAspect(logTrace);
    }
}
