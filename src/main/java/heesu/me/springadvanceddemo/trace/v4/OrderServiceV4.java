package heesu.me.springadvanceddemo.trace.v4;

import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import heesu.me.springadvanceddemo.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {

    private final OrderRepositoryV4 orderRepositoryV4;
    private final LogTrace trace;

    public void orderitem(String itemId){

        AbstractTemplate<Void> template = new AbstractTemplate<Void>(trace) {
            @Override
            protected Void call() {
                orderRepositoryV4.save(itemId);
                return null;
            }
        };

        template.execute("OrderService.orderitem()");

    }
}
