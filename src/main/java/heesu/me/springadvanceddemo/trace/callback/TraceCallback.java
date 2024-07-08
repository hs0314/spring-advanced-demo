package heesu.me.springadvanceddemo.trace.callback;

public interface TraceCallback<T> {
    T call();
}
