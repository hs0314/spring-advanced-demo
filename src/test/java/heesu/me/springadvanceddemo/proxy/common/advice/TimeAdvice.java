package heesu.me.springadvanceddemo.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    // spring proxy factory에서 target 객체를 가지고 있어서 여기서 넣어줄 필요 없음

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy call");
        long startTime = System.currentTimeMillis();

        // target 클래스 호출
        // target에 대한 정보는 invocation에 담겨있음
        Object result = invocation.proceed();

        long endTime = System.currentTimeMillis();

        log.info("TimeProxy end. resultTime={}", endTime-startTime);
        return result;
    }
}
