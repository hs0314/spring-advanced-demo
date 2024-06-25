package heesu.me.springadvanceddemo.trace.logtrace;

import heesu.me.springadvanceddemo.trace.TraceId;
import heesu.me.springadvanceddemo.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * 기존 HelloTraceV2에서 trace 생성 시, 파라미터를 넘기는 부분을 없애도록 개선
 */
@Slf4j
public class ThreadLocalLogTrace implements LogTrace {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    // traceId를 파라미터로 받지 않고 사용하도록 개선
    // ThreadLocal을 사용해서 동시성 이슈 해결
    // 주의사항
    //  * 톰캣같이 스레드풀에서 스레드를 가져와서 요청처리하는 경우, ThreadLocal을 remove()하지 않으면
    //    요청처리 완료 후, 풀에 스레드 반환할때도 데이터가 ThreadLocal에 살아있어서
    //    이후 요청에 영향을 줄 수 있음 (remove 필수)
    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        this.syncTraceId();
        TraceId traceId = traceIdHolder.get();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), this.addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }


    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), this.addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), this.addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }

        this.releaseTraceId();
    }

    private void syncTraceId() {
        TraceId traceId = traceIdHolder.get();

        if (traceId == null) {
            traceIdHolder.set(new TraceId());
        } else {
            traceIdHolder.set(traceId.createNextId());
        }
    }

    private void releaseTraceId() {
        TraceId traceId = traceIdHolder.get();

        if (traceId.isFirstLevel()) {
            traceIdHolder.remove();
        } else {
            traceIdHolder.set(traceId.createPreviousId());
        }
    }

    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
