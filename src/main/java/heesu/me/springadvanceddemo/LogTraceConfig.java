package heesu.me.springadvanceddemo;

import heesu.me.springadvanceddemo.trace.logtrace.ThreadLocalLogTrace;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    // 스프링 컨테이너에 싱글톤으로 bean 등록
    @Bean
    public LogTrace logTrace(){
        return new ThreadLocalLogTrace();
    }
}
