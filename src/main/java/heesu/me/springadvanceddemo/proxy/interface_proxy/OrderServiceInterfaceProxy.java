package heesu.me.springadvanceddemo.proxy.interface_proxy;

import heesu.me.springadvanceddemo.proxy.OrderRepository;
import heesu.me.springadvanceddemo.proxy.OrderService;
import heesu.me.springadvanceddemo.trace.TraceStatus;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderServiceInterfaceProxy implements OrderService {

    private final OrderService target; // proxy 객체 처리에서 실제 호출 target 접근용
    private final LogTrace logTrace;

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;

        try {
            status = logTrace.begin("OrderService.orderItem()");

            //target call
            target.orderItem(itemId);

            logTrace.end(status);
        } catch(Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
