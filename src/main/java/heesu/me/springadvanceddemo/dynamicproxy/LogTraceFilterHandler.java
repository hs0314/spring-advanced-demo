package heesu.me.springadvanceddemo.dynamicproxy;

import heesu.me.springadvanceddemo.trace.TraceStatus;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// LogTraceBasicHandler + 특정 메서드명에 대해서만 로그를 남길 수 있도록 필터 기능 추가
public class LogTraceFilterHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns;

    public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
        this.target = target;
        this.logTrace = logTrace;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 호출 메서드 이름 필터
        String methodName = method.getName();
        if (!PatternMatchUtils.simpleMatch(patterns, methodName)) {
            return method.invoke(target, args);
        }

        TraceStatus status = null;

        try {
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            status = logTrace.begin(message);

            //target call
            Object result = method.invoke(target, args);

            logTrace.end(status);

            return result;
        } catch(Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
