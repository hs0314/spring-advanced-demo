package heesu.me.springadvanceddemo.postprocessor;

import heesu.me.springadvanceddemo.proxyfactory.advice.LogTraceAdvice;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
//@Configuration
public class BeanPostProcessorConfig {

    private static final String[] PATTERNS = {"request*", "order*", "save*"};

    // 빈 후처리기를 통해서 등록된 빈을 프록시객체로 바꿔쳐서 등록하기 때문에 컨피그쪽에 프록시 생성 소스가 없음
    // 빈 수등등록뿐 아니라 컴포넌트 스캔을 통해서 등록되는 빈에 대해서도 프록시 적용 가능
    // 하지만 모든 빈을 대상으로 프록시 생성을 할 필요가 없기 때문에, 포인트컷을 이용
    //@Bean
    public LogTracePostProcessor logTracePostProcessor(LogTrace logTrace) {
        return new LogTracePostProcessor(this.getAdvisor(logTrace));
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames(PATTERNS);

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
