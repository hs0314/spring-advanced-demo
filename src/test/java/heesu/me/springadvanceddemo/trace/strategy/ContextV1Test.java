package heesu.me.springadvanceddemo.trace.strategy;

import heesu.me.springadvanceddemo.trace.strategy.code.ContextV1;
import heesu.me.springadvanceddemo.trace.strategy.code.Strategy;
import heesu.me.springadvanceddemo.trace.strategy.code.StrategyLogic1;
import heesu.me.springadvanceddemo.trace.strategy.code.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {


    @Test
    void strategyV0(){
        logic1();
        logic2();
    }

    // 전략패턴 사용
    @Test
    void strategyV1(){
        StrategyLogic1 strategy1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategy1);
        context1.execute();

        StrategyLogic2 strategy2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategy2);
        context2.execute();
    }

    // 익명 내부클래스 사용
    @Test
    void strategyV2(){
        Strategy strategy1 = new Strategy(){
            @Override
            public void call() {
                log.info("logic 1 call");
            }
        };

        ContextV1 context = new ContextV1(strategy1);
        context.execute();
    }

    // 람다 사용
    @Test
    void strategyV3(){

        ContextV1 context1 = new ContextV1(() -> log.info("logic1"));
        context1.execute();

        ContextV1 context2 = new ContextV1(() -> log.info("logic2"));
        context2.execute();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();

        //비즈니스로직 실행
        log.info("logic1 working..");
        //비즈니스로직 종료

        long endTime = System.currentTimeMillis();
        log.info("resultTime {}", endTime - startTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();

        //비즈니스로직 실행
        log.info("logic2 working..");
        //비즈니스로직 종료

        long endTime = System.currentTimeMillis();
        log.info("resultTime {}", endTime - startTime);
    }
}
