package heesu.me.springadvanceddemo.aoptest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestService {

    private final TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public void test() {
        log.info("TestService 실행");

        testRepository.test();
    }

    public void testException() {
        log.info("testException 실행");

        testRepository.testException();
    }
}
