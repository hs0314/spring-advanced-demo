package heesu.me.springadvanceddemo.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TraceStatus {

    private TraceId traceId;
    private Long startTimeMs;
    private String message;
}
