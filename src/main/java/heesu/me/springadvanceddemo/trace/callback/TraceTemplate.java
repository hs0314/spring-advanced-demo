package heesu.me.springadvanceddemo.trace.callback;

import heesu.me.springadvanceddemo.trace.TraceStatus;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// 템플릿콜백 패턴 적용(=전략패턴)
@RequiredArgsConstructor
@Component
public class TraceTemplate {
    private final LogTrace trace;

    public <T> T execute(String message, TraceCallback<T> callback){
        TraceStatus status = null;

        try {
            status = trace.begin(message);

            //logic call
            T result = callback.call();

            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
