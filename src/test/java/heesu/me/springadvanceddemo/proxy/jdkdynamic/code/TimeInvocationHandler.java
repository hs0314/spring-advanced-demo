package heesu.me.springadvanceddemo.proxy.jdkdynamic.code;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

    private final Object target;

    public TimeInvocationHandler(Object target) {
        this.target = target;
    }

    // 동적 프록시에 적용되는 핸들러 메서드
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("TimeProxy call");
        long startTime = System.currentTimeMillis();

        // 실행로직 (동적 호출)
        Object result = method.invoke(target, args);

        long endTime = System.currentTimeMillis();

        log.info("TimeProxy end. resultTime={}", endTime-startTime);
        return null;
    }
}
