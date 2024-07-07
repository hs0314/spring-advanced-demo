package heesu.me.springadvanceddemo.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {

    public void execute() {
        long startTime = System.currentTimeMillis();

        //비즈니스로직 실행
        this.call();
        //비즈니스로직 종료

        long endTime = System.currentTimeMillis();
        log.info("resultTime {}", endTime - startTime);
    }

    protected abstract void call();
}
