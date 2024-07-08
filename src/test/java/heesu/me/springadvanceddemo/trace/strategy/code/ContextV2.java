package heesu.me.springadvanceddemo.trace.strategy.code;

import lombok.extern.slf4j.Slf4j;

// 전략을 필드로 가지지 않고 파라미터로 전달 받는 방식
@Slf4j
public class ContextV2 {

    public void execute(Strategy strategy){
        long startTime = System.currentTimeMillis();

        strategy.call();

        long endTime = System.currentTimeMillis();

        log.info("resultTime={}", endTime - startTime);
    }
}
