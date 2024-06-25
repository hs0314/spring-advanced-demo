package heesu.me.springadvanceddemo.trace.logtrace;

import heesu.me.springadvanceddemo.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
