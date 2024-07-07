package heesu.me.springadvanceddemo.trace.template;

import heesu.me.springadvanceddemo.trace.TraceStatus;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

// 템플릿 메서드 패턴으로 변하는 것, 변하지 않는 것을 분리
// abstract method로 템플릿 분리
// SRP, OCP 만족 (변경지점을 한쪽으로 몰아놓음)
// 자식 클래스 입장에서는 부모클래스를 의존하기 떄문에부모클래스의 변화에 영향을 받을 수 있음 (이를 개선하기 위한 전략패턴이 있음)
@RequiredArgsConstructor
public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public T execute(String message){
        TraceStatus status = null;

        try {
            status = trace.begin(message);

            // logic call
            T result = this.call();

            trace.end(status);
            return result;

        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call();
}
