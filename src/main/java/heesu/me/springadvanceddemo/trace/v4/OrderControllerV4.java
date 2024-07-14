package heesu.me.springadvanceddemo.trace.v4;

import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import heesu.me.springadvanceddemo.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 템플릿메서드 패턴 적용
 */
@RestController
@RequiredArgsConstructor
public class OrderControllerV4 {

    private final OrderServiceV4 orderService;
    private final LogTrace trace;

    @GetMapping("/v4/request")
    public String request(String itemId) {

        AbstractTemplate<String> template = new AbstractTemplate<>(trace) {
            @Override
            protected String call() {
                orderService.orderitem(itemId);
                return "ok";
            }
        };

        return template.execute("OrderController.request()");

    }
}
