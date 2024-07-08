package heesu.me.springadvanceddemo.trace.strategy;

import heesu.me.springadvanceddemo.trace.strategy.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    // 전략패턴 사용
    @Test
    void strategyV1(){
        ContextV2 contextV2 = new ContextV2();

        contextV2.execute(new StrategyLogic1());
        contextV2.execute(new StrategyLogic2());
    }

    // 람다 사용
    @Test
    void strategyV2(){
        ContextV2 context = new ContextV2();

        context.execute(() -> log.info("logic 1 call"));
        context.execute(() -> log.info("logic 2 call"));
    }
}
