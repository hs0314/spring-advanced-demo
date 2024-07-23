package heesu.me.springadvanceddemo.proxyfactory.advice;

import heesu.me.springadvanceddemo.trace.TraceStatus;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.Method;

@RequiredArgsConstructor
public class LogTraceAdvice implements MethodInterceptor {

    private final LogTrace logTrace;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 호출 메서드 이름 필터
        /*
        String methodName = method.getName();
        if (!PatternMatchUtils.simpleMatch(patterns, methodName)) {
            return method.invoke(target, args);
        }*/

        TraceStatus status = null;

        try {
            Method method = invocation.getMethod();
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            status = logTrace.begin(message);

            //target call
            Object result = invocation.proceed();

            logTrace.end(status);

            return result;
        } catch(Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
