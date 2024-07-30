package heesu.me.springadvanceddemo.aoptest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class TestRepository {

    public void test(){
        log.info("testRepository 실행");
    }

    public void testException(){
        log.info("testRepository testException 실행");
        throw new IllegalStateException("exception 발생");
    }
}
