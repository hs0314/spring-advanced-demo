package heesu.me.springadvanceddemo.trace.v5;

import heesu.me.springadvanceddemo.trace.callback.TraceTemplate;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 템플릿콜백(전략패턴) 적용
 */
@RestController
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate template;

    public OrderControllerV5(OrderServiceV5 orderService, LogTrace trace) {
        this.orderService = orderService;
        // TraceTemplate 자체를 스프링 빈으로 등록해서 DI 받아도 됌
        this.template = new TraceTemplate(trace);
    }

    @GetMapping("/v5/request")
    public String request(String itemId) {

        return template.execute(
                "OrderController.request()",
                () -> {
                    orderService.orderitem(itemId);
                    return "ok";
                });

    }
}
