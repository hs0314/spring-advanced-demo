package heesu.me.springadvanceddemo.proxy.concrete_proxy;

import heesu.me.springadvanceddemo.proxy.OrderRepository;
import heesu.me.springadvanceddemo.trace.TraceStatus;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryConcreteProxy extends OrderRepository {

    private final OrderRepository target;
    private final LogTrace logTrace;


    // 구체클래스 상속을 이용해서 타겟(orderRepository)로직을 override한 프록시 로직 적용 가능
    @Override
    public void save(String itemId) {

        TraceStatus status = null;

        try {
            status = logTrace.begin("OrderRepository.save()");

            //target call
            target.save(itemId);

            logTrace.end(status);
        } catch(Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
