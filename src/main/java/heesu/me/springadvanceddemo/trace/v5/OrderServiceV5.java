package heesu.me.springadvanceddemo.trace.v5;

import heesu.me.springadvanceddemo.trace.callback.TraceTemplate;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepositoryV5;
    private final TraceTemplate template;

    public OrderServiceV5(OrderRepositoryV5 orderRepositoryV5, LogTrace trace) {
        this.orderRepositoryV5 = orderRepositoryV5;
        this.template = new TraceTemplate(trace);
    }

    public void orderitem(String itemId){

        template.execute(
                "OrderService.orderitem()",
                () -> {
                    orderRepositoryV5.save(itemId);
                    return null;
                }
        );
    }
}
