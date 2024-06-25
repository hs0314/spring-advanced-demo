package heesu.me.springadvanceddemo.trace.logtrace;

import heesu.me.springadvanceddemo.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class ThreadLocalLogTraceTest {

    ThreadLocalLogTrace trace = new ThreadLocalLogTrace();

    @Test
    void begin_end_level2(){
        TraceStatus status1 = trace.begin("hi1");
        TraceStatus status2 = trace.begin("hi2");

        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }

}